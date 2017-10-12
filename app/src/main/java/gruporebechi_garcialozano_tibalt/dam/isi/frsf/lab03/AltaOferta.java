package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;

public class AltaOferta extends AppCompatActivity {

    public static final int ALTA_OFERTA_REQUEST = 1;

    private Intent intentOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_oferta);

        intentOrigen = getIntent();

        Button cancelarBtn = (Button) findViewById(R.id.btnCancelar);
        Button guardarBtn = (Button) findViewById(R.id.btnGuardar);
        Spinner categoriasSpinner = (Spinner) findViewById(R.id.spinnerCategoria);
        categoriasSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, Categoria.CATEGORIAS_MOCK));
        Spinner monedaSpinner = (Spinner) findViewById(R.id.spinnerMoneda);
        ArrayAdapter<CharSequence> spinnerMonedaAdapter = ArrayAdapter.createFromResource(this,
                R.array.monedas, android.R.layout.simple_spinner_item);
        monedaSpinner.setAdapter(spinnerMonedaAdapter);
        guardarBtn.setOnClickListener(new GuardarBtnListener());
        cancelarBtn.setOnClickListener(new CancelarBtnListener());
    }

    private class CancelarBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setResult(RESULT_CANCELED, intentOrigen);
            finish();
        }
    }

    private class GuardarBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setResult(RESULT_OK, intentOrigen);
            finish();
        }
    }
}
