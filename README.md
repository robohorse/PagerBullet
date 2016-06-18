# PagerBullet
Simple Bullet-Indication ViewPager Wrapper

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

#About
Copyright 2016 Vadim Shchenev, and licensed under the MIT license. No attribution is necessary but it's very much appreciated. Star this project if you like it.
