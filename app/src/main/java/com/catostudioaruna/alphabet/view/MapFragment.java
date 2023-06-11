package com.catostudioaruna.alphabet.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.User;
import com.facebook.appevents.codeless.CodelessLoggingEventListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class MapFragment extends Fragment implements View.OnClickListener {
    private OnDestinationListener callback;
    private NavigationListener callback2;

    private ImageView bonbin;//1
    private ImageView taman;//2
    private ImageView kebun;//3
    private ImageView perpus;//4
    private ImageView quiz;//5
    private ImageView seasonal; //6

    private ImageView bonbinp;//1
    private ImageView tamanp;//2
    private ImageView kebunp;//3
    private ImageView perpusp;//4
    private ImageView quizp;
    private ImageView home;
    private int destination;
    private String[] dests;
    private TextView choice;
    private TextView stuju;
    private LinearLayout choiceParent;

    private ImageView landmarkIcon;
    private ImageView userImage;
    private ImageView theIsland;
    private AlphabetViewModel viewModel;
    private Context context;

    private Bundle extras;
    private AdView adView;
    private com.facebook.ads.AdView fbAds;
    private AdRequest adRequest;
    private LottieAnimationView bonbinAnim;
    private LottieAnimationView kebunAnim;
    private LottieAnimationView tamanAnim;
    private LottieAnimationView perpusAnim;
    private LottieAnimationView quizAnim;
    private LottieAnimationView seasonalAnim;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.map, null);


        adView = root.findViewById(R.id.adMap);
       // fbAds = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) root.findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
     //   adContainer.addView(fbAds);

        dests = new String[]{"Kita akan belajar  binatang", "Kita akan belajar angka", "Kita akan belajar buah dan sayur", "Kita akan belajar huruf alphabet", "Waktunya quiz","Mari pelajari Indonesia"};
        context = getContext();
        bonbin = root.findViewById(R.id.bonbin);
        kebun = root.findViewById(R.id.perkebunan);
        taman = root.findViewById(R.id.taman);
        perpus = root.findViewById(R.id.perpus);
        quiz = root.findViewById(R.id.quiz);
        seasonal = root.findViewById(R.id.seasonal);
        home = root.findViewById(R.id.home);

        theIsland = root.findViewById(R.id.theisland);
       // bonbinp = root.findViewById(R.id.bonbinp);
        kebunAnim = root.findViewById(R.id.kebunAnim);
        tamanAnim = root.findViewById(R.id.tamanAnim);
        perpusAnim = root.findViewById(R.id.perpusAnim);
        quizAnim = root.findViewById(R.id.quizAnim);
        seasonalAnim = root.findViewById(R.id.seasonalAnim);

        userImage = root.findViewById(R.id.character);

        choice = root.findViewById(R.id.choice2);
        stuju = root.findViewById(R.id.setuju);
        landmarkIcon = root.findViewById(R.id.landmarkicon);
        choiceParent = root.findViewById(R.id.choiceParent);
        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        bonbinAnim = root.findViewById(R.id.bonbinAnim);


        callback2.onSetImage(theIsland, R.drawable.island);
        callback2.onSetBackground(null,0 );

        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);

        adView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                Log.i("ads","Map load failed : " + i);
            }
        });
        //fbAds.loadAd();



        viewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    int resID = 0;
                    String textPilih="";
                    if(userImage.getDrawable()== null){
                        switch (user.getCharacterId()){
                            case 10:

                                callback2.onSetImage2(userImage,R.drawable.in_boyr);
                                textPilih = "Pilihlah tempat yang ingin kamu kunjungi bersama Bayu";
                                break;
                            case 11:
                                callback2.onSetImage2(userImage,R.drawable.in_girlr);
                                textPilih = "Pilihlah tempat yang ingin kamu kunjungi bersama Bulan";
                                break;
                        }

                        //  userImage.setImageResource(resID);
                        choice.setText(textPilih);
                        choiceParent.setVisibility(View.VISIBLE);
                    }


                }
            }
        });



        bonbin.setOnClickListener(this);
        kebun.setOnClickListener(this);
        taman.setOnClickListener(this);
        perpus.setOnClickListener(this);
        quiz.setOnClickListener(this);
        seasonal.setOnClickListener(this);

        bonbinAnim.setOnClickListener(this);
        kebunAnim.setOnClickListener(this);
        tamanAnim.setOnClickListener(this);
        perpusAnim.setOnClickListener(this);
        quizAnim.setOnClickListener(this);
        seasonalAnim.setOnClickListener(this);
        stuju.setOnClickListener(this::onClick);
        home.setOnClickListener(this);



        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        stuju.setVisibility(View.GONE);
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bonbinAnim:

            case R.id.bonbin:
                perpusAnim.pauseAnimation();
                kebunAnim.pauseAnimation();
                tamanAnim.pauseAnimation();
                quizAnim.pauseAnimation();

                destination = 1;
                choice.setText(dests[0]);
                stuju.setVisibility(View.VISIBLE);
                bonbinAnim.playAnimation();


                break;

            case R.id.tamanAnim:

            case R.id.taman:
                perpusAnim.pauseAnimation();
                kebunAnim.pauseAnimation();
                bonbinAnim.pauseAnimation();
                quizAnim.pauseAnimation();

                destination = 2;
                choice.setText(dests[1]);
                stuju.setVisibility(View.VISIBLE);
                tamanAnim.playAnimation();
                break;

            case R.id.kebunAnim:

            case R.id.perkebunan:
                perpusAnim.pauseAnimation();
                tamanAnim.pauseAnimation();
                bonbinAnim.pauseAnimation();
                quizAnim.pauseAnimation();

                destination = 3;
                choice.setText(dests[2]);
                stuju.setVisibility(View.VISIBLE);
                kebunAnim.playAnimation();
                break;

            case R.id.perpusAnim:

            case R.id.perpus:
                perpusAnim.pauseAnimation();
                kebunAnim.pauseAnimation();
                bonbinAnim.pauseAnimation();
                quizAnim.pauseAnimation();

                destination = 4;
                choice.setText(dests[3]);
                stuju.setVisibility(View.VISIBLE);
                perpusAnim.playAnimation();
                break;

            case R.id.quizAnim:

            case R.id.quiz:
                perpusAnim.pauseAnimation();
                kebunAnim.pauseAnimation();
                bonbinAnim.pauseAnimation();
                tamanAnim.pauseAnimation();

                destination = 5;
                choice.setText(dests[4]);
                stuju.setVisibility(View.VISIBLE);
                quizAnim.playAnimation();
                break;

            case R.id.seasonalAnim:

            case R.id.seasonal:
                perpusAnim.pauseAnimation();
                kebunAnim.pauseAnimation();
                bonbinAnim.pauseAnimation();
                tamanAnim.pauseAnimation();
                quizAnim.pauseAnimation();

                destination = 6;
                choice.setText(dests[5]);
                stuju.setVisibility(View.VISIBLE);
                seasonalAnim.playAnimation();
                break;



            case R.id.setuju:
                callback.onDestinationClicked(destination);
                break;

            case R.id.home:
                callback2.onHomeClicked();
                break;


        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            callback = (OnDestinationListener) context;
            callback2 = (NavigationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    public interface OnDestinationListener {
        public void onDestinationClicked(int dest);
    }

    @Override
    public void onPause() {
        super.onPause();
        adView.setAdListener(null);
        adView.destroy();

    }
}
