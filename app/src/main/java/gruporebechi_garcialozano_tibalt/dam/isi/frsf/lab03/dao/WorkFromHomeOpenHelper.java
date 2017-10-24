package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diegogarcialozano on 24/10/17.
 */

public class WorkFromHomeOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_CATEGORIA = "";

    private static final String SQL_CREATE_TRABAJO= "";

    private static WorkFromHomeOpenHelper _INSTANCE;

    private WorkFromHomeOpenHelper(Context ctx){
        super(ctx,"WORK_FROM_HOME",null,1);
    }

    public static WorkFromHomeOpenHelper getInstance(Context ctx){
        if(_INSTANCE==null) _INSTANCE = new WorkFromHomeOpenHelper(ctx);
        return _INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORIA);
        sqLiteDatabase.execSQL(SQL_CREATE_TRABAJO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
