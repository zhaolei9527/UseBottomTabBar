package sakura.usebottomtabbar.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sakura.usebottomtabbar.R;

/**
 * Created by 赵磊 on 2017/8/11.
 */

public class FragmentA extends Fragment {

    private View inflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_layout, container, false);
        ((TextView)inflate.findViewById(R.id.tv)).setText("草莓");
        return inflate;
    }


}
