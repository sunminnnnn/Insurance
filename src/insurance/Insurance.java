package insurance;

import java.util.Calendar;
import java.util.GregorianCalendar;

import contract.Contract;
import customer.Customer;

public class Insurance {
    // Attributes
    private String insuranceName, contents, insuranceType, insuranceCost;
    private int insuranceID=10;
    private float achieveRatio;

    // composition
    public Coverage m_hcoverage;
    public Coverage m_mcoverage;
    public Coverage m_lcoverage;
    public Approve m_approve;
    public SaleRecord m_saleRecord;
    private Contract contract; 
    private Customer customer;

    public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Insurance() {
        // composition
        this.m_hcoverage = new Coverage();
        this.m_mcoverage = new Coverage();
        this.m_lcoverage = new Coverage();
        this.m_approve = new Approve();
        this.m_saleRecord = new SaleRecord();
    }

    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        contents = contents;
    }

    public String getInsuranceCost() {
        return insuranceCost;
    }
    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public int getInsuranceID() {
        return insuranceID;
    }
    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getInsuranceName() {
        return insuranceName;
    }
    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getInsuranceType() {
        return insuranceType;
    }
    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public float getAchieveRatio() {
        return achieveRatio;
    }
    public void setAchieveRatio(float achieveRatio) {
        this.achieveRatio = achieveRatio;
    }

    public Coverage getM_hcoverage() {
        return m_hcoverage;
    }
    public void setM_hcoverage(Coverage m_hcoverage) {
        this.m_hcoverage = m_hcoverage;
    }

    public Coverage getM_mcoverage() {
        return m_mcoverage;
    }
    public void setM_mcoverage(Coverage m_mcoverage) {
        this.m_mcoverage = m_mcoverage;
    }

    public Coverage getM_lcoverage() {
        return m_lcoverage;
    }
    public void setM_lcoverage(Coverage m_lcoverage) {
        this.m_lcoverage = m_lcoverage;
    }

    public Approve getM_approve() {
        return m_approve;
    }
    public void setM_approve(Approve m_approve) {
        this.m_approve = m_approve;
    }

    public SaleRecord getM_SaleRecord() {
        return m_saleRecord;
    }
    public void setM_SaleRecord(SaleRecord m_saleRecord) {
        this.m_saleRecord = m_saleRecord;
    }
    
    public int calcualteCustomerAge() {
        String[] strArrary = customer.getCustomerNumber().split("");
        int ageYear;
        if (Integer.parseInt(strArrary[0]) != 0) {
            ageYear = 1900 + Integer.parseInt(strArrary[0]) * 10 + Integer.parseInt(strArrary[1]);
        } else {
            ageYear = 2000 + Integer.parseInt(strArrary[1]);
        }
        GregorianCalendar today = new GregorianCalendar();
        int year = today.get(Calendar.YEAR);
        return year - ageYear;
    }
}