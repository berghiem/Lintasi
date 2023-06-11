package com.catostudioaruna.alphabet.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.CharacterAdapter;
import com.catostudioaruna.alphabet.adapter.VehicleAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Character;
import com.catostudioaruna.alphabet.db.User;
import com.catostudioaruna.alphabet.db.Vehicle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class HomeScreenFragment extends Fragment implements View.OnClickListener {

    HomeFragmentListener callback;
    private TextView startTV;
    private RecyclerView characterLV;
    private RecyclerView vehicleLV;
    private CharacterAdapter characterAdapter;
    private VehicleAdapter vehicleAdapter;
    private AlphabetViewModel viewModel;
    private User userm;
    private ImageView profpic;
    private ImageView badge;
    private LinearLayout levelLayout;
    private TextView levelName;
    private TextView countBadge;
    private LinearLayout userLayout;

    private List<Character> characterList;
    private List<Vehicle> vehicleList;
    private Context context;
    private int characterId;
    private LottieAnimationView fly;
    private ImageView homeScreenImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.homescreen, null);
        context = getContext();
        startTV = root.findViewById(R.id.start);
        levelLayout = root.findViewById(R.id.levelLayout);
        levelName = root.findViewById(R.id.levelName);
        countBadge = root.findViewById(R.id.countBadge);
        badge = root.findViewById(R.id.reward);
        profpic = root.findViewById(R.id.profpic);
        userLayout = root.findViewById(R.id.userLayout);
        fly = root.findViewById(R.id.flyAnim);
        homeScreenImage = root.findViewById(R.id.homescreenImage);
        callback.onSetImageHome(homeScreenImage,R.drawable.in_homescreen);

        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);

        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User u) {
                if(u != null){
                    userm = u;
                    characterId = userm.getCharacterId();

                //    Log.i("character","character : " + characterId );
                    if(characterId >0){

                        viewModel.getCharacterById(characterId).observe(getActivity(), new Observer<Character>() {
                            @Override
                            public void onChanged(Character character) {
                                if(character != null){
                                    int resID = context.getResources().getIdentifier
                                            (character.getIconResourceName(), "drawable", context.getPackageName());
                                    profpic.setImageResource(resID);
                                    userLayout.setVisibility(View.VISIBLE);

                                    viewModel.countGetAllAchievedBadge().observe(getActivity(), new Observer<Integer>() {
                                        @Override
                                        public void onChanged(Integer integer) {
                                            countBadge.setText(integer+"");
                                        }
                                    });

                                }
                            }
                        });


                    }else {

                    }

                }
            }
        });

        startTV.setOnClickListener(this);
        profpic.setOnClickListener(this);
        levelLayout.setOnClickListener(this);
        badge.setOnClickListener(this);
        countBadge.setOnClickListener(this);
        return root;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            callback = (HomeFragmentListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if(characterId >= 10){
                    callback.onStartClicked(true);
                }else {
                    callback.onStartClicked(false);
                }

                break;
            case R.id.profpic :
                callback.onProfilePictureClicked();
                break;

            case R.id.levelLayout:
                callback.onLevelLayout();
                break;

            case R.id.reward:

            case R.id.countBadge:
                callback.onRewardClicked();
                break;
        }
    }

    public interface HomeFragmentListener {

        public void onStartClicked(boolean isCharacterExisted);

        public void onProfilePictureClicked();

        public void onLevelLayout();

        public void onRewardClicked();

        public void onSetBackgroundHome(View i, int sourceid);

        public void onSetImageHome(ImageView i, int sourceid);
    }




}
