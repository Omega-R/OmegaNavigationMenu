package com.omega_r.omeganavigationmenu.fragments;

import com.omega_r.omeganavigationmenu.R;

public class ContactsFragment extends ScreenMenuBinderFragment {

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    public String getTitle() {
        return getString(R.string.menu_contacts);
    }

}
