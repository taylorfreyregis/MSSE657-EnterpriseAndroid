package scis;

public class ScisCourse {

    private int mId;
    private int mProgramId;
    private String mTitle;

    public ScisCourse(int id, int programId, String title) {
        this.mId = id;
        this.mProgramId = programId;
        this.mTitle = title;
    }

    public int getId() {
        return mId;
    }
    
    public int getProgramId() {
        return mProgramId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setId(int id) {
        this.mId = id;
    }
    
    public void setProgramId(int programId) {
        mProgramId = programId;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

}
