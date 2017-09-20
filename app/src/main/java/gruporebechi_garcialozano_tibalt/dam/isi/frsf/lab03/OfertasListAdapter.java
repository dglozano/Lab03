package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by diegogarcialozano on 19/09/17.
 */

public class OfertasListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Trabajo> listaTrabajos;

    public OfertasListAdapter(Context applicationContext, List<Trabajo> listaTrabajos) {
        super();
        inflater = LayoutInflater.from(applicationContext);
        this.listaTrabajos = listaTrabajos;
    }

    @Override
    public int getCount() {
        return listaTrabajos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTrabajos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaTrabajos.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) row = inflater.inflate(R.layout.content_ofertalaboral, parent, false);
        ViewItemHolder holder = (ViewItemHolder) row.getTag();
        if(holder ==null){
            holder = new ViewItemHolder(row);
            row.setTag(holder);
        }

        Trabajo ofertaTrabajo = (Trabajo) getItem(position);

        holder.posicionTextView.setText(ofertaTrabajo.getCategoria().toString());
        holder.tituloOfertaTextView.setText(ofertaTrabajo.getDescripcion().toString());
        holder.horasTextView.setText(ofertaTrabajo.getHorasPresupuestadas().toString());
        holder.maxPesoHoraTextView.setText(ofertaTrabajo.getPrecioMaximoHora().toString());
        holder.fechaFinTextView.setText(ofertaTrabajo.getFechaEntrega().toString());
        holder.enInglesCheckBox.setChecked(ofertaTrabajo.getRequiereIngles());

        switch (ofertaTrabajo.getMonedaPago()){
            //1 US$ 2Euro 3 AR$- 4 Libra 5 R$
            case 1:
                holder.monedaImageView.setImageResource(R.drawable.us);
                break;
            case 2:
                holder.monedaImageView.setImageResource(R.drawable.eu);
                break;
            case 3:
                holder.monedaImageView.setImageResource(R.drawable.ar);
                break;
            case 4:
                holder.monedaImageView.setImageResource(R.drawable.uk);
                break;
            case 5:
                holder.monedaImageView.setImageResource(R.drawable.br);
                break;
        }

        return row;
    }

    private class ViewItemHolder {
        TextView posicionTextView = null;
        TextView tituloOfertaTextView = null;
        TextView horasTextView = null;
        TextView maxPesoHoraTextView = null;
        ImageView monedaImageView = null;
        TextView fechaFinTextView = null;
        CheckBox enInglesCheckBox = null;

        ViewItemHolder(View base) {
            this.posicionTextView = base.findViewById(R.id.tvPosicion);
            this.tituloOfertaTextView = base.findViewById(R.id.tvTituloOferta);
            this.horasTextView = base.findViewById(R.id.tvCantHoras);
            this.maxPesoHoraTextView = base.findViewById(R.id.tvMaxPagoHora);
            this.monedaImageView = base.findViewById(R.id.imgvMoneda);
            this.fechaFinTextView = base.findViewById(R.id.tvFechaFin);
            this.enInglesCheckBox = base.findViewById(R.id.chxbIngles);
        }
    }
}