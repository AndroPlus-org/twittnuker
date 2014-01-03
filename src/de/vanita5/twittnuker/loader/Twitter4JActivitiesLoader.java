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

package de.vanita5.twittnuker.loader;

import static de.vanita5.twittnuker.util.Utils.getTwitterInstance;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;

import org.mariotaku.jsonserializer.JSONSerializer;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.model.ParcelableActivity;

import twitter4j.Activity;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Twitter4JActivitiesLoader extends AsyncTaskLoader<List<ParcelableActivity>> implements Constants {
	private final Context mContext;

	private final long mAccountId;
    private final List<ParcelableActivity> mData = new ArrayList<ParcelableActivity>();
	private final boolean mIsFirstLoad;
    private final int mTabPosition;

	private final boolean mHiResProfileImage;

	private final Object[] mSavedActivitiesFileArgs;

	public Twitter4JActivitiesLoader(final Context context, final long account_id, final List<ParcelableActivity> data,
			final String[] save_file_args, final int tabPosition) {
		super(context);
		mContext = context;
		mAccountId = account_id;
		mIsFirstLoad = data == null;
		mTabPosition = tabPosition;
		mSavedActivitiesFileArgs = save_file_args;
		mHiResProfileImage = context.getResources().getBoolean(R.bool.hires_profile_image);
	}

	@Override
    public final List<ParcelableActivity> loadInBackground() {
        final File serializationFile = getSerializationFile();
        if (mIsFirstLoad && mTabPosition >= 0 && mSavedActivitiesFileArgs != null && serializationFile != null) {
            final List<ParcelableActivity> cached = getCachedData(serializationFile);
			if (cached != null) {
				mData.addAll(cached);
				Collections.sort(mData);
                return new CopyOnWriteArrayList<ParcelableActivity>(mData);
			}
		}
        final SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        final int loadItemLimit = prefs.getInt(PREFERENCE_KEY_LOAD_ITEM_LIMIT, PREFERENCE_DEFAULT_LOAD_ITEM_LIMIT);
		final List<Activity> activities;
		try {
			final Paging paging = new Paging();
            paging.setCount(Math.min(100, loadItemLimit));
			activities = getActivities(getTwitter(), paging);
		} catch (final TwitterException e) {
			e.printStackTrace();
            return new CopyOnWriteArrayList<ParcelableActivity>(mData);
		}
        if (activities == null) return new CopyOnWriteArrayList<ParcelableActivity>(mData);
		mData.clear();
		for (final Activity activity : activities) {
			mData.add(new ParcelableActivity(activity, mAccountId, mHiResProfileImage));
		}
		Collections.sort(mData);
        saveCachedData(serializationFile, mData);
        return new CopyOnWriteArrayList<ParcelableActivity>(mData);
	}

	protected final long getAccountId() {
		return mAccountId;
	}

	protected abstract List<Activity> getActivities(Twitter twitter, Paging paging) throws TwitterException;

	protected final List<ParcelableActivity> getData() {
		return mData;
	}

	protected final Twitter getTwitter() {
		return getTwitterInstance(mContext, mAccountId, true);
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}
	
	private List<ParcelableActivity> getCachedData(final File file) {
		if (file == null)
			return null;
		try {
			return JSONSerializer.listFromFile(file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private File getSerializationFile() {
		if (mSavedActivitiesFileArgs == null)
			return null;
		try {
			return JSONSerializer.getSerializationFile(mContext, mSavedActivitiesFileArgs);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void saveCachedData(final File file, final List<ParcelableActivity> data) {
		if (file == null || data == null) return;
        final SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        final int databaseItemLimit = prefs.getInt(PREFERENCE_KEY_DATABASE_ITEM_LIMIT,
                PREFERENCE_DEFAULT_DATABASE_ITEM_LIMIT);
		try {
            final List<ParcelableActivity> activities = data.subList(0, Math.min(databaseItemLimit, data.size()));
			JSONSerializer.toFile(file, activities.toArray(new ParcelableActivity[activities.size()]));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
