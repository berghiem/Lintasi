package com.catostudioaruna.alphabet.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.catostudioaruna.alphabet.R;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CariHurufActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView h1;
    private ImageView h2;
    private ImageView h3;
    private ImageView h4;
    private ImageView h5;
    private ImageView h6;
    private ImageView h7;

    private Integer[] angkaAudios;
    private TextView replay;
    private int counter;
    private LottieAnimationView animation;
    private MediaPlayer player;
    private Context context;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private Integer[] rids;
    private List<Integer> ridList;
    private ImageView[] imageViews;
    private AdRequest adRequest;
    private Bundle extras;
    private Integer[] hurufcolorfuls;
    private Random random;
    private int randomnumber;
    private char[] alphabet;
    private String quizText;
    private TextView quizTv;

    private int temp ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.carihuruf);
        context = this.getApplicationContext();
        counter = 0;
        temp = 0;
        hurufcolorfuls = new Integer[]{R.drawable.a2_30,R.drawable.bcolorful2, R.drawable.ccolorful2, R.drawable.dcolorful2, R.drawable.ecolorful2,
                R.drawable.fcolorful2, R.drawable.gcolorful2, R.drawable.hcolorful2, R.drawable.icolorful,
                R.drawable.mcolorful2, R.drawable.ocolorful2, R.drawable.pcolorful2, R.drawable.rcolorful,
                R.drawable.scolorful, R.drawable.wcolorful2, R.drawable.zcolorful2};

        alphabet = "abcdefghimoprswz".toCharArray();

        quizTv = (TextView) findViewById(R.id.quiz);

        rids = new Integer[]{R.id.h1, R.id.h2, R.id.h3, R.id.h4, R.id.h5, R.id.h6, R.id.h7, R.id.h8, R.id.h9, R.id.h10,
                R.id.h11, R.id.h12, R.id.h13, R.id.h14, R.id.h15, R.id.h16, R.id.h17, R.id.h18, R.id.h19, R.id.h20,};
        angkaAudios = new Integer[]{R.raw.angka1, R.raw.angka2, R.raw.angka3, R.raw.angka4, R.raw.angka5, R.raw.angka6, R.raw.angka7};


        ridList = Arrays.asList(rids);
        Collections.shuffle(ridList);

        random = new Random();
        randomnumber = random.nextInt(14);

        imageViews = new ImageView[]{h1, h2, h3, h4, h5, h6, h7};
        int imageRes = hurufcolorfuls[randomnumber];

        for (int x = 0; x < imageViews.length; x++) {
            imageViews[x] = (ImageView) findViewById(ridList.get(x));
            imageViews[x].setVisibility(View.VISIBLE);
            imageViews[x].setClickable(true);
            imageViews[x].setImageResource(imageRes);
            temp = x;
            imageViews[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.INVISIBLE);
                    playAngkaSound(v);
                }
            });

        }
        quizText = "Ayo temukan 7 huruf " + alphabet[randomnumber] + " , dengan mengkliknya !";
        quizTv.setText(quizText);


        replay = (TextView) findViewById(R.id.replay);
        animation = (LottieAnimationView) findViewById(R.id.animation);
        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(this.getString(R.string.test_add_unit_id_banner));
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed()  {
                interstitialAd.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                        .build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                interstitialAd.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                        .build());
            }
        });

        player = MediaPlayer.create(this, R.raw.happiness);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (counter == 7) {
                    playEndGameMusic();
                    animation.setVisibility(View.VISIBLE);
                    animation.playAnimation();
                    replay.setVisibility(View.VISIBLE);

                }
            }
        });
        replay.setClickable(true);

        // setLetterAction();
        replay.setOnClickListener(this::onClick);


        replay.setVisibility(View.GONE);
    }



    private void playAngkaSound(View v) {
        if (player.isPlaying()) {
            player.pause();

        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(angkaAudios[counter]);
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            v.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            Log.e("play " + counter, e.toString());
        }

        try {
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e("prev.prepare", e.toString());
        }
        counter++;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.replay:
                resetGame();

                break;

        }

    }



    private void playEndGameMusic() {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(R.raw.happiness);
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
         } catch (Exception e) {
            Log.e("play", e.getMessage());
        }

        try {
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e("prev.prepare", e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
    }

    private void resetGame() {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        if (interstitialAd.isLoaded()) {

            interstitialAd.show();
        }

        ridList = Arrays.asList(rids);
        Collections.shuffle(ridList);

        random = new Random();
        randomnumber = random.nextInt(14);
        int imageRes = hurufcolorfuls[randomnumber];

        for (int x = 0; x < imageViews.length; x++) {
            imageViews[x] = (ImageView) findViewById(ridList.get(x));
            imageViews[x].setVisibility(View.VISIBLE);
            imageViews[x].setClickable(true);
            imageViews[x].setImageResource(imageRes);
            temp = x;
            imageViews[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.INVISIBLE);
                    playAngkaSound(v);
                }
            });

        }
        quizText = "Ayo temukan 7 huruf " + alphabet[randomnumber] + " , dengan mengkliknya !";
        quizTv.setText(quizText);


        //  setLetterAction();


        counter = 0;
        animation.setVisibility(View.GONE);
        replay.setVisibility(View.GONE);
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();

        interstitialAd.loadAd(adRequest);

    }
}
