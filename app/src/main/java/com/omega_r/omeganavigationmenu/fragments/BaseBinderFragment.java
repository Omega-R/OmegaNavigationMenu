package com.omega_r.omeganavigationmenu.fragments;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omega_r.omeganavigationmenu.tools.Binder;

public abstract class BaseBinderFragment extends BaseFragment {

    private Binder.UnbinderContainer mUnbinderContainer = new Binder.UnbinderContainer();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return Binder.inflate(this, inflater, container, getContentView(), mUnbinderContainer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinderContainer.unbind();
    }

    @LayoutRes
    protected abstract int getContentView();

}
