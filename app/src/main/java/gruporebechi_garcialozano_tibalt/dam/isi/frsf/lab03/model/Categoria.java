package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdominguez on 07/09/16.
 */
public class Categoria implements Parcelable{
    private Integer id;
    private String descripcion;
    private List<Trabajo> trabajos;

    public Categoria(){
        this.trabajos=new ArrayList<Trabajo>();
    }

    public Categoria(Integer id, String desc){
        this();
        this.id = id;
        this.descripcion = desc;
    }

    protected Categoria(Parcel in) {
        id = in.readInt();
        descripcion = in.readString();
        trabajos = in.createTypedArrayList(Trabajo.CREATOR);
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Trabajo> getTrabajo() {
        return trabajos;
    }

    public void setTrabajo(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    public void addTrabajo(Trabajo t){
        this.trabajos.add(t);
    }

    public static final Categoria[] CATEGORIAS_MOCK= new Categoria[]{
            new Categoria(1,"Arquitecto"),
            new Categoria(2,"Desarrollador"),
            new Categoria(3, "Tester"),
            new Categoria(4,"Analista"),
            new Categoria(5,"Mobile Developer")
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(descripcion);
        parcel.writeTypedList(trabajos);
    }

    @Override
    public String toString(){
        return this.descripcion;
    }
}
