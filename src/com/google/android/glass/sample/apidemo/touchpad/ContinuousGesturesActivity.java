/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.google.android.glass.sample.apidemo.touchpad;

import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Displays information about the continuous gestures reported by the gesture detector (i.e.,
 * scrolling events).
 */
public class ContinuousGesturesActivity extends Activity
        implements GestureDetector.ScrollListener, GestureDetector.TwoFingerScrollListener{

    private TextView mScrollType;
    private TextView mDisplacement;
    private TextView mDelta;
    private TextView mVelocity;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_continuous_gestures);

        mScrollType = (TextView) findViewById(R.id.scroll_type);
        mDisplacement = (TextView) findViewById(R.id.displacement);
        mDelta = (TextView) findViewById(R.id.delta);
        mVelocity = (TextView) findViewById(R.id.velocity);

        // Initialize the gesture detector and set the activity to listen to the continuous
        // gestures.
        mGestureDetector = new GestureDetector(this)
                .setScrollListener(this).setTwoFingerScrollListener(this);
    }

    /**
     * Overridden to allow the gesture detector to process motion events that occur anywhere within
     * the activity.
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    @Override
    public boolean onScroll(float displacement, float delta, float velocity) {
        mScrollType.setText(R.string.scroll_one_finger);
        updateScrollInfo(displacement, delta, velocity);
        return false;
    }

    @Override
    public boolean onTwoFingerScroll(float displacement, float delta, float velocity) {
        mScrollType.setText(R.string.scroll_two_finger);
        updateScrollInfo(displacement, delta, velocity);
        return false;
    }

    /**
     * Updates the text views that show the detailed scroll information.
     *
     * @param displacement the scroll displacement (position relative to the original touch-down
     *     event)
     * @param delta the scroll delta from the previous touch event
     * @param velocity the velocity of the scroll event
     */
    private void updateScrollInfo(float displacement, float delta, float velocity) {
        mDisplacement.setText(getResources().getString(
                R.string.pixel_distance_units, displacement));
        mDelta.setText(getResources().getString(R.string.pixel_distance_units, delta));
        mVelocity.setText(getResources().getString(R.string.pixel_velocity_units, velocity));
    }
}
