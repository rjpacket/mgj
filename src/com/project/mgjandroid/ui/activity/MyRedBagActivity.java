package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.fragment.RedBagFragment;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by yuandi on 2016/5/24.
 */
public class MyRedBagActivity extends BaseActivity {

    @InjectView(R.id.my_redbag_act_back)
    private ImageView ivBack;

    private RedBagFragment fragmentRedBagCanUse;
    private RedBagFragment fragmentRedBagCantUse;
    private Fragment mCurrentFragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private double latitude;
    private double longitude;
    private double itemsPrice;
    private Long merchantId;
    private String promoInfoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_redbag);
        Injector.get(this).inject();
        latitude = getIntent().getDoubleExtra("latitude", -1);
        longitude = getIntent().getDoubleExtra("longitude", -1);
        itemsPrice = getIntent().getDoubleExtra("itemsPrice", -1);
        merchantId = getIntent().getLongExtra("merchantId", -1);
        promoInfoJson = getIntent().getStringExtra("PromoInfoJson");
        if(promoInfoJson == null) promoInfoJson = "[]";

        initView();
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putBoolean("canUse", true);
            args.putDouble("longitude", longitude);
            args.putDouble("latitude", latitude);
            args.putDouble("itemsPrice", itemsPrice);
            args.putLong("merchantId", merchantId);
            args.putString("PromoInfoJson", promoInfoJson);
            fragmentRedBagCanUse = new RedBagFragment();
            fragmentRedBagCanUse.setArguments(args);
            fragmentManager.beginTransaction().add(R.id.content_view,
                    fragmentRedBagCanUse).commit();
            mCurrentFragment = fragmentRedBagCanUse;
        }else if(fragmentRedBagCanUse != null){
            fragmentManager.beginTransaction()
                    .show(fragmentRedBagCanUse)
                    .commit();
            mCurrentFragment = fragmentRedBagCanUse;
        }
    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    public void doTransaction(boolean canUse) {
        if (canUse) {
            if(fragmentRedBagCantUse == null) {
                Bundle args = new Bundle();
                args.putBoolean("canUse", false);
                fragmentRedBagCantUse = new RedBagFragment();
                fragmentRedBagCantUse.setArguments(args);
                transaction(fragmentRedBagCantUse);
            }
            transaction(fragmentRedBagCantUse);
        } else {
            if(fragmentRedBagCanUse == null) {
                Bundle args = new Bundle();
                args.putBoolean("canUse", true);
                args.putDouble("longitude", longitude);
                args.putDouble("latitude", latitude);
                args.putDouble("itemsPrice", itemsPrice);
                args.putLong("merchantId", merchantId);
                args.putString("PromoInfoJson", promoInfoJson);
                fragmentRedBagCanUse = new RedBagFragment();
                fragmentRedBagCanUse.setArguments(args);
            }
            transaction(fragmentRedBagCanUse);
        }
    }

    private void transaction(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.unhold, R.anim.unfade);
            if (!to.isAdded()) {
                transaction.hide(mCurrentFragment).add(R.id.content_view, to).commit();
            } else {
                transaction.hide(mCurrentFragment).show(to).commit();
            }
            mCurrentFragment = to;
        }
    }
}
