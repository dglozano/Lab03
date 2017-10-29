package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Utiliza el patron Singleton.
 * Contiene el DDL para la creacion de las tablas y, al extener SQLiteOpenHelper,
 * hereda los metodos para el acceso a la db
 * Created by diegogarcialozano on 24/10/17.
 */

public class WorkFromHomeOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_CATEGORIA = "CREATE TABLE "
            + Constants.CATEGORIA_TABLENAME + "("
            + Constants.CATEGORIA_ID +  " integer primary key autoincrement,"
            + Constants.CATEGORIA_DESCRIPCION +  " descripcion text);";

    private static final String SQL_CREATE_TRABAJO= "CREATE TABLE "
            + Constants.TRABAJO_TABLENAME + "("
            + Constants.TRABAJO_ID +  " integer primary key autoincrement,"
            + Constants.TRABAJO_DESCRIPCION + " text,"
            + Constants.TRABAJO_HORAS_PRESUPUESTADAS + " integer,"
            + Constants.TRABAJO_CATEGORIAS_FK + " integer,"
            + Constants.TRABAJO_PRECIO_MAX_HORA + " real,"
            + Constants.TRABAJO_FECHA_ENTREGA + " string,"
            + Constants.TRABAJO_MONEDA_PAGO + " integer, "
            + Constants.TRABAJO_REQUIERE_INGLES + " boolean,"
            + "FOREIGN KEY("+Constants.TRABAJO_CATEGORIAS_FK+") " +
              "REFERENCES " + Constants.CATEGORIA_TABLENAME + "(" + Constants.CATEGORIA_ID + "));";

    private static WorkFromHomeOpenHelper _INSTANCE;

    private WorkFromHomeOpenHelper(Context ctx, String dbname, Integer version){
        super(ctx, dbname, null, version);
    }

    public static WorkFromHomeOpenHelper getInstance(Context ctx, String dbname, Integer version){
        if(_INSTANCE==null) _INSTANCE = new WorkFromHomeOpenHelper(ctx, dbname, version);
        return _INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORIA);
        sqLiteDatabase.execSQL(SQL_CREATE_TRABAJO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.CATEGORIA_TABLENAME);
        sqLiteDatabase.execSQL("drop table if exists " + Constants.TRABAJO_TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
