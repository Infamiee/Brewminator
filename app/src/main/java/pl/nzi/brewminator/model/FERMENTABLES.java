
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FERMENTABLES {
    public FERMENTABLES() {
    }

    @SerializedName("FERMENTABLE")
    @Expose
    private FERMENTABLE fERMENTABLE;

    public FERMENTABLE getFERMENTABLE() {
        return fERMENTABLE;
    }

    public void setFERMENTABLE(FERMENTABLE fERMENTABLE) {
        this.fERMENTABLE = fERMENTABLE;
    }

}
