package com.catostudioaruna.alphabet.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catostudioaruna.alphabet.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BodypartFragment extends Fragment {

    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.learning_bp_anim, null);
        context = getContext().getApplicationContext();
        return root;
    }
}
