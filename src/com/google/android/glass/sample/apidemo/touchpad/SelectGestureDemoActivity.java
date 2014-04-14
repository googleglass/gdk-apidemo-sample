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

import com.google.android.glass.app.Card;
import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.sample.apidemo.card.CardAdapter;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Lets the user select which gesture detector demo they want to run.
 */
public class SelectGestureDemoActivity extends Activity {

    private static final String TAG = SelectGestureDemoActivity.class.getSimpleName();

    // Index of the gesture detector demos.
    private static final int DISCRETE = 0;
    private static final int CONTINUOUS = 1;

    private CardScrollView mCardScroller;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        setContentView(mCardScroller);
        setCardScrollerListener();
    }

    /**
     * Create the list of cards that represent the available gesture detector demos.
     */
    private List<Card> createCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(DISCRETE, new Card(context).setText(R.string.discrete_gestures));
        cards.add(CONTINUOUS, new Card(context).setText(R.string.continuous_gestures));
        return cards;
    }

    /**
     * Sets the click listener that invokes a gesture detector demo based on the card that is
     * selected.
     */
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked view at position " + position + ", row-id " + id);
                switch (position) {
                    case DISCRETE:
                        startActivity(new Intent(SelectGestureDemoActivity.this,
                                DiscreteGesturesActivity.class));
                        break;

                    case CONTINUOUS:
                        startActivity(new Intent(SelectGestureDemoActivity.this,
                                ContinuousGesturesActivity.class));
                        break;

                    // TODO(hyunyoungs): Add other demos.

                    default:
                        Log.d(TAG, "Don't show anything");
              }
            }
        });
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
}
