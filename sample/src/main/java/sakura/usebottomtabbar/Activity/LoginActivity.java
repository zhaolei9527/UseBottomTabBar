package sakura.usebottomtabbar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sakura.usebottomtabbar.R;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener {

    private Button btn_viewpager;
    private Button btn_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_viewpager = (Button) findViewById(R.id.btn_viewpager);
        btn_fragment = (Button) findViewById(R.id.btn_fragment);
        btn_viewpager.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_viewpager:
                startActivity(new Intent(LoginActivity.this, ViewPagerActivity.class));
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(LoginActivity.this, FragmentActivity.class));
                break;
        }
    }
}
