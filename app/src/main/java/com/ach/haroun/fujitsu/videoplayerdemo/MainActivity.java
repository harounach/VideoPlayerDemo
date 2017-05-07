package com.ach.haroun.fujitsu.videoplayerdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView= (VideoView) findViewById(R.id.videoView);
        getAllsongs();
        ListView  ls= (ListView) findViewById(R.id.listView);
        ls.setAdapter(new MyCustomAdapter());
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    videoView.setVideoPath(fullsongpath.get(position).path);
                    videoView.start();
                }catch(Exception e){
                    e.printStackTrace();

                }

            }
        });
    }
    ArrayList<SongInfo> fullsongpath=new ArrayList<SongInfo>();
    public void getAllsongs(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Cursor cursor;
        Uri allsongsuri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Video.Media.DATA+" != 0";
        cursor=managedQuery(allsongsuri,null,selection,null,null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                do {
                    String song_name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String fullpath=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String album_name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist_name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    fullsongpath.add(new SongInfo(fullpath,song_name,album_name,artist_name));

                }while (cursor.moveToNext());
            }
        }


    }

    public void buPause(View view) {
        videoView.pause();
    }

    public void buResume(View view) {

        videoView.resume();
    }

    //MyCustomAdapter
    class MyCustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fullsongpath.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View myView=inflater.inflate(R.layout.item,null);
            SongInfo songInfo=fullsongpath.get(position);
            TextView txtName= (TextView) myView.findViewById(R.id.text_name_id);
            TextView txtDesc= (TextView) myView.findViewById(R.id.text_desc_id);
            txtName.setText(songInfo.song_name);
            txtDesc.setText(songInfo.artist_name);


            return myView;
        }
    }


}
