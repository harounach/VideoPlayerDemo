package com.ach.haroun.fujitsu.videoplayerdemo;

public class SongInfo {
    public String path;
    public String song_name;
    public String album_name;
    public String artist_name;
    public SongInfo(String path,String song_name,String album_name,String artist_name){
        this.path=path;
        this.song_name=song_name;
        this.album_name=album_name;
        this.artist_name=artist_name;
    }
}
