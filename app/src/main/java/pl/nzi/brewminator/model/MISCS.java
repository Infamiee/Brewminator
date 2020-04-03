
package pl.nzi.brewminator.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MISCS implements Serializable {

    @SerializedName("MISC")
    @Expose
    private List<MISC> mISC = null;

    public List<MISC> getMISC() {
        return mISC;
    }

    public void setMISC(List<MISC> mISC) {
        this.mISC = mISC;
    }

}
