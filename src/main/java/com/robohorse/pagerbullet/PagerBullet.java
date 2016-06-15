package com.robohorse.pagerbullet;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
    private ViewPager viewPager;
    private TextView textIndicator;
    private LinearLayout layoutIndicator;

    private int separatorId;
    private int offset = -1;

    public PagerBullet(Context context) {
        super(context);
        init(context);
    }

    public PagerBullet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PagerBullet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.item_view_pager, null);
        textIndicator = (TextView) rootView.findViewById(R.id.pagerBulletIndicatorText);
        layoutIndicator = (LinearLayout) rootView.findViewById(R.id.pagerBulletIndicator);
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
        addView(rootView);
    }

    public void setTextSeparatorIdWithOffset(int separatorId, int offset) {
        this.separatorId = separatorId;
        this.offset = offset;
    }

    public void setAdapter(final PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        invalidateBullets(adapter);
    }

    public void setCurrentItem(int position) {
        viewPager.setCurrentItem(position);
        setCurrentItem(position);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void invalidateBullets(PagerAdapter adapter) {
        final boolean hasSeparator = separatorId != 0;
        textIndicator.setVisibility(hasSeparator ? VISIBLE : INVISIBLE);
        layoutIndicator.setVisibility(hasSeparator ? INVISIBLE : VISIBLE);

        if (!hasSeparator()) {
            initIndicator(adapter.getCount());
        }
        setIndicatorItem(viewPager.getCurrentItem());
    }

    private void initIndicator(int count) {
        layoutIndicator.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        final int margin = 4;
        params.setMargins(margin, 0, margin, 0);

        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.inactive_dot));
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
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        return separatorId != 0 && pagerAdapter.getCount() > offset;
    }

    private void setItemText(int index) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (null != adapter) {
            final int count = adapter.getCount();
            textIndicator.setText(String.format(getContext().getString(separatorId), index + 1, count));
        }
    }

    private void setItemBullet(int index) {
        final int indicatorItemsCount = layoutIndicator.getChildCount();
        for (int i = 0; i < indicatorItemsCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i != index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),
                        R.drawable.inactive_dot));

            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),
                        R.drawable.active_dot));
            }
        }
    }
}
