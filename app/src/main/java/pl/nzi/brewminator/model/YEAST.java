
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YEAST {

    @SerializedName("AMOUNT")
    @Expose
    private String aMOUNT;
    @SerializedName("ATTENUATION")
    @Expose
    private String aTTENUATION;
    @SerializedName("FLOCCULATION")
    @Expose
    private String fLOCCULATION;
    @SerializedName("FORM")
    @Expose
    private String fORM;
    @SerializedName("LABORATORY")
    @Expose
    private String lABORATORY;
    @SerializedName("MAX_TEMPERATURE")
    @Expose
    private String mAXTEMPERATURE;
    @SerializedName("MIN_TEMPERATURE")
    @Expose
    private String mINTEMPERATURE;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("PRODUCT_ID")
    @Expose
    private String pRODUCTID;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getAMOUNT() {
        return aMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    public String getATTENUATION() {
        return aTTENUATION;
    }

    public void setATTENUATION(String aTTENUATION) {
        this.aTTENUATION = aTTENUATION;
    }

    public String getFLOCCULATION() {
        return fLOCCULATION;
    }

    public void setFLOCCULATION(String fLOCCULATION) {
        this.fLOCCULATION = fLOCCULATION;
    }

    public String getFORM() {
        return fORM;
    }

    public void setFORM(String fORM) {
        this.fORM = fORM;
    }

    public String getLABORATORY() {
        return lABORATORY;
    }

    public void setLABORATORY(String lABORATORY) {
        this.lABORATORY = lABORATORY;
    }

    public String getMAXTEMPERATURE() {
        return mAXTEMPERATURE;
    }

    public void setMAXTEMPERATURE(String mAXTEMPERATURE) {
        this.mAXTEMPERATURE = mAXTEMPERATURE;
    }

    public String getMINTEMPERATURE() {
        return mINTEMPERATURE;
    }

    public void setMINTEMPERATURE(String mINTEMPERATURE) {
        this.mINTEMPERATURE = mINTEMPERATURE;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getPRODUCTID() {
        return pRODUCTID;
    }

    public void setPRODUCTID(String pRODUCTID) {
        this.pRODUCTID = pRODUCTID;
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
