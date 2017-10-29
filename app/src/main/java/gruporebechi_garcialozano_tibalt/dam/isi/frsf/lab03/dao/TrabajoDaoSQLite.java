package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Trabajo;

/**
 * Created by diegogarcialozano on 29/10/17.
 */

public class TrabajoDaoSQLite implements TrabajoDao {

    private SQLiteDatabase db;
    private final Context context;
    private final WorkFromHomeOpenHelper dbhelper;

    /**
     * Constructor. Setea el contexto y obtiene la instancia del singleton del dbhelper
     * @param c
     */
    public TrabajoDaoSQLite(Context c){
        context = c;
        dbhelper = WorkFromHomeOpenHelper.getInstance(context, Constants.DATABASE_NAME,
                Constants.DATABASE_VERSION);
    }

    /**
     * Hace un SELECT * FROM TABLE categoria y, por cada resultado de la query, crea una
     * Categoria, le setea los datos y la agrega a la lista a retornar.
     * @return Lista de categorias en la tabla categorias
     */
    @Override
    public List<Categoria> listaCategoria() {
        List<Categoria> categorias = new ArrayList<>();
        db = dbhelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.CATEGORIA_TABLENAME, null);
        // Nos movemos con el cursor por cada resultado
        while(c.moveToNext()){
            // Y creamos la categoria con los datos correspondientes
            Categoria categoria = new Categoria();
            categoria.setId(c.getInt(c.getColumnIndex(Constants.CATEGORIA_ID)));
            categoria.setDescripcion(c.getString(c.getColumnIndex(Constants.CATEGORIA_DESCRIPCION)));
            categorias.add(categoria);
        }
        c.close();
        db.close();
        return categorias;
    }

    /**
     * Hace un insert en la DB donde en cada columna se ponen los valores en las variables de
     * instancia del trabajo P pasado como parametro.
     * @param p Trabajo a crear
     */
    @Override
    public void crearOferta(Trabajo p) {
        db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.TRABAJO_DESCRIPCION, p.getDescripcion());
        cv.put(Constants.TRABAJO_CATEGORIAS_FK, p.getCategoria().getId());
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        cv.put(Constants.TRABAJO_FECHA_ENTREGA, sdf.format(p.getFechaEntrega()));
        System.out.println(p.getFechaEntrega().getTime() + "fechaintegerwrite");
        cv.put(Constants.TRABAJO_HORAS_PRESUPUESTADAS, p.getHorasPresupuestadas());
        cv.put(Constants.TRABAJO_MONEDA_PAGO, p.getMonedaPago());
        cv.put(Constants.TRABAJO_PRECIO_MAX_HORA, p.getPrecioMaximoHora());
        cv.put(Constants.TRABAJO_REQUIERE_INGLES, p.getRequiereIngles());
        db.insert(Constants.TRABAJO_TABLENAME,null, cv);
        db.close();
    }

    /**
     * Hace un delete en la DB donde se borrara la fila con id igual al de la
     * instancia del trabajo P pasado como parametro.
     * @param p Trabajo a eliminar
     */
    @Override
    public void borrarOferta(Trabajo p) {
        db = dbhelper.getWritableDatabase();
        db.delete(Constants.TRABAJO_TABLENAME, Constants.TRABAJO_ID + "=" + p.getId(), null);
        db.close();
    }

    /**
     * Hace un SELECT * FROM trabajos y por cada resultado crea un trabajo, le setea los datos
     * y lo agrego a la lista de trabajos.
     * @return Lista de trabajos en la DB
     */
    @Override
    public List<Trabajo> listaTrabajos() {
        List<Trabajo> trabajos = new ArrayList<>();
        db = dbhelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TRABAJO_TABLENAME, null);
        while(c.moveToNext()){
            Trabajo trabajo = new Trabajo();
            trabajo.setId(c.getInt(c.getColumnIndex(Constants.TRABAJO_ID)));
            trabajo.setDescripcion(c.getString(c.getColumnIndex(Constants.TRABAJO_DESCRIPCION)));
            trabajo.setPrecioMaximoHora(c.getDouble(c.getColumnIndex(Constants.TRABAJO_PRECIO_MAX_HORA)));
            trabajo.setMonedaPago(c.getInt(c.getColumnIndex(Constants.TRABAJO_MONEDA_PAGO)));
            trabajo.setHorasPresupuestadas(c.getInt(c.getColumnIndex(Constants.TRABAJO_HORAS_PRESUPUESTADAS)));
            Integer requiereIngles = c.getInt(c.getColumnIndex(Constants.TRABAJO_REQUIERE_INGLES));
            trabajo.setRequiereIngles(requiereIngles == 1 ? true : false);
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            String fechaString = c.getString(c.getColumnIndex(Constants.TRABAJO_FECHA_ENTREGA));
            try {
                trabajo.setFechaEntrega(sdf.parse(fechaString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for(Categoria cat : this.listaCategoria()){
                if(cat.getId() == c.getInt(c.getColumnIndex(Constants.TRABAJO_CATEGORIAS_FK))){
                    trabajo.setCategoria(cat);
                    break;
                }
            }
            trabajos.add(trabajo);
        }
        c.close();
        db.close();
        for(Trabajo t: trabajos)
            System.out.println(t.getId() + " " + t.getDescripcion());
        return trabajos;
    }
}
