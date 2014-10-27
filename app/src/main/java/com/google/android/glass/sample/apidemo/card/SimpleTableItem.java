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

/**
 * Simple data object used to represent items in a table, which are converted into views by
 * {@link EmbeddedCardLayoutAdapter}.
 */
public class SimpleTableItem {

    /** The image resource ID associated with the table item. */
    public final int iconResId;

    /** The primary text associated with the table item. */
    public final CharSequence primaryText;

    /** The secondary text associated with the table item. */
    public final CharSequence secondaryText;

    /**
     * Initializes a new {@code SimpleTableItem} with the specified icon, primary text, and
     * secondary text.
     */
    public SimpleTableItem(int iconResId, CharSequence primaryText, CharSequence secondaryText) {
        this.iconResId = iconResId;
        this.primaryText = primaryText;
        this.secondaryText = secondaryText;
    }
}
