package com.example.nihal.flickrbrowser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetFlickrjsonData.OnDataAvailable{

    private final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.reclycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this,new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        //GetRawData getRawData = new GetRawData(this);
        //getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1");
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetFlickrjsonData getFlickrjsonData = new GetFlickrjsonData(this,"https://api.flickr.com/services/feeds/photos_public.gne","en-us",true);
//        getFlickrjsonData.executeOnSameThread("android,nougat");
        getFlickrjsonData.execute("android,nougat");
        Log.d(TAG, "onResume ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected returned : returned");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status==DownloadStatus.OK){
            mFlickrRecyclerViewAdapter.loadNewData(data);
        }else {
            // download or process failed
            Log.e(TAG, "onDataAvailable : failed with status" + status);
        }
        Log.d(TAG, "onDataAvailable: ends");
    }
}
