package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;
import java.util.stream.Collectors;

/* A class used to represent a Video Library. */
class VideoLibrary {

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
      allVidTitles.add("\n" + Tags(vid));
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
        curPlaying = "Currently playing: " + Tags(vid);

        if (paused) curPlaying += " - PAUSED";
      }
    }
    return curPlaying;
  }

  String Tags (Video video)
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
}