package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AltaOferta extends AppCompatActivity {

    public static final int ALTA_OFERTA_REQUEST = 1;

    private Intent intentOrigen;
    private Trabajo trabajo;

    private EditText nombreOfertaEditText ;
    private EditText horasEstimadasEditText;
    private EditText maxPrecioHoraEditText;
    private CheckBox requiereInglesCheckBox;
    private EditText fechaEntregaEditText;
    private Button cancelarBtn;
    private Button guardarBtn;
    private Spinner categoriasSpinner;
    private Spinner monedaSpinner;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_oferta);

        intentOrigen = getIntent();
        myCalendar = Calendar.getInstance();

        findViewsById();
        setAdapters();
        setListeners();
    }

    private class DatePickerListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fechaEntregaEditText.setText(sdf.format(myCalendar.getTime()));
        }
    }

    private void setListeners() {
        guardarBtn.setOnClickListener(new GuardarBtnListener());
        cancelarBtn.setOnClickListener(new CancelarBtnListener());
        fechaEntregaEditText.setOnFocusChangeListener(new FechaOnFocusChangeListener());
        fechaEntregaEditText.setOnClickListener(new FechaOnClickListener());
    }

    private void showCalendarPopUp() {
        new DatePickerDialog(AltaOferta.this, new DatePickerListener(),
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setAdapters() {
        categoriasSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, Categoria.CATEGORIAS_MOCK));
        ArrayAdapter<CharSequence> spinnerMonedaAdapter = ArrayAdapter.createFromResource(this,
                R.array.monedas, android.R.layout.simple_spinner_item);
        monedaSpinner.setAdapter(spinnerMonedaAdapter);
    }

    private void findViewsById() {
        nombreOfertaEditText = (EditText) findViewById(R.id.etNombreOferta);
        horasEstimadasEditText = (EditText) findViewById(R.id.etHorasTrabajo);
        maxPrecioHoraEditText = (EditText) findViewById(R.id.etMaxPrecioHora);
        requiereInglesCheckBox = (CheckBox) findViewById(R.id.chboxRequiereIngles);
        fechaEntregaEditText = (EditText) findViewById(R.id.etFechaEntrega);
        cancelarBtn = (Button) findViewById(R.id.btnCancelar);
        guardarBtn = (Button) findViewById(R.id.btnGuardar);
        categoriasSpinner = (Spinner) findViewById(R.id.spinnerCategoria);
        monedaSpinner = (Spinner) findViewById(R.id.spinnerMoneda);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nombre", nombreOfertaEditText.getText().toString());
        outState.putInt("categoria", categoriasSpinner.getSelectedItemPosition());
        outState.putString("horas", horasEstimadasEditText.getText().toString());
        outState.putString("precio-hora", maxPrecioHoraEditText.getText().toString());
        outState.putInt("moneda", monedaSpinner.getSelectedItemPosition());
        outState.putBoolean("requiere-ingles", requiereInglesCheckBox.isChecked());
        outState.putString("fecha-estimada", fechaEntregaEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nombreOfertaEditText.setText(savedInstanceState.getString("nombre"));
        categoriasSpinner.setSelection(savedInstanceState.getInt("categoria"));
        horasEstimadasEditText.setText(savedInstanceState.getString("horas"));
        maxPrecioHoraEditText.setText(savedInstanceState.getString("precio-hora"));
        monedaSpinner.setSelection(savedInstanceState.getInt("moneda"));
        requiereInglesCheckBox.setChecked(savedInstanceState.getBoolean("requiere-ingles"));
        fechaEntregaEditText.setText(savedInstanceState.getString("fecha-estimada"));
    }

    private class FechaOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                showCalendarPopUp();
            }
        }
    }

    private class FechaOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showCalendarPopUp();
        }
    }
}
