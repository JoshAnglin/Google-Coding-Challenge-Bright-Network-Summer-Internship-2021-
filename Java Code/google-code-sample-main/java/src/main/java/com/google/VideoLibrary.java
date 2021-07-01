package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* A class used to represent a Video Library. */
class VideoLibrary
{
  private final HashMap<String, Video> videos;

  String currentVid = "";
  boolean paused;

  VideoLibrary()
  {
    this.videos = new HashMap<>();

    // Wasn't able to find the file.. so did this janky method.
    Scanner scanner = new Scanner(
            "Funny Dogs | funny_dogs_video_id |  #dog , #animal"
                    + "\n" + "Amazing Cats | amazing_cats_video_id |  #cat , #animal"
                    + "\n" + "Another Cat Video | another_cat_video_id |  #cat , #animal"
                    + "\n" + "Life at Google | life_at_google_video_id |  #google , #career"
                    + "\n" + "Video about nothing | nothing_video_id |"
    );

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] split = line.split("\\|");
      String title = split[0].strip();
      String id = split[1].strip();
      List<String> tags;
      if (split.length > 2) {
        tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
                Collectors.toList());
      } else {
        tags = new ArrayList<>();
      }
      this.videos.put(id, new Video(title, id, tags));
    }
    scanner.close();
  }

  /* PART 1 */

  /** Number of videos */
  List<Video> getVideos()
  {return new ArrayList<>(this.videos.values());}

  /* Get a video by id. Returns null if the video is not found. */

  Video getVideo(String videoId)
  {return this.videos.get(videoId);}

  /** All Videos */
  String GetVideoNames()
  {
    String vidList = "";

    List<String> allVidTitles = new ArrayList<>();

    for(Video vid : getVideos())
    {
      allVidTitles.add("\n" + VidDetails(vid));
    }

    Collections.sort(allVidTitles);

    for(String titles : allVidTitles)
    {vidList += titles;}

    return vidList;
  }

  /** Play Videos */
  String PlayVideo(String input)
  {
    String res = "";

    for(Video vid : getVideos())
    {
      if (input.contains(vid.getVideoId()))
      {
        res += CurrentVidValid("", false);
        currentVid = vid.getTitle();
        paused = false;
        return res += "Playing video: " + currentVid;
      }
    }
    return "Cannot play video: Video does not exist";
  }

  /** Stops Video */
  String StopVideo()
  {return CurrentVidValid("Cannot stop video: No video is currently playing", true);}

  /** Plays Random Video */
  String PlayRandomVideo()
  {
    Random randNum = new Random();
    int chosenNum = randNum.nextInt(getVideos().size());

    if (getVideos().size() == 0) return "No videos available";
    paused = false;
    return CurrentVidValid("", false) + "Playing video: " + (currentVid = getVideos().get(chosenNum).getTitle());
  }

  /** Pause Video */
  String PauseVideo()
  {return PauseCont(true,"Pausing video: ", "Video already paused: ", "pause");}

  /** Continue Video */
  String ContinueVideo()
  {return PauseCont(false,"Continuing video: ", "Cannot continue video: Video is not paused", "continue");}

  /** Currently Playing */
  String CurrentlyPlaying()
  {
    String curPlaying = "";

    if (currentVid == "") return "No video is currently playing";

    for (Video vid : getVideos())
    {
      if (currentVid == vid.getTitle()) {
        curPlaying = "Currently playing: " + VidDetails(vid);

        if (paused) curPlaying += " - PAUSED";
      }
    }
    return curPlaying;
  }

  String VidDetails (Video video)
  {
    String tags = "";

    for (int i = 0; i < video.getTags().size(); i++)
    {
      tags += video.getTags().get(i);
      if (i != video.getTags().size() - 1) tags += " ";
    }

    return video.getTitle() + " (" + video.getVideoId() + ") " + "[" + tags + "]";
  }

  String PauseCont(boolean pausing, String resOne, String resTwo, String Nothing)
  {
    String res = "";

    if (currentVid != "")
    {
      if (!paused)
      {
        if (pausing)
        {
          res = resOne + currentVid;
          paused = true;
        }
        else if (!pausing) res = resTwo;
      }

      else if (paused)
      {
        if(pausing) res = resTwo + currentVid;
        else if (!pausing)
        {
          paused = false;
          res = resOne + currentVid;
        }
      }
    }

    else res = "Cannot " + Nothing + " video: No video is currently playing";

    return res;
  }

  String CurrentVidValid(String isNull, boolean onlyStop){
    if(currentVid != "")
    {
      String cv = "Stopping video: " + currentVid;
      currentVid = "";
      if (onlyStop) return cv;
      else if (!onlyStop) return cv + "\n";
    }
    return isNull;
  }

  /* PART 2 */

  Map<String, VideoPlaylist> playListMap = new HashMap<>();

  /** Creating Playlist */
  String CreatePlaylist(String playlistName)
  {
    String lcName = playlistName.toLowerCase();

    if (playListMap.containsKey(lcName)){
      return "Cannot create playlist: A playlist with the same name already exists";}

    else
    {
      playListMap.put(lcName, new VideoPlaylist(playlistName));
      return "Successfully created new playlist: " + playlistName;
    }
  }

  /** Adding Video to Playlist */
  String AddVideoToPlaylist(String playlistName, String videoId)
  {
    String lcName = playlistName.toLowerCase();

    if (playListMap.containsKey(lcName))
    {
      Video vid = getVideo(videoId);
      VideoPlaylist playList = playListMap.get(lcName);

      if (vid != null)
      {
        /*if (flags.containsKey(videoId))
        {
          return "Cannot add video to " + playlistName + " Video is currently flagged (reason: %s)%n", playlistName, flags.get(videoId));
        } */
        if (playList.addVideo(vid))
          return "Added video to " + playlistName + ": " + vid.getTitle();

        else
          return "Cannot add video to " + playlistName + ": Video already added";
      }
      else return "Cannot add video to " + playlistName + ": Video does not exist";
    }
    else return "Cannot add video to " + playlistName + ": Playlist does not exist";
  }

  /** Shows all Playlists */
  String ShowAllPlaylists()
  {
    List<String> lcNames = new ArrayList<>(playListMap.keySet());
    String playlists = "Showing all playlists: ";

    if (lcNames.isEmpty()) return "No playlists exist yet";

    Collections.sort(lcNames);

    for(String pl : lcNames)
      playlists += "\n" + pl;

    return playlists;
  }

  /** Shows 'playlistName''s Playlist */
  String ShowPlaylist(String playlistName)
  {
    VideoPlaylist playList = playListMap.get(playlistName.toLowerCase());

    if (playList != null)
    {
      String thisPlaylist = "Showing playlist: " + playlistName;

      if (playList.videos.isEmpty())
         thisPlaylist += "\nNo videos here yet";

      else
      {
        for(Video vid : playList.videos)
          thisPlaylist += "   \n" +  VidDetails(vid);
      }

      return thisPlaylist;
    }

    else return "Cannot show playlist " + playlistName + ": Playlist does not exist";
  }

  /** Removes video from Playlist */
  String RemoveFromPlaylist(String playlistName, String videoId)
  {
    String lcName = playlistName.toLowerCase();
    VideoPlaylist playList = playListMap.get(lcName);

    if (playList != null)
    {
      Video vid = getVideo(videoId);

      if (vid != null)
      {
        if (playList.removeVideo(vid))
          return "Removed video from " + playlistName + ": " + vid.getTitle();

        else
          return "Cannot remove video from " + playlistName + ": Video is not in playlist";
      }
      else return "Cannot remove video from " + playlistName + ": Video does not exist%n";
    }
    else return "Cannot remove video from " + playlistName + ": Playlist does not exist%n";
  }

  /** Clears Playlist */
  String ClearPlaylist(String playlistName)
  {
    String lcName = playlistName.toLowerCase();
    VideoPlaylist playList = playListMap.get(lcName);

    if (playList == null) return "Cannot clear playlist " + playlistName + ": Playlist does not exist";

    playList.videos = new ArrayList<>();
    return "Successfully removed all videos from " + playlistName;
  }

  /** Deletes Playlist */
  String DeletePlaylist(String playlistName)
  {
    String lcName = playlistName.toLowerCase();

    if (!playListMap.containsKey(lcName))
      return "Cannot delete playlist " + playlistName + ": Playlist does not exist%n";

      playListMap.remove(lcName);
      return "Deleted playlist: " + playlistName;
  }
}