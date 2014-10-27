/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.glass.sample.apidemo.slider;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.sample.apidemo.card.CardAdapter;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.widget.Slider;
import com.google.android.glass.widget.Slider.Determinate;
import com.google.android.glass.widget.Slider.GracePeriod;
import com.google.android.glass.widget.Slider.Indeterminate;
import com.google.android.glass.widget.Slider.Scroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity that demonstrates the slider API.
 */
public final class SliderActivity extends Activity {

    // Index of slider demo cards.
    private static final int SCROLLER = 0;
    private static final int DETERMINATE = 1;
    private static final int GRACE_PERIOD = 2;
    private static final int INDETERMINATE = 3;

    private static final int MAX_SLIDER_VALUE = 5;
    private static final long ANIMATION_DURATION_MILLIS = 5000;

    private final GracePeriod.Listener mGracePeriodListener = new GracePeriod.Listener() {
        @Override
        public void onGracePeriodEnd() {
            // Play a SUCCESS sound to indicate the end of the grace period.
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.playSoundEffect(Sounds.SUCCESS);
            mGracePeriod = null;
        }

        @Override
        public void onGracePeriodCancel() {
            // Play a DIMISS sound to indicate the cancellation of the grace period.
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.playSoundEffect(Sounds.DISMISSED);
            mGracePeriod = null;
        }
    };

    private CardScrollView mCardScroller;
    private Slider mSlider;
    private Slider.Indeterminate mIndeterminate;
    private Slider.GracePeriod mGracePeriod;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Ensure screen stays on during demo.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.TAP);
                processSliderRequest(position);
            }
        });
        setContentView(mCardScroller);
        mSlider = Slider.from(mCardScroller);
    }

    @Override
    public void onBackPressed() {
        // If the Grace Period is running, cancel it instead of finishing the Activity.
        if (mGracePeriod != null) {
            mGracePeriod.cancel();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    /**
     * Processes a request to show a slider.
     *
     * Starting a new Slider, regardless of its type, automatically hides any shown Slider.
     */
    private void processSliderRequest(int position) {
        switch (position) {
            case SCROLLER:
                Slider.Scroller scroller = mSlider.startScroller(MAX_SLIDER_VALUE, 0);

                // Start an animation showing the different positions of the slider, the slider
                // automatically hides after a short time of inactivity.
                ObjectAnimator.ofFloat(scroller, "position", 0, MAX_SLIDER_VALUE)
                    .setDuration(ANIMATION_DURATION_MILLIS)
                    .start();
                break;
            case DETERMINATE:
                final Slider.Determinate determinate =
                        mSlider.startDeterminate(MAX_SLIDER_VALUE, 0);
                ObjectAnimator animator = ObjectAnimator.ofFloat(determinate, "position", 0,
                        MAX_SLIDER_VALUE);

                // Hide the slider when the animation stops.
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        determinate.hide();
                    }
                });
                // Start an animation showing the different positions of the slider.
                animator.setDuration(ANIMATION_DURATION_MILLIS)
                    .start();
                break;
            case GRACE_PERIOD:
                // Start the grace period slider and play a sound when one of the listener method
                // gets fired.
                mGracePeriod = mSlider.startGracePeriod(mGracePeriodListener);
                break;
            case INDETERMINATE:
                // Toggle between showing/hiding the indeterminate slider.
                if (mIndeterminate != null) {
                    mIndeterminate.hide();
                    mIndeterminate = null;
                } else {
                    mIndeterminate = mSlider.startIndeterminate();
                }
                break;
        }
    }

    /**
     * Create a list of cards to display as activity content.
     */
    private List<CardBuilder> createCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();
        cards.add(SCROLLER, new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_slider_scroller));
        cards.add(DETERMINATE, new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_slider_determinate));
        cards.add(GRACE_PERIOD, new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_slider_grace_period));
        cards.add(INDETERMINATE, new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_slider_indeterminate));
        return cards;
    }
}
