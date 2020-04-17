package com.robohorse.pagerbullet;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by vadim on 28.01.17.
 */

public class TestPagerAdapter extends PagerAdapter {
    private final Context context;
    private final int count;

    public TestPagerAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final FrameLayout frameLayout = new FrameLayout(context);
        collection.addView(frameLayout);
        return frameLayout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

