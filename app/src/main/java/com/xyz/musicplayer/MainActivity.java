package com.xyz.musicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ListView lvAudio;
    String[] list, path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAudio = (ListView) findViewById(R.id.lvAudio);
        ContentResolver c = getContentResolver();
        Cursor c1 = c.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        Cursor c1 = c.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);


        int count = c1.getCount();
        list = new String[count];
        path = new String[count];

        int i = 0;
        while (c1.moveToNext()) {
            list[i]=c1.getString(c1.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            path[i]=c1.getString(c1.getColumnIndex(MediaStore.Audio.Media.DATA));
            i++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lvAudio.setAdapter(adapter);

        lvAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    String p=path[position];
                    MediaPlayer mp =new MediaPlayer();
                    mp.reset();
                    mp.setDataSource(p);
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
