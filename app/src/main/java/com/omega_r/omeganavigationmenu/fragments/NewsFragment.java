package com.omega_r.omeganavigationmenu.fragments;

import com.omega_r.omeganavigationmenu.R;

public class NewsFragment extends ScreenMenuBinderFragment {

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public String getTitle() {
        return getString(R.string.menu_news);
    }

}
