package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
