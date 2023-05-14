package contract;

import java.util.ArrayList;

public interface ContractList {
    public boolean add(Contract registration);

    public boolean delete(int registrationID);

    public Contract search(int registraionID);

    public ArrayList<Contract> searchByCustomer(int customerID);

    public ArrayList<Contract> getContractList();
}
