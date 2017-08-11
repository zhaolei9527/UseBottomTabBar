package sakura.usebottomtabbar.Activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sakura.bottomtabbar.BottomTabBar;
import sakura.usebottomtabbar.R;

public class ViewPagerActivity extends AppCompatActivity {

    private BottomTabBar BottomTabBar;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
    }

    private void initView() {
        BottomTabBar = (BottomTabBar) findViewById(R.id.BottomTabBar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                if (view == object) {
                    return true;
                }
                return false;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView textView = new TextView(ViewPagerActivity.this);
                textView.setText(String.valueOf(position));
                container.addView(textView);
                return textView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        BottomTabBar.initFragmentorViewPager(viewpager)
                .setChangeColor(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary))
                .addTabItem("草莓", getResources().getDrawable(R.mipmap.icon01), getResources().getDrawable(R.mipmap.icon_round))
                .addTabItem("凤梨", getResources().getDrawable(R.mipmap.icon02), getResources().getDrawable(R.mipmap.icon_round))
                .addTabItem("樱桃", getResources().getDrawable(R.mipmap.icon03), getResources().getDrawable(R.mipmap.icon_round))
                .addTabItem("香蕉", getResources().getDrawable(R.mipmap.icon04), getResources().getDrawable(R.mipmap.icon_round))
                .commit();
    }
}
