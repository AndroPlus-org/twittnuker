package de.vanita5.twittnuker.preference;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;

import de.vanita5.twittnuker.Constants;
import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.text.TwidereHighLightStyle;

public class LinkHighlightPreference extends AutoInvalidateListPreference implements Constants {

	private static final int[] ENTRIES_RES = { R.string.none, R.string.highlight, R.string.underline, R.string.both };
	private static final String[] VALUES = { LINK_HIGHLIGHT_OPTION_NONE, LINK_HIGHLIGHT_OPTION_HIGHLIGHT,
			LINK_HIGHLIGHT_OPTION_UNDERLINE, LINK_HIGHLIGHT_OPTION_BOTH };

	public LinkHighlightPreference(final Context context) {
		this(context, null);
	}

	public LinkHighlightPreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		final CharSequence[] entries = new CharSequence[VALUES.length];
		for (int i = 0, j = entries.length; i < j; i++) {
			final int res = ENTRIES_RES[i];
			final int option;
			switch (res) {
				case R.string.both: {
					option = LINK_HIGHLIGHT_OPTION_CODE_BOTH;
					break;
				}
				case R.string.highlight: {
					option = LINK_HIGHLIGHT_OPTION_CODE_HIGHLIGHT;
					break;
				}
				case R.string.underline: {
					option = LINK_HIGHLIGHT_OPTION_CODE_UNDERLINE;
					break;
				}
				default: {
					option = LINK_HIGHLIGHT_OPTION_CODE_NONE;
					break;
				}
			}
			final SpannableString str = new SpannableString(context.getString(res));
			str.setSpan(new TwidereHighLightStyle(option), 0, str.length(), 0);
			entries[i] = str;
		}
		setEntries(entries);
		setEntryValues(VALUES);
	}

}
