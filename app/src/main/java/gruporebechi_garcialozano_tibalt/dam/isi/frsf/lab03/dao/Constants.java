package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

/**
 * Define todas las constantes a ser utilizadas por TrabajoDaoSQLite y el dbhelper
 * Created by diegogarcialozano on 29/10/17.
 */

class Constants {
    public static final String DATABASE_NAME = "workfromhome";
    public static final int DATABASE_VERSION = 1;

    public static final String CATEGORIA_TABLENAME = "categoria";
    public static final String CATEGORIA_ID = "_id";
    public static final String CATEGORIA_DESCRIPCION = "descripcion";

    public static final String TRABAJO_TABLENAME = "trabajos";
    public static final String TRABAJO_ID = "_id";
    public static final String TRABAJO_DESCRIPCION = "descripcion";
    public static final String TRABAJO_HORAS_PRESUPUESTADAS = "horas_presupuestadas";
    public static final String TRABAJO_PRECIO_MAX_HORA = "precio_max_hora";
    public static final String TRABAJO_CATEGORIAS_FK = "categoria";
    public static final String TRABAJO_FECHA_ENTREGA = "fecha_entrega";
    public static final String TRABAJO_MONEDA_PAGO = "moneda_pago";
    public static final String TRABAJO_REQUIERE_INGLES = "requiere_ingles";

    public static final String DATE_FORMAT = "dd/MM/yyyy";
}
