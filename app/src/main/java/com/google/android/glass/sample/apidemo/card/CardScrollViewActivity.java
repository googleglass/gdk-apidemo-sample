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

import com.google.android.glass.media.Sounds;
import com.google.android.glass.sample.apidemo.R;
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
import java.util.Random;

/**
 * Creates a card scroll view with various examples of its API.
 */
public final class CardScrollViewActivity extends Activity {

    /** Actions associated with cards. */
    private enum Action {
        DELETION_HERE(R.string.text_card_tap_to_delete, R.drawable.codemonkey1),
        NAVIGATION_TO_BEGIN(R.string.text_card_tap_to_navigate_begin, R.drawable.codemonkey2),
        NAVIGATION_TO_END(R.string.text_card_tap_to_navigate_end, R.drawable.codemonkey3),
        INSERTION_AT_BEGIN(R.string.text_card_tap_to_insert_begin, R.drawable.codemonkey4),
        INSERTION_BEFORE(R.string.text_card_tap_to_insert_before, R.drawable.codemonkey5),
        INSERTION_AFTER(R.string.text_card_tap_to_insert_after, R.drawable.codemonkey6),
        INSERTION_AT_END(R.string.text_card_tap_to_insert_end, R.drawable.codemonkey7),
        NO_ACTION(R.string.text_card_no_action, R.drawable.codemonkey8);

        final int textId;
        final int imageId;

        Action(int textId, int imageId) {
            this.textId = textId;
            this.imageId = imageId;
        }
    }

    /**
     * Adapter class that handles list of cards with associated actions and
     * allows for mutations without notifying the adapter of the data change yet
     * (through method {@link #notifyDataSetChanged}). Useful to demonstrate
     * mutation animations.
     */
    private final class CardAdapterWithMutations extends CardAdapter {

        private final List<Action> mActions;

        public CardAdapterWithMutations() {
            super(new ArrayList<CardBuilder>());
            mActions = new ArrayList<Action>();
        }

        /** Inserts a card into the adapter, without notifying. */
        public void insertCardWithoutNotification(int position, CardBuilder card, Action action) {
            mCards.add(position, card);
            mActions.add(position, action);
        }

        /** Deletes card from the adapter, without notifying. */
        public void deleteCardWithoutNotification(int position) {
            mCards.remove(position);
            mActions.remove(position);
        }

        /** Returns the action associated with the card at position. */
        public Action getActionAt(int position) {
            return mActions.get(position);
        }
    }

    private final Random mRandom = new Random();

    private CardScrollView mCardScroller;
    private CardAdapterWithMutations mAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mCardScroller = new CardScrollView(this);
        setupAdapter();
        setupClickListener();
        setContentView(mCardScroller);
    }

    /**
     * Sets up adapter.
     */
    private void setupAdapter() {
        mAdapter = new CardAdapterWithMutations();

        // Insert initial cards, one of each kind.
        for (int i = 0; i < 8; i++) {
            int position = i;
            Action action = Action.values()[i];
            CardBuilder card = new CardBuilder(this, CardBuilder.Layout.COLUMNS);
            card.setText(action.textId).addImage(action.imageId);
            mAdapter.insertCardWithoutNotification(position, card, action);
        }

        // Setting adapter notifies the card scroller of new content.
        mCardScroller.setAdapter(mAdapter);
    }

    /**
     * Sets up click listener.
     */
    private void setupClickListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                switch (mAdapter.getActionAt(position)) {
                    case DELETION_HERE:
                        am.playSoundEffect(Sounds.TAP);
                        deleteCard(position);
                        break;
                    case NAVIGATION_TO_BEGIN:
                        am.playSoundEffect(Sounds.TAP);
                        navigateToCard(0);
                        break;
                    case NAVIGATION_TO_END:
                        am.playSoundEffect(Sounds.TAP);
                        navigateToCard(mAdapter.getCount() - 1);
                        break;
                    case INSERTION_AT_BEGIN:
                        am.playSoundEffect(Sounds.TAP);
                        insertNewCard(0);
                        break;
                    case INSERTION_BEFORE:
                        am.playSoundEffect(Sounds.TAP);
                        insertNewCard(position);
                        break;
                    case INSERTION_AFTER:
                        am.playSoundEffect(Sounds.TAP);
                        insertNewCard(position + 1);
                        break;
                    case INSERTION_AT_END:
                        am.playSoundEffect(Sounds.TAP);
                        insertNewCard(mAdapter.getCount());
                        break;
                    default:
                        am.playSoundEffect(Sounds.DISALLOWED);
                        break;
                }
            }
        });
    }

    /**
     * Deletes a card at the given position using proper insertion animation
     * (the card scroller will animate the old card from view).
     */
    private void deleteCard(int position) {
        // Delete card in the adapter, but don't call notifyDataSetChanged() yet.
        // Instead, request proper animation for deleted card from card scroller,
        // which will notify the adapter at the right time during the animation.
        mAdapter.deleteCardWithoutNotification(position);
        mCardScroller.animate(position, CardScrollView.Animation.DELETION);
    }

    /** Navigates to card at given position. */
    private void navigateToCard(int position) {
        mCardScroller.animate(position, CardScrollView.Animation.NAVIGATION);
    }

    /**
     * Inserts a new card at the given position using proper insertion animation
     * (the card scroller will animate to the new card).
     */
    private void insertNewCard(int position) {
        // Insert new card in the adapter, but don't call notifyDataSetChanged()
        // yet. Instead, request proper animation to inserted card from card scroller,
        // which will notify the adapter at the right time during the animation.
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.COLUMNS);
        Action action = Action.values()[mRandom.nextInt(8)];
        card.setText(action.textId).addImage(action.imageId);
        mAdapter.insertCardWithoutNotification(position, card, action);
        mCardScroller.animate(position, CardScrollView.Animation.INSERTION);
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
