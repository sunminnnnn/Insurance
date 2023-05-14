package insurance;

import java.util.ArrayList;

public interface InsuranceList {

    public boolean add(Insurance insurance);

    public boolean delete(int insuranceId);

    public Insurance search(int insuranceId);

    public ArrayList<Insurance> getInsuranceList();

}