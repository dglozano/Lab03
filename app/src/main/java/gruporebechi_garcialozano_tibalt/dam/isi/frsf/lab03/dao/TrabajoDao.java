package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;

import java.util.List;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.Trabajo;

public interface TrabajoDao {
    public List<Categoria> listaCategoria();
    public void crearOferta(Trabajo p);
    public List<Trabajo> listaTrabajos();
}
