package com.example.nihal.flickrbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        activateToolbar(true);

        Intent intent = getIntent();
        Photo photo = (Photo)intent.getSerializableExtra(PHOTO_TRANSFER);
        if(photo!=null){
            TextView photoTitle = (TextView)findViewById(R.id.photo_title);
            //photoTitle.setText("Title : "+photo.getTitle());
            Resources resources = getResources();
            String text = resources.getString(R.string.photo_title_text,photo.getTitle());
            photoTitle.setText(text);

            TextView photoTags = (TextView)findViewById(R.id.photo_tags);
            //photoTags.setText("Tags :"+photo.getTags());
            photoTags.setText(resources.getString(R.string.photo_tags_text,photo.getTags()));

            TextView photoAuthor = (TextView)findViewById(R.id.photo_author);
            photoAuthor.setText(photo.getAuthor());

            ImageView photoImage = (ImageView)findViewById(R.id.photo_image);
            Picasso.with(this).load(photo.getLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);
        }

    }

}
