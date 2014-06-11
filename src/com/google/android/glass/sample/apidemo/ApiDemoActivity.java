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

package com.google.android.glass.sample.apidemo;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.sample.apidemo.card.CardsActivity;
import com.google.android.glass.sample.apidemo.card.CardAdapter;
import com.google.android.glass.sample.apidemo.opengl.OpenGlService;
import com.google.android.glass.sample.apidemo.theming.ThemingActivity;
import com.google.android.glass.sample.apidemo.touchpad.SelectGestureDemoActivity;
import com.google.android.glass.sample.apidemo.voicemenu.VoiceMenuActivity;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a card scroll view with examples of different GDK APIs.
 *
 * <ol>
 * <li> Cards
 * <li> GestureDetector
 * <li> textAppearance[Large|Medium|Small]
 * <li> OpenGL LiveCard
 * <li> VoiceMenu
 * </ol>
 */
public class ApiDemoActivity extends Activity {

    private static final String TAG = ApiDemoActivity.class.getSimpleName();

    // Index of api demo cards.
    // Visible for testing.
    static final int CARDS = 0;
    static final int GESTURE_DETECTOR = 1;
    static final int THEMING = 2;
    static final int OPENGL = 3;
    static final int VOICE_MENU = 4;

    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;

    // Visible for testing.
    CardScrollView getScroller() {
        return mCardScroller;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardAdapter(createCards(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();
    }

    /**
     * Create list of API demo cards.
     */
    private List<Card> createCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(CARDS, new Card(context).setText(R.string.text_cards));
        cards.add(GESTURE_DETECTOR, new Card(context).setText(R.string.text_gesture_detector));
        cards.add(THEMING, new Card(context).setText(R.string.text_theming));
        cards.add(OPENGL, new Card(context).setText(R.string.text_opengl));
        cards.add(VOICE_MENU, new Card(context).setText(R.string.text_voice_menu));
        return cards;
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
     * Different type of activities can be shown, when tapped on a card.
     */
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked view at position " + position + ", row-id " + id);
                int soundEffect = Sounds.TAP;
                switch (position) {
                    case CARDS:
                        startActivity(new Intent(ApiDemoActivity.this, CardsActivity.class));
                        break;

                    case GESTURE_DETECTOR:
                        startActivity(new Intent(ApiDemoActivity.this,
                                SelectGestureDemoActivity.class));
                        break;

                    case THEMING:
                        startActivity(new Intent(ApiDemoActivity.this, ThemingActivity.class));
                        break;

                    case OPENGL:
                        startService(new Intent(ApiDemoActivity.this, OpenGlService.class));
                        break;

                    case VOICE_MENU:
                        startActivity(new Intent(ApiDemoActivity.this, VoiceMenuActivity.class));
                        break;

                    default:
                        soundEffect = Sounds.ERROR;
                        Log.d(TAG, "Don't show anything");
                }

                // Play sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        });
    }
}
