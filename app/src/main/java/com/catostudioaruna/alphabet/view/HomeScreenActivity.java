package com.catostudioaruna.alphabet.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetFileDescriptor;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.catostudioaruna.alphabet.AlertReceiver;
import com.catostudioaruna.alphabet.AlphabetAppUtil;
import com.catostudioaruna.alphabet.NotifyService;
import com.catostudioaruna.alphabet.R;
import com.google.android.gms.ads.MobileAds;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class HomeScreenActivity extends AppCompatActivity implements NavigationListener, MapFragment.OnDestinationListener,

        HomeScreenFragment.HomeFragmentListener, CharacterAndVehicleSelectionFragment.OnDoneButtonListener, BriefInfoFragment.OnVisitListener {

    private HomeScreenFragment homeScreenFragment;
    private AlphabetAppUtil alphabetAppUtil;
    private MediaPlayer player ;
    private boolean isHomeMap;

    public class SettingsContentObserver extends ContentObserver {

        public SettingsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            AudioManager audioManager =   (AudioManager) getApplicationContext().getSystemService(AUDIO_SERVICE);
            int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            Intent intent = new Intent("VOL");
            intent.putExtra("value", volumeLevel);
            getApplicationContext().sendBroadcast(intent);

        }
    }

    private SettingsContentObserver settingsContentObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//
//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo("com.catostudioaruna.alphabet", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception", e.toString());
//        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.main);

        //GOOGLe ADMOB
        MobileAds.initialize(this, this.getString(R.string.YOUR_ADMOB_APP_ID));



        settingsContentObserver = new SettingsContentObserver(new Handler());
        getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, settingsContentObserver );
        alphabetAppUtil = (AlphabetAppUtil) getApplication();

        isHomeMap = false;

        if (findViewById(R.id.fragmentContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            homeScreenFragment = new HomeScreenFragment();
            homeScreenFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, homeScreenFragment).commit();

        }

        player = MediaPlayer.create(this, R.raw.lintasimusic);
        playMusic();
        //startService(new Intent(this, NotifyService.class));


    }


    private void playMusic() {

        if(player==null) {
            player = MediaPlayer.create(this, R.raw.lintasimusic);

        }

        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();

        AssetFileDescriptor assetFileDescriptor =
                this.getResources().openRawResourceFd(R.raw.lintasimusic);
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());


        } catch (Exception e) {
            Log.e("play.load", e.toString());
        }

        try {
            player.prepare();
            player.start();

        } catch (Exception e) {
            Log.e("play.prepare", e.toString());
        }
    }

    private void playMusic2() {

        if(player == null){
            player = MediaPlayer.create(this, R.raw.lintasimusic);
        }

        if (!player.isPlaying()) {
            player.reset();

            AssetFileDescriptor assetFileDescriptor =
                    this.getResources().openRawResourceFd(R.raw.lintasimusic);
            try {
                player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(),
                        assetFileDescriptor.getLength());


            } catch (Exception e) {
                Log.e("play.load", e.toString());
            }

            try {
                player.prepare();
                player.start();

            } catch (Exception e) {
                Log.e("play.prepare", e.toString());
            }
        }
    }

    @Override
    public void onStartClicked(boolean isCharacterExisted) {
        if (isCharacterExisted) {
            MapFragment mFragment = new MapFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, mFragment);
            transaction.addToBackStack(null);
            isHomeMap = true;
            transaction.commit();
        } else {
            CharacterAndVehicleSelectionFragment cFragment = new CharacterAndVehicleSelectionFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, cFragment);
            transaction.addToBackStack(null);
            isHomeMap = true;
            transaction.commit();
        }
    }

    @Override
    public void onProfilePictureClicked() {
        MyProfileFragment fragment = new MyProfileFragment();
//        cFragment.myCharacter();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onLevelLayout() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isHomeMap){
            playMusic();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player!= null && player.isPlaying()) {
            player.pause();
            player.reset();
        }


    }

    @Override
    public void onRewardClicked() {
        RewardFragment fragment = new RewardFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onSetBackgroundHome(View i, int sourceid) {

    }

    @Override
    public void onSetImageHome(ImageView i, int sourceid) {

        alphabetAppUtil.setImage(i,sourceid);
    }

    @Override
    public void onDoneClicked() {
        MapFragment mFragment = new MapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mFragment);
        transaction.addToBackStack(null);
        isHomeMap = true;
        transaction.commit();
    }

    @Override
    public void onDestinationClicked(int dest) {
        BriefInfoFragment briefInfoFragment = new BriefInfoFragment();
        Bundle args = new Bundle();
        args.putInt("dest", dest);
        briefInfoFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, briefInfoFragment);
        transaction.addToBackStack(null);
        isHomeMap = true;
        transaction.commit();

    }

    @Override
    public void onVisitClicked(int destination) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (player!= null && player.isPlaying()) {
            player.pause();
            player.reset();
        }

        switch (destination) {
            case 1:
                ZooFragment z = new ZooFragment();
                Bundle args = new Bundle();
                args.putInt("dest", destination);
                z.setArguments(args);

                transaction.replace(R.id.fragmentContainer, z);
                transaction.addToBackStack(null);
                transaction.commit();
                isHomeMap = false;

                break;

            case 2:


                NumberFragment b = new NumberFragment();
                Bundle args2 = new Bundle();
                args2.putInt("dest", destination);
                b.setArguments(args2);

                transaction.replace(R.id.fragmentContainer, b);
                transaction.addToBackStack(null);

                transaction.commit();
                isHomeMap = false;
                break;

            case 3:
                FarmFragment f = new FarmFragment();
                Bundle args3 = new Bundle();
                args3.putInt("dest", destination);
                f.setArguments(args3);

                transaction.replace(R.id.fragmentContainer, f);
                transaction.addToBackStack(null);

                transaction.commit();
                isHomeMap = false;
                break;
            case 4:
                LibraryFragment l = new LibraryFragment();
                Bundle args4 = new Bundle();
                args4.putInt("dest", destination);
                l.setArguments(args4);

                transaction.replace(R.id.fragmentContainer, l);
                transaction.addToBackStack(null);

                transaction.commit();
                isHomeMap = false;
                break;
            case 5:
                QuizFragment q = new QuizFragment();
                Bundle args5 = new Bundle();
                args5.putInt("dest", destination);
                q.setArguments(args5);

                transaction.replace(R.id.fragmentContainer, q);
                transaction.addToBackStack(null);

                transaction.commit();
                isHomeMap = false;
                break;

            case 6:
                SeasonalFragment s = new SeasonalFragment();
                Bundle args6 = new Bundle();
                args6.putInt("dest", destination);
                s.setArguments(args6);

                transaction.replace(R.id.fragmentContainer, s);
                transaction.addToBackStack(null);

                transaction.commit();
                isHomeMap = false;
                break;
        }

    }

    @Override
    public void onHomeClicked() {
        isHomeMap = true ;
        playMusic2();
        alphabetAppUtil.unloadBitmap();
        HomeScreenFragment hFragment = new HomeScreenFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, hFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onMapClicked() {
        isHomeMap = true;
        playMusic2();
        MapFragment mFragment = new MapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    @Override
    public void onSetBackground(View i, int sourceid) {
        alphabetAppUtil.setBackground(i,sourceid);
    }

    @Override
    public void onSetImage(ImageView i, int sourceid) {

        alphabetAppUtil.setImage(i, sourceid);
    }

    @Override
    public void onSetImage2(ImageView i, int sourceid) {
        alphabetAppUtil.setImage2(i, sourceid);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        int action = event.getAction();
        AudioManager audioManager =   (AudioManager) this.getSystemService(AUDIO_SERVICE);

         Intent intent = new Intent();
        intent.setAction("VOL");

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                    int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                    intent.putExtra("value", volumeLevel);
                    this.sendBroadcast(intent);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 1);
                    int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                    intent.putExtra("value", volumeLevel);
                    this.sendBroadcast(intent);
                }
                return true;

        }
        return true;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        getContentResolver().unregisterContentObserver(settingsContentObserver);
    }
}
