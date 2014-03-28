package de.vanita5.twittnuker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class HalfWidthSpace extends View {

    /**
     * {@inheritDoc}
     */
    public HalfWidthSpace(final Context context) {
        // noinspection NullableProblems
        this(context, null);
    }

    /**
     * {@inheritDoc}
     */
    public HalfWidthSpace(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * {@inheritDoc}
     */
    public HalfWidthSpace(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        if (getVisibility() == VISIBLE) {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("Twidere", String.format("l:%d, t: %d", l, t));
    }

    /**
     * Draw nothing.
     *
     * @param canvas an unused parameter.
     */
    @Override
    public void draw(final Canvas canvas) {
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec), height = width / 2;
        setMeasuredDimension(width, height);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }
}