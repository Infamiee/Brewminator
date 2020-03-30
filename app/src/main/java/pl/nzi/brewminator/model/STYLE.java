
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class STYLE {

    public STYLE() {
    }

    @SerializedName("ABV_MAX")
    @Expose
    private String aBVMAX;
    @SerializedName("ABV_MIN")
    @Expose
    private String aBVMIN;
    @SerializedName("CATEGORY")
    @Expose
    private String cATEGORY;
    @SerializedName("CATEGORY_NUMBER")
    @Expose
    private String cATEGORYNUMBER;
    @SerializedName("COLOR_MAX")
    @Expose
    private String cOLORMAX;
    @SerializedName("COLOR_MIN")
    @Expose
    private String cOLORMIN;
    @SerializedName("FG_MAX")
    @Expose
    private String fGMAX;
    @SerializedName("FG_MIN")
    @Expose
    private String fGMIN;
    @SerializedName("IBU_MAX")
    @Expose
    private String iBUMAX;
    @SerializedName("IBU_MIN")
    @Expose
    private String iBUMIN;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("OG_MAX")
    @Expose
    private String oGMAX;
    @SerializedName("OG_MIN")
    @Expose
    private String oGMIN;
    @SerializedName("STYLE_GUIDE")
    @Expose
    private String sTYLEGUIDE;
    @SerializedName("STYLE_LETTER")
    @Expose
    private String sTYLELETTER;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getABVMAX() {
        return aBVMAX;
    }

    public void setABVMAX(String aBVMAX) {
        this.aBVMAX = aBVMAX;
    }

    public String getABVMIN() {
        return aBVMIN;
    }

    public void setABVMIN(String aBVMIN) {
        this.aBVMIN = aBVMIN;
    }

    public String getCATEGORY() {
        return cATEGORY;
    }

    public void setCATEGORY(String cATEGORY) {
        this.cATEGORY = cATEGORY;
    }

    public String getCATEGORYNUMBER() {
        return cATEGORYNUMBER;
    }

    public void setCATEGORYNUMBER(String cATEGORYNUMBER) {
        this.cATEGORYNUMBER = cATEGORYNUMBER;
    }

    public String getCOLORMAX() {
        return cOLORMAX;
    }

    public void setCOLORMAX(String cOLORMAX) {
        this.cOLORMAX = cOLORMAX;
    }

    public String getCOLORMIN() {
        return cOLORMIN;
    }

    public void setCOLORMIN(String cOLORMIN) {
        this.cOLORMIN = cOLORMIN;
    }

    public String getFGMAX() {
        return fGMAX;
    }

    public void setFGMAX(String fGMAX) {
        this.fGMAX = fGMAX;
    }

    public String getFGMIN() {
        return fGMIN;
    }

    public void setFGMIN(String fGMIN) {
        this.fGMIN = fGMIN;
    }

    public String getIBUMAX() {
        return iBUMAX;
    }

    public void setIBUMAX(String iBUMAX) {
        this.iBUMAX = iBUMAX;
    }

    public String getIBUMIN() {
        return iBUMIN;
    }

    public void setIBUMIN(String iBUMIN) {
        this.iBUMIN = iBUMIN;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getOGMAX() {
        return oGMAX;
    }

    public void setOGMAX(String oGMAX) {
        this.oGMAX = oGMAX;
    }

    public String getOGMIN() {
        return oGMIN;
    }

    public void setOGMIN(String oGMIN) {
        this.oGMIN = oGMIN;
    }

    public String getSTYLEGUIDE() {
        return sTYLEGUIDE;
    }

    public void setSTYLEGUIDE(String sTYLEGUIDE) {
        this.sTYLEGUIDE = sTYLEGUIDE;
    }

    public String getSTYLELETTER() {
        return sTYLELETTER;
    }

    public void setSTYLELETTER(String sTYLELETTER) {
        this.sTYLELETTER = sTYLELETTER;
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
