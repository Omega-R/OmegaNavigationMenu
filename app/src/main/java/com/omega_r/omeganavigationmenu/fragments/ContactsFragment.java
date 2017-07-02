package com.omega_r.omeganavigationmenu.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.omega_r.omeganavigationmenu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends ScreenMenuBinderFragment {


    public ContactsFragment() {
        // Required empty public constructor
    }


    public static ContactsFragment create() {
        return new ContactsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_contacts;
    }

    @Override
    public String getTitle() {
        return getString(R.string.menu_contacts);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
