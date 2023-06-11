package com.catostudioaruna.alphabet;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.ads.AudienceNetworkAds;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.recyclerview.widget.RecyclerView;

public class AlphabetAppUtil extends MultiDexApplication {
    private ImageView img; // the image
    private ImageView img2;
    private View bgimg; // layout of the activity
    private RecyclerView recyclerView;
    private Bitmap nav; // the image in the Bitmap format
    private Bitmap nav2; // the image in the Bitmap format
    private Bitmap background; // background in the Bitmap format
    private BitmapDrawable bg; // background in the Drawable format

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
   //     AudienceNetworkAds.initialize(this);
    }

    public void unloadRecyclerView(){

    }

    public void loadBackground(int id) {
        background = BitmapFactory.decodeStream(getResources().openRawResource(id));
        bg = new BitmapDrawable(background);
        bgimg.setBackgroundDrawable(bg);
    }
    public void unloadBackground() {
        if (bgimg != null)
            bgimg.setBackgroundDrawable(null);
        if (bg!= null) {
            background.recycle();
        }
        bg = null;
    }

    public void loadBitmap(int id) {
        nav = BitmapFactory.decodeStream(getResources().openRawResource(id));
        img.setImageBitmap(nav);
    }


    public void loadBitmap2(int id) {
        nav2 = BitmapFactory.decodeStream(getResources().openRawResource(id));
        img2.setImageBitmap(nav2);
    }


    public void unloadBitmap() {
        if (img != null)
            img.setImageBitmap(null);
        if (nav!= null) {
            nav.recycle();
        }
        nav = null;
    }

    public void unloadBitmap2() {
        if (img2 != null)
            img2.setImageBitmap(null);
        if (nav2!= null) {
            nav2.recycle();
        }
        nav2 = null;
    }
    public void setBackground(View i, int sourceid) {
        unloadBackground();
        if(i != null ){
            bgimg = i;
            loadBackground(sourceid);
        }

    }

    public void setImage(ImageView i, int sourceid) {
        unloadBitmap();
        img = i;
        loadBitmap(sourceid);
    }

    public void setImage2(ImageView i, int sourceid) {
        unloadBitmap2();
        img2 = i;
        loadBitmap2(sourceid);
    }

}
