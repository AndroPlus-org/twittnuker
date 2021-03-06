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

package de.vanita5.twittnuker.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.activity.support.DataExportActivity;
import de.vanita5.twittnuker.activity.support.DataImportActivity;
import de.vanita5.twittnuker.adapter.ArrayAdapter;
import de.vanita5.twittnuker.util.ThemeUtils;
import de.vanita5.twittnuker.view.holder.ViewHolder;

import java.util.List;

public class SettingsActivity extends BasePreferenceActivity {

	private static long HEADER_ID_RESTORE_ICON = 1001;

	private HeaderAdapter mAdapter;

	private PackageManager mPackageManager;

	public HeaderAdapter getHeaderAdapter() {
		if (mAdapter != null) return mAdapter;
		return mAdapter = new HeaderAdapter(ThemeUtils.getContextForActionIcons(this, getThemeResourceId()));
	}

	@Override
	public void onBuildHeaders(final List<Header> target) {
		loadHeadersFromResource(R.xml.settings_headers, target);
		final HeaderAdapter adapter = getHeaderAdapter();
		adapter.clear();
		adapter.addAll(target);
		final ComponentName main = new ComponentName(this, MainActivity.class);
		if (mPackageManager.getComponentEnabledSetting(main) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
			final Header restoreIconHeader = new Header();
			restoreIconHeader.titleRes = R.string.want_old_icon_back;
			restoreIconHeader.title = getString(restoreIconHeader.titleRes);
			restoreIconHeader.id = HEADER_ID_RESTORE_ICON;
			restoreIconHeader.intent = getIntent();
			adapter.add(restoreIconHeader);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		if (getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) != null) return false;
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	@Override
	public void onHeaderClick(final Header header, final int position) {
		if (header.id == HEADER_ID_RESTORE_ICON) {
			final ComponentName main = new ComponentName(this, MainActivity.class);
			//final ComponentName main2 = new ComponentName(this, MainHondaJOJOActivity.class);
			mPackageManager.setComponentEnabledSetting(main, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
			//mPackageManager.setComponentEnabledSetting(main2, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
			//		PackageManager.DONT_KILL_APP);
			Toast.makeText(this, R.string.icon_restored_message, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		super.onHeaderClick(header, position);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case MENU_HOME: {
				onBackPressed();
				return true;
			}
			case MENU_IMPORT_SETTINGS: {
				final Intent intent = new Intent(this, DataImportActivity.class);
				startActivity(intent);
				return true;
			}
			case MENU_EXPORT_SETTINGS: {
				final Intent intent = new Intent(this, DataExportActivity.class);
				startActivity(intent);
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setListAdapter(final ListAdapter adapter) {
		if (adapter == null) {
			super.setListAdapter(null);
		} else {
			super.setListAdapter(getHeaderAdapter());
		}
	}

	@Override
	public void switchToHeader(final Header header) {
		if (header == null || header.fragment == null && header.intent == null) return;
		super.switchToHeader(header);
	}

	@Override
	public void switchToHeader(final String fragmentName, final Bundle args) {
		if (fragmentName == null) return;
		super.switchToHeader(fragmentName, args);
	}

	@Override
	protected boolean isValidFragment(final String fragmentName) {
		final Class<?> cls;
		try {
			cls = Class.forName(fragmentName);
		} catch (final ClassNotFoundException e) {
			return false;
		}
		return Fragment.class.isAssignableFrom(cls);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		mPackageManager = getPackageManager();
		super.onCreate(savedInstanceState);
		setIntent(getIntent().addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState != null) {
			invalidateHeaders();
		}
	}

	private static class HeaderAdapter extends ArrayAdapter<Header> {

		static final int HEADER_TYPE_CATEGORY = 0;
		static final int HEADER_TYPE_NORMAL = 1;

		private final Context mContext;
		private final Resources mResources;

		public HeaderAdapter(final Context context) {
			super(context, R.layout.list_item_settings);
			mContext = context;
			mResources = context.getResources();
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public int getItemViewType(final int position) {
			final Header header = getItem(position);
			return getHeaderType(header);
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			final Header header = getItem(position);
			final int viewType = getHeaderType(header);
			final View view;
			switch (viewType) {
				case HEADER_TYPE_CATEGORY: {
					view = new TextView(mContext, null, android.R.attr.listSeparatorTextViewStyle);
					((TextView) view).setText(header.getTitle(mResources));
					break;
				}
				default: {
					final boolean viewChanged = convertView != null
							&& !(convertView.getTag() instanceof HeaderViewHolder);
					view = super.getView(position, viewChanged ? null : convertView, parent);
					final HeaderViewHolder holder;
					final Object tag = view.getTag();
					if (tag instanceof HeaderViewHolder) {
						holder = (HeaderViewHolder) tag;
					} else {
						holder = new HeaderViewHolder(view);
						view.setTag(holder);
					}
					final CharSequence title = header.getTitle(mResources);
					holder.title.setText(title);
					final CharSequence summary = header.getSummary(mResources);
					if (!TextUtils.isEmpty(summary)) {
						holder.summary.setVisibility(View.VISIBLE);
						holder.summary.setText(summary);
					} else {
						holder.summary.setVisibility(View.GONE);
					}
					if (header.iconRes != 0) {
						holder.icon.setImageDrawable(mResources.getDrawable(header.iconRes));
					} else {
						holder.icon.setImageDrawable(null);
					}
					break;
				}
			}
			return view;
		}

		@Override
		public boolean isEnabled(final int position) {
			return getItemViewType(position) != HEADER_TYPE_CATEGORY;
		}

		private static int getHeaderType(final Header header) {
			if (header.fragment == null && header.intent == null)
				return HEADER_TYPE_CATEGORY;
			else
				return HEADER_TYPE_NORMAL;
		}

		private static class HeaderViewHolder extends ViewHolder {
			private final TextView title, summary;
			private final ImageView icon;

			HeaderViewHolder(final View view) {
				super(view);
				title = (TextView) findViewById(android.R.id.title);
				summary = (TextView) findViewById(android.R.id.summary);
				icon = (ImageView) findViewById(android.R.id.icon);
			}
		}

	}

}
