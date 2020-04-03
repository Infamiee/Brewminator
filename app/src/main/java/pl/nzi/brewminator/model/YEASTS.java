
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YEASTS {

    @SerializedName("YEAST")
    @Expose
    private YEAST yEAST;

    public YEAST getYEAST() {
        return yEAST;
    }

    public void setYEAST(YEAST yEAST) {
        this.yEAST = yEAST;
    }

}
