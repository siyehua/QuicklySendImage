package com.siyehua.listenerfile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    View wantView;
    ImageView imageView;

    List<File> list;
    MyAdapter myAdapter;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = QuicklySendImage.getLastImage(MainActivity.this, 2 * 60 * 1000);
                if (path != null) {
                    imageView.setImageBitmap(MyAdapter.optimizeBitmap(path, (int) getResources()
                            .getDimension(R.dimen.want), (int) getResources().getDimension(R
                            .dimen.want)));
                    wantView.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView = (ImageView) findViewById(R.id.iv_want);
        wantView = findViewById(R.id.ll_want);
        wantView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new File(path));
                myAdapter.notifyDataSetChanged();
                wantView.setVisibility(View.GONE);
            }
        });
        ListView listView = (ListView) findViewById(R.id.lv_content);
        list = new ArrayList<>();
        myAdapter = new MyAdapter(list);
        listView.setAdapter(myAdapter);
    }
}