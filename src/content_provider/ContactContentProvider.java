package content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ContactContentProvider extends ContentProvider {

    private static final String TAG = "ContactContentProvider";

    // ContentProvider
    private ContactSvcDbHelper mContactDb;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // URIs
    private static final String AUTHORITY = "com.taylorcfrey.msse657.provider";
    private static final String PATH_CONTACT = "contact";
    private static final int CONTACT = 1;
    private static final String PATH_CONTACT_ID = "contact/#";
    private static final int CONTACT_ID = 2;

    static {
        sUriMatcher.addURI(AUTHORITY, PATH_CONTACT, CONTACT);
        sUriMatcher.addURI(AUTHORITY, PATH_CONTACT_ID, CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        mContactDb = new ContactSvcDbHelper(getContext());
        return true;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(TAG, "insert - uri: " + uri.toString());

        // get writable db
        SQLiteDatabase database = mContactDb.getWritableDatabase();

        // Content Values provided

        // insert
        long id = database.insert(
                ContactContract.ContactEntry.TABLE_NAME,
                ContactContract.ContactEntry.COLUMN_NULLABLE,
                contentValues);

        database.close();

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query - uri: " + uri.toString());

        Cursor cursor = null;

        SQLiteDatabase database = mContactDb.getReadableDatabase();


        switch (sUriMatcher.match(uri)) {
            case CONTACT:// get readable db

                if (selection.isEmpty()) {
                    selection = null;
                }
                if (selectionArgs.length == 0) {
                    selectionArgs = null;
                }

                cursor = database.query(
                        true, // DISTINCT
                        ContactContract.ContactEntry.TABLE_NAME, // FROM Table
                        projection, // SELECT column names
                        selection, // WHERE columns
                        selectionArgs, // WHERE values
                        null, // GROUP BY
                        null, // HAVING
                        sortOrder, // ORDER BY
                        null, // LIMIT
                        null); // cancel
                break;
            case CONTACT_ID:
                String where;

                if (selection == null || selection.isEmpty()) {
                    where = ContactContract.ContactEntry._ID + " = ?";
                } else {
                    where = selection;
                    selectionArgs = new String[]{uri.getLastPathSegment()};
                }

                cursor = database.query(
                        true, // DISTINCT
                        ContactContract.ContactEntry.TABLE_NAME, // FROM Table
                        projection, // SELECT column names
                        where, // WHERE
                        selectionArgs, // WHERE values
                        null, // GROUP BY
                        null, // HAVING
                        sortOrder, // ORDER BY
                        null, // LIMIT
                        null); // cancel
                break;
        }

        database.close();

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        //TODO: Use with Sync Provider to actually perform the delete

        SQLiteDatabase database = mContactDb.getWritableDatabase();

        // WHERE
        String where;

        if (selection == null || selection.isEmpty()) {
            where = ContactContract.ContactEntry._ID + " = ?";
        } else {
            where = selection;
            selectionArgs = new String[]{uri.getLastPathSegment()};
        }

        int result = database.delete(
                ContactContract.ContactEntry.TABLE_NAME,
                where,
                selectionArgs
        );

        database.close();

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        // TODO: If delete() implements a Sync Provider, then it's probably more prudent to call delete then insert.

        SQLiteDatabase database = mContactDb.getWritableDatabase();

        // WHERE
        String where;

        if (selection == null || selection.isEmpty()) {
            where = ContactContract.ContactEntry._ID + " = ?";
        } else {
            where = selection;
            selectionArgs = new String[]{uri.getLastPathSegment()};
        }

        int result = database.update(
                ContactContract.ContactEntry.TABLE_NAME,
                contentValues,
                where,
                selectionArgs
        );

        database.close();

        return result;
    }
}
