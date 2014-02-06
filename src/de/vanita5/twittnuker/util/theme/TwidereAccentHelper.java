/*
 * Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013-2014 vanita5 <mail@vanita5.de>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2014 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.vanita5.twittnuker.util.theme;

import android.content.Context;
import android.content.res.Resources;
import android.view.Window;

import com.negusoft.holoaccent.AccentHelper;
import com.negusoft.holoaccent.dialog.DividerPainter;

import de.vanita5.twittnuker.content.res.TwidereAccentResources;

public class TwidereAccentHelper extends AccentHelper {

    private DividerPainter mDividerPainter;
    private final int mAccentColor;
	private final int mThemeResources;
	private Resources mResources;

	public TwidereAccentHelper(final int color, final int theme) {
        super(color);
        mAccentColor = color;
		mThemeResources = theme;
    }

	@Override
	public Resources getResources(final Context c, final Resources resources) {
		if (mResources != null) return mResources;
		return mResources = new TwidereAccentResources(c, super.getResources(c, resources), mAccentColor, mThemeResources);
	}

    @Override
    public void prepareDialog(final Context c, final Window window) {
        if (mDividerPainter == null) {
            if (mAccentColor != 0) {
                mDividerPainter = new DividerPainter(mAccentColor);
            } else {
                mDividerPainter = new DividerPainter(c);
            }
        }
        mDividerPainter.paint(window);
    }

}