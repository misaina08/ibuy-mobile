package sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by misa on 9/3/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    Context context;
    public static String DB_NAME="ibuy.db";
    public static int VERSIONDB=1;
    public static String DB_PATH;
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        this.context = context;


        DB_PATH="/data/data/"+context.getApplicationContext().getPackageName()+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DbHelper init(Context c, boolean open)
    {
        DbHelper db=new DbHelper(c, DbHelper.DB_NAME, null, DbHelper.VERSIONDB);
        if(db.checkdatabase())
        {
            if(open)db.opendatabase();
        }
        else
        {
            try
            {
                db.createdatabase();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return db;
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();

        if(dbexist) {
            System.out.println(" Database exists. - creation");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    protected boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);

            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
            System.out.println("Database existe .....");
        } catch(SQLiteException e) {
            e.printStackTrace();
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);


        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public SQLiteDatabase opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        return  SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void openlocaldatabase(){
        //String mypath=
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
