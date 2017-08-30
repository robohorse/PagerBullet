package com.robohorse.pagerbullet;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertNotNull(pagerBullet);
        assertNotNull(dotsLayout);
        assertNotNull(textIndicator);
        assertEquals(View.VISIBLE, pagerBullet.getVisibility());
    }

    @Test
    public void testDotsVisibility() {
        final int dotsLimit = 6;
        final int dotsCount = dotsLimit - 1;
        pagerBullet.setTextSeparatorOffset(dotsLimit);
        pagerBullet.setAdapter(new TestPagerAdapter(activity, dotsCount));

        assertEquals(View.VISIBLE, dotsLayout.getVisibility());
        assertEquals(dotsCount, dotsLayout.getChildCount());
        assertEquals(View.INVISIBLE, textIndicator.getVisibility());
    }

    @Test
    public void testTextIndicatorVisibility() {
        final int dotsLimit = 6;
        final int dotsCount = dotsLimit + 1;
        pagerBullet.setTextSeparatorOffset(dotsLimit);
        pagerBullet.setAdapter(new TestPagerAdapter(activity, dotsCount));

        assertEquals(View.INVISIBLE, dotsLayout.getVisibility());
        assertEquals(View.VISIBLE, textIndicator.getVisibility());
    }

    @Test
    public void testGetCurrentItem() {
        pagerBullet.setAdapter(new TestPagerAdapter(activity, 10));

        assertEquals(0, pagerBullet.getCurrentItem());
    }

    @Test
    public void testSetCurrentItemNoAnimation() {
        pagerBullet.setAdapter(new TestPagerAdapter(activity, 10));
        pagerBullet.setCurrentItem(2, false);

        assertEquals(2, pagerBullet.getCurrentItem());
    }

    @Test
    public void testSetCurrentItemExplicitAnimation() {
        pagerBullet.setAdapter(new TestPagerAdapter(activity, 10));
        pagerBullet.setCurrentItem(2, true);

        assertEquals(2, pagerBullet.getCurrentItem());
    }

    @Test
    public void testSetCurrentItem() {
        PagerBullet spy = Mockito.spy(pagerBullet);

        spy.setAdapter(new TestPagerAdapter(activity, 10));
        spy.setCurrentItem(2);

        assertEquals(2, spy.getCurrentItem());
        Mockito.verify(spy, Mockito.times(1)).setCurrentItem(2, true);
    }
}
