
package pl.nzi.brewminator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    public Recipe() {
    }

    @SerializedName("BATCH_SIZE")
    @Expose
    private String bATCHSIZE;
    @SerializedName("BATCH_SIZE_MODE")
    @Expose
    private String bATCHSIZEMODE;
    @SerializedName("BF_CO2_LEVEL")
    @Expose
    private Object bFCO2LEVEL;
    @SerializedName("BF_CO2_UNIT")
    @Expose
    private Object bFCO2UNIT;
    @SerializedName("BF_PRIMING_AMOUNT")
    @Expose
    private Object bFPRIMINGAMOUNT;
    @SerializedName("BF_PRIMING_METHOD")
    @Expose
    private Object bFPRIMINGMETHOD;
    @SerializedName("BOIL_SIZE")
    @Expose
    private String bOILSIZE;
    @SerializedName("BOIL_TIME")
    @Expose
    private String bOILTIME;
    @SerializedName("BREWER")
    @Expose
    private Object bREWER;
    @SerializedName("CARBONATION_USED")
    @Expose
    private Object cARBONATIONUSED;
    @SerializedName("DISPLAY_BATCH_SIZE")
    @Expose
    private String dISPLAYBATCHSIZE;
    @SerializedName("DISPLAY_BOIL_SIZE")
    @Expose
    private String dISPLAYBOILSIZE;
    @SerializedName("EFFICIENCY")
    @Expose
    private String eFFICIENCY;
    @SerializedName("EST_ABV")
    @Expose
    private String eSTABV;
    @SerializedName("EST_COLOR")
    @Expose
    private String eSTCOLOR;
    @SerializedName("EST_FG")
    @Expose
    private String eSTFG;
    @SerializedName("EST_OG")
    @Expose
    private String eSTOG;
    @SerializedName("FERMENTABLES")
    @Expose
    private FERMENTABLES fERMENTABLES;
    @SerializedName("FG")
    @Expose
    private String fG;
    @SerializedName("HOPS")
    @Expose
    private HOPS hOPS;
    @SerializedName("IBU")
    @Expose
    private String iBU;
    @SerializedName("IBU_METHOD")
    @Expose
    private String iBUMETHOD;
    @SerializedName("MASH")
    @Expose
    private MASH mASH;
    @SerializedName("MISCS")
    @Expose
    private Object mISCS;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("NOTES")
    @Expose
    private Object nOTES;
    @SerializedName("NO_CHILL_EXTRA_MINUTES")
    @Expose
    private Object nOCHILLEXTRAMINUTES;
    @SerializedName("OG")
    @Expose
    private String oG;
    @SerializedName("PRIMARY_TEMP")
    @Expose
    private String pRIMARYTEMP;
    @SerializedName("PRIMING_SUGAR_NAME")
    @Expose
    private Object pRIMINGSUGARNAME;
    @SerializedName("STYLE")
    @Expose
    private STYLE sTYLE;
    @SerializedName("TYPE")
    @Expose
    private String tYPE;
    @SerializedName("URL")
    @Expose
    private Object uRL;
    @SerializedName("VERSION")
    @Expose
    private String vERSION;
    @SerializedName("WATERS")
    @Expose
    private Object wATERS;
    @SerializedName("YEASTS")
    @Expose
    private YEASTS yEASTS;
    @SerializedName("YEAST_STARTER")
    @Expose
    private String yEASTSTARTER;

    public String getBATCHSIZE() {
        return bATCHSIZE;
    }

    public void setBATCHSIZE(String bATCHSIZE) {
        this.bATCHSIZE = bATCHSIZE;
    }

    public String getBATCHSIZEMODE() {
        return bATCHSIZEMODE;
    }

    public void setBATCHSIZEMODE(String bATCHSIZEMODE) {
        this.bATCHSIZEMODE = bATCHSIZEMODE;
    }

    public Object getBFCO2LEVEL() {
        return bFCO2LEVEL;
    }

    public void setBFCO2LEVEL(Object bFCO2LEVEL) {
        this.bFCO2LEVEL = bFCO2LEVEL;
    }

    public Object getBFCO2UNIT() {
        return bFCO2UNIT;
    }

    public void setBFCO2UNIT(Object bFCO2UNIT) {
        this.bFCO2UNIT = bFCO2UNIT;
    }

    public Object getBFPRIMINGAMOUNT() {
        return bFPRIMINGAMOUNT;
    }

    public void setBFPRIMINGAMOUNT(Object bFPRIMINGAMOUNT) {
        this.bFPRIMINGAMOUNT = bFPRIMINGAMOUNT;
    }

    public Object getBFPRIMINGMETHOD() {
        return bFPRIMINGMETHOD;
    }

    public void setBFPRIMINGMETHOD(Object bFPRIMINGMETHOD) {
        this.bFPRIMINGMETHOD = bFPRIMINGMETHOD;
    }

    public String getBOILSIZE() {
        return bOILSIZE;
    }

    public void setBOILSIZE(String bOILSIZE) {
        this.bOILSIZE = bOILSIZE;
    }

    public String getBOILTIME() {
        return bOILTIME;
    }

    public void setBOILTIME(String bOILTIME) {
        this.bOILTIME = bOILTIME;
    }

    public Object getBREWER() {
        return bREWER;
    }

    public void setBREWER(Object bREWER) {
        this.bREWER = bREWER;
    }

    public Object getCARBONATIONUSED() {
        return cARBONATIONUSED;
    }

    public void setCARBONATIONUSED(Object cARBONATIONUSED) {
        this.cARBONATIONUSED = cARBONATIONUSED;
    }

    public String getDISPLAYBATCHSIZE() {
        return dISPLAYBATCHSIZE;
    }

    public void setDISPLAYBATCHSIZE(String dISPLAYBATCHSIZE) {
        this.dISPLAYBATCHSIZE = dISPLAYBATCHSIZE;
    }

    public String getDISPLAYBOILSIZE() {
        return dISPLAYBOILSIZE;
    }

    public void setDISPLAYBOILSIZE(String dISPLAYBOILSIZE) {
        this.dISPLAYBOILSIZE = dISPLAYBOILSIZE;
    }

    public String getEFFICIENCY() {
        return eFFICIENCY;
    }

    public void setEFFICIENCY(String eFFICIENCY) {
        this.eFFICIENCY = eFFICIENCY;
    }

    public String getESTABV() {
        return eSTABV;
    }

    public void setESTABV(String eSTABV) {
        this.eSTABV = eSTABV;
    }

    public String getESTCOLOR() {
        return eSTCOLOR;
    }

    public void setESTCOLOR(String eSTCOLOR) {
        this.eSTCOLOR = eSTCOLOR;
    }

    public String getESTFG() {
        return eSTFG;
    }

    public void setESTFG(String eSTFG) {
        this.eSTFG = eSTFG;
    }

    public String getESTOG() {
        return eSTOG;
    }

    public void setESTOG(String eSTOG) {
        this.eSTOG = eSTOG;
    }

    public FERMENTABLES getFERMENTABLES() {
        return fERMENTABLES;
    }

    public void setFERMENTABLES(FERMENTABLES fERMENTABLES) {
        this.fERMENTABLES = fERMENTABLES;
    }

    public String getFG() {
        return fG;
    }

    public void setFG(String fG) {
        this.fG = fG;
    }

    public HOPS getHOPS() {
        return hOPS;
    }

    public void setHOPS(HOPS hOPS) {
        this.hOPS = hOPS;
    }

    public String getIBU() {
        return iBU;
    }

    public void setIBU(String iBU) {
        this.iBU = iBU;
    }

    public String getIBUMETHOD() {
        return iBUMETHOD;
    }

    public void setIBUMETHOD(String iBUMETHOD) {
        this.iBUMETHOD = iBUMETHOD;
    }

    public MASH getMASH() {
        return mASH;
    }

    public void setMASH(MASH mASH) {
        this.mASH = mASH;
    }

    public Object getMISCS() {
        return mISCS;
    }

    public void setMISCS(Object mISCS) {
        this.mISCS = mISCS;
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

    public Object getNOCHILLEXTRAMINUTES() {
        return nOCHILLEXTRAMINUTES;
    }

    public void setNOCHILLEXTRAMINUTES(Object nOCHILLEXTRAMINUTES) {
        this.nOCHILLEXTRAMINUTES = nOCHILLEXTRAMINUTES;
    }

    public String getOG() {
        return oG;
    }

    public void setOG(String oG) {
        this.oG = oG;
    }

    public String getPRIMARYTEMP() {
        return pRIMARYTEMP;
    }

    public void setPRIMARYTEMP(String pRIMARYTEMP) {
        this.pRIMARYTEMP = pRIMARYTEMP;
    }

    public Object getPRIMINGSUGARNAME() {
        return pRIMINGSUGARNAME;
    }

    public void setPRIMINGSUGARNAME(Object pRIMINGSUGARNAME) {
        this.pRIMINGSUGARNAME = pRIMINGSUGARNAME;
    }

    public STYLE getSTYLE() {
        return sTYLE;
    }

    public void setSTYLE(STYLE sTYLE) {
        this.sTYLE = sTYLE;
    }

    public String getTYPE() {
        return tYPE;
    }

    public void setTYPE(String tYPE) {
        this.tYPE = tYPE;
    }

    public Object getURL() {
        return uRL;
    }

    public void setURL(Object uRL) {
        this.uRL = uRL;
    }

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

    public Object getWATERS() {
        return wATERS;
    }

    public void setWATERS(Object wATERS) {
        this.wATERS = wATERS;
    }

    public YEASTS getYEASTS() {
        return yEASTS;
    }

    public void setYEASTS(YEASTS yEASTS) {
        this.yEASTS = yEASTS;
    }

    public String getYEASTSTARTER() {
        return yEASTSTARTER;
    }

    public void setYEASTSTARTER(String yEASTSTARTER) {
        this.yEASTSTARTER = yEASTSTARTER;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "bATCHSIZE='" + bATCHSIZE + '\'' +
                ", bATCHSIZEMODE='" + bATCHSIZEMODE + '\'' +
                ", bFCO2LEVEL=" + bFCO2LEVEL +
                ", bFCO2UNIT=" + bFCO2UNIT +
                ", bFPRIMINGAMOUNT=" + bFPRIMINGAMOUNT +
                ", bFPRIMINGMETHOD=" + bFPRIMINGMETHOD +
                ", bOILSIZE='" + bOILSIZE + '\'' +
                ", bOILTIME='" + bOILTIME + '\'' +
                ", bREWER=" + bREWER +
                ", cARBONATIONUSED=" + cARBONATIONUSED +
                ", dISPLAYBATCHSIZE='" + dISPLAYBATCHSIZE + '\'' +
                ", dISPLAYBOILSIZE='" + dISPLAYBOILSIZE + '\'' +
                ", eFFICIENCY='" + eFFICIENCY + '\'' +
                ", eSTABV='" + eSTABV + '\'' +
                ", eSTCOLOR='" + eSTCOLOR + '\'' +
                ", eSTFG='" + eSTFG + '\'' +
                ", eSTOG='" + eSTOG + '\'' +
                ", fERMENTABLES=" + fERMENTABLES +
                ", fG='" + fG + '\'' +
                ", hOPS=" + hOPS +
                ", iBU='" + iBU + '\'' +
                ", iBUMETHOD='" + iBUMETHOD + '\'' +
                ", mASH=" + mASH +
                ", mISCS=" + mISCS +
                ", nAME='" + nAME + '\'' +
                ", nOTES=" + nOTES +
                ", nOCHILLEXTRAMINUTES=" + nOCHILLEXTRAMINUTES +
                ", oG='" + oG + '\'' +
                ", pRIMARYTEMP='" + pRIMARYTEMP + '\'' +
                ", pRIMINGSUGARNAME=" + pRIMINGSUGARNAME +
                ", sTYLE=" + sTYLE +
                ", tYPE='" + tYPE + '\'' +
                ", uRL=" + uRL +
                ", vERSION='" + vERSION + '\'' +
                ", wATERS=" + wATERS +
                ", yEASTS=" + yEASTS +
                ", yEASTSTARTER='" + yEASTSTARTER + '\'' +
                '}';
    }
}
