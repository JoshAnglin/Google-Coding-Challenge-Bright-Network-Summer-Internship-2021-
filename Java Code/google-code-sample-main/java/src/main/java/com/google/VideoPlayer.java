package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  public VideoPlayer() {this.videoLibrary = new VideoLibrary();}

  public void numberOfVideos() {System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());}

  public void showAllVideos() {System.out.printf("Here's a list of all available videos:" + videoLibrary.GetVideoNames());}

  public void playVideo(String videoId) {System.out.println(videoLibrary.PlayVideo(videoId));}

  public void stopVideo() {System.out.println(videoLibrary.StopVideo());}

  public void playRandomVideo() {System.out.println(videoLibrary.PlayRandomVideo());}

  public void pauseVideo() {System.out.println(videoLibrary.PauseVideo());}

  public void continueVideo() {System.out.println(videoLibrary.ContinueVideo());}

  public void showPlaying() {System.out.println(videoLibrary.CurrentlyPlaying());}

  public void createPlaylist(String playlistName) {System.out.println(videoLibrary.CreatePlaylist(playlistName));}

  public void addVideoToPlaylist(String playlistName, String videoId)
    {System.out.println(videoLibrary.AddVideoToPlaylist(playlistName, videoId));}

  public void showAllPlaylists() {System.out.println(videoLibrary.ShowAllPlaylists());}

  public void showPlaylist(String playlistName) {System.out.println(videoLibrary.ShowPlaylist(playlistName));}

  public void removeFromPlaylist(String playlistName, String videoId)
    {System.out.println(videoLibrary.RemoveFromPlaylist(playlistName, videoId));}

  public void clearPlaylist(String playlistName) {System.out.println(videoLibrary.ClearPlaylist(playlistName));}

  public void deletePlaylist(String playlistName) {System.out.println(videoLibrary.DeletePlaylist(playlistName));}

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}