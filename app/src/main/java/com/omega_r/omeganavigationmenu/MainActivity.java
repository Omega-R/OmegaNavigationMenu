package com.omega_r.omeganavigationmenu;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.omega_r.omeganavigationmenu.fragments.ContactsFragment;
import com.omega_r.omeganavigationmenu.fragments.MainFragment;
import com.omega_r.omeganavigationmenu.fragments.NewsFragment;
import com.omega_r.omeganavigationmenu.fragments.ScreenMenuBinderFragment;
import com.omega_r.omeganavigationmenu.tools.Binder;
import com.omega_r.omeganavigationmenulib.views.ContentMenuLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        ContentMenuLayout.OnProgressMenuChangedListener {

    @BindView(R.id.layout_container)
    ContentMenuLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            onMainPageMenuClick();
        }

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
                onMainPageMenuClick();
            }
        } else {
            mContainerView.hideMenu();
        }
    }

    @OnClick(R.id.textview_menu_main)
    protected void onMainPageMenuClick() {
        showPage(MainFragment.create());
    }

    @OnClick(R.id.textview_menu_news)
    protected void onNewsPageMenuClicks() {
        showPage(NewsFragment.create());
    }

    @OnClick(R.id.textview_menu_contacts)
    protected void onContactsPageMenuClick() {
        showPage(ContactsFragment.create());
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
        } else {
            if (hideMenu) {
                mContainerView.hideMenu();
            }
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
