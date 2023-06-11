package com.catostudioaruna.alphabet.view;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catostudioaruna.alphabet.R;
import com.catostudioaruna.alphabet.adapter.StickerAdapter;
import com.catostudioaruna.alphabet.db.AlphabetViewModel;
import com.catostudioaruna.alphabet.db.Sticker;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyAlbumStickerActivity extends AppCompatActivity {

    private RecyclerView stickersRV;
    private StickerAdapter adapter;
    private AlphabetViewModel alphabetViewModel;
    private LinearLayout emptyStickerlayout;
    private TextView totalSticker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.my_album_sticker);

        stickersRV = (RecyclerView) findViewById(R.id.stickers);
        emptyStickerlayout = (LinearLayout) findViewById(R.id.emptysticker);
        totalSticker = (TextView) findViewById(R.id.total);

        stickersRV.setLayoutManager(new GridLayoutManager(this, 4));
        alphabetViewModel = ViewModelProviders.of(this).get(AlphabetViewModel.class);
        adapter = new StickerAdapter();
        stickersRV.setAdapter(adapter);
        alphabetViewModel.getCollectedSticker().observe(this, new Observer<List<Sticker>>() {
            @Override
            public void onChanged(List<Sticker> stickers) {
                adapter.setStickers(stickers);
                if (stickers != null && stickers.size() > 0) {
                    emptyStickerlayout.setVisibility(View.GONE);
                    //////!!!!!WARNING HARDCODE
                    ///TOTAL sticker : 22
                    int size = stickers.size();
                    totalSticker.setText("" + size + "/22 stickers");
                }

            }
        });


    }
}
