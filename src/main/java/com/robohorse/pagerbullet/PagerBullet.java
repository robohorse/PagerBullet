package com.robohorse.pagerbullet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vadim on 15.06.16.
 */
public class PagerBullet extends FrameLayout {
    private static final String DIGIT_PATTERN = "[^0-9.]";
    private static final int DEFAULT_INDICATOR_OFFSET_VALUE = 20;
    private int offset = DEFAULT_INDICATOR_OFFSET_VALUE;

    private ViewPager viewPager;
    private TextView textIndicator;
    private LinearLayout layoutIndicator;
    private View indicatorContainer;

    private int activeColorTint;
    private int inactiveColorTint;

    public PagerBullet(Context context) {
        super(context);
        init(context);
    }

    public PagerBullet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttributes(context, attrs);
    }

    public PagerBullet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttributes(context, attrs);
    }

    public static Drawable wrapTintDrawable(Drawable sourceDrawable, int color) {
        if (color != 0) {
            final Drawable wrapDrawable = DrawableCompat.wrap(sourceDrawable);
            DrawableCompat.setTint(wrapDrawable, color);
            wrapDrawable.setBounds(
                    0, 0,
                    wrapDrawable.getIntrinsicWidth(),
                    wrapDrawable.getIntrinsicHeight()
            );
            return wrapDrawable;

        } else {
            return sourceDrawable;
        }
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerBullet);
        String heightValue = typedArray.getString(R.styleable.PagerBullet_panelHeightInDp);

        if (null != heightValue) {
            heightValue = heightValue.replaceAll(DIGIT_PATTERN, "");
            float height = Float.parseFloat(heightValue);
            final FrameLayout.LayoutParams params = (LayoutParams) indicatorContainer.getLayoutParams();
            params.height = Math.round(
                    TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            height,
                            getResources().getDisplayMetrics()
                    )
            );
            indicatorContainer.requestLayout();
        }
        typedArray.recycle();
    }

    public void setIndicatorTintColorScheme(int activeColorTint, int inactiveColorTint) {
        this.activeColorTint = activeColorTint;
        this.inactiveColorTint = inactiveColorTint;
        invalidateBullets();
    }

    public void setTextSeparatorOffset(int offset) {
        this.offset = offset;
    }

    public void setAdapter(final PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        invalidateBullets(adapter);
    }

    public void setCurrentItem(int position) {
        setCurrentItem(position, true);
    }

    public void setCurrentItem(int position, boolean smoothScroll) {
        viewPager.setCurrentItem(position, smoothScroll);
        setIndicatorItem(position);
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void invalidateBullets() {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (null != adapter) {
            invalidateBullets(adapter);
        }
    }

    public void invalidateBullets(PagerAdapter adapter) {
        final boolean hasSeparator = hasSeparator();
        textIndicator.setVisibility(hasSeparator ? VISIBLE : INVISIBLE);
        layoutIndicator.setVisibility(hasSeparator ? INVISIBLE : VISIBLE);

        if (!hasSeparator) {
            initIndicator(adapter.getCount());
        }
        setIndicatorItem(viewPager.getCurrentItem());
    }

    public void setIndicatorVisibility(boolean visibility) {
        indicatorContainer.setVisibility(visibility ? VISIBLE : INVISIBLE);
    }

    private void init(Context context) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View rootView = layoutInflater.inflate(R.layout.item_view_pager, this);

        indicatorContainer = rootView.findViewById(R.id.pagerBulletIndicatorContainer);
        textIndicator = (TextView) indicatorContainer.findViewById(R.id.pagerBulletIndicatorText);
        layoutIndicator = (LinearLayout) indicatorContainer.findViewById(R.id.pagerBulletIndicator);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPagerBullet);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicatorItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator(int count) {
        layoutIndicator.removeAllViews();
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        final int margin = Math.round(getContext().getResources()
                .getDimension(R.dimen.pager_bullet_indicator_dot_margin));

        params.setMargins(margin, 0, margin, 0);
        final Drawable drawableInactive = ContextCompat.getDrawable(
                getContext(),
                R.drawable.inactive_dot
        );

        for (int i = 0; i < count; i++) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(drawableInactive);
            layoutIndicator.addView(imageView, params);
        }
    }

    private void setIndicatorItem(int index) {
        if (!hasSeparator()) {
            setItemBullet(index);
        } else {
            setItemText(index);
        }
    }

    private boolean hasSeparator() {
        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        return null != pagerAdapter && pagerAdapter.getCount() > offset;
    }

    private void setItemText(int index) {
        final PagerAdapter adapter = viewPager.getAdapter();
        if (null != adapter) {
            final int count = adapter.getCount();
            textIndicator.setText(
                    String.format(getContext().getString(R.string.pager_bullet_separator),
                            String.valueOf(index + 1),
                            String.valueOf(count)
                    )
            );
        }
    }

    private void setItemBullet(int selectedPosition) {
        Drawable drawableInactive = ContextCompat.getDrawable(getContext(), R.drawable.inactive_dot);
        drawableInactive = wrapTintDrawable(drawableInactive, inactiveColorTint);
        Drawable drawableActive = ContextCompat.getDrawable(getContext(), R.drawable.active_dot);
        drawableActive = wrapTintDrawable(drawableActive, activeColorTint);

        final int indicatorItemsCount = layoutIndicator.getChildCount();

        for (int position = 0; position < indicatorItemsCount; position++) {
            final ImageView imageView = (ImageView) layoutIndicator.getChildAt(position);

            if (position != selectedPosition) {
                imageView.setImageDrawable(drawableInactive);

            } else {
                imageView.setImageDrawable(drawableActive);
            }
        }
    }
}
