package RESTreaming;

import Streaming.*;

public class Serveur {

    private final String serverName = "Server";
    private final String serverNb = "1";
    private final music[] tracklist;

    public Serveur() {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(serverName+":default -p 1000"+serverNb);
            StreamingServerPrx server = StreamingServerPrx.checkedCast(base);
            if(server == null) {
                throw new Error("There is an issue connecting with the server");
            }
            tracklist = server.shareTracklist();
        }
    }

    public String play(String artist, String title) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(serverName+":default -p 1000"+serverNb);
            StreamingServerPrx server = StreamingServerPrx.checkedCast(base);
            if(server == null) {
                throw new Error("There is an issue connecting with the server");
            }
            for (Streaming.music music : tracklist) {
                if (music.artist.equals(artist) && music.title.equals(title)) {
                    return server.streamTrack(new music(music.title, music.artist, music.filename));
                }
            }
            return null;
        }
    }

    public void pause(String ref) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(serverName+":default -p 1000"+serverNb);
            StreamingServerPrx server = StreamingServerPrx.checkedCast(base);
            if(server == null) {
                throw new Error("There is an issue connecting with the server");
            }
            server.pauseTrack(ref);
        }
    }

    public void resume(String ref) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(serverName+":default -p 1000"+serverNb);
            StreamingServerPrx server = StreamingServerPrx.checkedCast(base);
            if(server == null) {
                throw new Error("There is an issue connecting with the server");
            }
            server.resumeTrack(ref);
        }
    }

    public void stop(String ref) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize()) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(serverName+":default -p 1000"+serverNb);
            StreamingServerPrx server = StreamingServerPrx.checkedCast(base);
            if(server == null) {
                throw new Error("There is an issue connecting with the server");
            }
            server.stopTrack(ref);
        }
    }

}
