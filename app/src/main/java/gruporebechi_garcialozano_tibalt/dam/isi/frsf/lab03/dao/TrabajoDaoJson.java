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
import java.util.function.Predicate;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Trabajo;

/**
 * Created by diegogarcialozano on 24/10/17.
 */

public class TrabajoDaoJson implements TrabajoDao {

    private static final String JSON_CATEGORIAS_FILE_NAME = "categorias.json";
    private static final String JSON_TRABAJOS_FILENAME = "trabajos.json";
    private static final String DATE_FORMAT = "dd/mm/yyyy";
    private static final Integer CREAR = 0;
    private static final Integer BORRAR = 1;

    private List<Categoria> categorias;
    private Context context;

    /**
     * Si no existe un archivo con las categorias, lo crea y escribi las categorias existentes.
     * @param ctx
     */
    public TrabajoDaoJson(Context ctx) {
        context = ctx;
        categorias = new ArrayList<>();
        File archivo = new File(TrabajoDaoJson.JSON_CATEGORIAS_FILE_NAME);
        // Si no existe un archivo de categorias
        if(!archivo.exists()) try {
            JSONArray categoriasJsonArray = new JSONArray();
            categoriasJsonArray.put(new JSONObject().put("id", 1).put("descripcion", "Arquitecto"));
            categoriasJsonArray.put(new JSONObject().put("id", 2).put("descripcion", "Desarrollador"));
            categoriasJsonArray.put(new JSONObject().put("id", 3).put("descripcion", "Tester"));
            categoriasJsonArray.put(new JSONObject().put("id", 4).put("descripcion", "Analista"));
            categoriasJsonArray.put(new JSONObject().put("id", 5).put("descripcion", "Mobile Developer"));
            JSONObject categoriasJson = new JSONObject().put("categorias", categoriasJsonArray);
            // Lo creo y lo abro en MODE PRIVATE por las dudas (sobreescribe lo que haya)
            FileOutputStream mOutput = context.openFileOutput(JSON_CATEGORIAS_FILE_NAME, Activity.MODE_PRIVATE);
            // Y escribo el JSON de categorias como string.
            mOutput.write(categoriasJson.toString().getBytes());
            mOutput.flush();
            mOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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

    /**
     * Por cada Trabajo en la lista de trabajos recibida por parametro, crea un nuevo JSONObject
     * y le setea los pares <clave,valor> con los atributos del trabajo y lo va agregando
     * al JSONArray que finalmente retornara.
     * @param trabajos un List<Trabajo> de trabajos que se formateara a JSONArray
     * @return JSONArray con la lista de trabajos
     */
    private JSONArray trabajosToJson(List<Trabajo> trabajos) throws JSONException {
        // Creo el JSONObject con el JSONArray de trabajos a escribir
        JSONArray trabajosJsonArray = new JSONArray();
        for(Trabajo t: trabajos){
            JSONObject trabajoJson = new JSONObject();
            trabajoJson.put("id", t.getId());
            trabajoJson.put("descripcion", t.getDescripcion());
            trabajoJson.put("horas-presupuestadas", t.getHorasPresupuestadas());
            trabajoJson.put("precio-max-hora", t.getPrecioMaximoHora());
            trabajoJson.put("moneda", t.getMonedaPago());
            trabajoJson.put("requiere-ingles", t.getRequiereIngles());
            // Formateo el Date a dd/mm/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJson.DATE_FORMAT);
            trabajoJson.put("fecha-entrega", sdf.format(t.getFechaEntrega()));
            // Creo el JSONObject Categoria para añadir al trabajo
            JSONObject categoria = new JSONObject();
            categoria.put("id", t.getCategoria().getId());
            categoria.put("descripcion", t.getCategoria().getDescripcion());
            trabajoJson.put("categoria", categoria);
            // Y lo agrego al array
            trabajosJsonArray.put(trabajoJson);
        }
        return trabajosJsonArray;
    }

    /**
     * Las categorias son siempre las mismas, pero se almacenan en un JSON. La primera vez que se
     * llama a la funcion, leera el archivo, parseara el JSON obtenido a la lista y la retornara.
     * Pero, ademas, la deja guardada en memoria en la variable de instancia this.categorias para
     * no tener que volver a leer el archivo si se vuelve a llamar al metodo.
     * @return List<Categoria> Las categorias defenidas. Son fijas.
     */
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

    /**
     * Obtiene la lista de trabajos almacenada actualmente en el archivo, parsea el JSONArray
     * obtenido a un List<Trabajo>, añade o elimina el Trabajo p y finalmente convierte
     * el List<Trabajo> actualizado a un JSONArray para volverlo a escribir.
     * @param p Trabajo a crear
     * @param MODO si el MODO es CREAR agrega el trabajo p a la lista; si es BORRAR, lo elimina.
     */
    private void actualizarOferta(Trabajo p, Integer MODO) {
        try {
            // Obtengo la lista de trabajos que hay actualmente en el archivo
            List<Trabajo> trabajos = this.listaTrabajos();
            // Abro un flujo de salida al archivo
            FileOutputStream mOutput = context.openFileOutput(JSON_TRABAJOS_FILENAME, Activity.MODE_PRIVATE);
            // Si el MODO es CREAR lo agrega a la lista; si es BORRAR Lo elimina.
            if(MODO == TrabajoDaoJson.CREAR)
                trabajos.add(p);
            else if(MODO == TrabajoDaoJson.BORRAR){
                int index = 0;
                for(Trabajo t: trabajos) {
                    if(t.getDescripcion() == p.getDescripcion()){
                        index = trabajos.indexOf(t);
                    }
                }
                trabajos.remove(index);
            }
            // Parseo el List<Trabajo> a un JSONArray
            JSONArray trabajosJsonArray = trabajosToJson(trabajos);
            // Agrego el Array al Object, ya que lo que escribo es del tipo
            // { "trabajos": [ {...}, {...}, {...} ] }
            // Podria haberlo hecho directamente [ {...}, {...}, {...} ], pero es lo mismo.
            JSONObject trabajosObject = new JSONObject();
            trabajosObject.put("trabajos", trabajosJsonArray);
            // Lo escribo al archivo.
            mOutput.write(trabajosObject.toString().getBytes());
            mOutput.flush();
            mOutput.close();
            System.out.println(trabajos);
            System.out.println(trabajosJsonArray.toString(1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega el nuevo trabajo p al JSON almacenado en el archivo
     * @param p Trabajo a crear
     */
    @Override
    public void crearOferta(Trabajo p){
        actualizarOferta(p, TrabajoDaoJson.CREAR);
    }

    /**
     * Borra el trabajo p del JSON almacenado en el archivo
     * @param p Trabajo a borrar
     */
    @Override
    public void borrarOferta(Trabajo p){
        actualizarOferta(p, TrabajoDaoJson.BORRAR);
    }

    /**
     * Lee el archivo JSON de trabajos y, por cada JSONObject que representa a un Trabajo
     * en el JSONArray, crea una nueva instancia de Trabajo, obtiene del JSON almacenados los
     * valores almacenados en forma <clave, valor> y los setea al objeto trabajo.
     * Luego, añade el Trabajo ya inicializado a la lista que se retornara de List<Trabajo>
     * @return List<Trabajo> parseada del JSON guardado en el archivo
     */
    @Override
    public List<Trabajo> listaTrabajos() {
        // Creo la lista de trabajos que va a retornar
        List<Trabajo> trabajos = new ArrayList<>();
        try {
            // Obtengo el JSON como string
            String trabajosJsonString = readJsonFile(TrabajoDaoJson.JSON_TRABAJOS_FILENAME);
            if(!trabajosJsonString.isEmpty()) {
                // Obtengo el JSON Object a partir del string
                JSONObject object = (JSONObject) new JSONObject(trabajosJsonString);
                // Obtengo el arreglo de trabajos que esta en el objeto
                JSONArray trabajosJsonArray = object.getJSONArray("trabajos");
                // Por cada JSONObject que representa un trabajo en el arreglo de trabajos
                for (int i = 0; i < trabajosJsonArray.length(); i++) {
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
                    categoria.setDescripcion(categoriaJson.getString("descripcion"));
                    categoria.setId(categoriaJson.getInt("id"));
                    nuevoTrabajo.setCategoria(categoria);
                    // Obtengo el string de la fecha, lo parseo a Date y lo agrego al trabajo
                    SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJson.DATE_FORMAT);
                    String fechaString = trabajoJson.getString("fecha-entrega");
                    Date fechaEntrega = sdf.parse(fechaString);
                    nuevoTrabajo.setFechaEntrega(fechaEntrega);
                    // Y finalmente lo agrego a la lista
                    trabajos.add(nuevoTrabajo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return trabajos;
    }
}