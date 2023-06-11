package com.catostudioaruna.alphabet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.catostudioaruna.alphabet.NotifyService;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout banner1;
    private LinearLayout banner2;
    private LinearLayout banner3;

    private TextView banner4;
    private TextView credit;
    private TextView contact;
    private AlphabetViewModel viewModel;
    private String[] quotes;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.old_main);
        MobileAds.initialize(this, this.getString(R.string.YOUR_ADMOB_APP_ID));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        banner1 = findViewById(R.id.belajarhuruf);
        banner2 = findViewById(R.id.tebakhuruf);
        banner3 = findViewById(R.id.carihuruf);
        banner4 =  findViewById(R.id.angka);
        credit = findViewById(R.id.credit);
        contact = findViewById(R.id.contact);

        banner1.setClickable(true);
        banner1.setOnClickListener(this::onClick);
        banner2.setClickable(true);
        banner2.setOnClickListener(this::onClick);
        banner3.setClickable(true);
        banner3.setOnClickListener(this::onClick);
        banner4.setClickable(true);
        banner4.setOnClickListener(this::onClick);
        TextView textView = findViewById(R.id.myalbum);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyAlbumStickerActivity.class);
                banner1.setEnabled(false);
                banner2.setEnabled(false);
                banner3.setEnabled(false);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "album sticker");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                startActivity(intent);
            }
        });

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Image (background, and assets are designed by www.freepik.com", Toast.LENGTH_SHORT).show();
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "contact us : catostudio.id@gmail.com", Toast.LENGTH_SHORT).show();
            }
        });
        startService(new Intent(this,NotifyService.class));
    }




    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()){
            case R.id.belajarhuruf :
                intent = new Intent(this, NumberFragment.class);
                banner1.setEnabled(false);
                banner2.setEnabled(false);
                banner3.setEnabled(false);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "belajar");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                startActivity(intent);
                break;
            case R.id.tebakhuruf:
                intent = new Intent(this, TebakHurufActivity.class);
                banner1.setEnabled(false);
                banner2.setEnabled(false);
                banner3.setEnabled(false);
                Bundle bundle2 = new Bundle();
                bundle2.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle2.putString(FirebaseAnalytics.Param.ITEM_NAME, "tebak");
                bundle2.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle2);
                startActivity(intent);
                break;
            case R.id.carihuruf:
                intent = new Intent(this, CariHurufActivity.class);
                banner1.setEnabled(false);
                banner2.setEnabled(false);
                banner3.setEnabled(false);
                Bundle bundle3 = new Bundle();
                bundle3.putString(FirebaseAnalytics.Param.ITEM_ID, "3");
                bundle3.putString(FirebaseAnalytics.Param.ITEM_NAME, "cari");
                bundle3.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle3);
                startActivity(intent);
                break;

            case R.id.angka:
                intent = new Intent(this, NumberFragment.class);
                banner1.setEnabled(false);
                banner2.setEnabled(false);
                banner3.setEnabled(false);
                Bundle bundle4 = new Bundle();
                bundle4.putString(FirebaseAnalytics.Param.ITEM_ID, "5");
                bundle4.putString(FirebaseAnalytics.Param.ITEM_NAME, "angka");
                bundle4.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle4);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner1.setEnabled(true);
        banner2.setEnabled(true);
        banner3.setEnabled(true);
    }
}
