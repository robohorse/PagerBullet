package com.robohorse.pagerbullet;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by vadim on 28.01.17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PagerBulletTest {
    private PagerBullet pagerBullet;
    private Activity activity;
    private LinearLayout dotsLayout;
    private TextView textIndicator;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(Activity.class).create().get();
        pagerBullet = new PagerBullet(activity);
        dotsLayout = (LinearLayout) pagerBullet.findViewById(R.id.pagerBulletIndicator);
        textIndicator = (TextView) pagerBullet.findViewById(R.id.pagerBulletIndicatorText);
    }

    @Test
    public void testInitialization() {
        Assert.assertNotNull(pagerBullet);
        Assert.assertNotNull(dotsLayout);
        Assert.assertNotNull(textIndicator);
        Assert.assertTrue(pagerBullet.getVisibility() == View.VISIBLE);
    }

    @Test
    public void testDotsVisibility() {
        final int dotsLimit = 6;
        final int dotsCount = dotsLimit - 1;
        pagerBullet.setTextSeparatorOffset(dotsLimit);
        pagerBullet.setAdapter(new TestPagerAdapter(activity, dotsCount));

        Assert.assertTrue(dotsLayout.getVisibility() == View.VISIBLE);
        Assert.assertTrue(dotsLayout.getChildCount() == dotsCount);
        Assert.assertTrue(textIndicator.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testTextIndicatorVisibility() {
        final int dotsLimit = 6;
        final int dotsCount = dotsLimit + 1;
        pagerBullet.setTextSeparatorOffset(dotsLimit);
        pagerBullet.setAdapter(new TestPagerAdapter(activity, dotsCount));

        Assert.assertTrue(dotsLayout.getVisibility() == View.INVISIBLE);
        Assert.assertTrue(textIndicator.getVisibility() == View.VISIBLE);
    }
}
