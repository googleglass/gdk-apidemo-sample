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
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Displays information about the discrete gestures reported by the gesture detector (i.e., basic
 * tap/swipe gestures and finger counts).
 */
public class DiscreteGesturesActivity extends Activity
        implements GestureDetector.BaseListener, GestureDetector.FingerListener {

    private TextView mLastGesture;
    private TextView mFingerCount;
    private TextView mSwipeAgainTip;
    private GestureDetector mGestureDetector;

    private boolean mSwipedDownOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discrete_gestures);

        mLastGesture = (TextView) findViewById(R.id.last_gesture);
        mFingerCount = (TextView) findViewById(R.id.finger_count);
        mSwipeAgainTip = (TextView) findViewById(R.id.swipe_again_tip);

        // Initialize the gesture detector and set the activity to listen to discrete gestures.
        mGestureDetector = new GestureDetector(this).setBaseListener(this).setFingerListener(this);
    }

    /**
     * Overridden to allow the gesture detector to process motion events that occur anywhere within
     * the activity.
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    /**
     * This method includes special behavior to handle SWIPE_DOWN gestures. The first time the user
     * swipes down, we return true so that the user can still see the feedback in the gesture
     * label, and we fade in an instructional tip label. The second time the user swipes down, we
     * return false so that the activity can handle the event and return to the previous activity.
     */
    @Override
    public boolean onGesture(Gesture gesture) {
        mLastGesture.setText(gesture.name());

        if (gesture == Gesture.SWIPE_DOWN) {
            if (!mSwipedDownOnce) {
                mSwipeAgainTip.animate().alpha(1.0f);
                mSwipedDownOnce = true;
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onFingerCountChanged(int previousCount, int currentCount) {
        mFingerCount.setText(Integer.toString(currentCount));
    }
}
