package com.catostudioaruna.alphabet.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.NumberAdapter;
import com.catostudioaruna.alphabet.db.Number;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.SHORTCUT_SERVICE;

public class NumberFragment extends Fragment implements View.OnClickListener  {
    private TextView textView;
    private ImageView numberImage;
    private ImageView soundImage;
    private ImageView autoplayImage;
    private boolean autoplayMode;
    private LinearLayout objectNumber;
    private Animation animation;
    private int currentTrack;
    private RecyclerView trackView;
    private NumberAdapter trackAdapter;
    private MediaPlayer player;
    private Context context;
    private Animation animCrossFadeIn;
    private Animation animCrossFadeIn2;
    private List<Number> numberList;
    private View prev;
    private View next;
    private AudioManager audioManager;
    private int volumeLevel;
    private int copyCurrentTrack;
    private ImageView home;
    private ImageView map;
    private ImageView grassatas;
    private NavigationListener callback;
    private String filterNumber;
    private String filterVolume;
    private AdView adView;
    private Bundle extras;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null && intent.getAction().contentEquals(filterNumber)) {
                int index = intent.getIntExtra("index", 0);
                getActivity().findViewById(numberList.get(currentTrack).getObjectResourceId()).setVisibility(View.GONE);
                currentTrack = index;
                play(currentTrack);
            }else if(intent.getAction() != null && intent.getAction().contentEquals(filterVolume)){
                int value = intent.getIntExtra("value", 0);
                controlSound2(value);
            }
        }
    };

     @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            callback = (NavigationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.belajarangka_activity, null);
        context = this.getContext();
        super.onCreate(savedInstanceState);
/*
        List<String> testDeviceIds = Arrays.asList("46B3B78506234A43FD5259FB6D5BA494");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder()
                        //.setTestDeviceIds(testDeviceIds)
                        .build();
        MobileAds.setRequestConfiguration(configuration);*/
         adView = root.findViewById(R.id.adNum);

        autoplayMode = false;
        animCrossFadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        animCrossFadeIn2 = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        numberImage = root.findViewById(R.id.numberImage);
        soundImage = root.findViewById(R.id.sound);
        autoplayImage = root.findViewById(R.id.autoplay);
        trackView = root.findViewById(R.id.tracklist);
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);
        objectNumber = root.findViewById(R.id.objectLayout);
        player = MediaPlayer.create(getContext(), R.raw.angka1);
        prev = (View) root.findViewById(R.id.prev);
        next = (View) root.findViewById(R.id.next);
        currentTrack = 0;
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        filterNumber = "number";
        filterVolume = "VOL";
        grassatas = root.findViewById(R.id.grassatas);
        callback.onSetImage(grassatas, R.drawable.darkgrass2b);
        int[] audios = {R.raw.angka1, R.raw.angka2, R.raw.angka3, R.raw.angka4, R.raw.angka5, R.raw.angka6, R.raw.angka72, R.raw.angka8, R.raw.angka9, R.raw.angka10,
                        R.raw.angka11, R.raw.angka12, R.raw.angka13,R.raw.angka14, R.raw.angka15, R.raw.angka16, R.raw.angka17, R.raw.angka18, R.raw.angka19, R.raw.angka20};
        int[] numberResources = {R.drawable.no1, R.drawable.no2, R.drawable.no3, R.drawable.no4, R.drawable.no5,
                                R.drawable.no6, R.drawable.no7, R.drawable.no8, R.drawable.no9, R.drawable.no10,
                                R.drawable.number_11, R.drawable.number_12, R.drawable.number_13, R.drawable.number_14, R.drawable.number_15,
                                R.drawable.number_16_1, R.drawable.number_17,  R.drawable.number_18, R.drawable.number_19, R.drawable.number_20 };
        int[] objectResources = {R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.ten,
                                 R.id.eleven, R.id.twelve, R.id.thriteen, R.id.fourteen, R.id.fifteen, R.id.sixteen, R.id.seventeen, R.id.eighteen,
                                 R.id.nineteen, R.id.twenty};

        String[] titles = {"satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh",
                            "sebelas","dua belas","tiga belas","empat belas", "lima belas","enam belas","tujuh belas","delapan belas",
                            "sembilan belas","dua puluh"};

        numberList = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            Number number = new Number(audios[x], numberResources[x], objectResources[x], titles[x]);
            numberList.add(number);
        }

        audioCheck();
        trackAdapter = new NumberAdapter(numberList, context);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);

        adView.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                Log.i("ads","Number load failed : " + i);

            }


        });

        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                volumeLevel,
                0);
        trackView.setAdapter(trackAdapter);
        trackView.setLayoutManager(layoutManager);
        soundImage.setOnClickListener(this);
        autoplayImage.setOnClickListener(this);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        home.setOnClickListener(this);
        map.setOnClickListener(this::onClick);

        numberImage.setOnClickListener(this);
        objectNumber.setOnClickListener(this);
        start();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int action = event.getAction();
                switch (keyCode) {
                    case KeyEvent.KEYCODE_VOLUME_UP:
                        if (action == KeyEvent.ACTION_DOWN) {
                            controlSound();
                        }
                        return true;
                    case KeyEvent.KEYCODE_VOLUME_DOWN:
                        if (action == KeyEvent.ACTION_DOWN) {
                            controlSound();
                        }
                        return true;

                }
                return true;
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(filterNumber);
        IntentFilter volumeFilter = new IntentFilter(filterVolume);

        getContext().registerReceiver(broadcastReceiver, intentFilter);
        getContext().registerReceiver(broadcastReceiver, volumeFilter);

        play(currentTrack);
        adView.loadAd(new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build());

    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        adView.setAdListener(null);
        adView.destroy();
    }


    public void start() {

        numberImage.startAnimation(animCrossFadeIn);
        animCrossFadeIn2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getActivity().findViewById(numberList.get(0).getObjectResourceId()).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //(animation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
                      /*  .animate()
                        .translationY(objectNumber.getHeight())
                        .
                        .alpha(1.0f)
                        .setDuration(700)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                objectNumber.setVisibility(View.VISIBLE);
                            }
                        });*/
        animCrossFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                objectNumber.startAnimation(animCrossFadeIn2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void audioCheck(){
        AudioManager audioManager =   (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        controlSound2(volumeLevel);
    }
    private void play(int index) {


        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        currentTrack = index;

        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(numberList.get(currentTrack).getAudio());
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            trackView.smoothScrollToPosition(index);
            numberImage.setImageResource(numberList.get(currentTrack).getNumberResourceId());


        } catch (Exception e) {
            Log.e("play.load", e.toString());
        }

        try {
            animCrossFadeIn2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    getActivity().findViewById(numberList.get(currentTrack).getObjectResourceId()).setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //(animation);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            numberImage.startAnimation(animCrossFadeIn);
            player.prepare();
            player.start();

        } catch (Exception e) {
            Log.e("play.prepare", e.toString());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prev:
                if (currentTrack > 0) {
                    previousTrack();
                }

                break;

            case R.id.next:
                if ((currentTrack + 1) < numberList.size()) {
                    nextTrack();
                }

                break;
            case R.id.autoplay:
                if (autoplayMode) {
                    autoplayMode = false;

                    autoplayImage.setImageResource(R.drawable.autoplayoff);
                    player.setOnCompletionListener(null);
                } else {
                    autoplayMode = true;
                    autoplayImage.setImageResource(R.drawable.replay);
                    if ((currentTrack + 1) < numberList.size()) {
                        nextTrack();
                    }
                    autoplay();
                }
                break;
            case R.id.sound:
                controlSound();
                break;
            case R.id.numberImage:
                break;

            case R.id.objectLayout:
                break;


            case R.id.home:
                callback.onHomeClicked();
                break;
            case R.id.map:
                callback.onMapClicked();
                break;
        }

    }

    private void autoplay() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (currentTrack + 1 < numberList.size()) {
                    getActivity().findViewById(numberList.get(currentTrack).getObjectResourceId()).setVisibility(View.GONE);
                    currentTrack++;
                    play(currentTrack);
                }
            }
        });

    }

    private void controlSound() {
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volumeLevel != 0) {
            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    0,
                    0);
            soundImage.setImageResource(R.drawable.mute2);
        } else {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float percent = 0.7f;
            int seventyVolume = (int) (maxVolume * percent);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
            soundImage.setImageResource(R.drawable.audio);
        }
    }

    private void controlSound2(int volumeLevel){
        if(volumeLevel == 0){
            soundImage.setImageResource(R.drawable.mute2);
        }else{
            soundImage.setImageResource(R.drawable.audio);
        }

    }

    private void previousTrack() {
        getActivity().findViewById(numberList.get(currentTrack).getObjectResourceId()).setVisibility(View.GONE);
        currentTrack--;
        play(currentTrack);

    }

    private void nextTrack() {
        getActivity().findViewById(numberList.get(currentTrack).getObjectResourceId()).setVisibility(View.GONE);
        currentTrack++;
        play(currentTrack);

    }

    public void playAnimation() {

    }


}
