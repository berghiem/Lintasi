package com.catostudioaruna.alphabet.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Character;
import com.catostudioaruna.alphabet.db.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MyProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView profileIV;
    private TextView nameTV;
    private TextView gantiCharacterTV;
    private AlphabetViewModel viewModel;
    private Context context;
    private NavigationListener callback;
    private ImageView map;
    private ImageView home;


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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.myprofile, null);
        profileIV = root.findViewById(R.id.profile_image);
        nameTV = root.findViewById(R.id.name);
        gantiCharacterTV = root.findViewById(R.id.pilih);
        context = getContext();
        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);

        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    viewModel.getCharacterById(user.getCharacterId()).observe(getActivity(), new Observer<Character>() {
                        @Override
                        public void onChanged(Character character) {
                            if(character != null){
                                int resID = context.getResources().getIdentifier(character.getResourceName(), "drawable", context.getPackageName());
                                callback.onSetImage(profileIV, character.getResourceId());
                               // profileIV.setImageResource(resID);
                                nameTV.setText(character.getName());
                            }
                        }
                    });
                }
            }
        });

        gantiCharacterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterAndVehicleSelectionFragment cFragment = new CharacterAndVehicleSelectionFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, cFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        map.setOnClickListener(this::onClick);
        home.setOnClickListener(this::onClick);

        return root;

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                callback.onHomeClicked();
                break;
            case R.id.map:
                callback.onMapClicked();
                break;
        }
    }
}
