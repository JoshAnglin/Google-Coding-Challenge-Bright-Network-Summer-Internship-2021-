package com.google;
import java.util.List;
import java.util.ArrayList;

/** A class used to represent a Playlist */
class VideoPlaylist {

    public final String name;
    public List<Video> videos;

    VideoPlaylist(String name)
    {
        this.name = name;
        this.videos = new ArrayList<>();
    }

    public boolean addVideo(Video video)
    {
        if (videos.contains(video))
            return false;

        else {
            videos.add(video);
            return true;
        }
    }

    public boolean removeVideo(Video video)
    {
        if (videos.contains(video))
        {
            videos.remove(video);
            return true;
        }
        else return false;
    }
}
