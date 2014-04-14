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

package com.google.android.glass.sample.apidemo.card;

import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class CardsActivity extends Activity {

    private CardScrollView mCardScroller;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        setContentView(mCardScroller);
    }

    /**
     * Create list of cards that showcase different type of {@link Card} API.
     */
    private List<Card> createCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(getImagesCard(context)
                .setImageLayout(ImageLayout.LEFT)
                .setText(R.string.text_left_images_layout_card));
        cards.add(getImagesCard(context)
                .setImageLayout(ImageLayout.FULL)
                .setText(R.string.text_full_images_layout_card));
        return cards;
    }

    private Card getImagesCard(Context context) {
        Card card = new Card(context);
        card.addImage(R.drawable.codemonkey1);
        card.addImage(R.drawable.codemonkey2);
        card.addImage(R.drawable.codemonkey3);
        card.addImage(R.drawable.codemonkey4);
        card.addImage(R.drawable.codemonkey5);
        return card;
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
