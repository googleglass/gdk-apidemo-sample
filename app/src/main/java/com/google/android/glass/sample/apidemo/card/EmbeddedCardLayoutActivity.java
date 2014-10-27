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

package com.google.android.glass.sample.apidemo.card;

import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a card scroll view that shows an example of using a custom embedded layout in a
 * {@code CardBuilder}.
 */
public final class EmbeddedCardLayoutActivity extends Activity {

    private CardScrollView mCardScroller;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new EmbeddedCardLayoutAdapter(this, createItems()));
        setContentView(mCardScroller);
    }

    /** Creates some sample items that will be displayed on cards in the card scroll view. */
    private List<SimpleTableItem> createItems() {
        ArrayList<SimpleTableItem> items = new ArrayList<SimpleTableItem>();

        items.add(new SimpleTableItem(R.drawable.ic_circle_blue, "Water", "8 oz"));
        items.add(new SimpleTableItem(R.drawable.ic_circle_yellow, "Eggs, large", "2"));
        items.add(new SimpleTableItem(R.drawable.ic_circle_red, "Ground beef", "4 oz"));
        items.add(new SimpleTableItem(R.drawable.ic_circle_green, "Brussel sprouts", "1 cup"));
        items.add(new SimpleTableItem(R.drawable.ic_circle_green, "Celery", "1 stalk"));
        items.add(new SimpleTableItem(R.drawable.ic_circle_red, "Beef jerky", "8 strips"));
        items.add(new SimpleTableItem(R.drawable.ic_circle_yellow, "Almonds", "3 handfuls"));
        items.add(new SimpleTableItem(
                R.drawable.ic_circle_red, "Strawberry fruit leather", "2.5 miles"));

        return items;
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
