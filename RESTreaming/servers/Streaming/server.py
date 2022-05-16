import sys, Ice, IceStorm, vlc, csv, shutil, os, time, threading
import Streaming
from secrets import token_hex
from pathlib import Path
from tempfile import NamedTemporaryFile

#objet représentation une musique permettant d'en stocker les infos et la manipuler facilement
class Music(Streaming.music):
    def __init__(self, t, a, fn):
        self.title = t
        self.artist = a
        self.filename = fn

#objet message envoyé par le serveur sur le topic IceStorm pour propager les actualisations aux abonnés
#id correspond à l'identifiant du client qui est à l'origine du la modification
#nr correspond au serveur qui envoie le message
#modified correspond à la musique après la mise à jour
#original correspons à la musique avant la mise à jour
class Update(Streaming.update):
    def __init__(self, id, nr, modified, original):
        self.id = id
        self.nr = nr
        self.modified = modified
        self.original = original

#objet serveur
#id correspond au numéro du serveur
#directory correspond au chemin d'accès vers les musiques
#tracklist correspond à la liste des chansons disponibles sur ce serveur
#trackBuffer correspond à l'espace de stockage pour les fragements de piste audio lors d'upload
#playing correspond à la liste des players en cours de lecture
#streams correspond au nombre de streams actuellement ouvert
#paused correspond à l'état de lecture de chaque player
#emergency correspond à l'arrêt d'urgence de la fonction de surveillance de lecture

#lors du lancement du serveur, celui-ci récupère la liste de chansons dans la base de donnée (ici un fichier csv)
class StreamingServerI(Streaming.StreamingServer):
    def __init__(self, num, dir):
        self.id = num
        self.directory = dir
        self.tracklist = []
        self.trackBuffer = {}
        self.playing = {}
        self.streams = 0
        self.paused = {}
        self.emergency = {}
        with open (dir+"/track_list.csv") as file:
            csvreader = csv.reader(file)
            next(csvreader)
            for row in csvreader:
                if len(row) > 2:
                    music = Music(row[0], row[1], row[2])
                    self.tracklist.append(music) 

    #fonction qui renvoi la liste des chansons disponibles
    def shareTracklist(self, current=None):
        return self.tracklist

    #fonction qui renvoi le nombre de stream en cours de lecture
    def getStreamNumber(self, current=None):
        return self.streams

    #fonction qui enregistre dans trackBuffer un fragment de chanson en attendant de le recevoir tous
    def transferFragment(self, ref, fragment, current=None):
        if self.trackBuffer.get(ref) == None:
            self.trackBuffer[ref] = []
        self.trackBuffer.get(ref).append(fragment)
    
    #fonction qui concatène les fragments de chanson et créer le fichier audio dans le repetoire adéquat

    #crée le dossier destination s'il n'existe pas encore
    #crée le fichier destination et y écris les fragments dans l'ordre
    #si la musique n'est pas encore répertoriée, elle est ajotué à tracklist, au csv bdd
    #la nouveauté est notifiée à tous les abonnés au topic IceStorm
    def saveTrack(self, ref, music, current=None):
        p = Path(f"{self.directory}/{music.artist}/{music.title}")
        p.mkdir(parents=True, exist_ok=True)
        with open(f"{self.directory}/{music.artist}/{music.title}/{music.filename}", "wb") as file:
            for fragment in self.trackBuffer.get(ref):
                file.write(fragment)
        self.trackBuffer.pop(ref)
        if music not in self.tracklist:
            self.tracklist.append(music)
            with open(self.directory+"/track_list.csv", "a", newline="") as file:
                csvwriter = csv.writer(file)
                csvwriter.writerow([music.title, music.artist, music.filename])
            # ~ messenger.update(Update(ref, self.id, music, Music(None, None, "?")))
            return f"Track added : {music.title} - {music.artist}"
        else: return "Track updated"

    #fonction qui modifie les informations d'une chanson

    #trouve la piste à modifier et la modifie dans tracklist
    #actualise le csv bdd et déplace le fichier dans le dossier correspondant aux nouvelles informations
    #la modification est notifiée à tous les abonnés au topic IceStorm
    def modifyTrack(self, ref, music, new, current=None):
        for track in self.tracklist:
            if track == music:
                track.title = new.title
                track.artist = new.artist
        tempFile = NamedTemporaryFile(mode="w", delete=False, newline="")
        fields = ["Title", "Artist", "Filename"]
        with open(self.directory+"/track_list.csv") as file, tempFile:
            reader = csv.DictReader(file, fieldnames = fields)
            writer = csv.DictWriter(tempFile, fieldnames = fields)
            for row in reader:
                if row["Title"] == music.title and row["Artist"] == music.artist and row["Filename"] == music.filename:
                    row["Title"] = new.title
                    row["Artist"] = new.artist
                row = {"Title": row["Title"], "Artist": row["Artist"], "Filename": row["Filename"]}
                writer.writerow(row)
        shutil.move(tempFile.name, self.directory+"/track_list.csv")
        newPath = f"{self.directory}/{new.artist}/{new.title}"
        oldPath = f"{self.directory}/{music.artist}/{music.title}"
        p = Path(newPath)
        p.mkdir(parents=True, exist_ok=True)
        shutil.move(f"{oldPath}/{music.filename}", f"{newPath}/{new.filename}")
        directory = os.listdir(oldPath)
        if len(directory) == 0:
            os.rmdir(oldPath)
        directory = os.listdir(f"{self.directory}/{music.artist}")
        if len(directory) == 0:
            os.rmdir(f"{self.directory}/{music.artist}")
        # ~ messenger.update(Update(ref, self.id, new, music))
        return "Data updated"

    #fonction qui supprime une chanson

    #trouve la chanson à supprimer et le supprimer de tracklist
    #actualise le csv bdd et supprime le ficher audio
    #la suppression est notifiée à tous les abonnés au topic IceStorm
    def deleteTrack(self, ref, music, current=None):
        for track in self.tracklist:
            if track == music:
                self.tracklist.remove(track)
        tempFile = NamedTemporaryFile(mode="w", delete=False, newline="")
        fields = ["Title", "Artist", "Filename"]
        with open(self.directory+"/track_list.csv") as file, tempFile:
            reader = csv.DictReader(file, fieldnames = fields)
            writer = csv.DictWriter(tempFile, fieldnames = fields)
            for row in reader:
                if row["Title"] != music.title or row["Artist"] != music.artist or row["Filename"] != music.filename:
                    writer.writerow(row)
        shutil.move(tempFile.name, self.directory+"/track_list.csv")
        path = f"{self.directory}/{music.artist}/{music.title}"
        os.remove(f"{path}/{music.filename}")
        directory = os.listdir(path)
        if len(directory) == 0:
            os.rmdir(path)
        directory = os.listdir(f"{self.directory}/{music.artist}")
        if len(directory) == 0:
            os.rmdir(f"{self.directory}/{music.artist}")
        # ~ messenger.update(Update(ref, -1, music, Music(None, None, "?")))
        return "Track deleted"

    #fonction qui lance un stream audio en HTTP et retourne un identifiant unique pour écouter le musique

    #génère aléatoirement un id
    #crée une istance de vlc, la configure avec la chanson demandée et lance le flux
    #lance en arrière plan un agent de détection de fin de piste
    #retourne l'identifiant au client pour qu'il puisse commencer l'écoute
    def streamTrack(self, music, current=None):
        ref = str(self.id) + token_hex(8)
        vlc_instance = vlc.Instance()
        param = [f"{self.directory}/{music.artist}/{music.title}/{music.filename}", ":sout=#standard{access=http,mux=ogg,dst=:9090/"+ref+"}"]
        media = vlc_instance.media_new(*param)
        player = media.player_new_from_media()
        player.play()
        self.playing[ref] = player
        self.streams+=1
        self.paused[ref] = False
        thread = threading.Thread(target=self.isPlaying, args=(ref,))
        thread.start()
        return ref

    #fonction qui met en pause le lecteur enregistré sous la référence donnée (id généré au moment de la création du stream)
    def pauseTrack(self, ref, current=None):
        self.paused[ref] = True
        self.playing.get(ref).set_pause(1)

    #fonction qui reprend la lectur sur le lecteur enregistré sous la référence donnée
    def resumeTrack(self, ref, current=None):
        self.playing.get(ref).set_pause(0)
        self.paused[ref] = False
        time.sleep(0.1)

    #fonction qui arrête le lecteur enregistré sous la référence donnée
    def stopTrack(self, ref, current=None):
        if self.playing.get(ref) != None:
            self.emergency[ref] = True
            self.playing.get(ref).stop()
            self.playing.pop(ref)
            self.paused.pop(ref)
            self.streams -=1

    #fonction lancée dans un thread qui guère l'arrêt de la lecture du lecteur enregistré sous la référence donnée

    #verification de la fin de lecture toutes les 0.1 secondes
    #si lecteur arrêté par utilisateur, sortie d'urgence
    #sinon sortie lorsque le lecteur atteint la fin de la chanson
    #notifie l'abonné au topic IceStrom de la nécessité d'arrêter la lecture
    def isPlaying(self, ref, current=None):
        while self.playing.get(ref) != None:
            time.sleep(0.1)
            if self.emergency.get(ref): 
                self.emergency.pop(ref)
                return
            if self.playing.get(ref).is_playing() == 0 and not self.paused.get(ref):
                break
        # ~ messenger.stop(ref)

#fonction main d'initialisation du serveur avec Ice et du topic avec IceStorm
with Ice.initialize(sys.argv) as communicator:
    adapter = communicator.createObjectAdapterWithEndpoints("ServerAdapter", "default -p 10001")
    object = StreamingServerI(0, "srv")
    adapter.add(object, communicator.stringToIdentity("Server"))
    adapter.activate()
    # ~ storm = communicator.stringToProxy("Storm/TopicManager:default -h localhost -p 10010")
    # ~ manager = IceStorm.TopicManagerPrx.checkedCast(storm)
    # ~ topic = None
    # ~ while(topic == None):
        # ~ try:
            # ~ topic = manager.retrieve("tracklist")
        # ~ except:
            # ~ topic = manager.create("tracklist")
    # ~ publisher = topic.getPublisher().ice_oneway()
    # ~ messenger = Streaming.MessengerPrx.uncheckedCast(publisher)
    communicator.waitForShutdown()
