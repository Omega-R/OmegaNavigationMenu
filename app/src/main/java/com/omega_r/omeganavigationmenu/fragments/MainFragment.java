package com.omega_r.omeganavigationmenu.fragments;

import com.omega_r.omeganavigationmenu.R;

public class MainFragment extends ScreenMenuBinderFragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public String getTitle() {
        return getString(R.string.menu_main_page);
    }

}
