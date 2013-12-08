/*
 *			Twittnuker - Twitter client for Android
 *
 * Copyright (C) 2013 vanita5 <mail@vanita5.de>
 *
 * This program incorporates a modified version of Twidere.
 * Copyright (C) 2012-2013 Mariotaku Lee <mariotaku.lee@gmail.com>
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

package de.vanita5.twittnuker.activity;

import static de.vanita5.twittnuker.util.Utils.restartActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.activity.iface.IThemedActivity;
import de.vanita5.twittnuker.util.ThemeUtils;

public abstract class BaseThemedPreferenceActivity extends PreferenceActivity implements Constants, IThemedActivity {

	private int mCurrentThemeResource, mCurrentThemeColor;

	@Override
	public void finish() {
		super.finish();
		overrideCloseAnimationIfNeeded();
	}

	@Override
	public final int getCurrentThemeResource() {
		return mCurrentThemeResource;
	}

	@Override
	public Resources getDefaultResources() {
		return super.getResources();
	}

	@Override
	public void navigateUpFromSameTask() {
		NavUtils.navigateUpFromSameTask(this);
		overrideCloseAnimationIfNeeded();
	}

	@Override
	public void overrideCloseAnimationIfNeeded() {
		if (shouldOverrideActivityAnimation()) {
			ThemeUtils.overrideActivityCloseAnimation(this);
		} else {
			ThemeUtils.overrideNormalActivityCloseAnimation(this);
		}
	}

	@Override
	public boolean shouldOverrideActivityAnimation() {
		return true;
	}

	protected abstract int getThemeColor();

	protected abstract int getThemeResource();

	protected final boolean isThemeChanged() {
		return getThemeResource() != mCurrentThemeResource || getThemeColor() != mCurrentThemeColor;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		if (shouldOverrideActivityAnimation()) {
			ThemeUtils.overrideActivityOpenAnimation(this);
		}
		setTheme();
		super.onCreate(savedInstanceState);
		setActionBarBackground();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (shouldRestartWhenThemeChanged() && isThemeChanged()) {
			restart();
		}
	}

	protected final void restart() {
		restartActivity(this);
	}

	protected boolean shouldRestartWhenThemeChanged() {
		return true;
	}

	private final void setActionBarBackground() {
		ThemeUtils.applyActionBarBackground(getActionBar(), this);
	}

	private final void setTheme() {
		mCurrentThemeResource = getThemeResource();
		mCurrentThemeColor = getThemeColor();
		setTheme(mCurrentThemeResource);
	}

}