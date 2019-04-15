package android.example.autoattendie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LoginDB {
    public static final String KEY_ROWID="_id";
    public static final String KEY_ID="person_name";
    public static final String KEY_PASSWORD="_cell";

    private  final String DATABASE_NAME="PersonBB";
    private final String DATABASE_TABLE="PersonTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public  LoginDB (Context context)
    {
        ourContext=context;
    }
    private class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode="CREATE TABLE "+DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_ID + " TEXT NOT NULL , " +
                    KEY_PASSWORD + " TEXT NOT NULL );";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
            onCreate(db);
        }
    }

    public  LoginDB open() throws SQLException
    {
        ourHelper  = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String name, String cell)
    {
        ContentValues cv =new ContentValues();
        cv.put(KEY_ID,name);
        cv.put(KEY_PASSWORD,cell);
        return ourDatabase.insert(DATABASE_TABLE, null ,cv);
    }

    public String getData()
    {
        String [] columns = new String[] {KEY_ROWID,KEY_ID,KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String result = "";
        int iRowID=c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_ID);
        int iCell = c.getColumnIndex(KEY_PASSWORD);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result = result + c.getString(iRowID)+ ": "+ c.getString(iName) + " "+
                    c.getString(iCell) + "\n";
        }
        c.close();
        return  result;
    }

    public long updatePassword(String rowId, String name,String amt)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID,rowId);
        cv.put(KEY_ID,name);
        cv.put(KEY_PASSWORD,amt);
        return ourDatabase.update(DATABASE_TABLE,cv,KEY_ROWID + "=" + rowId,null);
    }

    public int findIndex(String Id)
    {
        String [] columns = new String[] {KEY_ROWID,KEY_ID,KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        int iRowID=c.getColumnIndex(KEY_ROWID);
        int iId = c.getColumnIndex(KEY_ID);
        int iCell = c.getColumnIndex(KEY_PASSWORD);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(Id.equals(c.getString(iId)))
            {
                return Integer.parseInt(c.getString(iRowID));
            }
        }
        c.close();
        return -1;
    }

    public Person checkLonginDetails(String name,String password) {
        String[] columns = new String[]{KEY_ROWID, KEY_ID, KEY_PASSWORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iId = c.getColumnIndex(KEY_ID);
        int iPassword = c.getColumnIndex(KEY_PASSWORD);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (name.equals(c.getString(iId)) && password.equals(c.getString(iPassword))) {
                Person p = new Person(c.getString(iId), c.getString(iPassword));
                return p;
            }
        }
        c.close();
        return null;
    }
}