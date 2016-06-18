# PagerBullet
Simple Bullet-Indication ViewPager Wrapper

<p>
<img src="images/sample.png" height="500">
</p>

#Quick start

1) Just add this dependency into your build.gradle
```gradle
compile 'com.robohorse.pagerbullet:pagerbullet:1.0.5'
```
2) Replace your default ViewPager by com.robohorse.pagerbullet.PagerBullet

3) Call this
```java
pagerBullet.invalidateBullets();
```
when 
```java
pagerAdapter.notifyDataSetChanged();
```
4) Profit!

#Customization

<b>Dot color:</b>
```xml
    <color name="pager_bullet_active">#836556</color>
    <color name="pager_bullet_inactive">#219382</color>
```
or
```java
pagerBullet.setIndicatorTintColorScheme(Color.WHITE, Color.BLUE);
```

<b>Dot size:</b>
```xml
    <dimen name="pager_bullet_indicator_active_dot_diameter">12dp</dimen>
    <dimen name="pager_bullet_indicator_inactive_dot_diameter">8dp</dimen>
```

<b>Dot margin:</b>
```xml
    <dimen name="pager_bullet_indicator_dot_margin">4dp</dimen>
```

#About
Copyright 2016 Vadim Shchenev, and licensed under the MIT license. No attribution is necessary but it's very much appreciated. Star this project if you like it.
