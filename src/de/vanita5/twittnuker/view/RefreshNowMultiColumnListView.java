package de.vanita5.twittnuker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.huewu.pla.lib.MultiColumnListView;

import org.mariotaku.refreshnow.widget.OnRefreshListener;
import org.mariotaku.refreshnow.widget.RefreshMode;
import org.mariotaku.refreshnow.widget.iface.IRefreshNowView;

public class RefreshNowMultiColumnListView extends MultiColumnListView implements IRefreshNowView {

	private final Helper mHelper;

	public RefreshNowMultiColumnListView(final Context context) {
		this(context, null);
	}

	public RefreshNowMultiColumnListView(final Context context, final AttributeSet attrs) {
		this(context, attrs, android.R.attr.listViewStyle);
	}

	public RefreshNowMultiColumnListView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		mHelper = new Helper(this, context, attrs, defStyle);
	}

	@Override
	public boolean canOverScroll() {
		final int childCount = getChildCount(), count = getCount();
		if (childCount == 0) return false;
		if (count > childCount)
			return true;
		else {
			final View firstVisibleChild = getChildAt(0);
			final View lastVisibleChild = getChildAt(childCount - 1);
			return firstVisibleChild.getTop() < 0 || lastVisibleChild.getBottom() > getBottom();
		}
	}

	@Override
	public RefreshMode getRefreshMode() {
		return mHelper.getRefreshMode();
	}

	@Override
	public boolean isRefreshing() {
		return mHelper.isRefreshing();
	}

	@Override
	public boolean onTouchEvent(final MotionEvent ev) {
		mHelper.beforeOnTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

	@Override
	public void setOnRefreshListener(final OnRefreshListener listener) {
		mHelper.setOnRefreshListener(listener);
	}

	@Override
	public void setRefreshComplete() {
		mHelper.setRefreshComplete();
	}

	@Override
	public void setRefreshIndicatorView(final View view) {
		mHelper.setRefreshIndicatorView(view);
	}

	@Override
	public void setRefreshing(final boolean refreshing) {
		mHelper.setRefreshing(refreshing);
	}

	@Override
	public void setRefreshMode(final RefreshMode mode) {
		mHelper.setRefreshMode(mode);
	}

	@Override
	protected void onOverScrolled(final int scrollX, final int scrollY, final boolean clampedX, final boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		mHelper.afterOnOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	@Override
	protected boolean overScrollBy(final int deltaX, final int deltaY, final int scrollX, final int scrollY,
								   final int scrollRangeX, final int scrollRangeY, final int maxOverScrollX, final int maxOverScrollY,
								   final boolean isTouchEvent) {
		if (!canOverScroll()) return true;
		mHelper.beforeOverScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
				maxOverScrollY, isTouchEvent);
		final int computedDy = mHelper.computeDeltaY(deltaY, scrollY, isTouchEvent);
		return super.overScrollBy(deltaX, computedDy, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
				mHelper.getMaxYOverscrollDistance(), isTouchEvent);
	}

}