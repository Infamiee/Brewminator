
package pl.nzi.brewminator.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HOPS {

    @SerializedName("HOP")
    @Expose
    private List<HOP> hOP = null;

    public List<HOP> getHOP() {
        return hOP;
    }

    public void setHOP(List<HOP> hOP) {
        this.hOP = hOP;
    }

}
