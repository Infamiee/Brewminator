
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WATERS {

    @SerializedName("WATER")
    @Expose
    private WATER wATER;

    public WATER getWATER() {
        return wATER;
    }

    public void setWATER(WATER wATER) {
        this.wATER = wATER;
    }

}
