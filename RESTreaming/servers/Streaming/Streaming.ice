module Streaming
{
    struct music {
        string title;
        string artist;
        string filename;
    }

    struct update {
        string id;
        int nr;
        music modified;
        music original;
    }

    sequence<byte> Bytes;
    sequence<music> MusicList;

    interface Messenger
    {
        void update(update u);
        void stop(string ref);
    }

    interface StreamingServer
    {
        MusicList shareTracklist();
        int getStreamNumber();
        void transferFragment(string ref, Bytes f);
        string saveTrack(string ref, music m);
        string modifyTrack(string ref, music m, music n);
        string deleteTrack(string ref, music m);
        string streamTrack(music m);
        void pauseTrack(string ref);
        void resumeTrack(string ref);
        void stopTrack(string ref);
    }
}