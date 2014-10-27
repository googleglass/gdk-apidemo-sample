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

import com.google.android.glass.sample.apidemo.R;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class CardBuilderActivity extends Activity {

    private CardScrollView mCardScroller;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        setContentView(mCardScroller);
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
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));
        cards.add(createCardWithImages(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_card_text_with_images)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TEXT_FIXED)
                .setText(R.string.text_card_text_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TEXT)
                .setText(R.string.text_card_text_stack_indicator)
                .showStackIndicator(true)
                .setAttributionIcon(R.drawable.ic_smile));

        // Add cards that demonstrate COLUMNS layouts.
        cards.add(createCardWithImages(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.text_card_columns_not_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));
        cards.add(new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.text_card_columns_with_icon)
                .setIcon(R.drawable.ic_wifi_150)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));
        cards.add(createCardWithImages(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.text_card_columns_fixed)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));

        // Add cards that demonstrate CAPTION layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.CAPTION)
                .addImage(R.drawable.beach)
                .setText(R.string.text_card_caption)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));
        cards.add(new CardBuilder(context, CardBuilder.Layout.CAPTION)
                .addImage(R.drawable.beach)
                .setText(R.string.text_card_caption_with_icon)
                .setIcon(R.drawable.ic_avatar_70)
                .setFootnote(R.string.text_card_footnote)
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));

        // Add cards that demonstrate TITLE layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.beach)
                .setText(R.string.text_card_title));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .addImage(R.drawable.beach)
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
                .setTimestamp(R.string.text_card_timestamp)
                .setAttributionIcon(R.drawable.ic_smile));

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
        card.addImage(R.drawable.codemonkey6);
        card.addImage(R.drawable.codemonkey7);
        card.addImage(R.drawable.codemonkey8);
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
