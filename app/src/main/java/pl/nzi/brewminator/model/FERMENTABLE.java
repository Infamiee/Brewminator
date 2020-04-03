
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FERMENTABLE {

    @SerializedName("ADD_AFTER_BOIL")
    @Expose
    private String aDDAFTERBOIL;
    @SerializedName("AMOUNT")
    @Expose
    private String aMOUNT;
    @SerializedName("COLOR")
    @Expose
    private String cOLOR;
    @SerializedName("DIASTATIC_POWER")
    @Expose
    private String dIASTATICPOWER;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("ORIGIN")
    @Expose
    private Object oRIGIN;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;
    @SerializedName("YIELD")
    @Expose
    private String yIELD;

    public String getADDAFTERBOIL() {
        return aDDAFTERBOIL;
    }

    public void setADDAFTERBOIL(String aDDAFTERBOIL) {
        this.aDDAFTERBOIL = aDDAFTERBOIL;
    }

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getCOLOR() {
        return cOLOR;
    }

    public void setCOLOR(String cOLOR) {
        this.cOLOR = cOLOR;
    }

    public String getDIASTATICPOWER() {
        return dIASTATICPOWER;
    }

    public void setDIASTATICPOWER(String dIASTATICPOWER) {
        this.dIASTATICPOWER = dIASTATICPOWER;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public Object getORIGIN() {
        return oRIGIN;
    }

    public void setORIGIN(Object oRIGIN) {
        this.oRIGIN = oRIGIN;
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

    public String getYIELD() {
        return yIELD;
    }

    public void setYIELD(String yIELD) {
        this.yIELD = yIELD;
    }

}
