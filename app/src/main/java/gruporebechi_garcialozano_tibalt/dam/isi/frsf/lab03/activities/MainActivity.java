package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.OfertasListAdapter;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.R;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao.TrabajoDao;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.dao.TrabajoDaoJson;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Categoria;
import gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model.Trabajo;

public class MainActivity extends AppCompatActivity {

    private List<Trabajo> listaTrabajos;
    private OfertasListAdapter ofertasListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AltaOferta.class);
                startActivityForResult(intent, AltaOferta.ALTA_OFERTA_REQUEST);
            }
        });

        listaTrabajos = new ArrayList<>(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        ListView lvOfertasTrabajo = (ListView) findViewById(R.id.lvOfertasTrabajo);

        registerForContextMenu(lvOfertasTrabajo);

        ofertasListAdapter = new OfertasListAdapter(this, listaTrabajos);
        lvOfertasTrabajo.setAdapter(ofertasListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String descripcionOfertaSeleccionada = listaTrabajos.get(info.position).toString();
        switch (item.getItemId()) {
            case R.id.opcionPostularseOferta:
                String nombreIntent = "gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03";
                Intent broadcastIntent = new Intent(nombreIntent);
                broadcastIntent.putExtra("message", descripcionOfertaSeleccionada);
                broadcastIntent.putExtra("title", "Postulacion exitosa");
                sendBroadcast(broadcastIntent);
                return true;
            case R.id.opcionDescartarOferta:
                Toast.makeText(this, "Descartaste la oferta " + descripcionOfertaSeleccionada, Toast.LENGTH_SHORT).show();
                listaTrabajos.remove(info.position);
                ofertasListAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AltaOferta.ALTA_OFERTA_REQUEST) {
            if(resultCode == RESULT_OK) {
                Trabajo nuevaOferta = data.getParcelableExtra(AltaOferta.TRABAJO_EXTRA_KEY);
                listaTrabajos.add(nuevaOferta);
                ofertasListAdapter.notifyDataSetChanged();
            } else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.alta_oferta_cancelada), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
