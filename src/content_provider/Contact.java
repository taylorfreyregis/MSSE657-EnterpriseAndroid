package content_provider;

public class Contact {

    private int mId;
    private String mName;
    private String mPhone;
    private String mEmail;

    public Contact() {
        this(-1, null, null, null);
    }

    public Contact (int id, String name, String phone, String email) {
        this.mId = id;
        this.mName = name;
        this.mPhone = phone;
        this.mEmail = email;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @Override
    public String toString() {
        return "Contact: " + "\n" +
                "Id: " + mId + "\n" +
                "Name: " + mName + "\n" +
                "Phone: " + mPhone + "\n" +
                "Email: " + mEmail;
    }
}
