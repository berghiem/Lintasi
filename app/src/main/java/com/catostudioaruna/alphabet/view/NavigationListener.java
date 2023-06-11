package com.catostudioaruna.alphabet.view;

import android.view.View;
import android.widget.ImageView;

public interface NavigationListener {
    public void onHomeClicked();

    public void onMapClicked();

    public void onSetBackground(View i, int sourceid);

    public void onSetImage(ImageView i, int sourceid);

    public void onSetImage2(ImageView i, int sourceid);
}
