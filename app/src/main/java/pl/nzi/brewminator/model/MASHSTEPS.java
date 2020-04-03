
package pl.nzi.brewminator.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MASHSTEPS implements Serializable {

    @SerializedName("MASH_STEP")
    @Expose
    private List<MASHSTEP> mASHSTEP = null;

    public List<MASHSTEP> getMASHSTEP() {
        return mASHSTEP;
    }

    public void setMASHSTEP(List<MASHSTEP> mASHSTEP) {
        this.mASHSTEP = mASHSTEP;
    }

}
