package content_provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for utilizing SQLite.
 */
public class ContactSvcDbHelper extends SQLiteOpenHelper implements IContactSvc {

    private static final String TAG = "ContactSvcDbHelper";

    /* -------- SQL DB INFO -------- */
    public static final int DATABASE_VERSION = 101;
    public static final String DATABASE_NAME = "Contacts.db";

    /* -------- SQL COMMANDS -------- */
    private final static String TEXT_TYPE = " TEXT";
    private final static String COMMA_SEPARATOR = ",";
    private final static String SEMI_COL = ";";
    private final static String OPEN_PAREN = "(";
    private final static String CLOSE_PAREN = ")";

    /* -------- Contacts -------- */
    private final static String SQL_CREATE_TABLE_CONTACT =
            "CREATE TABLE " + ContactContract.ContactEntry.TABLE_NAME + OPEN_PAREN +
                    ContactContract.ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactContract.ContactEntry.COLUMN_CONTACT_NAME + TEXT_TYPE + COMMA_SEPARATOR +
                    ContactContract.ContactEntry.COLUMN_PHONE + TEXT_TYPE + COMMA_SEPARATOR +
                    ContactContract.ContactEntry.COLUMN_EMAIL + TEXT_TYPE + CLOSE_PAREN + SEMI_COL;

    private final static String SQL_DROP_TABLE_CONTACT =
            "DROP TABLE IF EXISTS " + ContactContract.ContactEntry.TABLE_NAME + SEMI_COL;

    /* -------- Constructor(s) -------- */
    public ContactSvcDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* -------- SQL Helper Overrides -------- */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: " + SQL_CREATE_TABLE_CONTACT);
        db.execSQL(SQL_CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: " + db + " oldVersion: " + oldVersion + " newVersion: " + newVersion);
        db.execSQL(SQL_DROP_TABLE_CONTACT);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Don't really care about downgrading, however for consistency, let's start anew
        onUpgrade(db, oldVersion, newVersion);
    }

    /* -------- Contact Service Implementation -------- */

    //  Returns id of contact
    @Override
    public Contact createContact(Contact contact) {
        Log.d(TAG, "createContact: " + contact);

        // get writable db
        SQLiteDatabase database = this.getWritableDatabase();

        // create value object
        ContentValues contactContentValues = new ContentValues();
        contactContentValues.put(
                ContactContract.ContactEntry.COLUMN_CONTACT_NAME, contact.getName()
        );
        contactContentValues.put(
                ContactContract.ContactEntry.COLUMN_PHONE, contact.getPhone()
        );
        contactContentValues.put(
                ContactContract.ContactEntry.COLUMN_EMAIL, contact.getEmail()
        );

        // insert
        database.insert(
                ContactContract.ContactEntry.TABLE_NAME,
                ContactContract.ContactEntry.COLUMN_NULLABLE,
                contactContentValues);

        database.close();

        return contact;
    }

    // Returns number of rows updated
    @Override
    public Contact updateContact(Contact contact) {
        Log.d(TAG, "updateContact: " + contact);

        SQLiteDatabase database = this.getWritableDatabase();

        // create value object
        ContentValues contactContentValues = new ContentValues();
        contactContentValues.put(
                ContactContract.ContactEntry.COLUMN_CONTACT_NAME, contact.getName()
        );
        contactContentValues.put(
                ContactContract.ContactEntry.COLUMN_PHONE, contact.getPhone()
        );
        contactContentValues.put(
                ContactContract.ContactEntry.COLUMN_EMAIL, contact.getEmail()
        );

        // WHERE
        String where = ContactContract.ContactEntry._ID + " = ?";

        database.update(
                ContactContract.ContactEntry.TABLE_NAME,
                contactContentValues,
                where,
                new String[]{Integer.toString(contact.getId())}
        );

        database.close();

        return contact;
    }

    // Returns number of rows updated
    @Override
    public Contact deleteContact(Contact contact) {
        Log.d(TAG, "deleteContact: " + contact);

        SQLiteDatabase database = this.getWritableDatabase();

        // WHERE
        String where = ContactContract.ContactEntry._ID + " = ?";

        database.delete(
                ContactContract.ContactEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(contact.getId())}
        );

        database.close();

        return contact;
    }

    @Override
    public List<Contact> readContacts() {
        Log.d(TAG, "readContacts");

        // Sort order
        String sortOrder = ContactContract.ContactEntry.COLUMN_CONTACT_NAME + " ASC";

        // get readable db
        SQLiteDatabase database = this.getReadableDatabase();

        // a projection is really just a list of the column names to query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_CONTACT_NAME,
                ContactContract.ContactEntry.COLUMN_PHONE,
                ContactContract.ContactEntry.COLUMN_EMAIL
        };

        Cursor cursor = database.query(
                true, // DISTINCT
                ContactContract.ContactEntry.TABLE_NAME, // FROM Table
                projection, // SELECT column names
                null, // WHERE columns
                null, // WHERE values
                null, // GROUP BY
                null, // HAVING
                sortOrder, // ORDER BY
                null, // LIMIT
                null); // cancel

        cursor.moveToFirst();
        List<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < cursor.getCount(); i++) {

            int id = (int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry._ID)
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_CONTACT_NAME)
            );

            String phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_PHONE)
            );

            String email = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_EMAIL)
            );

            contacts.add(new Contact(id, name, phone, email));
            cursor.moveToNext();
        }

        database.close();

        return contacts;
    }

    @Override
    public Contact readContact(int index) {
        Log.d(TAG, "readContact - id: " + index);

        // Sort order
        String sortOrder = ContactContract.ContactEntry.COLUMN_CONTACT_NAME + " ASC";

        // WHERE
        String where = ContactContract.ContactEntry._ID + " = ?";

        // get readable db
        SQLiteDatabase database = this.getReadableDatabase();

        // a projection is really just a list of the column names to query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_CONTACT_NAME,
                ContactContract.ContactEntry.COLUMN_PHONE,
                ContactContract.ContactEntry.COLUMN_EMAIL
        };

        Cursor cursor = database.query(
                true, // DISTINCT
                ContactContract.ContactEntry.TABLE_NAME, // FROM Table
                projection, // SELECT column names
                where, // WHERE columns
                new String[]{Integer.toString(index)}, // WHERE values
                null, // GROUP BY
                null, // HAVING
                sortOrder, // ORDER BY
                null, // LIMIT
                null); // cancel

        cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            return null;
        }

        int id = (int) cursor.getLong(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry._ID)
        );

        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_CONTACT_NAME)
        );

        String phone = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_PHONE)
        );

        String email = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_EMAIL)
        );

        database.close();

        return new Contact(id, name, phone, email);
    }

    @Override
    public Contact readContact(String contactName) {
        Log.d(TAG, "readContact - name: " + contactName);

        // Sort order
        String sortOrder = ContactContract.ContactEntry.COLUMN_CONTACT_NAME + " ASC";

        // WHERE
        String where = ContactContract.ContactEntry.COLUMN_CONTACT_NAME + " LIKE ?";

        // get readable db
        SQLiteDatabase database = this.getReadableDatabase();

        // a projection is really just a list of the column names to query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_CONTACT_NAME,
                ContactContract.ContactEntry.COLUMN_PHONE,
                ContactContract.ContactEntry.COLUMN_EMAIL
        };

        Cursor cursor = database.query(
                true, // DISTINCT
                ContactContract.ContactEntry.TABLE_NAME, // FROM Table
                projection, // SELECT column names
                where, // WHERE columns
                new String[]{contactName}, // WHERE values
                null, // GROUP BY
                null, // HAVING
                sortOrder, // ORDER BY
                null, // LIMIT
                null); // cancel

        cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            return null;
        }

        int id = (int) cursor.getLong(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry._ID)
        );

        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_CONTACT_NAME)
        );

        String phone = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_PHONE)
        );

        String email = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_EMAIL)
        );

        database.close();

        return new Contact(id, name, phone, email);
    }
}