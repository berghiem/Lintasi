package com.catostudioaruna.alphabet.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.AnimalAdapter;
import com.catostudioaruna.alphabet.adapter.NumberAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Animal;
import com.catostudioaruna.alphabet.db.Number;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.AUDIO_SERVICE;

public class ZooFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private boolean autoplayMode;
    private ImageView soundImage;
    private ImageView autoplayImage;
    private ImageView home;
    private ImageView map;
    private ImageView mediumA;
    private AlphabetViewModel viewModel;
    private FrameLayout frameBg;

    private TextView animName;

    private NavigationListener callback;
    private Animation animation;
    private int currentTrack;
    private RecyclerView trackView;
    private AnimalAdapter trackAdapter;
    private MediaPlayer player;
    private Animation animCrossFadeIn;
    private Animation animCrossFadeIn2;
    private List<Animal> animalList;
    private View prev;
    private View next;
    private AudioManager audioManager;
    private int volumeLevel;
    private String filterVolume;
    private String filterAnimal;

    private Integer[] audioFiles;
    private AdView adView;
    private Bundle extras;

    private BroadcastReceiver  zooReceiver = new  BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(filterAnimal)) {
                int id = intent.getIntExtra("id", 0);
                int index = intent.getIntExtra("index", 0);
                String resourceName = intent.getStringExtra("rname");
                Log.i("resource",resourceName);
                String name = intent.getStringExtra("name");
                int type = intent.getIntExtra("type", 0);
                int audio = intent.getIntExtra("audio", 0);
                int source = intent.getIntExtra("sourceId",0);
                int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
               // mediumA.setImageResource(resID);
                callback.onSetImage(mediumA,source);
                animName.setText(name);
                currentTrack = index;
                play(currentTrack);

            } else if (intent.getAction() != null && intent.getAction().contentEquals(filterVolume)) {
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.learning_anim, null);
        context = getContext().getApplicationContext();
        List<String> testDeviceIds = Arrays.asList("46B3B78506234A43FD5259FB6D5BA494");
        /*RequestConfiguration configuration =
                new RequestConfiguration.Builder()
                        //.setTestDeviceIds(testDeviceIds)
                        .build();
        MobileAds.setRequestConfiguration(configuration);*/

        autoplayMode = false;
        filterAnimal = "animal";
        filterVolume = "VOL";
        animCrossFadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        animCrossFadeIn2 = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        mediumA = (ImageView) root.findViewById(R.id.mediumAnimal);
         adView = root.findViewById(R.id.adZoo);

        soundImage = (ImageView) root.findViewById(R.id.sound);
        autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
        home = (ImageView) root.findViewById(R.id.home);
        map = (ImageView) root.findViewById(R.id.map);

        trackView = (RecyclerView) root.findViewById(R.id.tracklist);
        animName = (TextView) root.findViewById(R.id.name);

        player = MediaPlayer.create(context, R.raw.a);
        prev = (View) root.findViewById(R.id.prev);
        next = (View) root.findViewById(R.id.next);
        frameBg = (FrameLayout) root.findViewById(R.id.zooBg);
        callback.onSetBackground(frameBg,R.drawable.zoo_bg);
        audioCheck();
        currentTrack = 0;
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        trackAdapter = new AnimalAdapter(context);
        audioFiles = new Integer[]{R.raw.ayamhp, R.raw.anjiunghp, R.raw.babihp, R.raw.badakhp, R.raw.bantenghp, R.raw.bebekhp,
                                   R.raw.belalanghp, R.raw.beruanghp, R.raw.burunghp, R.raw.buayahp, R.raw.burunguntahp,
                                   R.raw.dinohp, R.raw.dombahp, R.raw.flamingohp, R.raw.gajahp, R.raw.harimauhp, R.raw.iguanahp,
                                   R.raw.itikhp, R.raw.ikanhp, R.raw.jerapahp, R.raw.kljhp, R.raw.kambinghp, R.raw.kanguruhp,
                                   R.raw.kelincihp, R.raw.kepitinghp, R.raw.kijanghpp, R.raw.koalahp, R.raw.kucinghp, R.raw.kudahp,
                                   R.raw.kudanilhp, R.raw.kodokhp, R.raw.kupukupuhp, R.raw.kurakurahp, R.raw.landakhp, R.raw.lebahhp,
                                   R.raw.macanhp, R.raw.monyethp, R.raw.ontahp, R.raw.pandahp, R.raw.pinguinhp, R.raw.runahhp,
                                   R.raw.rusahp, R.raw.sapihp, R.raw.semuthp, R.raw.serigalahp, R.raw.singaahp, R.raw.tupaihp,
                                   R.raw.ularhp, R.raw.ulathp, R.raw.zebrahp };


        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);


        adView.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                Log.i("ads","Zoo load failed : " + i);

            }


        });
        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        viewModel.getAllAnimall().observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(List<Animal> animals) {
                if (animals.size() > 0) {
                    animalList = animals;
                    trackAdapter.setTracks(animalList);
                }
            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trackView.setAdapter(trackAdapter);
        trackView.setLayoutManager(layoutManager);

        prev.setOnClickListener(this::onClick);
        next.setOnClickListener(this::onClick);
        home.setOnClickListener(this::onClick);
        map.setOnClickListener(this::onClick);
        soundImage.setOnClickListener(this);
        autoplayMode = false;
        autoplayImage.setOnClickListener(this::onClick);
        start();
        return root;
    }

    private void start() {

        animName.startAnimation(animCrossFadeIn);
        mediumA.startAnimation(animCrossFadeIn2);

    }

    private void audioCheck(){
        AudioManager audioManager =   (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        controlSound2(volumeLevel);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter fAnimal = new IntentFilter(filterAnimal);
        IntentFilter fVol = new IntentFilter(filterVolume);

        this.getContext().registerReceiver(zooReceiver, fAnimal);
        this.getContext().registerReceiver(zooReceiver, fVol);

        play(currentTrack);
        adView.loadAd(new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build());

    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(zooReceiver);
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        adView.setAdListener(null);
        adView.destroy();

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
                if ((currentTrack + 1) < animalList.size()) {
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
                    if ((currentTrack + 1) < animalList.size()) {
                        nextTrack();
                    }
                    autoplay();
                }
                break;

            case R.id.home:
                callback.onHomeClicked();
                break;

            case R.id.sound:
                controlSound();
                break;

            case R.id.map:
                callback.onMapClicked();
                break;
        }
    }

    public void previousTrack() {
        mediumA.setVisibility(View.GONE);
        currentTrack--;
        play(currentTrack);
    }

    private void nextTrack() {

        mediumA.setVisibility(View.GONE);
        currentTrack++;
        play(currentTrack);
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
    public void play(int index) {

        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        currentTrack = index;
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(audioFiles[index]);

        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());

            trackView.smoothScrollToPosition(index);
            Animal animal = animalList.get(currentTrack);


            int id = animal.getId();
            String resourceName = animal.getSourceName();
            String name = animal.getName();

            int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());

            mediumA.setImageResource(resID);

            animName.setText(name);


        } catch (Exception e) {
            Log.e("play.load", e.toString());
        }

        try {

            animName.startAnimation(animCrossFadeIn);
            mediumA.startAnimation(animCrossFadeIn2);
            player.prepare();
            player.start();

        } catch (Exception e) {
            Log.e("play.prepare", e.toString());
        }


    }

    private void autoplay() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediumA.setVisibility(View.GONE);
                if (currentTrack + 1 < animalList.size()) {
                    currentTrack++;
                    play(currentTrack);
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
    }

    public void maskingToBlack() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);


        float[] colorMatrix = {
                0, 0, 0, 0, -128 * 255,
                0, 0, 0, 0, -128 * 255,
                0, 0, 0, 0, -128 * 255,
                0, 0, 0, 1, 0  //alpha    autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
        };

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        mediumA.setColorFilter(filter);
    }
}
