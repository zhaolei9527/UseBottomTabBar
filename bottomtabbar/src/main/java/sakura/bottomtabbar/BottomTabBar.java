package sakura.bottomtabbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵磊 on 2017/8/11.
 */

public class BottomTabBar  extends LinearLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Context context;
    private LinearLayout mLayout;
    //分割线
    private View mDivider;
    //图片的宽高
    private float imgWidth = 0, imgHeight = 0;
    //文字尺寸
    private float fontSize = 14;
    //文字图片的间隔
    private float fontImgPadding;
    //上边距和下边距
    private float paddingTop, paddingBottom;
    //选中未选中的颜色
    private int selectColor = Color.parseColor("#626262"), unSelectColor = Color.parseColor("#F1453B");
    //分割线高度
    private float dividerHeight;
    //是否显示分割线
    private boolean isShowDivider = false;
    //分割线背景
    private int dividerBackgroundColor = Color.parseColor("#CCCCCC");
    //BottomTabBar的整体背景
    private int tabBarBackgroundColor = Color.parseColor("#FFFFFF");
    //tabId集合
    private List<String> tabIdList = new ArrayList<>();
    //Fragment集合
    private List<Class> FragmentList = new ArrayList<>();
    //选中图片集合
    private List<Drawable> selectdrawableList = new ArrayList<>();
    //未选中集合
    private List<Drawable> unselectdrawableList = new ArrayList<>();
    //Viewpager绑定
    private ViewPager mViewpager;
    //FragmentManager绑定
    private FragmentManager mFragmentManager;
    //Tab对象
    private LinearLayout mTabContent;
    private LinearLayout.LayoutParams tab_item_imgparams;
    private LayoutParams tab_item_tvparams;
    private int mReplaceLayout = 0;
    private OnTabChangeListener listener=null;

    //mViewpager界面滑动
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //mViewpager界面选中
    @Override
    public void onPageSelected(int position) {
        changeTab(position);
    }

    //Tab选中更改
    private void changeTab(int position) {
        if (position + 1 > mTabContent.getChildCount()) {
            throw new IndexOutOfBoundsException("onPageSelected:" + (position + 1) +
                    "，of Max mTabContent ChildCount:" + mTabContent.getChildCount());
        }
        for (int i = 0; i < mTabContent.getChildCount(); i++) {
            View TabItem = mTabContent.getChildAt(i);
            if (i == position) {
                ((TextView) TabItem.findViewById(R.id.tab_item_tv)).setTextColor(selectColor);
                if (!selectdrawableList.isEmpty())
                    TabItem.findViewById(R.id.tab_item_img).setBackground(selectdrawableList.get(i));
            } else {
                ((TextView) TabItem.findViewById(R.id.tab_item_tv)).setTextColor(unSelectColor);
                if (!selectdrawableList.isEmpty())
                    TabItem.findViewById(R.id.tab_item_img).setBackground(unselectdrawableList.get(i));
            }
        }
    }

    //mViewpager界面滑动状态改变
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //Tab点击事件处理
    @Override
    public void onClick(View v) {
        for (int i = 0; i < tabIdList.size(); i++) {
            if (tabIdList.get(i).equals(v.getTag())) {
                if (mViewpager != null) {
                    //绑定ViewPager
                    mViewpager.setCurrentItem(i);
                }
                if (mFragmentManager != null) {
                    if (mReplaceLayout == 0) {
                        throw new IllegalStateException(
                                "Must input ReplaceLayout of mReplaceLayout");
                    }
                    relaceFrament(i);
                    changeTab(i);
                }
                //绑定点击监听回调
                if (listener!=null){
                    listener.onTabChange(i, v);
                }
            }
        }
    }

    //Fragment切换处理
    private void relaceFrament(int i) {
        Class aClass = FragmentList.get(i);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(aClass.getName());
            Fragment Fragment = (Fragment) clazz.newInstance();
            android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(mReplaceLayout, Fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tab标签切换监听
     */
    public interface OnTabChangeListener {
        void onTabChange(int position, View V);
    }


    public BottomTabBar(Context context) {
        super(context);
        this.context = context;
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BottomTabBar);
        if (attributes != null) {
            //图片宽度
            imgWidth = attributes.getDimension(R.styleable.BottomTabBar_tab_img_width, dp2px(30));
            //图片高度
            imgHeight = attributes.getDimension(R.styleable.BottomTabBar_tab_img_height, dp2px(30));
            //文字尺寸
            fontSize = attributes.getDimension(R.styleable.BottomTabBar_tab_font_size, 14);
            //上边距
            paddingTop = attributes.getDimension(R.styleable.BottomTabBar_tab_padding_top, dp2px(2));
            //图片文字间隔
            fontImgPadding = attributes.getDimension(R.styleable.BottomTabBar_tab_img_font_padding, dp2px(3));
            //下边距
            paddingBottom = attributes.getDimension(R.styleable.BottomTabBar_tab_padding_bottom, dp2px(5));
            //分割线高度
            dividerHeight = attributes.getDimension(R.styleable.BottomTabBar_tab_divider_height, dp2px(1));
            //是否显示分割线
            isShowDivider = attributes.getBoolean(R.styleable.BottomTabBar_tab_isshow_divider, false);
            //选中的颜色
            selectColor = attributes.getColor(R.styleable.BottomTabBar_tab_selected_color, Color.parseColor("#F1453B"));
            //未选中的颜色
            unSelectColor = attributes.getColor(R.styleable.BottomTabBar_tab_unselected_color, Color.parseColor("#626262"));
            //BottomTabBar的整体背景
            tabBarBackgroundColor = attributes.getColor(R.styleable.BottomTabBar_tab_bar_background, Color.parseColor("#FFFFFF"));
            //分割线背景
            dividerBackgroundColor = attributes.getColor(R.styleable.BottomTabBar_tab_divider_background, Color.parseColor("#CCCCCC"));
            attributes.recycle();
        }
    }

    /**
     * 初始化，主要是需要传入一个ViewPager
     * <p>
     * 此方法必须在所有的方法之前调用
     *
     * @param viewPager
     * @return
     */
    public BottomTabBar initFragmentorViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            throw new IllegalStateException(
                    "Must input Viewpager of initViewPager");
        }
        mViewpager = viewPager;
        mViewpager.addOnPageChangeListener(this);
        initView();
        return this;
    }

    /**
     * 初始化，主要是需要传入一个Fragment
     * <p>
     * 此方法必须在所有的方法之前调用
     *
     * @param FragmentManager
     * @return
     */
    public BottomTabBar initFragmentorViewPager(FragmentManager FragmentManager) {
        if (FragmentManager == null) {
            throw new IllegalStateException(
                    "Must input FragmentManager of initFragment");
        }
        mFragmentManager = FragmentManager;
        initView();
        return this;
    }

    private void initView() {
        mLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bottom_tab_bar, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLayout.setLayoutParams(layoutParams);
        mLayout.setBackgroundColor(tabBarBackgroundColor);
        addView(mLayout);
        mDivider = mLayout.findViewById(R.id.mDivider);
        mTabContent = (LinearLayout) mLayout.findViewById(R.id.mTabContent);
        if (isShowDivider) {
            LayoutParams dividerParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) dividerHeight);
            mDivider.setLayoutParams(dividerParams);
            mDivider.setBackgroundColor(dividerBackgroundColor);
            mDivider.setVisibility(VISIBLE);
        } else {
            mDivider.setVisibility(GONE);
        }
    }

    /**
     * 添加Frament替换的布局
     *
     * @param mReplaceLayoutId 替换的布局
     * @return
     */
    public BottomTabBar addReplaceLayout(int mReplaceLayoutId) {
        mReplaceLayout = mReplaceLayoutId;
        return this;
    }

    /**
     * 添加TabItem
     *
     * @param name  文字
     * @param imgId 图片id
     * @return
     */
    public BottomTabBar addTabItem(String name, int imgId) {
        return addTabItem(name, ContextCompat.getDrawable(context, imgId));
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param selectimgid   选中图片id
     * @param unselectimgid 未选中图片id
     * @return
     */
    public BottomTabBar addTabItem(String name, int selectimgid, int unselectimgid) {
        return addTabItem(name, ContextCompat.getDrawable(context, selectimgid), ContextCompat.getDrawable(context, unselectimgid));
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param imgId         图片id
     * @param Fragmentclazz Fragment类
     * @return
     */
    public BottomTabBar addTabItem(String name, int imgId, Class Fragmentclazz) {
        return addTabItem(name, ContextCompat.getDrawable(context, imgId), Fragmentclazz);
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param selectimgid   选中图片id
     * @param unselectimgid 未选中图片id
     * @param Fragmentclazz Fragment类
     * @return
     */
    public BottomTabBar addTabItem(String name, int selectimgid, int unselectimgid, Class Fragmentclazz) {
        return addTabItem(name, ContextCompat.getDrawable(context, selectimgid), ContextCompat.getDrawable(context, unselectimgid), Fragmentclazz);
    }

    /**
     * 添加TabItem
     *
     * @param name     文字
     * @param drawable 图片
     * @return
     */
    public BottomTabBar addTabItem(final String name, Drawable drawable) {
        LinearLayout TabItem = (LinearLayout) View.inflate(context, R.layout.tab_item, null);
        TabItem.setGravity(Gravity.CENTER);
        //设置TabItem标记
        TabItem.setTag(name);
        //添加标记至集合以作辨别
        tabIdList.add(String.valueOf(TabItem.getTag()));
        TabItem.setOnClickListener(this);
        //Tab图片样式及内容设置
        ImageView tab_item_img = (ImageView) TabItem.findViewById(R.id.tab_item_img);
        tab_item_imgparams = new LayoutParams((int) imgWidth, (int) imgHeight);
        tab_item_imgparams.topMargin = (int) paddingTop;
        tab_item_imgparams.bottomMargin = (int) fontImgPadding;
        tab_item_img.setLayoutParams(tab_item_imgparams);
        tab_item_img.setBackground(drawable);
        //Tab文字样式及内容设置
        TextView tab_item_tv = (TextView) TabItem.findViewById(R.id.tab_item_tv);
        tab_item_tvparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tab_item_tvparams.bottomMargin = (int) paddingBottom;
        tab_item_tv.setLayoutParams(tab_item_tvparams);
        tab_item_tv.setTextSize(fontSize);
        tab_item_tv.setText(name);
        if (tabIdList.size() == 1) {
            tab_item_tv.setTextColor(selectColor);
        } else {
            tab_item_tv.setTextColor(unSelectColor);
        }
        TabItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        mTabContent.addView(TabItem);
        return this;
    }

    /**
     * 添加TabItem
     *
     * @param name             文字
     * @param selectdrawable   选中图片
     * @param unselectdrawable 未选中图片
     * @return
     */
    public BottomTabBar addTabItem(final String name, Drawable selectdrawable, Drawable unselectdrawable) {
        selectdrawableList.add(selectdrawable);
        unselectdrawableList.add(unselectdrawable);
        LinearLayout TabItem = (LinearLayout) View.inflate(context, R.layout.tab_item, null);
        TabItem.setGravity(Gravity.CENTER);
        //设置TabItem标记
        TabItem.setTag(name);
        //添加标记至集合以作辨别
        tabIdList.add(String.valueOf(TabItem.getTag()));
        TabItem.setOnClickListener(this);
        //Tab图片样式及内容设置
        ImageView tab_item_img = (ImageView) TabItem.findViewById(R.id.tab_item_img);
        tab_item_imgparams = new LayoutParams((int) imgWidth, (int) imgHeight);
        tab_item_imgparams.topMargin = (int) paddingTop;
        tab_item_imgparams.bottomMargin = (int) fontImgPadding;
        tab_item_img.setLayoutParams(tab_item_imgparams);
        //Tab文字样式及内容设置
        TextView tab_item_tv = (TextView) TabItem.findViewById(R.id.tab_item_tv);
        tab_item_tvparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tab_item_tvparams.bottomMargin = (int) paddingBottom;
        tab_item_tv.setLayoutParams(tab_item_tvparams);
        tab_item_tv.setTextSize(fontSize);
        tab_item_tv.setText(name);
        if (tabIdList.size() == 1) {
            tab_item_tv.setTextColor(selectColor);
            tab_item_img.setBackground(selectdrawable);
        } else {
            tab_item_tv.setTextColor(unSelectColor);
            tab_item_img.setBackground(unselectdrawable);
        }
        TabItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        mTabContent.addView(TabItem);
        return this;
    }

    /**
     * 添加TabItem
     *
     * @param name          文字
     * @param drawable      图片
     * @param fragmentClass fragment类
     * @return
     */
    public BottomTabBar addTabItem(final String name, Drawable drawable, Class fragmentClass) {

        Class<?> clazz = null;
        try {
            clazz = Class.forName(fragmentClass.getName());
            Fragment fragment = (Fragment) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentList.add(fragmentClass);
        LinearLayout TabItem = (LinearLayout) View.inflate(context, R.layout.tab_item, null);
        TabItem.setGravity(Gravity.CENTER);
        //设置TabItem标记
        TabItem.setTag(name);
        //添加标记至集合以作辨别
        tabIdList.add(String.valueOf(TabItem.getTag()));
        TabItem.setOnClickListener(this);
        //Tab图片样式及内容设置
        ImageView tab_item_img = (ImageView) TabItem.findViewById(R.id.tab_item_img);
        tab_item_imgparams = new LayoutParams((int) imgWidth, (int) imgHeight);
        tab_item_imgparams.topMargin = (int) paddingTop;
        tab_item_imgparams.bottomMargin = (int) fontImgPadding;
        tab_item_img.setLayoutParams(tab_item_imgparams);
        tab_item_img.setBackground(drawable);
        //Tab文字样式及内容设置
        TextView tab_item_tv = (TextView) TabItem.findViewById(R.id.tab_item_tv);
        tab_item_tvparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tab_item_tvparams.bottomMargin = (int) paddingBottom;
        tab_item_tv.setLayoutParams(tab_item_tvparams);
        tab_item_tv.setTextSize(fontSize);
        tab_item_tv.setText(name);
        if (tabIdList.size() == 1) {
            tab_item_tv.setTextColor(selectColor);
        } else {
            tab_item_tv.setTextColor(unSelectColor);
        }
        TabItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        mTabContent.addView(TabItem);
        return this;
    }

    /**
     * 添加TabItem
     *
     * @param name             文字
     * @param selectdrawable   选中图片
     * @param unselectdrawable 未选中图片
     * @param fragmentClass    fragment类
     * @return
     */
    public BottomTabBar addTabItem(final String name, Drawable selectdrawable, Drawable unselectdrawable, Class fragmentClass) {
        selectdrawableList.add(selectdrawable);
        unselectdrawableList.add(unselectdrawable);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fragmentClass.getName());
            Fragment fragment = (Fragment) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentList.add(fragmentClass);
        LinearLayout TabItem = (LinearLayout) View.inflate(context, R.layout.tab_item, null);
        TabItem.setGravity(Gravity.CENTER);
        //设置TabItem标记
        TabItem.setTag(name);
        //添加标记至集合以作辨别
        tabIdList.add(String.valueOf(TabItem.getTag()));
        TabItem.setOnClickListener(this);
        //Tab图片样式及内容设置
        ImageView tab_item_img = (ImageView) TabItem.findViewById(R.id.tab_item_img);
        tab_item_imgparams = new LayoutParams((int) imgWidth, (int) imgHeight);
        tab_item_imgparams.topMargin = (int) paddingTop;
        tab_item_imgparams.bottomMargin = (int) fontImgPadding;
        tab_item_img.setLayoutParams(tab_item_imgparams);
        //Tab文字样式及内容设置
        TextView tab_item_tv = (TextView) TabItem.findViewById(R.id.tab_item_tv);
        tab_item_tvparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tab_item_tvparams.bottomMargin = (int) paddingBottom;
        tab_item_tv.setLayoutParams(tab_item_tvparams);
        tab_item_tv.setTextSize(fontSize);
        tab_item_tv.setText(name);
        if (tabIdList.size() == 1) {
            tab_item_tv.setTextColor(selectColor);
            tab_item_img.setBackground(selectdrawable);
        } else {
            tab_item_tv.setTextColor(unSelectColor);
            tab_item_img.setBackground(unselectdrawable);
        }
        TabItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        mTabContent.addView(TabItem);
        return this;
    }

    public void commit() {
        if (mFragmentManager != null) {
            if (mReplaceLayout == 0) {
                throw new IllegalStateException(
                        "Must input ReplaceLayout of mReplaceLayout");
            }
            if (1 > mTabContent.getChildCount()) {
                throw new IndexOutOfBoundsException("onPageSelected:1" +
                        "，of Max mTabContent ChildCount:" + mTabContent.getChildCount());
            }
            relaceFrament(0);
        }
        if (tabIdList.isEmpty()) {
            throw new IllegalStateException(
                    "You Mast addTabItem before commit");
        }
    }

    public BottomTabBar setOnTabChangeListener(OnTabChangeListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        return this;
    }

    //=========================参数设置START========================================

    /**
     * 设置图片的尺寸
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param width  宽度 px
     * @param height 高度 px
     * @return
     */
    public BottomTabBar setImgSize(float width, float height) {
        this.imgWidth = width;
        this.imgHeight = height;
        return this;
    }

    /**
     * 设置文字的尺寸
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param textSize 文字的尺寸 sp
     * @return
     */
    public BottomTabBar setFontSize(float textSize) {
        this.fontSize = textSize;
        return this;
    }

    /**
     * 设置Tab的padding值
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param top    上边距 px
     * @param middle 文字图片的距离 px
     * @param bottom 下边距 px
     * @return
     */
    public BottomTabBar setTabPadding(float top, float middle, float bottom) {
        this.paddingTop = top;
        this.fontImgPadding = middle;
        this.paddingBottom = bottom;
        return this;
    }

    /**
     * 设置选中未选中的颜色
     * <p>
     * 此方法必须在addTabItem()之前调用
     *
     * @param selectColor   选中的颜色
     * @param unSelectColor 未选中的颜色
     * @return
     */
    public BottomTabBar setChangeColor(@ColorInt int selectColor, @ColorInt int unSelectColor) {
        this.selectColor = selectColor;
        this.unSelectColor = unSelectColor;
        return this;
    }

    /**
     * 设置BottomTabBar的整体背景
     *
     * @param color 背景颜色
     * @return
     */
    public BottomTabBar setTabBarBackgroundColor(@ColorInt int color) {
        this.tabBarBackgroundColor = color;
        mLayout.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置BottomTabBar的整体背景
     *
     * @param resid 背景图片id
     * @return
     */
    public BottomTabBar setTabBarBackgroundResource(@DrawableRes int resid) {
        mLayout.setBackgroundResource(resid);
        return this;
    }

    /**
     * 设置BottomTabBar的整体背景
     * api 16开始才支持
     *
     * @param drawable 背景图片
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public BottomTabBar setTabBarBackgroundResource(Drawable drawable) {
        mLayout.setBackground(drawable);
        return this;
    }

    /**
     * 是否显示分割线
     *
     * @param isShowDivider
     * @return
     */
    public BottomTabBar isShowDivider(boolean isShowDivider) {
        this.isShowDivider = isShowDivider;
        if (isShowDivider) {
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) dividerHeight);
            mDivider.setLayoutParams(dividerParams);
            mDivider.setBackgroundColor(dividerBackgroundColor);
            mDivider.setVisibility(VISIBLE);
        } else {
            mDivider.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置分割线的高度
     *
     * @param height
     * @return
     */
    public BottomTabBar setDividerHeight(float height) {
        this.dividerHeight = height;
        if (isShowDivider) {
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) dividerHeight);
            mDivider.setLayoutParams(dividerParams);
        }
        return this;
    }

    /**
     * 设置分割线的颜色
     *
     * @param color
     * @return
     */
    public BottomTabBar setDividerColor(@ColorInt int color) {
        this.dividerBackgroundColor = color;
        if (isShowDivider) {
            mDivider.setBackgroundColor(dividerBackgroundColor);
        }
        return this;
    }

    //=========================参数设置END===========================================

    //=========================工具类START========================================

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    private int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //=========================工具类END===========================================
}