package accident;

import customer.Customer;

public class Accident {
    public enum EAccidnetSize {
        EHigh, EMiddle, ELow;
    }

    //attribute
    private String accidentDate, date, accidentPlace, accidentTime, accidentType, accidentName;
    private int accidentID, accidentComplete, judgementComplete;
    //composition Class
    private SiteInfo m_siteInfo;
    private EAccidnetSize accidentSize;
    private Customer customer;

    public Accident() {
        setM_siteInfo(new SiteInfo());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getAccidentComplete() {
        return accidentComplete;
    }

    public void setAccidentComplete(int i) {
        this.accidentComplete = i;
    }

    public String getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(String accidentDate) {
        this.accidentDate = accidentDate;
    }

    public int getAccidentID() {
        return accidentID;
    }

    public void setAccidentID(int accidentNum) {
        this.accidentID = accidentNum;
    }

    public String getAccidentPlace() {
        return accidentPlace;
    }

    public void setAccidentPlace(String accidentPlace) {
        this.accidentPlace = accidentPlace;
    }

    public String getAccidentTime() {
        return accidentTime;
    }

    public void setAccidentTime(String accidentTime) {
        this.accidentTime = accidentTime;
    }

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }

    public EAccidnetSize getAccidentSize() {
        return accidentSize;
    }

    public void setAccidentSize(EAccidnetSize accidentSize) {
        this.accidentSize = accidentSize;
    }

    public int getJudgementComplete() {
        return judgementComplete;
    }

    public void setJudgementComplete(int judgementComplete) {
        this.judgementComplete = judgementComplete;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SiteInfo getM_siteInfo() {
        return m_siteInfo;
    }

    public void setM_siteInfo(SiteInfo m_siteInfo) {
        this.m_siteInfo = m_siteInfo;
    }

    public void finalize() throws Throwable {
    }

    public String getAccidentName() {
        return accidentName;
    }

    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }

    public String getCompleteText() {
        if (this.getAccidentComplete() == 1) return "처리";
        else return "미처리";
    }

}