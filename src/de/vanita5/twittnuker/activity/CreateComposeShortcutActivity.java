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

import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.R;

public class CreateComposeShortcutActivity extends Activity implements Constants {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = new Intent();
		final Intent launch_intent = new Intent(INTENT_ACTION_COMPOSE);
		final ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this,
				R.drawable.ic_launcher);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launch_intent);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.compose));
		setResult(RESULT_OK, intent);
		finish();
	}
}
