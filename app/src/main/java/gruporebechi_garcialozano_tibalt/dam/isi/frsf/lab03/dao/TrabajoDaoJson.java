package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao;


import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.Trabajo;

public class TrabajoDaoJson implements TrabajoDao {

    public TrabajoDaoJson(Context ctx) {
        File archivo = new File("categorias.json");
        if(!archivo.exists()) {
            try {
                archivo.createNewFile();

                // TODO crear categorias

                FileOutputStream mOutput = ctx.openFileOutput("categorias.json", Activity.MODE_APPEND);
                mOutput.write(categorias);
                mOutput.flush();
                mOutput.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Categoria> listaCategoria() {
        // TODO completar
        return null;
    }

    @Override
    public void crearOferta(Trabajo p) {
        // TODO completar
    }

    @Override
    public List<Trabajo> listaTrabajos() {
        // TODO completar
        return null;
    }
}
