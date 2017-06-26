package com.example.nihal.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable,
RecyclerItemClickListener.OnReclyclerClickListener{

    private final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.reclycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,recyclerView,this));
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(FLICKR_QUERY,"");
        if(queryResult.length()>0){
            GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this,"https://api.flickr.com/services/feeds/photos_public.gne","en-us",true);
//          getFlickrJsonData.executeOnSameThread("android,nougat");
            getFlickrJsonData.execute(queryResult);
        }

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
        if(id==R.id.action_search){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
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


    @Override
    public void onItemClick(View view, int postition) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(MainActivity.this,"Normal tap at position "+postition,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER,mFlickrRecyclerViewAdapter.getPhoto(postition));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int postition) {
        Log.d(TAG, "onItemLongClick: starts");
        Toast.makeText(MainActivity.this,"Long tap at position "+postition,Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(this,PhotoDetailActivity.class);
        //.putExtra(PHOTO_TRANSFER,mFlickrRecyclerViewAdapter.getPhoto(postition));
        //startActivity(intent);

    }
}
