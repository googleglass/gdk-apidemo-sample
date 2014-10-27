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

package com.google.android.glass.sample.apidemo.theming;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.sample.apidemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Adapter class that displays examples of the default textAppearance styles on Glass.
 */
public class TextAppearanceAdapter extends CardScrollAdapter {

    enum TextAppearanceLayout {
        LARGE(R.layout.text_appearances_large),
        MEDIUM(R.layout.text_appearances_medium),
        SMALL(R.layout.text_appearances_small);

        private final int mResid;

        TextAppearanceLayout(int resid) {
            mResid = resid;
        }

        int getResourceID() {
            return mResid;
        }
    }

    private LayoutInflater mInflater;

    public TextAppearanceAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return TextAppearanceLayout.values().length;
    }

    @Override
    public TextAppearanceLayout getItem(int position) {
        return TextAppearanceLayout.values()[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        // Because cards in this example are static, a non-null convertView
        // can be reused unconditionally.
        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(getItem(position).getResourceID(), null);
        }
        return view;
    }

    @Override
    public int getPosition(Object item) {
        for (int i = 0; i < TextAppearanceLayout.values().length; i++) {
            if (getItem(i).equals(item)) {
                return i;
            }
        }
        return AdapterView.INVALID_POSITION;
    }
}
