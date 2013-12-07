/*
 *				Twidere - Twitter client for Android
 * 
 * Copyright (C) 2012 Mariotaku Lee <mariotaku.lee@gmail.com>
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

package org.mariotaku.twidere.activity.support;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import org.mariotaku.twidere.Constants;
import org.mariotaku.twidere.app.TwidereApplication;
import org.mariotaku.twidere.fragment.iface.IBasePullToRefreshFragment;
import org.mariotaku.twidere.util.AsyncTwitterWrapper;
import org.mariotaku.twidere.util.MessagesManager;
import org.mariotaku.twidere.util.ThemeUtils;

@SuppressLint("Registered")
public class BaseSupportActivity extends BaseSupportThemedActivity implements Constants {

	private boolean mInstanceStateSaved, mIsVisible, mIsOnTop;

	public MessagesManager getMessagesManager() {
		return getTwidereApplication() != null ? getTwidereApplication().getMessagesManager() : null;
	}

	public TwidereApplication getTwidereApplication() {
		return (TwidereApplication) getApplication();
	}

	public AsyncTwitterWrapper getTwitterWrapper() {
		return getTwidereApplication() != null ? getTwidereApplication().getTwitterWrapper() : null;
	}

	public boolean isOnTop() {
		return mIsOnTop;
	}

	public boolean isVisible() {
		return mIsVisible;
	}

	@Override
	public void startActivity(final Intent intent) {
		super.startActivity(intent);
	}

	@Override
	public void startActivityForResult(final Intent intent, final int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	protected IBasePullToRefreshFragment getCurrentPullToRefreshFragment() {
		return null;
	}

	@Override
	protected int getThemeColor() {
		return ThemeUtils.getUserThemeColor(this);
	}

	@Override
	protected int getThemeResource() {
		return ThemeUtils.getThemeResource(this);
	}

	protected boolean isStateSaved() {
		return mInstanceStateSaved;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		mIsOnTop = false;
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mInstanceStateSaved = false;
		mIsOnTop = true;
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		mInstanceStateSaved = true;
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mIsVisible = true;
		final MessagesManager manager = getMessagesManager();
		if (manager != null) {
			manager.addMessageCallback(this);
		}
	}

	@Override
	protected void onStop() {
		mIsVisible = false;
		final MessagesManager manager = getMessagesManager();
		if (manager != null) {
			manager.removeMessageCallback(this);
		}
		super.onStop();
	}

}
