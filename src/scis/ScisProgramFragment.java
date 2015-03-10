package scis;

import java.util.List;

import web_services.JsonParser;
import web_services.RestVerb;
import web_services.RestfulIntentService;

import com.taylorcfrey.msse657enterpriseandroid.R;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ScisProgramFragment extends Fragment /*implements WebServiceResponseListener*/ {
	
	private static final String TAG = "ScisProgramFragment";
	
	private List<ScisProgram> mPrograms;
    private List<ScisCourse> mCourses;
//	private ScisProgramAdapter mScisProgramAdapter;
    private ScisProgramExpandableAdapter mScisProgramExpandableAdapter;
	
	// Views
	private RelativeLayout mRelativeLayout;
//	private ListView mProgramListView;
    private ExpandableListView mProgramListView;
	private ProgressDialog mProgressDialog;

    private Uri mProgramUri = Uri.parse("http://regisscis.net/Regis2/webresources/regis2.program");
    private Uri mCourseUri = Uri.parse("http://regisscis.net/Regis2/webresources/regis2.course");

	// Receiver
	private BroadcastReceiver mProgramReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "mProgramReceiver");
			if (intent.getAction().equals(RestfulIntentService.ACTION_WEB_SERVICE_RESPONSE) &&
                    intent.getStringExtra(RestfulIntentService.EXTRA_URI).equals(mProgramUri.toString())){
				JsonParser parser = new JsonParser();
				List<ScisProgram> programs = parser.parseProgramsJson(intent.getByteArrayExtra(RestfulIntentService.EXTRA_DATA));
                if (programs != null && programs.size() > 0) {
                    mPrograms = programs;
                    Log.d(TAG, "Query Courses");
                    queryCourses();
                }
			}
		}
		
	};
    private BroadcastReceiver mCourseReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "mCourseReceiver");
            if (intent.getAction().equals(RestfulIntentService.ACTION_WEB_SERVICE_RESPONSE) &&
                    intent.getStringExtra(RestfulIntentService.EXTRA_URI).equals(mCourseUri.toString())){
                JsonParser parser = new JsonParser();
                List<ScisCourse> courses = parser.parseCoursesJson(intent.getByteArrayExtra(RestfulIntentService.EXTRA_DATA));
                if (courses != null && courses.size() > 0) {
                    mCourses = courses;
                    for (ScisCourse course : mCourses) {
                        for (ScisProgram program : mPrograms) {
                            if (program.getId() == course.getProgramId()) {
                                program.getScisCourses().add(course);
                                break;
                            }
                        }
                    }
                    mProgressDialog.dismiss();
                    mScisProgramExpandableAdapter = new ScisProgramExpandableAdapter(getActivity(),
                            mPrograms,
                            R.layout.list_group_scis_program,
                            R.layout.list_item_scis_course);
                    mProgramListView.setAdapter(mScisProgramExpandableAdapter);
                }
            }
        }

    };
	IntentFilter mFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_scis_program_list, container, false);

		mProgramListView = (ExpandableListView) mRelativeLayout.findViewById(R.id.list_view_expandable_scis);
		mProgramListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView,
                                        View view,
                                        int groupPosition,
                                        int childPosition,
                                        long id) {
                Toast.makeText(getActivity(), "Child: " + mScisProgramExpandableAdapter.getChild(groupPosition, childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
		
		// Create the progress dialog. Dismiss when results begin to show from the web service call
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setCancelable(false);
		mProgressDialog.setTitle("Loading");
		mProgressDialog.setMessage("Querying...");
		mProgressDialog.show();
		
		mFilter = new IntentFilter(RestfulIntentService.ACTION_WEB_SERVICE_RESPONSE);
		
//		new WebServiceRequestTask(this).execute(); // No longer used
		
        return mRelativeLayout;
    }
	
	@Override
	public void onResume() {
		super.onResume();
        Log.d(TAG, "Register Receivers");
		getActivity().registerReceiver(mProgramReceiver, mFilter);
        getActivity().registerReceiver(mCourseReceiver, mFilter);
        Log.d(TAG, "Query Programs");
        queryPrograms();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(mProgramReceiver);
        getActivity().unregisterReceiver(mCourseReceiver);
	}

    private void queryPrograms() {
        Intent serviceIntent = new Intent(getActivity(), RestfulIntentService.class);
        serviceIntent.setData(mProgramUri);
        serviceIntent.setAction(RestVerb.GET.toString());
        getActivity().startService(serviceIntent);
    }

    private void queryCourses() {
        Intent serviceIntent = new Intent(getActivity(), RestfulIntentService.class);
        serviceIntent.setData(mCourseUri);
        serviceIntent.setAction(RestVerb.GET.toString());
        getActivity().startService(serviceIntent);
    }

	/* No longer used
	@Override
	public void onWebServiceResponse(String response) {
		// TODO Auto-generated method stub
		JsonParser parser = new JsonParser();
		mPrograms = parser.parseJson(response);
		mScisProgramAdapter = new ScisProgramAdapter(getActivity(), R.layout.list_item_scis_program, mPrograms);
		mProgramListView.setAdapter(mScisProgramAdapter);
		
	}
	*/

	// To dismiss the dialog
//	progress.dismiss();
	
	
}
