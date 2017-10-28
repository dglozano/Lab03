package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

import org.json.JSONException;

import java.util.List;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Trabajo;

/**
 * Created by diegogarcialozano on 24/10/17.
 */

public interface TrabajoDao {
    /**
     *  Lista de las categorias predefinidas
     * @return List<Categoria>
     */
    List<Categoria> listaCategoria();

    /**
     * Crea un nuevo trabajo
     * @param p Trabajo a crear
     */
    void crearOferta(Trabajo p);

    /**
     * Elimina el trabajo
     * @param p Trabajo a eliminar
     */
    void borrarOferta(Trabajo p);

    /**
     * Retorna la lista de trabajos
     * @return List<Trabajo>
     */
    List<Trabajo> listaTrabajos();
}
