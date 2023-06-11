package com.catostudioaruna.alphabet.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.CharacterAdapter;
import com.catostudioaruna.alphabet.adapter.VehicleAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Character;
import com.catostudioaruna.alphabet.db.Skill;
import com.catostudioaruna.alphabet.db.SkillJoinCharacter;
import com.catostudioaruna.alphabet.db.User;
import com.catostudioaruna.alphabet.db.Vehicle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterAndVehicleSelectionFragment extends Fragment implements View.OnClickListener {

    OnDoneButtonListener callback;
    private RecyclerView characterLV;
    private RecyclerView vehicleLV;
    private LinearLayout vehicleLayout;
    private LinearLayout karakterLayout;

    private CharacterAdapter characterAdapter;
    private VehicleAdapter vehicleAdapter;
    private AlphabetViewModel viewModel;
    private TextView u1;
    private TextView u2;
    private TextView beli;
    private ImageView next;
    private ImageView frame;
    private ImageView objectChar;

    private ImageView frame2;
    private ImageView objectChar2;

    private ImageView line;
    private User user;
    private Vehicle vehicle;

    private List<Character> characterList;
    private List<Vehicle> vehicleList;
    private LinearLayout pilihKarakter;
    private LinearLayout piilihKendaraan;

    private  BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("US")) {
                int id = intent.getIntExtra("uid", 0);
                int resourceId = intent.getIntExtra("rid", 0);
                next.setImageResource(R.drawable.h_next);
                objectChar.setImageResource(resourceId);
                objectChar.setVisibility(View.VISIBLE);
                next.setEnabled(true);
                user.setCharacterId(id);
                frame.setImageResource(R.drawable.frame);
                line.setBackgroundColor(Color.parseColor("#ffde17"));


            } else if (intent.getAction().equalsIgnoreCase("vh")) {
                int id = intent.getIntExtra("vid", 0);
                int rid = intent.getIntExtra("rid", 0);
                next.setImageResource(R.drawable.h_done);
                next.setEnabled(true);
                frame2.setEnabled(true);

                frame2.setImageResource(R.drawable.frame);
                objectChar2.setImageResource(rid);
                objectChar2.setVisibility(View.VISIBLE);
                u2.setTextColor(Color.parseColor("#443d6c"));
                /*
                vehicle.setActive(true);
                List<Vehicle> vlist = user.getVehicleList();
                if (vlist == null) {
                    vlist = new ArrayList<>();
                }
                vlist.add(vehicle);
                user.setVehicleList(vlist);
                vehicle.setActive(true);
                vehicle.setOwned(true);*/
            }

        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.pilihcharacter, null);

        characterLV = root.findViewById(R.id.listCharacter);
        vehicleLV = root.findViewById(R.id.listVehicle);
        u1 = root.findViewById(R.id.u1);
        u2 = root.findViewById(R.id.u2);
        next = root.findViewById(R.id.next);
        frame = root.findViewById(R.id.frame);
        objectChar = root.findViewById(R.id.object);

        frame2 = root.findViewById(R.id.frame2);
        frame2.setEnabled(false);
        objectChar2 = root.findViewById(R.id.object2);
        line = root.findViewById(R.id.line);
        beli = root.findViewById(R.id.beli);
        pilihKarakter = root.findViewById(R.id.pickKarakter);
        piilihKendaraan = root.findViewById(R.id.pickVehicle);

        pilihKarakter.setOnClickListener(this::onClick);
        piilihKendaraan.setOnClickListener(this::onClick);
        next.setOnClickListener(this::onClick);

        vehicleLayout = root.findViewById(R.id.vehiclelayout);
        karakterLayout = root.findViewById(R.id.karakter_layout);

        next.setOnClickListener(this);

        characterAdapter = new CharacterAdapter(getContext());
        vehicleAdapter = new VehicleAdapter(getContext());
        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User u) {
                if(u != null){
                    user = u;
                }

            }
        });


        viewModel.getAllSkillCharacters().observe(this, new Observer<List<SkillJoinCharacter>>() {
            @Override
            public void onChanged(List<SkillJoinCharacter> skillJoinCharacters) {
                if (skillJoinCharacters.size() > 0) {

                    viewModel.getCharacters().observe(getActivity(), new Observer<List<Character>>() {
                        @Override
                        public void onChanged(List<Character> characters) {
                            if (characters.size() > 0) {
                                new AsyncTask<Void, List<Character>, List<Character>>() {


                                    @Override
                                    protected List<Character> doInBackground(Void... voids) {

                                        characterList = characters;
                                        for (Character c : characterList) {
                                            List<Skill> s = viewModel.getSkillOfCharacters(c.getId());
                                            c.setSkills(s);

                                            List<SkillJoinCharacter> sj = viewModel.getSkillCharacters(c.getId());
                                            c.setSkillJoinCharacter(sj);
                                        }
                                        return characterList;
                                    }

                                    @Override
                                    protected void onPostExecute(List<Character> characterList) {
                                        super.onPostExecute(characterList);

                                        characterAdapter.setItem(characterList);
                                    }
                                }.execute();


                            }


                        }
                    });

                }

            }
        });

        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    int characterId = user.getCharacterId();
                    if(characterId > 0){
                        characterAdapter.myCurrentProfileSetting(characterId);
                    }
                }
            }
        });



        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        characterLV.setAdapter(characterAdapter);
        characterLV.setLayoutManager(layoutManager);

        viewModel.getAllVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                if (vehicles.size() > 0) {
                    vehicleAdapter.setItem(vehicles);
                }
            }
        });

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        vehicleLV.setAdapter(vehicleAdapter);
        vehicleLV.setLayoutManager(layoutManager2);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (user.getCharacterId() > 0 && vehicleLayout.getVisibility() == View.GONE) {
                    karakterLayout.setVisibility(View.GONE);
                    vehicleLayout.setVisibility(View.VISIBLE);
                    next.setImageResource(R.drawable.h_done_g);

                    next.setEnabled(false);
                } else if (objectChar2.getVisibility() == View.VISIBLE && user.getCharacterId() > 0
                        && karakterLayout.getVisibility() == View.GONE) {
                    frame2.setImageResource(R.drawable.frame);
                    next.setEnabled(true);
                    viewModel.updateUser(user);
                    callback.onDoneClicked();
                }
                break;

            case R.id.pickKarakter:

                vehicleLayout.setVisibility(View.GONE);
                karakterLayout.setVisibility(View.VISIBLE);
                next.setImageResource(R.drawable.h_next);
                next.setEnabled(true);

                break;

            case R.id.pickVehicle:
                if (frame2.isEnabled()) {


                    karakterLayout.setVisibility(View.GONE);
                    vehicleLayout.setVisibility(View.VISIBLE);
                    next.setImageResource(R.drawable.h_done);
                    next.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            callback = (OnDoneButtonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("US");
        filter.addAction("vh");
        this.getContext().registerReceiver(broadcastReceiver, filter);
    }



    public interface OnDoneButtonListener {
        public void onDoneClicked();
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);

    }
}
