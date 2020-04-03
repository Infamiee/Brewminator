
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HOP {
    public HOP() {
    }

    public HOP(String aLPHA, String aMOUNT, String tIME) {
        this.aLPHA = aLPHA;
        this.aMOUNT = aMOUNT;
        this.tIME = tIME;
    }

    @SerializedName("ALPHA")
    @Expose
    private String aLPHA;
    @SerializedName("AMOUNT")
    @Expose
    private String aMOUNT;
    @SerializedName("FORM")
    @Expose
    private String fORM;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("TIME")
    @Expose
    private String tIME;
    @SerializedName("USE")
    @Expose
    private String uSE;
    @SerializedName("USER_HOP_USE")
    @Expose
    private String uSERHOPUSE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;
    @SerializedName("HOP_TEMP")
    @Expose
    private String hOPTEMP;
    @SerializedName("TEMPERATURE")
    @Expose
    private String tEMPERATURE;
    @SerializedName("UTILIZATION")
    @Expose
    private String uTILIZATION;

    public String getALPHA() {
        return aLPHA;
    }

    public void setALPHA(String aLPHA) {
        this.aLPHA = aLPHA;
    }

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getFORM() {
        return fORM;
    }

    public void setFORM(String fORM) {
        this.fORM = fORM;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getTIME() {
        return tIME;
    }

    public void setTIME(String tIME) {
        this.tIME = tIME;
    }

    public String getUSE() {
        return uSE;
    }

    public void setUSE(String uSE) {
        this.uSE = uSE;
    }

    public String getUSERHOPUSE() {
        return uSERHOPUSE;
    }

    public void setUSERHOPUSE(String uSERHOPUSE) {
        this.uSERHOPUSE = uSERHOPUSE;
    }

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

    public String getHOPTEMP() {
        return hOPTEMP;
    }

    public void setHOPTEMP(String hOPTEMP) {
        this.hOPTEMP = hOPTEMP;
    }

    public String getTEMPERATURE() {
        return tEMPERATURE;
    }

    public void setTEMPERATURE(String tEMPERATURE) {
        this.tEMPERATURE = tEMPERATURE;
    }

    public String getUTILIZATION() {
        return uTILIZATION;
    }

    public void setUTILIZATION(String uTILIZATION) {
        this.uTILIZATION = uTILIZATION;
    }

}
