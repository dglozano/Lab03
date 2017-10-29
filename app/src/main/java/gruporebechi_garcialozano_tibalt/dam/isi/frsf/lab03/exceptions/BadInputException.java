package gruporebechi_garcialozano_tibalt.dam.isi.frsf.lab03.exceptions;

/**
 * Created by diegogarcialozano on 12/10/17.
 */

public class BadInputException extends Exception {

    private String msg;

    public BadInputException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }
}
