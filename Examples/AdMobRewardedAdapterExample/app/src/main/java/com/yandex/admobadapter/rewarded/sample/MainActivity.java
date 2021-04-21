/*
 * This file is a part of the Yandex Advertising Network
 *
 * Version for Android (C) 2019 YANDEX
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://legal.yandex.com/partner_ch/
 */
package com.yandex.admobadapter.rewarded.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/YYYYYYYYYY";

    private Button mLoadAdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadAdButton = findViewById(R.id.load_button);
        mLoadAdButton.setOnClickListener(new RewardedAdClickListener());
    }

    private class RewardedAdClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mLoadAdButton.setEnabled(false);
            mLoadAdButton.setText(getResources().getText(R.string.start_load_ad_button));

            /*
             Replace AD_UNIT_ID with your unique Ad Unit ID.
             Please, read official documentation how to obtain one: https://apps.admob.co
             */
            final AdRequest adRequest = new AdRequest.Builder().build();
            final RewardedAdListener rewardedAdListener = new RewardedAdListener();
            RewardedAd.load(MainActivity.this, AD_UNIT_ID, adRequest, rewardedAdListener);
        }
    }

    private class RewardedAdListener extends RewardedAdLoadCallback implements OnUserEarnedRewardListener {

        @Override
        public void onAdLoaded(@NonNull final RewardedAd rewardedAd) {
            super.onAdLoaded(rewardedAd);

            rewardedAd.show(MainActivity.this, this);

            mLoadAdButton.setEnabled(true);
            mLoadAdButton.setText(getResources().getText(R.string.load_button));
        }

        @Override
        public void onAdFailedToLoad(@NonNull final LoadAdError loadAdError) {
            super.onAdFailedToLoad(loadAdError);

            mLoadAdButton.setEnabled(true);
            mLoadAdButton.setText(getResources().getText(R.string.load_button));

            Toast.makeText(MainActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUserEarnedReward(@NonNull final RewardItem rewardItem) {
            final String rewardedCallbackMessage = String.format(Locale.US, "onRewarded(), %d %s",
                    rewardItem.getAmount(), rewardItem.getType());
            Toast.makeText(MainActivity.this, rewardedCallbackMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
