package sakura.usebottomtabbar.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import sakura.bottomtabbar.BottomTabBar;
import sakura.usebottomtabbar.Fragment.FragmentA;
import sakura.usebottomtabbar.Fragment.FragmentB;
import sakura.usebottomtabbar.Fragment.FragmentC;
import sakura.usebottomtabbar.Fragment.FragmentD;
import sakura.usebottomtabbar.R;

public class FragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();
    }

    private void initView() {
        //添加Frament替换的布局(仅绑定Fragment需要设置)
        //=========参数设置START========
        //.setChangeColor(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary))
        //.setDividerColor(getResources().getColor(R.color.colorPrimaryDark))
        //.setDividerHeight(2)
        //默认false
        //.isShowDivider(true)
        //.setTabPadding(5, 5, 5)
        //.setTabBarBackgroundColor(getResources().getColor(R.color.colorAccent))
        //.setTabBarBackgroundResource(getResources().getDrawable(R.mipmap.icon))
        //.setFontSize(12)
        //.setImgSize(30, 30)
        //参数设置需要在addTabItem()之前调用
        //=========参数设置END=======
        //添加TabItem
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
    }
}
