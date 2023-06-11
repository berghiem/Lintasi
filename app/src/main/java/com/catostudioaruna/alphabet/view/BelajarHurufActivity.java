package com.catostudioaruna.alphabet.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.catostudioaruna.alphabet.db.Letter;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.TrackAdapter;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BelajarHurufActivity extends AppCompatActivity implements View.OnClickListener {
    private int batas;
    private String[] motionFiles;
    private Integer[] audioFiles;
    private String[] titles;
    private MediaPlayer player;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView bottomList;
    private ImageView openCloseButton;
    private ImageView prevButton;
    private ImageView playButton;
    private ImageView nextButton;
    private ImageView soundButton;
    private int currentTrack;
    private Context context;
    private List<Letter> letters;
    private LottieAnimationView lottieAnimationView;
    private TrackAdapter trackAdapter;
    private ImageView closeButton;
    private RecyclerView tracksView;
    private LinearLayout backView;
    private InterstitialAd interstitialAd;
    private int clickCount;
    private AudioManager audioManager;
    private int volumeLevel;
    private int tempVolume;
    private AdRequest adRequest;
    private Bundle extras;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null && intent.getAction().contentEquals("track")) {
                int index = intent.getIntExtra("index", 0);
                currentTrack = index;
                play(index);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.belajarhuruf_activity);
        batas = 15;
        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);

        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();

        context = getApplicationContext();
        lottieAnimationView = this.findViewById(R.id.animation);
        openCloseButton = (ImageView) findViewById(R.id.open_close_button);
        prevButton = (ImageView) findViewById(R.id.prev);
        nextButton = (ImageView) findViewById(R.id.next);
        playButton = (ImageView) findViewById(R.id.play);
        backView = (LinearLayout) findViewById(R.id.back);
        soundButton = (ImageView) findViewById(R.id.sound);
        letters = new ArrayList<>();

        currentTrack = 0;
        clickCount = 0;
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(this.getString(R.string.test_add_unit_id_banner));

        motionFiles = new String[]{"a.json", "b.json", "c.json", "d.json", "e.json", "f.json", "g.json", "h.json", "i.json", "j.json",
                "k.json", "l.json", "m.json", "n.json", "o.json", "p.json", "q.json", "r.json", "s.json", "t.json",
                "u.json", "v.json", "w.json", "x.json", "y.json", "z.json"};

        audioFiles = new Integer[]{R.raw.a, R.raw.b, R.raw.c, R.raw.d, R.raw.e, R.raw.f, R.raw.g, R.raw.h, R.raw.i, R.raw.jjaket
                , R.raw.kkauskaki, R.raw.l, R.raw.m, R.raw.n, R.raw.o, R.raw.ppensil, R.raw.q, R.raw.rroti, R.raw.s, R.raw.t, R.raw.u
                , R.raw.v, R.raw.w, R.raw.x, R.raw.y, R.raw.z};

        titles = new String[]{"A apel", "B bunga", "C cicak", "D donat", "E eskrim", "F flaminggo", "G gigi", "H hujan", "I ikan"
                , "J jam", "K kereta", "L lampu", "M matahari", "N nanas", "O onta", "P pelangi"
                , "Q quran", "R rumah", "S sepeda", "T teman", "U ulat", "V vas", "W wortel",
                "X xilofon", "Y yoyo", "Z zebra"};


        player = MediaPlayer.create(this, R.raw.a);
        bottomList = (RecyclerView) findViewById(R.id.tracklist);
        closeButton = (ImageView) findViewById(R.id.close);

        LinearLayout view = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(view);

        for (int i = 0; i < motionFiles.length; i++) {
            Letter l = new Letter(audioFiles[i], motionFiles[i], titles[i]);
            letters.add(l);
        }
        trackAdapter = new TrackAdapter(letters, this.getApplicationContext());
        //     RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL , false);

        Intent intent = getIntent();



        bottomList.setAdapter(trackAdapter);
        bottomList.setLayoutManager(new LinearLayoutManager(this));

        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                volumeLevel,
                0);

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
        playButton.setOnClickListener(this::onClick);
        prevButton.setOnClickListener(this::onClick);
        nextButton.setOnClickListener(this::onClick);
        soundButton.setOnClickListener(this::onClick);
        playButton.setClickable(true);
        prevButton.setClickable(true);
        soundButton.setClickable(true);
        closeButton.setOnClickListener(this);

        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            ;

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        openCloseButton.setClickable(true);
        openCloseButton.setOnClickListener(this::onClick);


        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.pause();
                player.reset();
                playButton.setImageResource(R.drawable.play);
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });

        lottieAnimationView.setAnimation("a.json");
        play(currentTrack);

        interstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                String reason = "";
                switch (i) {
                    case 0:
                        reason = "ERROR_CODE_INTERNAL_ERROR";
                        break;
                    case 1:
                        reason = "ERROR_CODE_INVALID_REQUEST";
                        break;
                    case 2:
                        reason = "ERROR_CODE_NETWORK_ERROR";
                        break;
                    case 3:
                        reason = "ERROR_CODE_NO_FILL";
                        break;
                }
                //Log.i("failed load ad ", reason);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("track");

        registerReceiver(broadcastReceiver, intentFilter);
        play(currentTrack);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_close_button:
                clickCount++;
                if (clickCount == 2 || (clickCount % batas) == 0) {
                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                            .build();


                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }

                }
                openTrackList();
                break;

            case R.id.play:
                clickCount++;
                if (clickCount == 2 || (clickCount % batas) == 0) {
                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            // .addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                            .build();

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }

                }

                play();
                break;

            case R.id.prev:
                clickCount++;
                if (clickCount == 2 || (clickCount % batas) == 0) {
                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                            .build();

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }

                previousTrack();
                break;

            case R.id.next:
                clickCount++;
                if (clickCount == 2 || (clickCount % batas) == 0) {

                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                            .build();

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }
                nextTrack();
                break;

            case R.id.close:
                clickCount++;
                if (clickCount == 2 || (clickCount % batas) == 0) {

                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                            .build();

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }

                closeTrackList();
                break;

            case R.id.sound:
                clickCount++;
                if (clickCount == 2 || (clickCount % batas) == 0) {

                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            // .addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                            .build();

                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }
                volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (volumeLevel != 0) {
                    audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            0,
                            0);
                    soundButton.setImageResource(R.drawable.mute);
                } else {
                    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float percent = 0.7f;
                    int seventyVolume = (int) (maxVolume * percent);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
                    soundButton.setImageResource(R.drawable.soundon);
                }
                break;
        }

    }

    private void play() {
        if (!player.isPlaying()) {

            play(currentTrack);
        }


    }

    private void stop() {
        if (player.isPlaying()) {

        }
    }

    private void previousTrack() {

        if (currentTrack > 0) {
            if (player.isPlaying()) {
                player.pause();
            }
            player.reset();
            currentTrack--;
            AssetFileDescriptor assetFileDescriptor =
                    context.getResources().openRawResourceFd(letters.get(currentTrack).getAudio());
           try {
               player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                       assetFileDescriptor.getLength());
                lottieAnimationView.setAnimation(letters.get(currentTrack).getMotion());
            } catch (Exception e) {
                Log.e("prev.load", e.toString());
            }

            try {
                lottieAnimationView.playAnimation();
                player.prepare();
                player.start();
            } catch (Exception e) {
                Log.e("prev.prepare", e.toString());
            }
        }


    }

    private void nextTrack() {
        if ((currentTrack + 1) < motionFiles.length) {
            if (player.isPlaying()) {
                player.pause();
            }
            player.reset();
            currentTrack++;
            AssetFileDescriptor assetFileDescriptor =
                    context.getResources().openRawResourceFd(letters.get(currentTrack).getAudio());
            try {
                player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                        assetFileDescriptor.getLength());
                lottieAnimationView.setAnimation(letters.get(currentTrack).getMotion());
            } catch (Exception e) {
                Log.e("prev.load", e.toString());
            }

            try {
                lottieAnimationView.playAnimation();
                player.prepare();
                player.start();
            } catch (Exception e) {
                Log.e("prev.prepare", e.toString());
            }
        }
    }

    private void openTrackList() {
        Toast.makeText(this, "open", Toast.LENGTH_SHORT);
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    private void closeTrackList() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    private void play(int index) {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(letters.get(index).getAudio());
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                assetFileDescriptor.getLength());
            lottieAnimationView.setAnimation(letters.get(index).getMotion());
        } catch (Exception e) {
            Log.e("play " + index, e.toString());
        }

        try {
            lottieAnimationView.playAnimation();
            player.prepare();
            player.start();
            currentTrack = index;
        } catch (Exception e) {
            Log.e("prev.prepare", e.toString());
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


}
