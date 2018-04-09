package com.omega_r.omeganavigationmenu;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.omega_r.omeganavigationmenu.fragments.ContactsFragment;
import com.omega_r.omeganavigationmenu.fragments.MainFragment;
import com.omega_r.omeganavigationmenu.fragments.NewsFragment;
import com.omega_r.omeganavigationmenu.fragments.ScreenMenuBinderFragment;
import com.omega_r.navigationmenu.views.ContentMenuLayout;

public class MainActivity extends AppCompatActivity implements
        ContentMenuLayout.OnProgressMenuChangedListener, View.OnClickListener {

    private final MainFragment mMainFragment = new MainFragment();
    private final NewsFragment mNewsFragment = new NewsFragment();
    private final ContactsFragment mContactsFragment = new ContactsFragment();
    private ContentMenuLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainerView = findViewById(R.id.layout_container);
        findViewById(R.id.textview_menu_main).setOnClickListener(this);
        findViewById(R.id.textview_menu_news).setOnClickListener(this);
        findViewById(R.id.textview_menu_contacts).setOnClickListener(this);

        if (savedInstanceState == null) showPage(mMainFragment);
        mContainerView.setOnProgressMenuChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (!mContainerView.isMenuShown()) {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.framelayout_fragment_container);
            if (currentFragment instanceof MainFragment) {
                super.onBackPressed();
            } else {
                showPage(mMainFragment);
            }
        } else {
            mContainerView.hideMenu();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_menu_main:
                showPage(mMainFragment);
                break;
            case R.id.textview_menu_news:
                showPage(mNewsFragment);
                break;
            case R.id.textview_menu_contacts:
                showPage(mContactsFragment);
                break;
        }
    }

    @Override
    public void onProgressMenuChanged(float progress) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.framelayout_fragment_container);
        if (fragment instanceof ScreenMenuBinderFragment) {
            ((ScreenMenuBinderFragment) fragment).setMenuProgress(progress);
        }
    }

    private void showPage(Fragment fragment) {
        showPage(fragment, true);
    }

    private void showPage(Fragment fragment, boolean hideMenu) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.framelayout_fragment_container);

        if (currentFragment == null || !fragment.getClass().getCanonicalName().equals(currentFragment.getClass().getCanonicalName())) {
            updatePage(fragment, hideMenu);
        } else if (hideMenu) {
            mContainerView.hideMenu();
        }
    }

    private void updatePage(Fragment fragment, boolean hideMenu) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.framelayout_fragment_container, fragment)
                .commitAllowingStateLoss();

        if (hideMenu) {
            mContainerView.hideMenu();
        }
    }

    public void showMenu() {
        mContainerView.showMenu();
    }
}
