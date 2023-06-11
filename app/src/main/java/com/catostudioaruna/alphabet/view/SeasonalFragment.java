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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.FruitAdapter;
import com.catostudioaruna.alphabet.adapter.NumberAdapter;
import com.catostudioaruna.alphabet.adapter.SeasonalAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Fruit;
import com.catostudioaruna.alphabet.db.Number;
import com.catostudioaruna.alphabet.db.Seasonal;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
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

public class SeasonalFragment  extends Fragment implements View.OnClickListener  {


    private Context context;
    private boolean autoplayMode;
    private ImageView soundImage;
    private ImageView autoplayImage;
    private ImageView seasonalImage;
    private ImageView bg;
    private ImageView map;
    private ImageView home;
    private NavigationListener callback;
    private AlphabetViewModel viewModel;

    private TextView seasonalName;

    private Animation animation;
    private int currentTrack;
    private RecyclerView trackView;
    private SeasonalAdapter trackAdapter;
    private MediaPlayer player;
    private Animation animCrossFadeIn;
    private Animation animCrossFadeIn2;
    private List<Seasonal> seasonals;
    private View prev;
    private View next;
    private AudioManager audioManager;
    private int volumeLevel;
    private String filterSeasonal;
    private String filterVolume;
    private Integer[] audioFiles;
    private AdView adView;
    private Bundle extras;
    private int seasonalCode; //17 Agustus = 1;


    private BroadcastReceiver farmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(filterSeasonal)) {
                int id = intent.getIntExtra("id", 0);
                int index = intent.getIntExtra("index", 0);
                String resourceName = intent.getStringExtra("rname");
                String name = intent.getStringExtra("name");
                int type = intent.getIntExtra("type", 0);
                int audio = intent.getIntExtra("audio", 0);

                int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());


                seasonalImage.setImageResource(resID);
                seasonalName.setText(name);
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

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.learning_indonesia, null);

        context = getContext().getApplicationContext();

        List<String> testDeviceIds = Arrays.asList("46B3B78506234A43FD5259FB6D5BA494");
       /* RequestConfiguration configuration =
                new RequestConfiguration.Builder()
                       // .setTestDeviceIds(testDeviceIds)
                        .build();
        MobileAds.setRequestConfiguration(configuration);*/
        adView = root.findViewById(R.id.adFarm);


        autoplayMode = false;
        filterSeasonal = "seasonal";
        filterVolume = "VOL";
        animCrossFadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        animCrossFadeIn2 = AnimationUtils.loadAnimation(context,
                R.anim.fadein);

        soundImage = (ImageView) root.findViewById(R.id.sound);
        autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
        trackView = (RecyclerView) root.findViewById(R.id.tracklist);
        seasonalName = (TextView) root.findViewById(R.id.seasonalName);
        seasonalImage = (ImageView) root.findViewById(R.id.mediumAnimal);
        audioCheck();

        player = MediaPlayer.create(context, R.raw.a);
        prev = (View) root.findViewById(R.id.prev);
        next = (View) root.findViewById(R.id.next);
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);
        bg = root.findViewById(R.id.bg);
        callback.onSetImage(bg, R.drawable.in_bg4);

        seasonalCode= 1;

        audioFiles = new Integer[]{R.raw.bendera,R.raw.garudapancasila ,R.raw.paskibra, R.raw.bungaraflesia, R.raw.taripendet,
                R.raw.tarigong,
                R.raw.barong, R.raw.janurkuning, R.raw.karapansapi, R.raw.hanoman, R.raw.wayang,
                R.raw.ondelondel, R.raw.becak, R.raw.bajaj, R.raw.suroboyo, R.raw.rumahgadang, R.raw.monas,
                R.raw.candiborobudur, R.raw.rumahtongkongan, R.raw.mudik,R.raw.balapkarung, R.raw.lombamakankerupuk,
                R.raw.panjatpinang, R.raw.tariktambang };

        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                Log.i("ads", "Seasonal load failed : " + i);

            }


        });

        currentTrack = 0;
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        trackAdapter = new SeasonalAdapter(context);


        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        viewModel.getAllSeasonal(seasonalCode).observe(this, new Observer<List<Seasonal>>() {
            @Override
            public void onChanged(List<Seasonal> ss) {
                if (ss.size() > 0) {
                    seasonals = ss;
                    trackAdapter.setTracks(seasonals);
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
        seasonalName.startAnimation(animCrossFadeIn);
        seasonalImage.startAnimation(animCrossFadeIn2);
    }

    private void audioCheck() {
        AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        controlSound2(volumeLevel);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(filterSeasonal);
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
                if ((currentTrack + 1) < seasonals.size()) {
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
                    if ((currentTrack + 1) < seasonals.size()) {
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
        seasonalImage.setVisibility(View.GONE);
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
        Log.i("Seasonal", "destroy AD");
    }

    private void nextTrack() {

        seasonalImage.setVisibility(View.GONE);
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
            Seasonal seasonal = seasonals.get(currentTrack);

            currentTrack = index;
            String resourceName = seasonal.getSourceName();
            String name = seasonal.getName();
            int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());

            seasonalImage.setImageResource(resID);
            seasonalName.setText(name);


        } catch (Exception e) {
            Log.e("play.load", e.toString());
        }

        try {

            seasonalName.startAnimation(animCrossFadeIn);
            seasonalImage.startAnimation(animCrossFadeIn2);
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
                seasonalImage.setVisibility(View.GONE);
                if (currentTrack + 1 < seasonals.size()) {
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
