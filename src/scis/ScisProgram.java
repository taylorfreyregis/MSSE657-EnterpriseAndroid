package scis;

import java.util.ArrayList;
import java.util.List;

public class ScisProgram {

	private int mId;
	private String mTitle;
    private List<ScisCourse> mScisCourses;
	
	public ScisProgram(int id, String title) {
		this.mId = id;
		this.mTitle = title;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getTitle() {
		return mTitle;
	}
    public List<ScisCourse> getScisCourses() {
        if (mScisCourses == null) {
            mScisCourses = new ArrayList<ScisCourse>();
        }
        return mScisCourses;
    }
	
	public void setId(int id) {
		this.mId = id;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}

    public void setScisCourses(List<ScisCourse> scisCourses) {
        mScisCourses = scisCourses;
    }

    public void addScisCourse(ScisCourse scisCourse) {
        if (!mScisCourses.contains(scisCourse)) {
            this.mScisCourses.add(scisCourse);
        }
    }

}
