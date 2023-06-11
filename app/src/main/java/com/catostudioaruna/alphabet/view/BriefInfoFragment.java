package com.catostudioaruna.alphabet.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
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

public class BriefInfoFragment extends Fragment implements View.OnClickListener {

    private ImageView rid;
    private TextView visit;
    private OnVisitListener callback;
    private NavigationListener callbackNavigation;
    private int dest;
    private TextView namaTempat;
    private ImageView home;
    private ImageView map;

    private AdView adView;
    private AdRequest adRequest;
    private Bundle extras;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.briefinfo, null);

        adView = root.findViewById(R.id.adBrief);
        dest = getArguments().getInt("dest", 0);
        visit = root.findViewById(R.id.visit);
        rid = root.findViewById(R.id.rid);
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);


        namaTempat = root.findViewById(R.id.namaTempat);
        switch (dest){
            case 1 :
                callbackNavigation.onSetImage(rid,R.drawable.bzoo);
                namaTempat.setText("Kebun Binatang");
                break;
            case 2 :
                callbackNavigation.onSetImage(rid,R.drawable.bpark);
                namaTempat.setText("Taman");
                break;
            case 3 :
                callbackNavigation.onSetImage(rid,R.drawable.bfarm);
                namaTempat.setText("Perkebunan");
                break;
            case 4 :
                callbackNavigation.onSetImage(rid,R.drawable.blib);
                namaTempat.setText("Perpustakaan");
                break;
            case 5 :
                callbackNavigation.onSetImage(rid,R.drawable.bquiz);
                namaTempat.setText("Quiz");
                break;
            case 6 :
                callbackNavigation.onSetImage(rid,R.drawable.in_briefinfo);
                namaTempat.setText("Indonesia");
                break;

        }


        extras = new Bundle();
        extras.putBoolean("tag_for_under_age_of_consent", true);


        adView.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                adView.loadAd(new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build());
                Log.i("ads","Brief info load failed : " + i);

            }


        });

        visit.setOnClickListener(this::onClick);
        home.setOnClickListener(this);
        map.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.visit :
                callback.onVisitClicked(dest);
                break;

            case R.id.home:
                callbackNavigation.onHomeClicked();
                break;

            case R.id.map:
                callbackNavigation.onMapClicked();
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            callback = (OnVisitListener) context;
            callbackNavigation = (NavigationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    public interface OnVisitListener{
        public void onVisitClicked(int destination);
    }

    @Override
    public void onResume() {
        super.onResume();
        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        adView.setAdListener(null);
        adView.destroy();
    }
}
