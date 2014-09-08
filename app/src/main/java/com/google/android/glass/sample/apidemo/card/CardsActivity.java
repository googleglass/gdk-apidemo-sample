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

import com.google.android.glass.media.Sounds;
import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class CardsActivity extends Activity {

    private CardAdapter mAdapter;
    private CardScrollView mCardScroller;

    private int mTapPosition;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mAdapter = new CardAdapter(createCards(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setupClickListener();
        setContentView(mCardScroller);
    }

    /**
     * Sets up click listener.
     */
    private void setupClickListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (position == mTapPosition) {
                    // Clicking on this card inserts a new card at the end.
                    am.playSoundEffect(Sounds.TAP);
                    insertNewCardAtEnd();
                } else {
                    // Clicking on any other card is not allowed.
                    am.playSoundEffect(Sounds.DISALLOWED);
                }
            }
        });
    }

    /**
     * Creates list of cards that showcase different type of {@link CardBuilder} API.
     */
    private List<CardBuilder> createCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        // Add cards that demonstrate TEXT layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_card_text_not_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));
        cards.add(createCardWithImages(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_card_text_with_images)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TEXT_FIXED)
                .setText(R.string.text_card_text_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));

        // Action card. Tapping on this card will insert a new card at the end.
        mTapPosition = cards.size();
        cards.add(new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_card_tap_to_insert)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));

        // Add cards that demonstrate COLUMNS layouts.
        cards.add(createCardWithImages(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.text_card_columns_not_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));
        cards.add(new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.text_card_columns_with_icon)
                .setIcon(R.drawable.ic_wifi_150)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));
        cards.add(createCardWithImages(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.text_card_columns_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));

        // Add cards that demonstrate CAPTION layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.CAPTION)
                .addImage(R.drawable.beach)
                .setText(R.string.text_card_caption)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));
        cards.add(new CardBuilder(context, CardBuilder.Layout.CAPTION)
                .addImage(R.drawable.beach)
                .setText(R.string.text_card_caption_with_icon)
                .setIcon(R.drawable.ic_avatar_70)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));

        // Add cards that demonstrate TITLE layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.codemonkey1)
                .setText(R.string.text_card_title));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.codemonkey1)
                .setText(R.string.text_card_title_icon)
                .setIcon(R.drawable.ic_phone_50));

        // Add cards that demonstrate MENU layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.text_card_menu)
                .setFootnote(R.string.text_card_menu_description)
                .setIcon(R.drawable.ic_phone_50));

        // Add cards that demonstrate ALERT layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.ALERT)
                .setText(R.string.text_card_alert)
                .setFootnote(R.string.text_card_alert_description)
                .setIcon(R.drawable.ic_warning_150));

        // Add cards that demonstrate AUTHOR layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.AUTHOR)
                .setText(R.string.text_card_author_text)
                .setIcon(R.drawable.ic_avatar_70)
                .setHeading(R.string.text_card_author_heading)
                .setSubheading(R.string.text_card_author_subheading)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp));

        return cards;
    }

    /**
     * Returns a new {@link CardBuilder} with the specified layout and adds five images to it for
     * the mosaic.
     */
    private static CardBuilder createCardWithImages(Context context, CardBuilder.Layout layout) {
        CardBuilder card = new CardBuilder(context, layout);
        card.addImage(R.drawable.codemonkey1);
        card.addImage(R.drawable.codemonkey2);
        card.addImage(R.drawable.codemonkey3);
        card.addImage(R.drawable.codemonkey4);
        card.addImage(R.drawable.codemonkey5);
        return card;
    }

    /**
     * Inserts a new card at the end using proper insertion animation
     * (the card scroller will animate to the new card).
     */
    private void insertNewCardAtEnd() {
        // Insert new card in the adapter, but don't call notifyDataSetChanged()
        // yet. Instead, request proper animation to inserted card from card scroller,
        // which will notify the adapter at the right time during the animation.
        int newPosition = mAdapter.getCount();
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.COLUMNS)
                .addImage(R.drawable.codemonkey3)
                .setText("New card at position " + newPosition);
        mAdapter.appendCardWithoutNotification(card);
        mCardScroller.animate(newPosition, CardScrollView.Animation.INSERTION);
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
