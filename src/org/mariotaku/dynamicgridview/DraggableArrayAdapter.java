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

package org.mariotaku.dynamicgridview;

import android.content.Context;

import org.mariotaku.dynamicgridview.DraggableAdapter;

import de.vanita5.twittnuker.adapter.ArrayAdapter;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DraggableArrayAdapter<T> extends ArrayAdapter<T> implements DraggableAdapter {

	final int INVALID_ID = -1;

	private final HashMap<T, Integer> mIdMap = new HashMap<T, Integer>();

	public DraggableArrayAdapter(final Context context, final int layoutRes) {
		this(context, layoutRes, null);
	}

	public DraggableArrayAdapter(final Context context, final int layoutRes, final Collection<? extends T> collection) {
		super(context, layoutRes, collection);
		rebuildIdMap();
	}

	@Override
	public void add(final T item) {
		super.add(item);
		rebuildIdMap();
	}

	@Override
	public void addAll(final Collection<? extends T> collection) {
		super.addAll(collection);
		rebuildIdMap();
	}

	@Override
	public void clear() {
		super.clear();
		rebuildIdMap();
	}

	@Override
	public long getItemId(final int position) {
		if (position < 0 || position >= mIdMap.size()) return INVALID_ID;
		final T item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean remove(final int position) {
		final boolean result = super.remove(position);
		rebuildIdMap();
		return result;
	}

	@Override
	public void removeAll(final List<T> collection) {
		super.removeAll(collection);
		rebuildIdMap();
	}

	@Override
	public void reorderElements(final int position, final int newPosition) {
		final List<T> objects = mData;

		T previous = objects.get(position);
		final int iterator = newPosition < position ? 1 : -1;
		final int afterPosition = position + iterator;
		for (int cellPosition = newPosition; cellPosition != afterPosition; cellPosition += iterator) {
			final T tmp = objects.get(cellPosition);
			objects.set(cellPosition, previous);
			previous = tmp;
		}
		notifyDataSetChanged();
	}

	@Override
	public void sort(final Comparator<? super T> comparator) {
		super.sort(comparator);
		rebuildIdMap();
	}

	@Override
	public void swapElements(final int position, final int newPosition) {
		final List<T> objects = mData;
		final T temp = objects.get(position);
		objects.set(position, objects.get(newPosition));
		objects.set(newPosition, temp);
		notifyDataSetChanged();
	}

	private void rebuildIdMap() {
		mIdMap.clear();
		for (int i = 0, j = mData.size(); i < j; ++i) {
			mIdMap.put(mData.get(i), i);
		}
	}
}