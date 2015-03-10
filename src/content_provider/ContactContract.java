package content_provider;

import android.provider.BaseColumns;

/**
 * Contract class used for defining the schema of the database and enforcing type.
 */
public class ContactContract {

    // Used to prevent accidental creation of this class
    private ContactContract() {}

    // ID provided by BaseColumn implementation
    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_CONTACT_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NULLABLE = "email";
    }
}
