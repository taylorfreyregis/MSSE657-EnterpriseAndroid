package linkedin;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class CourseServerMessagingFragment extends Fragment {

	RelativeLayout mRelativeLayout;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_gcm, container, false);

        return mRelativeLayout;
    }
}
