package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Trabajo;

/**
 * Created by diegogarcialozano on 24/10/17.
 */

public class TrabajoDaoJson implements TrabajoDao {

    private static final String JSON_CATEGORIAS_FILE_NAME = "categorias.json";
    private static final String JSON_TRABAJOS_FILENAME = "trabajos.json";
    private static final String DATE_FORMAT = "dd/mm/yyyy";

    private static String JSON_CATEGORIAS_STRING = "{"
            + "\"categorias\": [ "
            + "{\"id\": 1, \"descripcion\": \"Arquitecto\"},"
            + "{\"id\": 2, \"descripcion\": \"Desarrollador\"},"
            + "{\"id\": 3, \"descripcion\": \"Tester\"},"
            + "{\"id\": 4, \"descripcion\": \"Analista\"},"
            + "{\"id\": 5, \"descripcion\": \"Mobile Developer\"}"
            + "]"
            + "}";

    private List<Categoria> categorias;
    private Context context;

    public TrabajoDaoJson(Context ctx) {
        context = ctx;
        categorias = new ArrayList<>();
        File archivo = new File(TrabajoDaoJson.JSON_CATEGORIAS_FILE_NAME);
        // Si no existe un archivo de categorias
        if(!archivo.exists()) try {
            // Lo creo y lo abro en MODE PRIVATE por las dudas (sobreescribe lo que haya)
            FileOutputStream mOutput = context.openFileOutput(JSON_CATEGORIAS_FILE_NAME, Activity.MODE_PRIVATE);
            // Y escribo el JSON de categorias como string.
            mOutput.write(JSON_CATEGORIAS_STRING.getBytes());
            mOutput.flush();
            mOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre un input stream para leer del archivo pasado por parametro y crea un
     * String que representa el JSON Object.
     * @param fileName del archivo almacenada con el JSON
     * @return Devuelve el objeto JSON almacenado en el archivo
     */
    private String readJsonFile(String fileName){
        // Uso string builder porque es mas eficiente
        StringBuilder sb = new StringBuilder();
        try {
            // Creo FileInputStream del archivo con nombre pasado por parametro
            FileInputStream mInput = context.openFileInput(fileName);
            // Arreglo de bytes donde se almacenaran las lecturas. Lee de a 128 bytes.
            byte[] data = new byte[128];
            // Mientras que haya algo por leer, leo en data
            while(mInput.read(data)!=-1){
                // Paso los bytes de data a string y los concateno al string builder
                sb.append(new String(data));
            }
            // Cierro el stream
            mInput.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace();}
        // Devuelvo el string generado con el string builder.
        return sb.toString();
    }

    @Override
    public List<Categoria> listaCategoria() {
        // Si la lista de categorias, que es siempre fija, no fue leida aun del archivo
        if(this.categorias == null || this.categorias.isEmpty()) {
            try {
                // Obtengo el JSON como string
                String categoriasJsonString = readJsonFile(TrabajoDaoJson.JSON_CATEGORIAS_FILE_NAME);
                // Obtengo el JSON Object a partir del string
                JSONObject object = (JSONObject) new JSONTokener(categoriasJsonString).nextValue();
                // Obtengo el arreglo de categorias que esta en el objeto
                JSONArray categoriasJsonArray = object.getJSONArray("categorias");
                // Por cada JSONObject que representa una categoria en el arreglo de categorias
                for(int i=0; i<categoriasJsonArray.length(); i++) {
                    // Leo el JSONObject que representa a la categoria
                    JSONObject categoriaJson = (JSONObject) categoriasJsonArray.get(i);
                    // Creo una nueva instancia de categoria
                    Categoria nuevaCategoria = new Categoria();
                    // Le seteo el "id" y "descripcion" obtenidos del JSONObject mediante <clave,valor>
                    nuevaCategoria.setId(categoriaJson.getInt("id"));
                    nuevaCategoria.setDescripcion(categoriaJson.getString("descripcion"));
                    // Y finalmente lo agrego a la lista
                    this.categorias.add(nuevaCategoria);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.categorias;
    }

    @Override
    public void crearOferta(Trabajo p) {
        try {
            // Abro un flujo de salida al archivo
            FileOutputStream mOutput = context.openFileOutput(JSON_TRABAJOS_FILENAME, Activity.MODE_PRIVATE);
            // Obtengo la lista de trabajos que hay actualmente en el archivo
            List<Trabajo> trabajosAntes = this.listaTrabajos();
            // Le agrego el nuevo
            trabajosAntes.add(p);
            /*StringBuilder sb = new StringBuilder();
            sb.append("{ trabajos : [");*/
            JSONArray trabajosJsonArray = new JSONArray();
            for(Trabajo t: trabajosAntes){
                JSONObject trabajoJson = new JSONObject();
                trabajoJson.put("id", t.getId());
                trabajoJson.put("descripcion", t.getDescripcion());
                trabajoJson.put("horas-presupuestadas", t.getHorasPresupuestadas());
                trabajoJson.put("precio-max-hora", t.getPrecioMaximoHora());
                trabajoJson.put("moneda", t.getMonedaPago());
                trabajoJson.put("requiere-ingles", t.getRequiereIngles());

                SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJson.DATE_FORMAT);
                trabajoJson.put("fecha-entrega", sdf.format(t.getFechaEntrega()));

                JSONObject categoria = new JSONObject();
                categoria.put("id", t.getCategoria().getId());
                categoria.put("descripcion", t.getCategoria().getDescripcion());
                trabajoJson.put("categoria", categoria);

                trabajosJsonArray.put(trabajoJson);
            }
            JSONObject trabajos = new JSONObject();
            trabajos.put("trabajos", trabajosJsonArray);

            mOutput.write(trabajos.toString().getBytes());
            mOutput.flush();
            mOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trabajo> listaTrabajos() {
        // Creo la lista de trabajos que va a retornar
        List<Trabajo> trabajos = new ArrayList<>();
        try {
            // Obtengo el JSON como string
            String trabajosJsonString = readJsonFile(TrabajoDaoJson.JSON_TRABAJOS_FILENAME);
            // Obtengo el JSON Object a partir del string
            JSONObject object = (JSONObject) new JSONTokener(trabajosJsonString).nextValue();
            // Obtengo el arreglo de trabajos que esta en el objeto
            JSONArray trabajosJsonArray = object.getJSONArray("trabajos");
            // Por cada JSONObject que representa un trabajo en el arreglo de trabajos
            for(int i=0; i<trabajosJsonArray.length(); i++) {
                // Leo el JSONObject que representa al trabajo
                JSONObject trabajoJson = (JSONObject) trabajosJsonArray.get(i);
                // Creo una nueva instancia de Trabajo
                Trabajo nuevoTrabajo = new Trabajo();
                // Le seteo los atributos que obtengo del JSONObject mediante <clave,valor>
                nuevoTrabajo.setId(trabajoJson.getInt("id"));
                nuevoTrabajo.setDescripcion(trabajoJson.getString("descripcion"));
                nuevoTrabajo.setHorasPresupuestadas(trabajoJson.getInt("horas-presupuestadas"));
                nuevoTrabajo.setPrecioMaximoHora(trabajoJson.getDouble("precio-max-hora"));
                nuevoTrabajo.setMonedaPago(trabajoJson.getInt("moneda"));
                nuevoTrabajo.setRequiereIngles(trabajoJson.getBoolean("requiere-ingles"));
                // Obtengo el json object de la categoria, cargo los datos al objeto categoria y lo pongo al trabajo
                JSONObject categoriaJson = trabajoJson.getJSONObject("categoria");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaJson.getInt("id"));
                categoria.setDescripcion(categoriaJson.getString("descripcion"));
                nuevoTrabajo.setCategoria(categoria);
                // Obtengo el string de la fecha, lo parseo a Date y lo agrego al trabajo
                SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJson.DATE_FORMAT);
                String fechaString = trabajoJson.getString("fecha-entrega");
                Date fechaEntrega = sdf.parse(fechaString);
                nuevoTrabajo.setFechaEntrega(fechaEntrega);
                // Y finalmente lo agrego a la lista
                trabajos.add(nuevoTrabajo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return trabajos;
    }
}