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
import com.catostudioaruna.alphabet.adapter.AlphabetAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Animal;
import com.catostudioaruna.alphabet.db.Fruit;
import com.catostudioaruna.alphabet.db.Huruf;
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

public class LibraryFragment extends Fragment implements View.OnClickListener {

    private TextView hurufText;
    private ImageView hurufImage;
    private View next;
    private View prev;
    private ImageView soundImage;
    private ImageView autoplayImage;
    private boolean autoplayMode;
    private RecyclerView trackView;
    private AlphabetAdapter trackAdapter;
    private MediaPlayer player;
    private Context context;
    private Animation animCrossFadeIn;
    private Animation animCrossFadeIn2;
    private List<Huruf> hurufList;
    private AudioManager audioManager;
    private int currentTrack;
    private int volumeLevel;
    private AlphabetViewModel viewModel;
    private ImageView home;
    private ImageView map;
    private ImageView bukubawah;
    private NavigationListener callback;
    private String filterVolume;
    private String filterHuruf;
    private Integer[] audioFiles;
    private AdView adView;
    private Bundle extras;
    private AdRequest adRequest;

    private BroadcastReceiver libraryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(filterHuruf)) {
                int id = intent.getIntExtra("id", 0);
                int index = intent.getIntExtra("index", 0);
                String resourceName = intent.getStringExtra("rname");
                String name = intent.getStringExtra("name");
                int audio = intent.getIntExtra("audio", 0);
                String iconName = intent.getStringExtra("iconName");

                int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());


                hurufImage.setImageResource(resID);
                hurufText.setText(name);
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


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.learning_alphabet, null);
        context = getContext().getApplicationContext();

        List<String> testDeviceIds = Arrays.asList("46B3B78506234A43FD5259FB6D5BA494");
       /* RequestConfiguration configuration =
                new RequestConfiguration.Builder()
                        //.setTestDeviceIds(testDeviceIds)
                        .build();
        MobileAds.setRequestConfiguration(configuration);*/
         adView = root.findViewById(R.id.adLibr);

        autoplayMode = false;
        animCrossFadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fadein);
        animCrossFadeIn2 = AnimationUtils.loadAnimation(context,
                R.anim.fadein);

        filterHuruf = "huruf";
        filterVolume = "VOL";

        soundImage = (ImageView) root.findViewById(R.id.sound);
        autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
        trackView = (RecyclerView) root.findViewById(R.id.tracklist);
        hurufText = (TextView) root.findViewById(R.id.huruf);
        hurufImage = (ImageView) root.findViewById(R.id.hurufImage);
        bukubawah =  root.findViewById(R.id.bukubawah);

        player = MediaPlayer.create(context, R.raw.a);
        prev = (View) root.findViewById(R.id.prev);
        next = (View) root.findViewById(R.id.next);
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);
        currentTrack = 0;
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        trackAdapter = new AlphabetAdapter(context);
        audioFiles = new Integer[]{R.raw.a, R.raw.b, R.raw.c, R.raw.d, R.raw.e, R.raw.f, R.raw.g, R.raw.h, R.raw.i, R.raw.jjaket
                , R.raw.kkauskaki, R.raw.l, R.raw.m, R.raw.n, R.raw.o, R.raw.ppensil, R.raw.q, R.raw.rroti, R.raw.s, R.raw.t, R.raw.u
                , R.raw.v, R.raw.w, R.raw.x, R.raw.y, R.raw.z};

        callback.onSetImage(bukubawah,R.drawable.l_bukubawah);
        audioCheck();
        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);


        adView.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                        .build());
                Log.i("ads","Library : load failed : " + i);

            }


        });

        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        viewModel.getAllHuruf().observe(this, new Observer<List<Huruf>>() {
            @Override
            public void onChanged(List<Huruf> fs) {
                if (fs.size() > 0) {
                    hurufList = fs;
                    trackAdapter.setTracks(hurufList);
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
        hurufText.startAnimation(animCrossFadeIn);
        hurufImage.startAnimation(animCrossFadeIn2);
    }

    private void audioCheck(){
        AudioManager audioManager =   (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        controlSound2(volumeLevel);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(filterHuruf);
        IntentFilter filterVol = new IntentFilter(filterVolume);

        this.getContext().registerReceiver(libraryReceiver, filter);
        this.getContext().registerReceiver(libraryReceiver, filterVol);

        play(currentTrack);
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        adView.loadAd(adRequest);
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
                if ((currentTrack + 1) < hurufList.size()) {
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
                    if ((currentTrack + 1) < hurufList.size()) {
                        nextTrack();
                    }
                    autoplay();
                }
                break;

            case R.id.map:
                callback.onMapClicked();
                break;

            case R.id.home:
                callback.onHomeClicked();
                break;

            case R.id.sound:
                controlSound();
                break;
        }
    }

    public void previousTrack() {
        hurufImage.setVisibility(View.GONE);
        currentTrack--;
        play(currentTrack);
    }

    private void nextTrack() {

        hurufImage.setVisibility(View.GONE);
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
                context.getResources().openRawResourceFd(audioFiles[currentTrack]);
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            trackView.smoothScrollToPosition(index);

            Huruf huruf = hurufList.get(currentTrack);
            String resourceName = huruf.getSourceName();
            String name = huruf.getName();
            int resID = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());

            hurufImage.setImageResource(resID);
            hurufText.setText(name);


        } catch (Exception e) {
            Log.e("play.load", e.toString());
        }

        try {

            hurufImage.startAnimation(animCrossFadeIn);
            hurufText.startAnimation(animCrossFadeIn2);
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
                hurufImage.setVisibility(View.GONE);
                if (currentTrack + 1 < hurufList.size()) {
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

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(libraryReceiver);
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        adView.setAdListener(null);
        adView.destroy();
    }


}
