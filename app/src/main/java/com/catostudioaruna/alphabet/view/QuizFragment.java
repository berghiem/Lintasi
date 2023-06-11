package com.catostudioaruna.alphabet.view;

import android.animation.Animator;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Animal;
import com.catostudioaruna.alphabet.db.Badge;
import com.catostudioaruna.alphabet.db.Fruit;
import com.catostudioaruna.alphabet.db.Huruf;
import com.catostudioaruna.alphabet.db.User;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class QuizFragment extends Fragment implements View.OnClickListener {


    private Context context;
    private MediaPlayer player;
    private TextView description;
    private TextView question;
    private ImageView questionImage;
    private TextView a1;
    private TextView a2;
    private TextView a3;
    private TextView scorebawah;
    private TextView replayText;
    private TextView finalScoreTV;
    private View bg;
    private LinearLayout numberLayout;
    private LinearLayout highscoreLayout;
    private ImageView stickerIcon;
    private ImageView rewardImage;
    private TextView terimaTV;
    private User user;
    private Badge badge;
    private int lastLevelUser;

    private ImageView choice1;
    private ImageView choice2;
    private ImageView choice3;

    private ImageView home;
    private ImageView map;
    private LottieAnimationView animation1;
    private LottieAnimationView animation2;
    private LottieAnimationView animation3;
    private LottieAnimationView endAnimation;
    private LottieAnimationView endAnimationHS;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private ImageView starbawah1;
    private ImageView starbawah2;
    private ImageView starbawah3;
    private ImageView starbawah4;
    private ImageView starbawah5;
    private ImageView[] starsBawah;
    private ImageView[] starFinal;
    private int[] objectResources;
    private int temp;
    private View[] vs;

    private ImageView[] choices;
    private LottieAnimationView[] animationLottie;
    private int roundCounter;
    private int roundScore;
    private int count;
    private int rightAnswer;
    private LinearLayout papanscore;
    private LinearLayout stickerNotif;
    private ImageView stickerImageView;
    private TextView klaimTV;

    private NavigationListener callback;

    private AlphabetViewModel viewModel;

    private Bundle extras;
    private AdView adView;
    private AdRequest adRequest;
    private int resID2;


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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.quiz, null);
        List<String> testDeviceIds = Arrays.asList("46B3B78506234A43FD5259FB6D5BA494");

       /* RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);*/

        adView = root.findViewById(R.id.adQuiz);
        resID2 = 0;
        context = this.getContext();
        description = root.findViewById(R.id.desc);
        question = root.findViewById(R.id.q);
        a1 = root.findViewById(R.id.a1);
        a2 = root.findViewById(R.id.a2);
        a3 = root.findViewById(R.id.a3);
        choice1 = root.findViewById(R.id.choice1);
        choice2 = root.findViewById(R.id.choice2);
        choice3 = root.findViewById(R.id.choice3);
        questionImage = root.findViewById(R.id.qImage);

        lastLevelUser = 0;

        scorebawah = root.findViewById(R.id.scoreb);
        starbawah1 = root.findViewById(R.id.bs1);
        starbawah2 = root.findViewById(R.id.bs2);
        starbawah3 = root.findViewById(R.id.bs3);
        starbawah4 = root.findViewById(R.id.bs4);
        starbawah5 = root.findViewById(R.id.bs5);
        starsBawah = new ImageView[]{starbawah1, starbawah2, starbawah3, starbawah4, starbawah5};

        star1 = root.findViewById(R.id.s1);
        star2 = root.findViewById(R.id.s2);
        star3 = root.findViewById(R.id.s3);
        star4 = root.findViewById(R.id.s4);
        star5 = root.findViewById(R.id.s5);
        starFinal = new ImageView[]{star1, star2, star3, star4, star5};
        numberLayout = root.findViewById(R.id.objectLayout);

        temp = 0;
        player = MediaPlayer.create(context, R.raw.happiness);

        papanscore = root.findViewById(R.id.papanscore);
        stickerNotif = root.findViewById(R.id.stickernotif);
        stickerIcon = root.findViewById(R.id.sticker_icon);
        rewardImage = root.findViewById(R.id.reward_image);
        highscoreLayout = root.findViewById(R.id.highscore_layout);
        terimaTV = root.findViewById(R.id.terimaHS);
        klaimTV = root.findViewById(R.id.terima);
        replayText = root.findViewById(R.id.replay);
        finalScoreTV = (TextView) root.findViewById(R.id.score);
        bg = (View) root.findViewById(R.id.bg);
        description = root.findViewById(R.id.reward_desc);
        map = root.findViewById(R.id.map);
        home = root.findViewById(R.id.home);
        callback.onSetImage(home,R.drawable.i_home);

        count = 0;
        roundScore = 0;
        rightAnswer = 0;
        animation1 = (LottieAnimationView) root.findViewById(R.id.animation1);
        animation2 = (LottieAnimationView) root.findViewById(R.id.animation2);
        animation3 = (LottieAnimationView) root.findViewById(R.id.animation3);
        endAnimation = (LottieAnimationView) root.findViewById(R.id.animationEnd);
        endAnimationHS = (LottieAnimationView) root.findViewById(R.id.animationEndHS);
        choices = new ImageView[]{choice1, choice2, choice3};
        animationLottie = new LottieAnimationView[]{animation1, animation2, animation3};
        objectResources = new int[]{0, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine};
        vs = new View[objectResources.length];
        for (int x = 0; x < objectResources.length; x++) {
            vs[x] = root.findViewById(objectResources[x]);
        }

        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);


        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();


        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                        .build());
                Log.i("ads","Quiz load failed : " + i);

            }


        });




        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);
        choice1.setOnClickListener(this::onClick);
        choice2.setOnClickListener(this::onClick);
        choice3.setOnClickListener(this::onClick);
        terimaTV.setOnClickListener(this::onClick);
        replayText.setOnClickListener(this::onClick);
        map.setOnClickListener(this::onClick);
        home.setOnClickListener(this);

        Random r = new Random();
        int max = 4;// 4;
        int min = 1;
        int quiz = r.nextInt((max - min) + 1) + min;


        manageQuiz(quiz);
        manageHighScore();

        return root;

    }

    private void manageHighScore() {
        highscoreLayout.setVisibility(View.GONE);
        endAnimation.setVisibility(View.GONE);
        replayText.setVisibility(View.GONE);
        for (int x = 0; x < 5; x++) {
            starsBawah[x].setVisibility(View.GONE);
            starFinal[x].setVisibility(View.GONE);
        }
        bg.setVisibility(View.GONE);
        papanscore.setVisibility(View.GONE);
        roundScore = 0;
        scorebawah.setText("");

     /*   adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();
        interstitialAd.loadAd(adRequest);*/


        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User u) {
                if (u != null) {
                    user = u;
                    int[] lastLevel = new int[1];
                    lastLevel[0] = user.getLastLevel();
                    if (lastLevel[0] == 0) {
                        lastLevel[0] = lastLevel[0] + 1;
                    }


                    Log.i("badgeU", "Update user : " + lastLevel[0]);
                    viewModel.getUser().removeObserver(this::onChanged);
                    Log.i("HOIHOI", "level : " + user.getLastLevel());
                    viewModel.getUnlockedBadgeByLevelId(lastLevel[0]).observe(getActivity(), new Observer<List<Badge>>() {
                        @Override
                        public void onChanged(List<Badge> badges) {
                            Log.i("badgeU", "Update badge on level :"+lastLevel[0] );

                            Log.i("badgeH", "size: " +badges.size());
                            if (badges != null && badges.size() > 0) {

                                Badge b = badges.get(0);
                                // Log.i("badgeG",badge.getGiftSourceNameBoy());
                                badge = b;
                                badge.setAchieved(true);
                                lastLevelUser = badge.getLevelId();
                                if (badges.size() == 1) {

                                    user.setLastLevel(lastLevelUser + 1);
                                    Log.i("badgeH", "naik level : " + user.getLastLevel());
                                } else {
                                    user.setLastLevel(lastLevelUser);
                                }

                                // viewModel.updateBadge(badge);

                                String iconName = b.getIconSourceName();
                                int resID = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
                                stickerIcon.setImageResource(resID);

                                String stickerReward = "";
                                if (user.getCharacterId() == 10) {
                                    stickerReward = b.getGiftSourceNameBoy();
                                    resID2 = badge.getSourceIdBoy();
                                } else {
                                    stickerReward = b.getGiftSourceNameGirl();
                                    resID2 = badge.getSourceIdGirl();
                                }
                              //  resID2 = context.getResources().getIdentifier(stickerReward, "drawable", context.getPackageName());
                              //  rewardImage.setImageResource(resID2);

                                description.setText("dan " + b.getDescription());
                                description.setVisibility(View.VISIBLE);
                                viewModel.getUnlockedBadgeByLevelId(lastLevel[0]).removeObserver(this);
                                Log.i("badgeF"," badge : " + badge.getIconSourceName());
                            }

                        }

                    });


                }
            }

        });
    }

    private void finishGame() {

        choice1.setEnabled(false);
        choice2.setEnabled(false);
        choice3.setEnabled(false);

        a1.setEnabled(false);
        a2.setEnabled(false);
        a3.setEnabled(false);


        playEndGameMusic();

        Log.i("resID","res id" + resID2);
        if (roundScore == 500 && resID2 > 0) {
            Toast.makeText(context, "dapat stiker", Toast.LENGTH_SHORT);


           /* viewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User u) {
                    if (u != null) {
                        user = u;
                        Log.i("badge","finish update user");
                        int lastLevel = u.getLastLevel();
                        viewModel.getUnlockedBadgeByLevelId(lastLevel).observe(getActivity(), new Observer<List<Badge>>() {
                            @Override
                            public void onChanged(List<Badge> badges) {
                                if (badges != null && badges.size() > 0 && roundScore == 500) {
                                    if (badges.size() == 1) {
                                        user.setLastLevel(lastLevel + 1);

                                        // viewModel.updateUser(user);
                                    }
                                    Badge b = badges.get(0);
                                    b.setAchieved(true);
                                    badge = b;
                                    Log.i("badge","finish badge : " + badge.getIconSourceName());
                                }
                            }
                        });
                    }
                }
            });
            */

            callback.onSetImage2(rewardImage,resID2);
            highscoreLayout.setVisibility(View.VISIBLE);
            endAnimationHS.setVisibility(View.VISIBLE);
            endAnimationHS.playAnimation();

        } else {

            endAnimation.setVisibility(View.VISIBLE);
            replayText.setVisibility(View.VISIBLE);
            bg.setVisibility(View.VISIBLE);
            papanscore.setVisibility(View.VISIBLE);
            finalScoreTV.setText(roundScore + "");
            endAnimation.playAnimation();
        }


    }

    private void playRightAnswer() {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(R.raw.right);
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

    private void playEndGameMusic() {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        AssetFileDescriptor assetFileDescriptor =
                context.getResources().openRawResourceFd(R.raw.great);
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

    public void manageQuiz(int q) {
        animationLottie[rightAnswer].removeAllAnimatorListeners();
        numberLayout.setVisibility(View.GONE);
        if (count < 5) {
            switch (q) {
                case 1:
                    animalQuiz();
                    break;

                case 2:
                    fruitQuiz();
                    break;

                case 3:
                    alphabetQuiz();
                    break;

                case 4:
                    numberQuiz();
                    break;


            }

        } else {

            Log.i("badge","FINISH");
            finishGame();
        }


    }

    private void animalQuiz() {
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);

        choice1.setEnabled(true);
        choice2.setEnabled(true);
        choice3.setEnabled(true);

        endAnimation.setVisibility(View.GONE);


        question.setVisibility(View.GONE);

        final List<Integer> ids = new ArrayList<>();

        viewModel.getAllAnimallId().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                if (integers.size() > 0) {
                    ids.addAll(integers);
                    Collections.shuffle(ids);
                    int c1 = ids.get(0); //also question
                    int c2 = ids.get(1);
                    int c3 = ids.get(2);


                    viewModel.getAnimalById(c1).observe(getActivity(), new Observer<Animal>() {
                        @Override
                        public void onChanged(Animal animal) {
                            if (animal != null) {
                                choice1.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(animal.getSourceName(), "drawable", context.getPackageName());
                                choice1.setImageResource(resID);
                            }

                        }
                    });

                    viewModel.getAnimalById(c2).observe(getActivity(), new Observer<Animal>() {
                        @Override
                        public void onChanged(Animal animal) {
                            if (animal != null) {
                                choice2.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(animal.getSourceName(), "drawable", context.getPackageName());
                                choice2.setImageResource(resID);
                            }

                        }
                    });

                    viewModel.getAnimalById(c3).observe(getActivity(), new Observer<Animal>() {
                        @Override
                        public void onChanged(Animal animal) {
                            if (animal != null) {
                                choice3.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(animal.getSourceName(), "drawable", context.getPackageName());
                                choice3.setImageResource(resID);
                            }

                        }
                    });

                    rightAnswer = new Random().nextInt(2);


                    viewModel.getAnimalById(ids.get(rightAnswer)).observe(getActivity(), new Observer<Animal>() {
                        @Override
                        public void onChanged(Animal animal) {
                            if (animal != null) {
                                questionImage.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(animal.getSourceName(), "drawable", context.getPackageName());
                                questionImage.setImageResource(resID);
                                questionImage.setVisibility(View.VISIBLE);

                                //masking to black
                                ColorMatrix matrix = new ColorMatrix();
                                matrix.setSaturation(0);
                                float[] colorMatrix = {
                                        0, 0, 0, 0, -128 * 255,
                                        0, 0, 0, 0, -128 * 255,
                                        0, 0, 0, 0, -128 * 255,
                                        0, 0, 0, 1, 0  //alpha    autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
                                };
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
                                questionImage.setColorFilter(filter);
                            }

                        }
                    });
                    int z[] = new int[]{0};

                }
            }
        });


    }

    private void fruitQuiz() {
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);

        choice1.setEnabled(true);
        choice2.setEnabled(true);
        choice3.setEnabled(true);

        endAnimation.setVisibility(View.GONE);


        question.setVisibility(View.GONE);

        final List<Integer> ids = new ArrayList<>();

        viewModel.getAllFruitId().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                if (integers.size() > 0) {
                    ids.addAll(integers);
                    Collections.shuffle(ids);
                    int c1 = ids.get(0); //also question
                    int c2 = ids.get(1);
                    int c3 = ids.get(2);


                    viewModel.getFruitById(c1).observe(getActivity(), new Observer<Fruit>() {
                        @Override
                        public void onChanged(Fruit fruit) {
                            if (fruit != null) {
                                choice1.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(fruit.getSourceName(), "drawable", context.getPackageName());
                                choice1.setImageResource(resID);
                            }

                        }
                    });

                    viewModel.getFruitById(c2).observe(getActivity(), new Observer<Fruit>() {
                        @Override
                        public void onChanged(Fruit fruit) {
                            if (fruit != null) {
                                choice2.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(fruit.getSourceName(), "drawable", context.getPackageName());
                                choice2.setImageResource(resID);
                            }

                        }
                    });

                    viewModel.getFruitById(c3).observe(getActivity(), new Observer<Fruit>() {
                        @Override
                        public void onChanged(Fruit fruit) {
                            if (fruit != null) {
                                choice3.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(fruit.getSourceName(), "drawable", context.getPackageName());
                                choice3.setImageResource(resID);
                            }

                        }
                    });

                    rightAnswer = new Random().nextInt(2);


                    viewModel.getFruitById(ids.get(rightAnswer)).observe(getActivity(), new Observer<Fruit>() {
                        @Override
                        public void onChanged(Fruit fruit) {
                            if (fruit != null) {
                                questionImage.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(fruit.getSourceName(), "drawable", context.getPackageName());
                                questionImage.setImageResource(resID);
                                questionImage.setVisibility(View.VISIBLE);

                                //masking to black
                                ColorMatrix matrix = new ColorMatrix();
                                matrix.setSaturation(0);
                                float[] colorMatrix = {
                                        0, 0, 0, 0, -128 * 255,
                                        0, 0, 0, 0, -128 * 255,
                                        0, 0, 0, 0, -128 * 255,
                                        0, 0, 0, 1, 0  //alpha    autoplayImage = (ImageView) root.findViewById(R.id.autoplay);
                                };
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
                                questionImage.setColorFilter(filter);
                            }

                        }
                    });
                    int z[] = new int[]{0};

                }
            }
        });
    }


    private void alphabetQuiz() {
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);

        a1.setEnabled(true);
        a2.setEnabled(true);
        a3.setEnabled(true);


        endAnimation.setVisibility(View.GONE);


        question.setVisibility(View.GONE);

        final List<Integer> ids = new ArrayList<>();

        viewModel.getAllHurufId().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                if (integers.size() > 0) {
                    ids.addAll(integers);
                    Collections.shuffle(ids);
                    int c1 = ids.get(0); //also question
                    int c2 = ids.get(1);
                    int c3 = ids.get(2);


                    viewModel.getHurufById(c1).observe(getActivity(), new Observer<Huruf>() {
                        @Override
                        public void onChanged(Huruf huruf) {
                            if (huruf != null) {
                                a1.setVisibility(View.VISIBLE);
                                String letter = huruf.getName().charAt(2) + "";
                                a1.setText(letter);
                            }

                        }
                    });

                    viewModel.getHurufById(c2).observe(getActivity(), new Observer<Huruf>() {
                        @Override
                        public void onChanged(Huruf huruf) {
                            if (huruf != null) {
                                a2.setVisibility(View.VISIBLE);

                                String letter = huruf.getName().charAt(2) + "";
                                a2.setText(letter);
                            }

                        }
                    });

                    viewModel.getHurufById(c3).observe(getActivity(), new Observer<Huruf>() {
                        @Override
                        public void onChanged(Huruf huruf) {
                            if (huruf != null) {
                                a3.setVisibility(View.VISIBLE);

                                String letter = huruf.getName().charAt(2) + "";
                                a3.setText(letter);
                            }

                        }
                    });

                    rightAnswer = new Random().nextInt(2);


                    viewModel.getHurufById(ids.get(rightAnswer)).observe(getActivity(), new Observer<Huruf>() {
                        @Override
                        public void onChanged(Huruf huruf) {
                            if (huruf != null) {
                                questionImage.clearColorFilter();
                                questionImage.setVisibility(View.VISIBLE);
                                int resID = context.getResources().getIdentifier(huruf.getIconOnQuizName(), "drawable", context.getPackageName());
                                questionImage.setImageResource(resID);
                                questionImage.setVisibility(View.VISIBLE);

                            }

                        }
                    });
                    int z[] = new int[]{0};

                }
            }
        });
    }

    private void numberQuiz() {
        choice1.setVisibility(View.GONE);
        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        a1.setEnabled(true);
        a2.setEnabled(true);
        a3.setEnabled(true);

        numberLayout.setVisibility(View.VISIBLE);
        if (temp > 0) {
            vs[temp].setVisibility(View.GONE);

        }
        endAnimation.setVisibility(View.GONE);
        question.setVisibility(View.GONE);

        final List<Integer> ids = new ArrayList<>();
        for (int x = 1; x < 10; x++) {
            ids.add(x);
        }

        Collections.shuffle(ids);
        int c1 = ids.get(0); //also question
        int c2 = ids.get(1);
        int c3 = ids.get(2);
        int[] cs = new int[]{c1, c2, c3};
        TextView[] tvs = new TextView[]{a1, a2, a3};

        a1.setVisibility(View.VISIBLE);
        a1.setText(c1 + "");
        a2.setVisibility(View.VISIBLE);
        a2.setText(c2 + "");
        a3.setVisibility(View.VISIBLE);
        a3.setText(c3 + "");


        rightAnswer = new Random().nextInt(2);
        questionImage.setVisibility(View.GONE);
        temp = cs[rightAnswer];
        vs[temp].setVisibility(View.VISIBLE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.a1:
                a1.setEnabled(false);
                a2.setEnabled(false);
                a3.setEnabled(false);
                reveal(0);
                break;

            case R.id.a2:
                a1.setEnabled(false);
                a2.setEnabled(false);
                a3.setEnabled(false);
                reveal(1);
                break;

            case R.id.a3:
                a1.setEnabled(false);
                a2.setEnabled(false);
                a3.setEnabled(false);
                reveal(2);
                break;

            case R.id.choice1:
                choice1.setEnabled(false);
                choice2.setEnabled(false);
                choice3.setEnabled(false);
                reveal(0);
                break;

            case R.id.choice2:
                choice1.setEnabled(false);
                choice2.setEnabled(false);
                choice3.setEnabled(false);
                reveal(1);
                break;

            case R.id.choice3:
                choice1.setEnabled(false);
                choice2.setEnabled(false);
                choice3.setEnabled(false);
                reveal(2);
                break;

            case R.id.terimaHS:
                //unlock sticker
                roundScore = 0;
                viewModel.updateUser(user);
                Log.i("badgedata", "badge to update: " + badge.getIconSourceName());
                viewModel.updateBadge(badge);
                highscoreLayout.setVisibility(View.GONE);

            case R.id.replay:
                resetGame();
                break;

            case R.id.home:
                if (player.isPlaying()) {
                    player.pause();
                }

                player.reset();
                callback.onHomeClicked();
                break;
            case R.id.map:
                if (player.isPlaying()) {
                    player.pause();
                }

                player.reset();
                callback.onMapClicked();
                break;
        }
    }

    private void reveal(int userAnswer) {
        if (userAnswer == rightAnswer) {

            roundScore = roundScore + 100;
            scorebawah.setText("" + roundScore);
            starsBawah[count].setVisibility(View.VISIBLE);
            starFinal[count].setVisibility(View.VISIBLE);
            playRightAnswer();


        }
        animationLottie[rightAnswer].setVisibility(View.VISIBLE);
        animationLottie[rightAnswer].playAnimation();
        animationLottie[rightAnswer].addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationLottie[rightAnswer].setVisibility(View.GONE);
                Random r = new Random();
                int max = 4;// 4;
                int min = 1;
                int quiz = r.nextInt((max - min) + 1) + min;
                manageQuiz(quiz);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        count++;

    }

    private void unlockBadge() {

    }

    private void resetGame() {
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();

        count = 0;

        highscoreLayout.setVisibility(View.GONE);
        callback.onSetImage(home, R.drawable.i_home);
        endAnimation.setVisibility(View.GONE);
        replayText.setVisibility(View.GONE);
        for (int x = 0; x < 5; x++) {
            starsBawah[x].setVisibility(View.GONE);
            starFinal[x].setVisibility(View.GONE);
        }
        bg.setVisibility(View.GONE);
        papanscore.setVisibility(View.GONE);
        roundScore = 0;
        scorebawah.setText("");

        Random r = new Random();
        int max = 4;// 4;
        int min = 1;
        int quiz = r.nextInt((max - min) + 1) + min;


        manageQuiz(quiz);
     /*   adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                //.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
                .build();
        interstitialAd.loadAd(adRequest);*/


      /*  viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User u) {
                if (u != null) {
                    user = u;
                    int[] lastLevel = new int[1];
                    lastLevel[0]=user.getLastLevel() ;
                    if(lastLevel[0] == 0){
                        lastLevel[0] = lastLevel[0] + 1;
                    }

                    viewModel.getUser().removeObserver(this::onChanged);
                    viewModel.getUnlockedBadgeByLevelId(lastLevel[0]).observe(getActivity(), new Observer<List<Badge>>() {
                        @Override
                        public void onChanged(List<Badge> badges) {
                            if (badges != null && badges.size() > 0  ) {

                                Badge b = badges.get(0);

                                badge = b;
                                // viewModel.updateBadge(badge);
                                badge.setAchieved(true);

                                lastLevelUser = badge.getLevelId();
                                if (badges.size() == 1) {

                                    user.setLastLevel(lastLevelUser + 1);
                                    Log.i("badgeR", "naik level : " + user.getLastLevel());
                                } else {
                                    user.setLastLevel(lastLevelUser);
                                }
                                String iconName = b.getIconSourceName();
                                int resID = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
                                stickerIcon.setImageResource(resID);

                                String stickerReward = "";
                                if (u.getCharacterId() == 10) {
                                    stickerReward = b.getGiftSourceNameBoy();
                                } else {
                                    stickerReward = b.getGiftSourceNameGirl();
                                }
                                int resID2 = context.getResources().getIdentifier(stickerReward, "drawable", context.getPackageName());
                                rewardImage.setImageResource(resID2);
                                description.setText("dan " + b.getDescription());

                                Log.i("badgeG"," badge : " + badge.getIconSourceName());
                                description.setVisibility(View.VISIBLE);
                                viewModel.getUnlockedBadgeByLevelId(lastLevel[0]).removeObserver(this::onChanged);
                            }
                        }
                    });
                }
            }
        });
*/

    }


    @Override
    public void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.pause();
        }

        player.reset();
        adView.setAdListener(null);
        adView.destroy();
    }
}



