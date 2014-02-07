package de.vanita5.twittnuker.util.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.Icon;

import de.vanita5.twittnuker.R;
import de.vanita5.twittnuker.content.iface.ITwidereContextWrapper;
import de.vanita5.twittnuker.content.res.iface.IThemedResources.DrawableInterceptor;
import de.vanita5.twittnuker.graphic.icon.TwidereIcon;
import de.vanita5.twittnuker.util.ThemeUtils;

public class ActionIconsInterceptor implements DrawableInterceptor {

    private static final SparseArray<ActionIconsInterceptor.IconSpec> sIconMap = new SparseArray<ActionIconsInterceptor.IconSpec>();

    static {
        sIconMap.put(R.drawable.ic_iconic_action_twittnuker, new IconSpec(TwidereIcon.TWITTNUKER, 0.875f));
        sIconMap.put(R.drawable.ic_iconic_action_web, new IconSpec(TwidereIcon.WEB, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_compose, new IconSpec(TwidereIcon.COMPOSE, 0.875f));
        sIconMap.put(R.drawable.ic_iconic_action_color_palette, new IconSpec(TwidereIcon.COLOR_PALETTE, 0.875f));
        sIconMap.put(R.drawable.ic_iconic_action_camera, new IconSpec(TwidereIcon.CAMERA, 0.9375f));
        sIconMap.put(R.drawable.ic_iconic_action_new_message, new IconSpec(TwidereIcon.NEW_MESSAGE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_server, new IconSpec(TwidereIcon.SERVER, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_gallery, new IconSpec(TwidereIcon.GALLERY, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_save, new IconSpec(TwidereIcon.SAVE, 0.6875f));
        sIconMap.put(R.drawable.ic_iconic_action_star, new IconSpec(TwidereIcon.STAR, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_search, new IconSpec(TwidereIcon.SEARCH, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_retweet, new IconSpec(TwidereIcon.RETWEET, 0.875f));
        sIconMap.put(R.drawable.ic_iconic_action_reply, new IconSpec(TwidereIcon.REPLY, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_delete, new IconSpec(TwidereIcon.DELETE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_add, new IconSpec(TwidereIcon.ADD, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_share, new IconSpec(TwidereIcon.SHARE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_inbox, new IconSpec(TwidereIcon.INBOX, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_outbox, new IconSpec(TwidereIcon.OUTBOX, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_copy, new IconSpec(TwidereIcon.COPY, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_translate, new IconSpec(TwidereIcon.TRANSLATE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_user, new IconSpec(TwidereIcon.USER, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_accounts, new IconSpec(TwidereIcon.USERS, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_send, new IconSpec(TwidereIcon.SEND, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_edit, new IconSpec(TwidereIcon.EDIT, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_accept, new IconSpec(TwidereIcon.ACCEPT, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_cancel, new IconSpec(TwidereIcon.CANCEL, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_preferences, new IconSpec(TwidereIcon.PREFERENCES, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_mylocation, new IconSpec(TwidereIcon.LOCATION, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_mute, new IconSpec(TwidereIcon.MUTE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_quote, new IconSpec(TwidereIcon.QUOTE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_message, new IconSpec(TwidereIcon.MESSAGE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_twitter, new IconSpec(TwidereIcon.TWITTER, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_home, new IconSpec(TwidereIcon.HOME, 0.9375f));
        sIconMap.put(R.drawable.ic_iconic_action_mention, new IconSpec(TwidereIcon.AT, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_hashtag, new IconSpec(TwidereIcon.HASHTAG, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_trends, new IconSpec(TwidereIcon.TRENDS, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_list, new IconSpec(TwidereIcon.LIST, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_staggered, new IconSpec(TwidereIcon.STAGGERED, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_neko, new IconSpec(TwidereIcon.NEKO, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_tab, new IconSpec(TwidereIcon.TAB, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_extension, new IconSpec(TwidereIcon.EXTENSION, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_card, new IconSpec(TwidereIcon.CARD, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_refresh, new IconSpec(TwidereIcon.REFRESH, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_grid, new IconSpec(TwidereIcon.GRID, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_about, new IconSpec(TwidereIcon.ABOUT, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_more, new IconSpec(TwidereIcon.MORE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_open_source, new IconSpec(TwidereIcon.OPEN_SOURCE, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_notification, new IconSpec(TwidereIcon.NOTIFICATION, 0.75f));
        sIconMap.put(R.drawable.ic_iconic_action_interface, new IconSpec(TwidereIcon.INTERFACE, 0.75f));
		sIconMap.put(R.drawable.ic_iconic_action_block, new IconSpec(TwidereIcon.BLOCK, 0.75f));
		sIconMap.put(R.drawable.ic_iconic_action_warning, new IconSpec(TwidereIcon.WARNING, 0.75f));

        sIconMap.put(R.drawable.ic_iconic_action_mark, new IconSpec(TwidereIcon.ACCEPT, 0.75f));

        /** Twittnuker **/
        sIconMap.put(R.drawable.ic_iconic_action_heart, new IconSpec(TwidereIcon.HEART, 0.75f));
    }

    private static int MENU_ICON_SIZE_DP = 32;
    private final Context mContext;
    private final int mIconSize;
    private final int mIconColor;
    private final float mDensity;

    public ActionIconsInterceptor(final Context context, final DisplayMetrics dm) {
        mContext = context;
        if (context instanceof ITwidereContextWrapper) {
            final int resId = ((ITwidereContextWrapper) context).getThemeResourceId();
            mIconColor = ThemeUtils.getActionIconColor(resId);
        } else {
            mIconColor = ThemeUtils.getActionIconColor(context);
        }
        mDensity = dm.density;
        mIconSize = Math.round(mDensity * MENU_ICON_SIZE_DP);
    }

    @Override
	public Drawable getDrawable(final Resources res, final int resId) {
		final ActionIconsInterceptor.IconSpec spec = sIconMap.get(resId, null);
        if (spec == null) return null;
        final IconicFontDrawable drawable = new IconicFontDrawable(mContext, spec.icon);
        drawable.setIconPadding(Math.round(mIconSize * (1 - spec.contentFactor)) / 2);
        drawable.setIntrinsicWidth(mIconSize);
        drawable.setIntrinsicHeight(mIconSize);
        drawable.setIconColor(mIconColor);
        return drawable;
    }

    private static class IconSpec {
        private final Icon icon;
        private final float contentFactor;

        IconSpec(final Icon icon, final float contentFactor) {
            this.icon = icon;
            this.contentFactor = contentFactor;
        }
    }

}