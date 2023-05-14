package exemption;

import accident.Accident;

public class Exemption extends Accident {
    private int exemptionID, customerID;
    private String subFile, reason, legacy;

    public int getExemptionID() {
        return exemptionID;
    }
    public void setExemptionID(int exemptionID) {
        this.exemptionID = exemptionID;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSubFile() {
        return subFile;
    }
    public void setSubFile(String subFile) {
        this.subFile = subFile;
    }

    public String getLegacy() {
        return legacy;
    }
    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
