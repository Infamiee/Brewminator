
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WATER {

    @SerializedName("AMOUNT")
    @Expose
    private String aMOUNT;
    @SerializedName("BICARBONATE")
    @Expose
    private String bICARBONATE;
    @SerializedName("CALCIUM")
    @Expose
    private String cALCIUM;
    @SerializedName("CHLORIDE")
    @Expose
    private String cHLORIDE;
    @SerializedName("MAGNESIUM")
    @Expose
    private String mAGNESIUM;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("NOTES")
    @Expose
    private Object nOTES;
    @SerializedName("SODIUM")
    @Expose
    private String sODIUM;
    @SerializedName("SULFATE")
    @Expose
    private String sULFATE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getBICARBONATE() {
        return bICARBONATE;
    }

    public void setBICARBONATE(String bICARBONATE) {
        this.bICARBONATE = bICARBONATE;
    }

    public String getCALCIUM() {
        return cALCIUM;
    }

    public void setCALCIUM(String cALCIUM) {
        this.cALCIUM = cALCIUM;
    }

    public String getCHLORIDE() {
        return cHLORIDE;
    }

    public void setCHLORIDE(String cHLORIDE) {
        this.cHLORIDE = cHLORIDE;
    }

    public String getMAGNESIUM() {
        return mAGNESIUM;
    }

    public void setMAGNESIUM(String mAGNESIUM) {
        this.mAGNESIUM = mAGNESIUM;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public Object getNOTES() {
        return nOTES;
    }

    public void setNOTES(Object nOTES) {
        this.nOTES = nOTES;
    }

    public String getSODIUM() {
        return sODIUM;
    }

    public void setSODIUM(String sODIUM) {
        this.sODIUM = sODIUM;
    }

    public String getSULFATE() {
        return sULFATE;
    }

    public void setSULFATE(String sULFATE) {
        this.sULFATE = sULFATE;
    }

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

}
