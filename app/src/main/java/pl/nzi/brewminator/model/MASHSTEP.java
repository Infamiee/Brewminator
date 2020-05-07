
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MASHSTEP {

    @SerializedName("INFUSE_AMOUNT")
    @Expose
    private String iNFUSEAMOUNT;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("STEP_TEMP")
    @Expose
    private String sTEPTEMP;
    @SerializedName("STEP_TIME")
    @Expose
    private String sTEPTIME;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getINFUSEAMOUNT() {
        return iNFUSEAMOUNT;
    }

    public void setINFUSEAMOUNT(String iNFUSEAMOUNT) {
        this.iNFUSEAMOUNT = iNFUSEAMOUNT;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getSTEPTEMP() {
        return sTEPTEMP;
    }

    public void setSTEPTEMP(String sTEPTEMP) {
        this.sTEPTEMP = sTEPTEMP;
    }

    public String getSTEPTIME() {
        return sTEPTIME;
    }

    public void setSTEPTIME(String sTEPTIME) {
        this.sTEPTIME = sTEPTIME;
    }

    public String getTYPE() {
        return tYPE;
    }

    public void setTYPE(String tYPE) {
        this.tYPE = tYPE;
    }

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

}
