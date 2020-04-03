
package pl.nzi.brewminator.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FERMENTABLES implements Serializable {

    @SerializedName("FERMENTABLE")
    @Expose
    private List<FERMENTABLE> fERMENTABLE = null;

    public List<FERMENTABLE> getFERMENTABLE() {
        return fERMENTABLE;
    }

    public void setFERMENTABLE(List<FERMENTABLE> fERMENTABLE) {
        this.fERMENTABLE = fERMENTABLE;
    }

}
