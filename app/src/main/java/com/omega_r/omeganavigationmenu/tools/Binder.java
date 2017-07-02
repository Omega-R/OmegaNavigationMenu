package com.omega_r.omeganavigationmenu.tools;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Binder {

    public static void setContentView(Activity activity, @LayoutRes int layoutRes) {
        activity.setContentView(layoutRes);
        bind(activity);
    }

    public static void bind(Activity activity) {
        ButterKnife.bind(activity);
    }

    public static View inflate(Object bindObject, LayoutInflater inflater, ViewGroup container, @LayoutRes int layoutRes, @Nullable UnbinderContainer unbinderContainer) {
        View view = inflater.inflate(layoutRes, container, false);
        Unbinder unbinder = ButterKnife.bind(bindObject, view);

        if (unbinderContainer != null) {
            unbinderContainer.unbinder = unbinder;
        }

        return view;
    }

    public static class UnbinderContainer {

        @NonNull
        private Unbinder unbinder = Unbinder.EMPTY;

        public UnbinderContainer() {
        }

        public void unbind() {
            unbinder.unbind();
        }
    }

}
