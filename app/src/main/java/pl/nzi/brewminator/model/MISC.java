
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MISC {

    @SerializedName("AMOUNT")
    @Expose
    private String aMOUNT;
    @SerializedName("AMOUNT_IS_WEIGHT")
    @Expose
    private String aMOUNTISWEIGHT;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("TIME")
    @Expose
    private String tIME;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("USE")
    @Expose
    private String uSE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getAMOUNTISWEIGHT() {
        return aMOUNTISWEIGHT;
    }

    public void setAMOUNTISWEIGHT(String aMOUNTISWEIGHT) {
        this.aMOUNTISWEIGHT = aMOUNTISWEIGHT;
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

    public String getTYPE() {
        return tYPE;
    }

    public void setTYPE(String tYPE) {
        this.tYPE = tYPE;
    }

    public String getUSE() {
        return uSE;
    }

    public void setUSE(String uSE) {
        this.uSE = uSE;
    }

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

}
