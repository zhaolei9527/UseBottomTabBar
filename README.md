
## 就不能有个东西，能够一句话搞定Android底部导航栏，一键绑定Fragment、ViewPager吗？所以，这个BottomTabBar产生了。

## 相关内容参数详细介绍《简书》：[一句话搞定Android底部导航栏，一键绑定Fragment、ViewPager](https://github.com/zhaolei9527/UseBottomTabBar)

<img src="https://img.shields.io/badge/Build-1.0.2-brightgreen.svg" align=center />

<img src="https://github.com/zhaolei9527/UseBottomTabBar/blob/master/sample/src/main/res/drawable/5124923-4fbc8113a029953a.gif" width = "250" height = "400" alt="演示" align=center />

# How to use UseBottomTabBar：
**Step 1. Add the JitPack repository to your build file**
**Add it in your root build.gradle at the end of repositories:**
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2. Add the dependency**
```java
	dependencies {
	     compile 'com.github.zhaolei9527:UseBottomTabBar:v1.0.2'
	}
```

## XML布局文件代码：

```xml
        <sakura.bottomtabbar.BottomTabBar
            android:id="@+id/BottomTabBar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
          ></sakura.bottomtabbar.BottomTabBar>
```

**控件可设置参数：**

| 参数名 | 描述 |
|:-------------:|:-------------:|
| **tab_bar_background** | **BottomTabBar的整体背景颜色** |
| **tab_img_width** | **图片宽度** |
| **tab_img_height** | **图片高度** |
| **tab_font_size** | **文字尺寸** |
| **tab_img_font_padding** | **图片文字间隔** |
| **tab_padding_top** | **上边距** |
| **tab_padding_bottom** | **下边距** |
| **tab_isshow_divider** | **是否显示分割线** |
| **tab_divider_height** | **分割线高度** |
| **tab_divider_background** | **分割线背景** |
| **tab_selected_color** | **选中的颜色** |
| **tab_unselected_color** | **未选中的颜色** |

## Activity文件代码：
**导包：`import sakura.bottomtabbar.BottomTabBar;`**

**绑定Fragment的:（一句话搞定有木有？）**
```java 
            BottomTabBar.initFragmentorViewPager(getSupportFragmentManager())
                .addReplaceLayout(R.id.fl_content)
                .addTabItem("草莓", getResources().getDrawable(R.mipmap.icon01), getResources().getDrawable(R.mipmap.icon_round), FragmentA.class)
                .addTabItem("凤梨", getResources().getDrawable(R.mipmap.icon02), getResources().getDrawable(R.mipmap.icon_round), FragmentB.class)
                .addTabItem("樱桃", getResources().getDrawable(R.mipmap.icon03), getResources().getDrawable(R.mipmap.icon_round), FragmentC.class)
                .addTabItem("香蕉", getResources().getDrawable(R.mipmap.icon04), getResources().getDrawable(R.mipmap.icon_round), FragmentD.class)
                .commit();
```
**绑定ViewPager的:（一句话搞定有木有？）**
```java
        BottomTabBar.initFragmentorViewPager(viewpager)
                .setChangeColor(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary))
                .addTabItem("草莓", getResources().getDrawable(R.mipmap.icon01), getResources().getDrawable(R.mipmap.icon_round))
                .addTabItem("凤梨", getResources().getDrawable(R.mipmap.icon02), getResources().getDrawable(R.mipmap.icon_round))
                .addTabItem("樱桃", getResources().getDrawable(R.mipmap.icon03), getResources().getDrawable(R.mipmap.icon_round))
                .addTabItem("香蕉", getResources().getDrawable(R.mipmap.icon04), getResources().getDrawable(R.mipmap.icon_round))
                .commit(); 
```
#### 划重点，这个initFragmentorViewPager ( getSupportFragmentManager() | ViewPager)方法一定要第一个调用，没有这个初始化，后边什么也做不了。还有，记得 Commit。

**代码可配置参数方法：**

| 参数名 | 描述 |
|:-------------:|:-------------:|
| **setImgSize** | **设置图片的尺寸** |
| **setFontSize** | **设置文字的尺寸** |
| **setTabPadding** | **设置Tab的padding值* |
| **setChangeColor** | **设置选中未选中的颜色** |
| **setTabBarBackgroundColor** | **设置BottomTabBar的整体背景颜色** |
| **setTabBarBackgroundResource** | **设置BottomTabBar的整体背景图片** |
| **isShowDivider** | **是否显示分割线** |
| **setDividerHeight** | **设置分割线的高度** |
| **setDividerColor** | **设置分割线的颜色** |

**很简单，对不对，你想干什么，我都替你干**。

另外，上述实例的`addTabItem`是支持选中状态图片切换的方法，在此之外，还支持不需要切换图片的模式。
```java
/**
     * 添加TabItem
     * @param name  文字
     * @param imgId 图片id
     * @return
     */
    public BottomTabBar addTabItem(String name, int imgId)｛...｝

/**
     * 添加TabItem
     * @param name          文字
     * @param imgId         图片id
     * @param Fragmentclazz Fragment类
     * @return
     */
    public BottomTabBar addTabItem(String name, int imgId, Class Fragmentclazz)｛...｝
```

另外，考虑到在底部导航栏点击切换界面的需求之外的其他需求，`BottomTabBar `提供了点击事件的回调。
```java
((BottomTabBar) findViewById(R.id.BottomTabBar))
                .initFragmentorViewPager(getSupportFragmentManager())
                .addReplaceLayout(R.id.fl_content)
                .addTabItem("草莓", getResources().getDrawable(R.mipmap.icon01), getResources().getDrawable(R.mipmap.icon_round), FragmentA.class)
                .addTabItem("凤梨", getResources().getDrawable(R.mipmap.icon02), getResources().getDrawable(R.mipmap.icon_round), FragmentB.class)
                .addTabItem("樱桃", getResources().getDrawable(R.mipmap.icon03), getResources().getDrawable(R.mipmap.icon_round), FragmentC.class)
                .addTabItem("香蕉", getResources().getDrawable(R.mipmap.icon04), getResources().getDrawable(R.mipmap.icon_round), FragmentD.class)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, View V) {
                        Toast.makeText(FragmentActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .commit();
```

**觉得还不够方便？还想要什么功能？告诉我！欢迎Issues，欢迎Star**

****


