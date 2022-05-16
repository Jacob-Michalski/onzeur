import json
import sys, Ice
import Parse

word_to_action = {}

class Resparse(Parse.Resparse):
    def __init__(self, act, tit, art):
        self.action = act
        self.title = tit
        self.artist = art

class ParserI(Parse.Parser):
    def __init__(self):
        global word_to_action
        with open('actions.json') as actions:
            word_to_action = json.load(actions)

    def parseRequest(self, request, current=None):
        request = request.lower()
        if request == "joue licorne":
            return Resparse("licorne", "", "")
        words = request.split()
        separator = ["par", "de"]
        action = "default"
        actioned = False
        titled = False
        title = ""
        artist = ""
        for word in words:
            if actioned:
                if titled:
                    artist = artist+word+" "
                else:
                    if word in separator:
                        titled = True
                    else:
                        title = title+word+" "
            if word in word_to_action and not actioned:
                actioned = True
                action = word_to_action[word]
        title = title[:-1]
        artist = artist[:-1]
        return Resparse(action, title, artist)
        

with Ice.initialize(sys.argv) as communicator:
    adapter = communicator.createObjectAdapterWithEndpoints("ParserAdapter", "default -p 10002")
    object = ParserI()
    adapter.add(object, communicator.stringToIdentity("Parser"))
    adapter.activate()
    communicator.waitForShutdown()
