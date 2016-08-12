package binh.app.englishidiom.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.text.Spanned;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_PATH = "/data/data/binh.app.englishidiom/databases/";
    public static String DB_NAME = "idiom.sqlite";
    public static final int DB_VERSION = 4;

    public static final String TB_USER = "idiomtable";
    public static final String TB_SLANG = "slangtable";
    private SQLiteDatabase myDataBase;

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }
    public void openDataBase() throws SQLException {

        //Open the database
        if(checkDataBase()) {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()) {
                myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            }
        }
        else try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);
            File creatoutput = new File(outputFileName);
            if (!creatoutput.exists()) {
                creatoutput.mkdir();
            }

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
        }

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
        }


        if (!dbExist) {
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory()) {
                tempDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
            }

        } catch (SQLiteException e) {
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    /*****************************
     * STRART SLANG
     *********************************/
    public List<String> getallSlang() {
        List<String> listUsers = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_SLANG + " WHERE id IS NOT NULL ", null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {

                name = c.getString(1);
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < listUsers.size(); i++) {
            Spanned st2 = Html.fromHtml(listUsers.get(i));
            result.add(st2.toString());

        }

        return result;
    }

    public String getdefWithID() {
        List<String> listUsers = new ArrayList<String>();
        String content = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("slang",
                context.MODE_PRIVATE);
        int test = 0;
        test = pref.getInt("slang_index", 0) + 1;

        try {
            c = db.rawQuery("SELECT  * FROM " + TB_SLANG + " WHERE id=" + test,
                    null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(2);
                content = name;
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return content;
    }

    public String getlangWithID() {
        List<String> listUsers = new ArrayList<String>();
        String content = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("slang",
                context.MODE_PRIVATE);
        int test = 0;
        test = pref.getInt("slang_index", 0) + 1;

        try {
            c = db.rawQuery("SELECT  * FROM " + TB_SLANG + " WHERE id=" + test,
                    null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(3);
                content = name;
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return content;
    }

    /***************************************
     * SLANG
     ********************************************/
    public List<String> getAllUsers() {
        List<String> listUsers = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("binh",
                context.MODE_PRIVATE);
        String st = pref.getString("tieude", "");
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER + " WHERE char=" + "'"
                    + st.toLowerCase() + "'", null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {

                name = c.getString(2);
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < listUsers.size(); i++) {
            Spanned st2 = Html.fromHtml(listUsers.get(i));
            result.add(st2.toString());

        }

        return result;
    }

    public String getAllContent() {
        List<String> listUsers = new ArrayList<String>();
        String content = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("idiom",
                context.MODE_PRIVATE);
        int test = 0;
        test = pref.getInt("chiso2", 0);

        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER + " WHERE _id=" + test,
                    null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(3);
                content = name;
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return content;
    }

    public String getAllbookmarkContent() {
        String content = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("bookmark",
                context.MODE_PRIVATE);
        int tg = pref.getInt("bookindex", 0);

        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER + " WHERE _id =" + tg,
                    null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(3);
                content = name;
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return content;
    }

    /***************************************************************/

    public List<Integer> getslangId() {
        List<Integer> result = new ArrayList<Integer>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("slang",
                context.MODE_PRIVATE);
        String st = pref.getString("slangtitle", "");
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_SLANG + " WHERE title=" + "'"
                    + st.toLowerCase() + "'", null);
            if (c == null)
                return null;

            int dem;
            c.moveToFirst();
            do {

                dem = c.getInt(0);
                result.add(dem);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return result;
    }

    /***************************************************************/

    public List<Integer> getId() {
        List<Integer> result = new ArrayList<Integer>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        @SuppressWarnings("static-access")
        SharedPreferences pref = context.getSharedPreferences("binh",
                context.MODE_PRIVATE);
        String st = pref.getString("tieude", "");
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER + " WHERE char=" + "'"
                    + st.toLowerCase() + "'", null);
            if (c == null)
                return null;

            int dem;
            c.moveToFirst();
            do {

                dem = c.getInt(0);
                result.add(dem);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return result;
    }

    public List<Integer> getIdbook() {
        List<Integer> result = new ArrayList<Integer>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER + " WHERE favorite="
                    + 1, null);
            if (c == null)
                return null;

            int dem;
            c.moveToFirst();
            do {

                dem = c.getInt(0);
                result.add(dem);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return result;
    }

    public List<String> getAllbookmark() {
        List<String> listUsers = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER
                    + " WHERE favorite IS NOT NULL", null);
            if (c == null)
                return null;

            String name;
            c.moveToFirst();
            do {

                name = c.getString(2);
                listUsers.add(name);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return listUsers;
    }

    public boolean check(int n) {
        boolean ok = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT  * FROM " + TB_USER + " WHERE _id=" + n,
                    null);

            int dem;
            c.moveToFirst();

            dem = c.getInt(5);
            if (dem == 1)
                ok = true;
            c.close();
        } catch (Exception e) {
        }

        db.close();

        return ok;

    }

    public void addbookmark(int n) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TB_USER + " SET favorite=" + 1
                + " WHERE _id=" + n;
        db.execSQL(query);
    }

    public void removebookmark(int n) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TB_USER + " SET favorite= NULL WHERE _id ="
                + n;
        db.execSQL(query);
    }




}
