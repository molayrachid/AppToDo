package com.zouhaoui.nabil.apptodo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FacebookActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        TextView textView1 = (TextView) findViewById(R.id.textview1);
        textView1.setText(getIntent().getStringExtra("name"));
        TextView textView2 = (TextView) findViewById(R.id.textview2);
        Albums albums = (Albums) (getIntent().getSerializableExtra("albums"));
        Log.e("Serie",albums.getAlbums().size()+"");
        if(albums.getAlbums().size() >0)
        textView2.setText(albums.getAlbums().get(1).getName());
        ImageView imageView1 = (ImageView) findViewById(R.id.imageview1);
        Picasso.with(this).load(getIntent().getExtras().getString("imageUrl")).into(imageView1);

    }
    public class album implements Serializable
    {
        private String name;

        public album(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
//    public static  Bitmap LoadImageFromWebOperations(String scr) {
//        try {
//
//            URL url=new URL(scr);
//            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input=connection.getInputStream();
//            Bitmap bmp = BitmapFactory.decodeStream(input);
//            return bmp;
//        } catch (Exception e) {
//            Log.e("erreur",e+"");
//            return  null;
//        }
//    }
}
