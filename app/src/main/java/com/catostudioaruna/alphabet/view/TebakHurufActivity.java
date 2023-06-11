package com.catostudioaruna.alphabet.view;

import android.animation.Animator;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.TebakHuruf;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Sticker;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class TebakHurufActivity extends AppCompatActivity implements View.OnClickListener {
    private Integer[] hurufs;
    private ImageView choice1;
    private ImageView choice2;
    private ImageView playButton;
    private ImageView soundButton;
    private LottieAnimationView animation1;
    private LottieAnimationView animation2;
    private LottieAnimationView endAnimation;
    private Integer[] audios;
    private List<TebakHuruf> tebaks;
    private int index;
    private int count;
    private MediaPlayer player;
    private Context context;
    private InterstitialAd interstitialAd;
    private TextView replayText;
    private Bundle extras;
    private AdRequest adRequest;
    private int score;
    private int batas;
    private TextView scoreTV;
    private ImageView bstar1;
    private ImageView bstar2;
    private ImageView bstar3;
    private ImageView bstar4;
    private ImageView bstar5;
    private ImageView[] stars;
    private View bg;
    private LinearLayout papanscore;
    private LinearLayout stickerNotif;
    private ImageView stickerImageView;
    private TextView klaimTV;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private ImageView[] finalstars;
    private TextView finalScoreTV;
    private int roundCounter;
    private int roundScore;
    private AlphabetViewModel viewModel;
    private Sticker sticker;
    private AudioManager audioManager;
    private int volumeLevel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.tebakhuruf_activity);
        score = 0;
        batas = 5;
        roundCounter = 0;
        roundScore = 0;
        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);

        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();

        context = this.getApplicationContext();
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


        choice1 = (ImageView) findViewById(R.id.choice1);
        choice2 = (ImageView) findViewById(R.id.choice2);
        animation1 = (LottieAnimationView) findViewById(R.id.animation1);
        animation2 = (LottieAnimationView) findViewById(R.id.animation2);
        endAnimation = (LottieAnimationView) findViewById(R.id.animationEnd);
        playButton = (ImageView) findViewById(R.id.play);
        soundButton = (ImageView) findViewById(R.id.sound);
        replayText = (TextView) findViewById(R.id.replay);
        scoreTV = (TextView) findViewById(R.id.scoreb);
        bstar1 = (ImageView) findViewById(R.id.bs1);
        bstar2 = (ImageView) findViewById(R.id.bs2);
        bstar3 = (ImageView) findViewById(R.id.bs3);
        bstar4 = (ImageView) findViewById(R.id.bs4);
        bstar5 = (ImageView) findViewById(R.id.bs5);
        bg = (View) findViewById(R.id.bg);
        papanscore = (LinearLayout) findViewById(R.id.papanscore);
        finalScoreTV = (TextView) findViewById(R.id.score);
        star1 = (ImageView) findViewById(R.id.s1);
        star2 = (ImageView) findViewById(R.id.s2);
        star3 = (ImageView) findViewById(R.id.s3);
        star4 = (ImageView) findViewById(R.id.s4);
        star5 = (ImageView) findViewById(R.id.s5);
        stickerImageView = (ImageView) findViewById(R.id.sticker);
        klaimTV = (TextView) findViewById(R.id.terima);
        stickerNotif = (LinearLayout) findViewById(R.id.stickernotif);
        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        viewModel.getReward().observe(this, new Observer<Sticker>() {
            @Override
            public void onChanged(Sticker s) {
                sticker = s;
                if (sticker != null) {
                    stickerImageView.setImageResource(sticker.getSourceId());
                }
            }
        });

        //     0           1             2             3             4              5             6            7             8                9
        hurufs = new Integer[]{R.drawable.b, R.drawable.c, R.drawable.g, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u};
        audios = new Integer[]{R.raw.tebakb, R.raw.tebakc, R.raw.tebakg, R.raw.tebakk, R.raw.tebakl, R.raw.tebakm, R.raw.tebakr, R.raw.tebaks, R.raw.tebakt, R.raw.tebaku, R.raw.happiness};
        tebaks = new ArrayList<>();

        TebakHuruf tebakHuruf1 = new TebakHuruf(hurufs[0], hurufs[1], hurufs[0], audios[0]);
        TebakHuruf tebakHuruf2 = new TebakHuruf(hurufs[0], hurufs[1], hurufs[1], audios[1]);
        TebakHuruf tebakHuruf3 = new TebakHuruf(hurufs[2], hurufs[5], hurufs[2], audios[2]);
        TebakHuruf tebakHuruf4 = new TebakHuruf(hurufs[7], hurufs[3], hurufs[3], audios[3]);
        TebakHuruf tebakHuruf5 = new TebakHuruf(hurufs[1], hurufs[4], hurufs[4], audios[4]);
        TebakHuruf tebakHuruf6 = new TebakHuruf(hurufs[5], hurufs[0], hurufs[5], audios[5]);
        TebakHuruf tebakHuruf7 = new TebakHuruf(hurufs[6], hurufs[8], hurufs[6], audios[6]);
        TebakHuruf tebakHuruf8 = new TebakHuruf(hurufs[2], hurufs[7], hurufs[7], audios[7]);
        TebakHuruf tebakHuruf9 = new TebakHuruf(hurufs[9], hurufs[8], hurufs[8], audios[8]);
        TebakHuruf tebakHuruf10 = new TebakHuruf(hurufs[0], hurufs[9], hurufs[9], audios[9]);
        index = 0;
        count = 0;

        stars = new ImageView[]{bstar1, bstar2, bstar3, bstar4, bstar5};
        finalstars = new ImageView[]{star1, star2, star3, star4, star5};
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        tebaks.add(tebakHuruf1);
        tebaks.add(tebakHuruf2);
        tebaks.add(tebakHuruf3);
        tebaks.add(tebakHuruf4);
        tebaks.add(tebakHuruf5);
        tebaks.add(tebakHuruf6);
        tebaks.add(tebakHuruf7);
        tebaks.add(tebakHuruf8);
        tebaks.add(tebakHuruf9);
        tebaks.add(tebakHuruf10);

        Collections.shuffle(tebaks);

        player = MediaPlayer.create(this, tebaks.get(index).getQuestion());
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


        animation1.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation1.setVisibility(View.GONE);

                if (index < batas) {
                    play(index);
                } else {
                    finishGame();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animation2.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                animation2.setVisibility(View.GONE);
                if (index < batas) {
                    play(index);
                } else {
                    finishGame();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        choice1.setEnabled(true);
        choice2.setEnabled(true);
        playButton.setEnabled(true);
        soundButton.setEnabled(true);
        replayText.setClickable(true);
        choice1.setClickable(true);
        choice2.setClickable(true);
        playButton.setClickable(true);
        soundButton.setClickable(true);
        klaimTV.setClickable(true);
        klaimTV.setOnClickListener(this);

        choice1.setOnClickListener(this::onClick);
        choice2.setOnClickListener(this::onClick);
        playButton.setOnClickListener(this::onClick);
        soundButton.setOnClickListener(this::onClick);
        replayText.setOnClickListener(this::onClick);
        play(0);
    }

    private void finishGame() {

        if (roundCounter < 2) {
            roundCounter++;
            roundScore = roundScore + score;

            if (roundScore >= 800) {
                viewModel.getReward().observe(this, new Observer<Sticker>() {
                    @Override
                    public void onChanged(Sticker s) {
                        sticker = s;
                        stickerImageView.setImageResource(sticker.getSourceId());
                    }
                });
                stickerNotif.setVisibility(View.VISIBLE);
                roundCounter = 0;
                roundScore = 0;
            }

        } else {
            roundCounter = 0;
            roundScore = 0;
            roundCounter++;
            roundScore = roundScore + score;
        }


        playButton.setEnabled(false);
        soundButton.setEnabled(false);
        choice1.setEnabled(false);
        choice2.setEnabled(false);

        endAnimation.setVisibility(View.VISIBLE);
        replayText.setVisibility(View.VISIBLE);
        bg.setVisibility(View.VISIBLE);
        papanscore.setVisibility(View.VISIBLE);
        finalScoreTV.setText(score + "");
        playEndGameMusic();
        endAnimation.playAnimation();

    }

    private void playEndGameMusic() {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(audios[10]);
        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
        } catch (Exception e) {
            Log.e("play ", e.getMessage());
        }

        try {
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e("prev.prepare","why : "+ e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.choice1:
                if (index < batas) {
                    choice1.setEnabled(false);
                    choice2.setEnabled(false);
                    reveal(1);
                    index++;
                }

                break;

            case R.id.choice2:
                if (index < batas) {
                    choice1.setEnabled(false);
                    choice2.setEnabled(false);
                    reveal(2);
                    index++;
                }
                break;

            case R.id.play:
                play();
                break;
            case R.id.sound:
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
            case R.id.replay:
                resetGame();
                break;
            case R.id.terima:
                stickerNotif.setVisibility(View.GONE);
                sticker.setCollected(true);
                viewModel.updateSticker(sticker);
                break;
        }
    }

    private void reveal(int userAnswer) {
        if (tebaks.get(index).getAnswer() == tebaks.get(index).getChoice1()) {
            animation1.setVisibility(View.VISIBLE);
            animation1.playAnimation();
            if (userAnswer == 1) {
                score = score + 100;
                scoreTV.setText("" + score);
                stars[(score / 100) - 1].setVisibility(View.VISIBLE);
                finalstars[(score / 100) - 1].setVisibility(View.VISIBLE);
            }

        } else {
            animation2.setVisibility(View.VISIBLE);
            animation2.playAnimation();
            if (userAnswer == 2) {
                score = score + 100;
                scoreTV.setText("" + score);
                stars[(score / 100) - 1].setVisibility(View.VISIBLE);
                finalstars[(score / 100) - 1].setVisibility(View.VISIBLE);
            }
        }
    }

    private void play() {
        if (!player.isPlaying()) {
            play(index);
        }


    }


    private void play(int index) {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(tebaks.get(index).getQuestion());

        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
        } catch (Exception e) {
            Log.e("play " + index, e.toString());
        }
        choice1.setImageResource(tebaks.get(index).getChoice1());
        choice2.setImageResource(tebaks.get(index).getChoice2());
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        try {
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e("prev.prepare", e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (endAnimation.getVisibility() == View.GONE) {
            play(index);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        index = 0;

        Collections.shuffle(tebaks);
        playButton.setEnabled(true);
        soundButton.setEnabled(true);
        choice1.setImageResource(tebaks.get(index).getChoice1());
        choice2.setImageResource(tebaks.get(index).getChoice2());
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        endAnimation.setVisibility(View.GONE);
        replayText.setVisibility(View.GONE);
        for (int x = 0; x < batas; x++) {
            stars[x].setVisibility(View.GONE);
            finalstars[x].setVisibility(View.GONE);
        }
        bg.setVisibility(View.GONE);
        papanscore.setVisibility(View.GONE);
        score = 0;
        scoreTV.setText("");
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();
        interstitialAd.loadAd(adRequest);


    }
}
