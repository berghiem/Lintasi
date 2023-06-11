package com.catostudioaruna.alphabet.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.LevelAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Badge;
import com.catostudioaruna.alphabet.db.Level;
import com.catostudioaruna.alphabet.db.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RewardFragment extends Fragment implements View.OnClickListener  {
    private boolean isFemale;

    private ImageView home;
    private ImageView map;
    private NavigationListener callback;
    private ImageView levelImage;
    private View bar;
    private RecyclerView levelRecycler;
    private AlphabetViewModel viewModel;
    private Context context;
    private List<Level> levels;
    private LevelAdapter adapter;
    private TextView levelName;
    private TextView reset;
    private LinearLayout rewardLayout;
    private ProgressBar progressBar;
    private Space space;
    private int resId1;


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

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.reward, null);
        context = getContext().getApplicationContext();
        home = root.findViewById(R.id.home);
        map = root.findViewById(R.id.map);
        levelImage = root.findViewById(R.id.levelImage);
        bar = root.findViewById(R.id.barPercent);
        levelRecycler = root.findViewById(R.id.levelRecycler);
        levels = new ArrayList<>();
        levelName = root.findViewById(R.id.levelName);
        viewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        reset = root.findViewById(R.id.reset);
        rewardLayout = root.findViewById(R.id.rewardLayout);
        progressBar = root.findViewById(R.id.progress);
        space = root.findViewById(R.id.s);
        resId1 = 0;
        home.setOnClickListener(this);
        map.setOnClickListener(this);
        bar.setOnClickListener(this);
        reset.setOnClickListener(this::onClick);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
         adapter = new LevelAdapter(context);
        viewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    isFemale = user.getCharacterId() == 11;
                    int lastLevel = user.getLastLevel() ;
                    if(lastLevel == 0){
                        lastLevel = lastLevel + 1;
                    }

                    adapter.setLastLevel(lastLevel);
                    int width[] = new int[1];
                    width[0] = 100 * lastLevel;

                    viewModel.getLevelById(lastLevel).observe(getActivity(), new Observer<Level>() {
                        @Override
                        public void onChanged(Level level) {

                            if(level != null){
                                levelName.setText(level.getName());
                                if(isFemale){
                                    //resID1 = context.getResources().getIdentifier(level.getSourceNameGirl(), "drawable", context.getPackageName());
                                    resId1 = level.getSourceIdGirl();
                                }else {
                                   // resID1 = context.getResources().getIdentifier(level.getSourceNameBoy(), "drawable", context.getPackageName());
                                    resId1 = level.getSourceIdBoy();
                                }
                               // levelImage.setImageResource(resID1);
                               callback.onSetImage(levelImage,resId1);
                                space.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                rewardLayout.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                    viewModel.getAllLevel().observe(getActivity(), new Observer<List<Level>>() {
                        @Override
                        public void onChanged(List<Level> ls) {
                            if(ls.size() > 0){
                                levels = ls;
                                for(Level l : levels){
                                    viewModel.getBadgeByLevel(l.getId()).observe(getActivity(), new Observer<List<Badge>>() {
                                        @Override
                                        public void onChanged(List<Badge> badges) {
                                            if(badges.size() > 0){
                                                l.setBadges(badges);
                                                Log.i("badge","badge");
                                                adapter.notifyDataSetChanged();


                                                if(user.getLastLevel() == 5 ){
                                                        width[0] = 500;
                                                }

                                            }
                                        }
                                    });



                                }


                                bar.setLayoutParams(new FrameLayout.LayoutParams(width[0], 25));

                                adapter.setLevels(levels);
                            }
                        }
                    });



                }
            }
        });

        levelRecycler.setAdapter(adapter);
        levelRecycler.setLayoutManager(layoutManager);

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

            case R.id.reset:
                reset();
                break;

        }

    }

    private void reset() {
        viewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                user.setLastLevel(0);
                viewModel.updateUser(user);
                viewModel.resetBadgeStatus();
                adapter.notifyDataSetChanged();

            }
        });



    }




}
