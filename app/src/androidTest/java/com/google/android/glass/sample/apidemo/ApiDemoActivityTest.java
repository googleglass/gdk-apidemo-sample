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

package com.google.android.glass.sample.apidemo;

import com.google.android.glass.sample.apidemo.card.CardsActivity;
import com.google.android.glass.sample.apidemo.theming.ThemingActivity;
import com.google.android.glass.sample.apidemo.touchpad.SelectGestureDemoActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Unit tests for {@link ApiDemoActivity}.
 */
@SmallTest
public class ApiDemoActivityTest extends ActivityInstrumentationTestCase2<ApiDemoActivity> {

    private static final int MONITOR_TIMEOUT = 5 * 1000;
    private Instrumentation.ActivityMonitor mCardsActivityMonitor;
    private Instrumentation.ActivityMonitor mDetectorActivityMonitor;
    private Instrumentation.ActivityMonitor mThemingActivityMonitor;

    public ApiDemoActivityTest() {
        super(ApiDemoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCardsActivityMonitor = new Instrumentation.ActivityMonitor(
            CardsActivity.class.getName(), null, false);
        mDetectorActivityMonitor = new Instrumentation.ActivityMonitor(
            SelectGestureDemoActivity.class.getName(), null, false);
        mThemingActivityMonitor = new Instrumentation.ActivityMonitor(
            ThemingActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(mCardsActivityMonitor);
        getInstrumentation().addMonitor(mDetectorActivityMonitor);
        getInstrumentation().addMonitor(mThemingActivityMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests if the "Cards" API demo can be started when the card was tapped.
     */
    public void testTapFirstCard() throws Exception {
         final ApiDemoActivity activity = getActivity();
         assertNotNull(activity);
         activity.runOnUiThread(new Runnable() {

              @Override
              public void run() {
                  activity.getScroller().performItemClick(null /* not used */,
                          ApiDemoActivity.CARD_BUILDER, -1 /* not used */);
              }
         });

         Activity cardsActivity = mCardsActivityMonitor.waitForActivityWithTimeout(MONITOR_TIMEOUT);
         assertNotNull("Activity was not started", cardsActivity);
         cardsActivity.finish();
    }

    /**
     * Tests if the "GestureDetector" API demo can be started when the card was tapped.
     */
    public void testTapSecondCard() throws Exception {
        final ApiDemoActivity activity = getActivity();
        assertNotNull(activity);
        activity.runOnUiThread(new Runnable() {

             @Override
             public void run() {
                 activity.getScroller().performItemClick(null /* not used */,
                         ApiDemoActivity.GESTURE_DETECTOR, -1 /* not used */);
             }
        });

        Activity touchActivity = mDetectorActivityMonitor.waitForActivityWithTimeout(MONITOR_TIMEOUT);
        assertNotNull("Activity was not started", touchActivity);
        touchActivity.finish();
   }

    /**
     * Tests if the "Theming" API demo can be started when the card was tapped.
     */
    public void testTapThirdCard() throws Exception {
        final ApiDemoActivity activity = getActivity();
        assertNotNull(activity);
        activity.runOnUiThread(new Runnable() {

             @Override
             public void run() {
                 activity.getScroller().performItemClick(null /* not used */,
                         ApiDemoActivity.THEMING, -1 /* not used */);
             }
        });

        Activity themeActivity = mThemingActivityMonitor.waitForActivityWithTimeout(MONITOR_TIMEOUT);
        assertNotNull("Activity was not started", themeActivity);
        themeActivity.finish();
   }
}
