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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.AnimalAdapter;
import com.catostudioaruna.alphabet.adapter.FruitAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Animal;
import com.catostudioaruna.alphabet.db.Fruit;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.AUDIO_SERVICE;

public class FarmFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private boolean autoplayMode;
    private ImageView soundImage;
    private ImageView autoplayImage;
    private ImageView fruitImage;
    private ImageView map;
    private ImageView home;
    private ImageView farmBawah;
    private NavigationListener callback;
    private AlphabetViewModel viewModel;

    private TextView fruitName;

    private Animation animation;
    private int currentTrack;
    private RecyclerView trackView;
    private FruitAdapter trackAdapter;
    private MediaPlayer player;
    private Animation animCrossFadeIn;
    private Animation animCrossFadeIn2;
    private List<Fruit> fruits;
    private View prev;
    private View next;
    private AudioManager audioManager;
    private int volumeLevel;
    private String filterAnimal;
    private String filterVolume;
    private Integer[] audioFiles;
    private AdView adView;
    private Bundle extras;


    private BroadcastReceiver farmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(filterAnimal)) {
                int id = intent.getIntExtra("id", 0);
                int index = intent.getIntExtra("index", 0);
                String resourceName = intent.getStringExtra("rname");
                String name = intent.getStringExtra("name");
                int type = intent.getIntExtra("type", 0);
                int audio = intent.getIntExtra("audio", 0);

                int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());


                fruitImage.setImageResource(resID);
                fruitName.setText(name);
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

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.learning_vegs, null);

        context = getContext().getApplicationContext();

        List<String> testDeviceIds = Arrays.asList("46B3B78506234A43FD5259FB6D5BA494");
       /* RequestConfiguration configuration =
                new RequestConfiguration.Builder()
                       // .setTestDeviceIds(testDeviceIds)
                        .build();
        MobileAds.setRequestConfiguration(configuration);*/
        adView = root.findViewById(R.id.adFarm);


        autoplayMode = false;
        filterAnimal = "animal";
        filterVolume = "VOL";
        animCrossFadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        animCrossFadeIn2 = AnimationUtils.loadAnimation(context,
                R.anim.fadein);

        soundImage = (ImageView) root.findViewById(R.id.sound);
        autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
        trackView = (RecyclerView) root.findViewById(R.id.tracklist);
        fruitName = (TextView) root.findViewById(R.id.fruitName);
        fruitImage = (ImageView) root.findViewById(R.id.fruitImage);
        audioCheck();

        player = MediaPlayer.create(context, R.raw.a);
        prev = (View) root.findViewById(R.id.prev);
        next = (View) root.findViewById(R.id.next);
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);
        farmBawah = root.findViewById(R.id.farmBawah);
        callback.onSetImage(farmBawah, R.drawable.farm_bawah_2);

        audioFiles = new Integer[]{R.raw.alpukat, R.raw.anggur, R.raw.apel, R.raw.bawangbombay, R.raw.bawangputih,
                R.raw.bawangmerah, R.raw.belimbing, R.raw.bit, R.raw.brokoli, R.raw.cabai,
                R.raw.ceri, R.raw.delima, R.raw.durian, R.raw.jagung, R.raw.jamur, R.raw.jeruk,
                R.raw.kelapa, R.raw.kiwi, R.raw.labu,R.raw.leci, R.raw.lemon, R.raw.lobak, R.raw.mangga, R.raw.manggis,
                R.raw.markisa, R.raw.melon, R.raw.naga, R.raw.pepaya, R.raw.pir, R.raw.pisang, R.raw.rambutan,
                R.raw.semangka, R.raw.strawberi, R.raw.terong, R.raw.timun, R.raw.tomat, R.raw.wortel};

        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);



        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                Log.i("ads", "Farm load failed : " + i);

            }


        });

        currentTrack = 0;
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        trackAdapter = new FruitAdapter(context);


        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        viewModel.getAllFruit().observe(this, new Observer<List<Fruit>>() {
            @Override
            public void onChanged(List<Fruit> fs) {
                if (fs.size() > 0) {
                    fruits = fs;
                    trackAdapter.setTracks(fruits);
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
        autoplayMode = false;
        autoplayImage.setOnClickListener(this::onClick);
        soundImage.setOnClickListener(this);
        start();
        return root;
    }

    private void start() {
        fruitName.startAnimation(animCrossFadeIn);
        fruitImage.startAnimation(animCrossFadeIn2);
    }

    private void audioCheck() {
        AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        controlSound2(volumeLevel);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(filterAnimal);
        IntentFilter filterVol = new IntentFilter(filterVolume);

        this.getContext().registerReceiver(farmReceiver, filter);
        this.getContext().registerReceiver(farmReceiver, filterVol);

        play(currentTrack);
        adView.loadAd(new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build());
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
                if ((currentTrack + 1) < fruits.size()) {
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
                    if ((currentTrack + 1) < fruits.size()) {
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
        fruitImage.setVisibility(View.GONE);
        currentTrack--;
        play(currentTrack);
    }

    @Override
    public void onPause() {
        super.onPause();

        this.getContext().unregisterReceiver(farmReceiver);
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        adView.setAdListener(null);
        adView.destroy();
        Log.i("Farm", "destroy AD");
    }

    private void nextTrack() {

        fruitImage.setVisibility(View.GONE);
        currentTrack++;
        play(currentTrack);
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
            Fruit fruit = fruits.get(currentTrack);

            currentTrack = index;
            String resourceName = fruit.getSourceName();
            String name = fruit.getName();
            int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());

            fruitImage.setImageResource(resID);
            fruitName.setText(name);


        } catch (Exception e) {
            Log.e("play.load", e.toString());
        }

        try {

            fruitName.startAnimation(animCrossFadeIn);
            fruitImage.startAnimation(animCrossFadeIn2);
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
                fruitImage.setVisibility(View.GONE);
                if (currentTrack + 1 < fruits.size()) {
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


    private void controlSound2(int volumeLevel) {
        if (volumeLevel == 0) {
            soundImage.setImageResource(R.drawable.mute2);
        } else {
            soundImage.setImageResource(R.drawable.audio);
        }

    }


}
