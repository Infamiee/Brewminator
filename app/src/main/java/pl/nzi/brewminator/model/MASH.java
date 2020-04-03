
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MASH {

    @SerializedName("GRAIN_TEMP")
    @Expose
    private String gRAINTEMP;
    @SerializedName("MASH_STEPS")
    @Expose
    private MASHSTEPS mASHSTEPS;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getGRAINTEMP() {
        return gRAINTEMP;
    }

    public void setGRAINTEMP(String gRAINTEMP) {
        this.gRAINTEMP = gRAINTEMP;
    }

    public MASHSTEPS getMASHSTEPS() {
        return mASHSTEPS;
    }

    public void setMASHSTEPS(MASHSTEPS mASHSTEPS) {
        this.mASHSTEPS = mASHSTEPS;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

}
