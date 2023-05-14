package UI;

import accident.Accident;
import accident.AccidentList;
import accident.AccidentListImpl;
import accident.SiteInfo;
import contract.Contract;
import contract.ContractList;
import contract.ContractListImpl;
import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import employee.Employee;
import exemption.Exemption;
import exemption.ExemptionList;
import exemption.ExemptionListImpl;
import insurance.*;
import pCustomer.PCustomer;
import pCustomer.PCustomerList;
import pCustomer.PCustomerListImpl;
import reward.RewardInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ISMain {
    private static InsuranceList insuranceList;
    private static PCustomerList pCustomerList;
    private static CustomerList customerList;
    private static ContractList contractList;

    public ISMain() {
        pCustomerList = new PCustomerListImpl();
        customerList = new CustomerListImpl();
        contractList = new ContractListImpl();
    }

    public static void main(String[] args) throws IOException {
        ISMain main = new ISMain();//생성
        boolean flag = false;   // 프로그램 종료를 판단하는 flag 데이터.
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                checkEmployee();    // 직원의 유형(보험개발, 영업, 보상)별로 접속할 수 있는 메인 메뉴를 분리해주는 목적
                String sChoice = objReader.readLine().trim();
                switch (sChoice) {
                    case "1":   // 보험개발
                        System.out.println("========== 보험 개발 메뉴 ==========");
                        System.out.println("1. 보험 설계하기");
                        System.out.println("2. 상품 인가하기");
                        System.out.println("3. 사후 관리하기");
                        System.out.println("4. 종료하기");

                        int submenu = Integer.parseInt(objReader.readLine());
                        switch (submenu) {
                            case 1:
                                designInsurance(objReader);
                                break;
                            case 2:
                                System.out.println("******* 보험상품을 인가하는 페이지입니다 *******");
                                approveInsurance(objReader);
                                break;
                            case 3:
                                System.out.println("******* 보험상품을 사후관리하는 페이지입니다 ********");
                                manageInsurance(objReader);
                                break;
                            case 4:
                                System.out.println("프로그램을 종료합니다.");
                                flag = true;
                                break;
                        }
                        break;
                    case "2":   // 보험영업및계약 파트
                        System.out.println("========== 보험 영업 및 계약 메뉴 ==========");
                        System.out.println("1. 보험료 관리하기");

                        System.out.println("3. 잠재 고객 관리하기");
                        System.out.println("4. 고객 관리하기");
                        System.out.println("5. 계약 관리하기");
                        System.out.println("6. 계약 체결하기");

                        submenu = Integer.parseInt(objReader.readLine());
                        switch (submenu) {
                            case 1:
                                manageInsurancepremium(objReader);
                                break;
                            case 3:
                                ////테스트용 임시정보입력
                                PCustomer pcustomer = new PCustomer();
                                pcustomer.setPCustomerID(1);
                                pcustomer.setDate("2023.05.08");
                                pcustomer.setPhoneNumber("01058830056");
                                pcustomer.setConsultContext("");
                                pcustomer.setCustomerName("박승연");
                                pCustomerList.add(pcustomer);
                                ////
                                consult(objReader);
                                break;
                            case 4:
                                manageCustomer(objReader);
                                break;
                            case 5:
                                manageContract(objReader);
                                break;
                            case 6:
                                contract(objReader);
                                break;
                        }

                        break;
                    case "3":   // 보험보상 파트
                        System.out.println("========== 보험 보상 메뉴 ==========");
                        System.out.println("1. 면/부책 판단하기");
                        System.out.println("2. 손해사정");
                        System.out.println("3. 보상 지급하기");
                        System.out.println("4. 종료하기");
                        int submenu3 = Integer.parseInt(objReader.readLine());
                        switch (submenu3) {
                            case 1:
                                decideExemption(objReader);
                                break;
                            case 2:
                                damageAssessment(objReader);
                                break;
                            case 3:
                                reward(objReader);
                                break;
                            case 4:
                                System.out.println("프로그램을 종료합니다.");
                                flag = true;
                                break;
                        }
                        break;
                    case "x":
                        return;
                    default:
                        System.out.println("Invalid Choice !!!");
                }
                if (flag == true) break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void checkEmployee() {   // 직원의 유형(보험개발, 영업, 보상)별로 접속할 수 있는 메인 메뉴를 분리해주는 목적
        System.out.println("****************** CHECK EMPLOYEE *******************");
        System.out.println("접근하고자 하는 부서의 파트를 입력하세요.");
        System.out.println("1. 보험 개발 파트");
        System.out.println("2. 보험 영업 파트");
        System.out.println("3. 보험 보상 파트");
        System.out.println("x. EXIT");
    }

    // 보험 설계 로직
    private static void designInsurance(BufferedReader objReader) throws IOException {
        InsuranceList insuranceList = new InsuranceListImpl();
        Insurance insurance = new Insurance();
        System.out.println("======= 보험 설계를 시작하겠습니다. =======");
        System.out.println("설계하고자 하는 보험의 유형을 선택해주세요.");
        System.out.println("1. 자동차 보험");
        System.out.println("2. 운전자 보험");
        System.out.println("3. 건물 화재보험");
        String insuranceType = objReader.readLine().trim();
        switch (insuranceType) {
            case "1":
                insurance.setInsuranceType("car");
                break;
            case "2":
                insurance.setInsuranceType("driver");
                break;
            case "3":
                insurance.setInsuranceType("building");
                break;
        }

        System.out.println("보험명을 입력하세요.");
        insurance.setInsuranceName(objReader.readLine());
        System.out.println("보험 설명을 입력하세요.");
        insurance.setContents(objReader.readLine());
        System.out.println("보험 기본 가입비를 입력하세요.");
        insurance.setInsuranceCost(objReader.readLine());

        // 보험 ID부여는 추후 DB처리에서 뭔가 자동화되어야 하지 않을까?
        System.out.println("보험 ID를 입력하세요.");
        int insuranceID = Integer.parseInt(objReader.readLine());
        insurance.setInsuranceID(insuranceID);

        Coverage hcoverage = new Coverage();
        hcoverage.setCoverageCondition("high");
        System.out.println("사고위험 정도가 높은 경우(HIGH) 보장 내용을 입력하세요.");
        hcoverage.setCoverageContent(objReader.readLine());
        System.out.println("사고위험 정도가 높은 경우(HIGH) 최대 보장 금액을 입력하세요.");
        hcoverage.setCoverageCost(Integer.parseInt(objReader.readLine()));

        hcoverage.setInsuranceID(insuranceID);
        // ID처리 마찬가지로 DB 단계에서 자동 부여되어야 하지않을까?
        System.out.println("보장(Coverage)ID를 입력하세요.");
        int hcoverageID = Integer.parseInt(objReader.readLine());
        hcoverage.setInsuranceID(hcoverageID);
        insurance.setM_hcoverage(hcoverage);

        Coverage mcoverage = new Coverage();
        mcoverage.setCoverageCondition("middle");
        System.out.println("사고위험 정도가 중간인 경우(MIDDLE) 보장 내용을 입력하세요.");
        mcoverage.setCoverageContent(objReader.readLine());
        System.out.println("사고위험 정도가 중간인 경우(MIDDLE) 최대 보장 금액을 입력하세요.");
        mcoverage.setCoverageCost(Integer.parseInt(objReader.readLine()));

        mcoverage.setInsuranceID(insuranceID);
        // ID처리 마찬가지로 DB 단계에서 자동 부여되어야 하지않을까?
        System.out.println("보장(Coverage)ID를 입력하세요.");
        int mcoverageID = Integer.parseInt(objReader.readLine());
        mcoverage.setInsuranceID(mcoverageID);
        insurance.setM_mcoverage(mcoverage);

        Coverage lcoverage = new Coverage();
        lcoverage.setCoverageCondition("low");
        System.out.println("사고위험 정도가 낮은 경우(LOW) 보장 내용을 입력하세요.");
        lcoverage.setCoverageContent(objReader.readLine());
        System.out.println("사고위험 정도가 낮은 경우(LOW) 최대 보장 금액을 입력하세요.");
        lcoverage.setCoverageCost(Integer.parseInt(objReader.readLine()));

        lcoverage.setInsuranceID(insuranceID);
        // ID처리 마찬가지로 DB 단계에서 자동 부여되어야 하지않을까?
        System.out.println("보장(Coverage)ID를 입력하세요.");
        int lcoverageID = Integer.parseInt(objReader.readLine());
        lcoverage.setInsuranceID(lcoverageID);
        insurance.setM_lcoverage(lcoverage);

        insuranceList.add(insurance);
        System.out.println("보험 설계가 완료되었습니다.");
    }

    private static void approveInsurance(BufferedReader objReader) throws IOException {
        // 인가가 되지 않은 보험 목록 로딩 로딩하고 보여주는 로직 필요 (DAO)
        // ~~~ ...
        ArrayList<Insurance> insurances = insuranceList.getInsuranceList();
        System.out.println("***** 보험 목록 *****");
        for (Insurance insurance : insurances) {
            System.out.println(insurance.getInsuranceID() + " " + insurance.getInsuranceName());
        }

        System.out.println("상품 인가를 진행할 보험 ID를 입력하세요.");
        Insurance insurance = insuranceList.search(Integer.parseInt(objReader.readLine()));
        while (insurance == null) {
            System.out.println("존재하지 않는 보험ID를 입력하셨습니다. 다시 입력하세요.");
            insurance = insuranceList.search(Integer.parseInt(objReader.readLine()));
        }

        System.out.println("===== 보험 정보 =====");
        System.out.println("보험 ID : " + insurance.getInsuranceID());
        System.out.println("보험명 : " + insurance.getInsuranceName());
        System.out.println("보험유형 : " + insurance.getInsuranceType());
        System.out.println("보험 기본 가입비 : " + insurance.getInsuranceCost());
        System.out.println("보험내용 : " + insurance.getContents());
        System.out.println("상 - 보장내용 : " + insurance.getM_hcoverage().getCoverageContent());
        System.out.println("상 - 최대보장금액 : " + insurance.getM_hcoverage().getCoverageCost());
        System.out.println("중 - 보장내용 : " + insurance.getM_mcoverage().getCoverageContent());
        System.out.println("중 - 최대보장금액 : " + insurance.getM_mcoverage().getCoverageCost());
        System.out.println("하 - 보장내용 : " + insurance.getM_lcoverage().getCoverageContent());
        System.out.println("하 - 최대보장금액 : " + insurance.getM_lcoverage().getCoverageCost());
        System.out.println("상품 인가를 승인하시려면 [1], 거절하시려면 [2]를 입력하세요");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date time = new Date();
        Approve approve = new Approve();

        switch (Integer.parseInt(objReader.readLine())) {
            case 1:
                approve.setInsuranceID(insurance.getInsuranceID());
                approve.setApproved(1);
                approve.setPermissionDate(format.format(time));
                System.out.println(format.format(time) + "시각에" + insurance.getInsuranceName() + "보험이 인가 승인되었습니다.");
                insurance.setM_approve(approve);
                insuranceList.delete(insurance.getInsuranceID());
                // 승인 처리된 리스트 dao 저장 로직 필요
                // ...
                break;
            case 2:
                System.out.println("상품 인가를 거절하셨습니다.");
                approve.setInsuranceID(insurance.getInsuranceID());
                approve.setApproved(0);
                approve.setPermissionDate(format.format(time));
                System.out.println("거절 사유를 입력해주세요.");
                approve.setPermissionRefuse(objReader.readLine());
                System.out.println("보험의 문제점을 입력해주세요.");
                approve.setInsuranceProblem(objReader.readLine());
                System.out.println(format.format(time) + "에 " + insurance.getInsuranceName() + "보험 인가를 거부하셨습니다.");
                insurance.setM_approve(approve);
                insuranceList.delete(insurance.getInsuranceID());
                // 거절 처리된 리스트 dao 저장 로직 필요
                // ...
                break;
        }
    }

    private static void manageInsurance(BufferedReader objReader) throws IOException {
        System.out.println("===== 보험 목록 =====");
        ArrayList<Insurance> insurances = insuranceList.getInsuranceList();
        for (Insurance insurance : insurances) {
            System.out.println(insurance.getInsuranceID() + " " + insurance.getInsuranceName());
        }

        System.out.println("판매실적표를 작성할 보험의 ID를 입력하세요.");
        Insurance insurance = insuranceList.search(Integer.parseInt(objReader.readLine()));
        while (insurance == null) {
            System.out.println("존재하지 않는 보험 ID를 입력하셨습니다. 다시 입력하세요.");
            insurance = insuranceList.search(Integer.parseInt(objReader.readLine()));
        }

        System.out.println("----- 보험정보 -----");
        System.out.println("보험 ID : " + insurance.getInsuranceID());
        System.out.println("보험명 : " + insurance.getInsuranceName());
        System.out.println("보험유형 : " + insurance.getInsuranceType());
        System.out.println("보험 기본 가입비 : " + insurance.getInsuranceCost());
        System.out.println("보험내용 : " + insurance.getContents());
        System.out.println("상 - 보장내용 : " + insurance.getM_hcoverage().getCoverageContent());
        System.out.println("상 - 최대보장금액 : " + insurance.getM_hcoverage().getCoverageCost());
        System.out.println("중 - 보장내용 : " + insurance.getM_mcoverage().getCoverageContent());
        System.out.println("중 - 최대보장금액 : " + insurance.getM_mcoverage().getCoverageCost());
        System.out.println("하 - 보장내용 : " + insurance.getM_lcoverage().getCoverageContent());
        System.out.println("하 - 최대보장금액 : " + insurance.getM_lcoverage().getCoverageCost());
        System.out.println("판매 실적표 작성을 진행하시려면 [1], 취소하시려면 [2]를 입력하세요");

        switch (Integer.parseInt(objReader.readLine())) {
            case 1:
                if (insurance.getM_SaleRecord().getInsuranceID() != 0) {
                    System.out.println("이미 기록된 판매실적표가 있습니다.");
                    System.out.println("--------- 판매실적표 ---------");
                    System.out.println("목표 개수 : " + insurance.getM_SaleRecord().getGoalCnt());
                    System.out.println("달성 개수 : " + insurance.getM_SaleRecord().getSaleCnt());
                    System.out.println("달성율 : " + ((double) insurance.getM_SaleRecord().getSaleCnt()
                            / (double) insurance.getM_SaleRecord().getGoalCnt()) * 100);
                } else {
                    SaleRecord saleRecord = new SaleRecord();
                    System.out.println("해당 보험의 목표 개수를 입력하세요.");
                    saleRecord.setGoalCnt(Integer.parseInt(objReader.readLine()));
                    System.out.println("해당 보험의 판매 가수를 입력하세요.");
                    saleRecord.setSaleCnt(Integer.parseInt(objReader.readLine()));
                    System.out.println("입력한 판매실적표는 다음과 같습니다. 맞으면 [1], 틀리면 [2]를 입력하세요.");
                    System.out.println("--------- 판매실적표 ---------");
                    System.out.println("목표 개수 : " + saleRecord.getGoalCnt());
                    System.out.println("달성 개수 : " + saleRecord.getSaleCnt());
                    System.out.println("달성율 : " + ((double) saleRecord.getSaleCnt() / (double) saleRecord.getSaleCnt()) * 100);

                    if (Integer.parseInt(objReader.readLine()) == 1) {
                        saleRecord.setInsuranceID(insurance.getInsuranceID());
                        insurance.setM_SaleRecord(saleRecord);
                        System.out.println("판매실적표 작성이 완료되었습니다.");
                    }
                }
                break;
            case 2:
                System.out.println("판매실적표 작성이 취소되었습니다.");
                break;
        }
    }


    // ================ 가의 수정 =====================================================
    private static void manageInsurancepremium(BufferedReader objReader) throws IOException {
        CustomerList customerList = new CustomerListImpl();
        Customer customer = new Customer();
        System.out.println("======= 고객 관리 페이지에 진입했습니다. =======");
        System.out.println("고객 관리 유형 중 원하는 유형을 선택해주세요.");
        System.out.println("1. 보험료 관리");
        System.out.println("2. 보험료 미납 고객 관리하기");
        String managepreium = objReader.readLine().trim();
        switch (managepreium) {
            case "1":
                List<Customer> customers = customerList.getList();
                System.out.println("고객명 / 가입 상품명 / 보험료 납부 여부");
                for (int i = 0; i < customers.size(); i++) {
                    Customer selectedcustomer = customers.get(i);
                    //가입 상품명을 보험 ID로 할지 가입 상품명을 string으로 둘지
                    //System.out.println((i+1) + ". " + customer.getCustomerID() + " " + customer.insuranceID() + " " + (customer.getManageArrears() ? "Y" : "N"));
                    System.out.println((i + 1) + ". " + customer.getCustomerID() + " " + (customer.getManageArrears() ? "Y" : "N"));
                }
                System.out.println("보험료 납부일 공지할 고객을 선택해주세요");
                String selectedCustomer = objReader.readLine().trim();
                Customer selectedcustomer = customers.get(Integer.parseInt(selectedCustomer) - 1);
                System.out.println("해당 고객에게 보험료 납부일 관련 문자 및 이메일을 전송하시겠습니까?");
                System.out.println("1. 네   2. 아니오");
                String notificationOption = objReader.readLine().trim();
                if (notificationOption.equals("1")) {
                    System.out.println("정상적으로 알림을 전송하였습니다.");
                    System.out.println("1. 프로그램 종료   2. 보험료 미납 고객 관리 페이지로 이동");
                    String selectedOption = objReader.readLine().trim();
                    switch (selectedOption) {
                        case "1":
                            System.exit(0);
                        case "2":
                            manageArrearsCustomer(objReader);
                            return;
                    }
                } else
                    System.out.println("해당 고객의 보험료 관리가 정상적으로 취소되었습니다! 재진행을 원하신다면 처음부터 진행해주세요!");
                manageInsurancepremium(objReader);

            case "2":
                manageArrearsCustomer(objReader);

        }
    }

    //보험료 독촉
    private static void manageArrearsCustomer(BufferedReader objReader) throws IOException {
        System.out.println("======보험료 미납 고객 관리하기======");
        System.out.println("고객명 / 보험료 납부 여부 / 미납 횟수 / 블랙리스트 여부");

        // 고객 리스트에서 보험료 미납 고객만 뽑기
        CustomerList customerList = new CustomerListImpl();
        List<Customer> arrearsCustomers = customerList.getList().stream()
                .filter(customer -> !customer.getManageArrears())
                .collect(Collectors.toList());

        // 필터링된 고객 리스트를 뽑기
        for (int i = 0; i < arrearsCustomers.size(); i++) {
            Customer customer = arrearsCustomers.get(i);
            System.out.println((i + 1) + ". " + customer.getCustomerID() + " / " + customer.getManageArrears()
                    + " / " + customer.getArrearsCount() + " / " + customer.isBlackList());
        }

        System.out.println("선택할 고객의 번호를 입력해주세요:");
        int selectedCustomerIdx = Integer.parseInt(objReader.readLine().trim()) - 1;

        // 선택된 고객의 보험료 미납 횟수가 3회 이상이라면 문자 전송
        Customer selectedCustomer = arrearsCustomers.get(selectedCustomerIdx);
        if (selectedCustomer.getArrearsCount() >= 3) {
            System.out.println("보험료 미납 횟수가 3회 이상인 고객입니다.");
            System.out.println("문자를 전송하시겠습니까?");
            System.out.println("1. 네");
            System.out.println("2. 아니오");

            String selectedOption = objReader.readLine().trim();
            switch (selectedOption) {
                case "1":
                    System.out.println("문자가 전송되었습니다!");
                    System.out.println("상담 내용을 작성해주세요:");
                    String counseling = objReader.readLine().trim();
                    //selectedCustomer.addCounselingRecord(counseling);
                    System.out.println("상담 내용이 저장되었습니다: " + counseling);
                    break;
                case "2":
                    break;
            }
        }

        System.out.println("블랙리스트에 등록하시겠습니까?");
        System.out.println("1. 네");
        System.out.println("2. 아니오");

        String selectedOption = objReader.readLine().trim();
        switch (selectedOption) {
            case "1":
                selectedCustomer.setBlackList(true);
                System.out.println("블랙리스트에 등록되었습니다!");
                break;
            case "2":
                System.out.println("해당 고객의 블랙리스트 등록이 정상적으로 취소되었습니다! 재진행을 원하신다면 처음부터 진행해주세요!");
                manageInsurancepremium(objReader);
                break;
        }
    }
    // ================ 가의 수정 =====================================================


    // ================ 승연 수정 =======================================================
//잠재 고객 관리 로직
    private static void consult(BufferedReader objReader) {
//    	boolean consultFlag=true; //닫기를 할 때까지 프로세스 반복할 경우 추가
//    	while(consultFlag) {
        //잠재고객 선택
        ArrayList<PCustomer> customers = pCustomerList.getCustomerList();
        System.out.println("======= 잠재 고객 관리 =======");
        System.out.println("관리할 잠재 고객의 ID를 선택하세요.");
        for (PCustomer customer : customers) {
            System.out.println(customer.getPCustomerID() + " " + customer.getCustomerName() + " " + customer.getPhoneNumber() + " " + customer.getDate());
        }
        int selectionPCustomer = 0;//상담을 진행할 고객 ID
        int submenu = 0;//서브메뉴
        try {
            while (true) {//<상담을 진행한다> 버튼과 아이디 재입력
                selectionPCustomer = Integer.parseInt(objReader.readLine());
                System.out.println("1. 상담을 진행한다.");
                System.out.println("2. 아이디를 바꾼다.");
                submenu = Integer.parseInt(objReader.readLine());
                if (submenu == 1) {
                    break;
                } else {
                    System.out.println("상담할 고객 아이디 재입력을 진행합니다.");
                }
            }
            //상담 진행
            PCustomer pCustomer = pCustomerList.search(selectionPCustomer);
            System.out.println(pCustomer.getCustomerName() + " " + pCustomer.getPhoneNumber() + " " + "현재 상담 진행중.");
            System.out.println("1. 상담 종료");
            System.out.println("2. 고객 부재");
//        		System.out.println("3. 보험 가입"); 없어진 요소
            submenu = Integer.parseInt(objReader.readLine());
            switch (submenu) {
                //상담 이력 저장, PCustomer->Customer로 바꿈
                case 1:
                    System.out.println("상담 이력을 저장합니다.");
                    Customer customer = new Customer();
                    customer.setCustomerID(pCustomer.getPCustomerID());
                    customer.setCustomerName(pCustomer.getCustomerName());
                    customer.setDate(pCustomer.getDate());
                    customer.setPhoneNumber(pCustomer.getPhoneNumber());
                    while (true) {//<저장하기> 버튼과 내용 재입력
                        System.out.println("고객의 주민번호를 입력하세요.");
                        customer.setCustomerNumber(objReader.readLine());
                        System.out.println("고객의 성별을 입력하세요.");
                        customer.setSex(objReader.readLine());
                        System.out.println("고객의 직업을 입력하세요.");
                        customer.setJob(objReader.readLine());
                        System.out.println("상담 이력을 입력하세요.");
                        customer.setConsultContext(objReader.readLine());
                        System.out.println("저장하시겠습니까? 1-예 2-아니오");
                        submenu = Integer.parseInt(objReader.readLine());
                        if (submenu == 1) {
                            ////테스트용 임시정보입력
                            Insurance insurance = new Insurance();
                            insurance.setInsuranceName("보험1");
                            Contract contract = new Contract();
                            contract.setCustomer(customer);
                            contract.setInsurance(insurance);
                            contractList.add(contract);
                            ////
                            customerList.add(customer);
                            System.out.println("상담 내용을 정상적으로 저장하였습니다!");
                            break;
                        } else if (submenu == 2) {
                            System.out.println("상담 내용 저장 재입력을 진행합니다.");
                        }
                    }
                    break; //저장 확인까지 되어야(while) 종료
                //고객 부재인 경우
                case 2:
                    System.out.println("고객이 부재중입니다.");
                    System.out.println("작업중인 직원의 이름을 입력하세요.");
                    String employeeName = objReader.readLine();
                    System.out.println("안녕하세요. 신동아화재 직원 " + employeeName + "입니다. " +
                            "고객님께 꼭 필요하실 보험 상품이 출시되어 안내차 연락 드립니다. 상담을 원하실 경우 1588-3333으로 연락 부탁드립니다.");
                    System.out.println("알림을 전송하시겠습니까? 1-예 2-아니오");
                    submenu = Integer.parseInt(objReader.readLine());
                    if (submenu == 1) {
                        System.out.println("알림이 성공적으로 전송되었습니다!");
                        break;
                    }
                    // 아니오를 눌렀을 경우 처음으로 돌아가는 로직>이렇게 되면 닫기 할 때까지 프로세스를 반복하도록 해야함.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//    	} //닫기를 할 때까지 프로세스 반복할 경우 추가
        System.out.println("잠재 고객 관리가 완료되었습니다.");
    }

    //고객 관리 로직
    private static void manageCustomer(BufferedReader objReader) {
        boolean manageCustomerFlag = true;
        while (manageCustomerFlag) {
            try {
                //고객 선택
                ArrayList<Customer> customers = customerList.getCustomerList();
                System.out.println("======= 고객 관리 =======");
                //여기 관리를 그만두고 나가게 해야 할 거 같음!! 시나리오엔 없으나 추가 필요
                System.out.println("관리할 고객의 이름을 선택하세요.");
                //ID가 아니라 이름을 선택하도록 되어 있음.. 계약을 진행한 직원 정보는 없어도 되나?
                //각 고객과 계약한 보험의 목록
                for (Customer customer : customers) {
                    ArrayList<Contract> contracts = contractList.searchByCustomer(customer.getCustomerID());
                    System.out.println("---" + customer.getCustomerName());
                    for (Contract contract : contracts) {
                        System.out.println(contract.getInsurance().getInsuranceName() + " " + contract.getDate());
                    }
                }
                //고객 선택(이름으로)
                String customerName = objReader.readLine();
                int customerIndex = 0;
                for (Customer cus : customers) {
                    if (cus.getCustomerName().equals(customerName)) {
                        customerIndex = customers.indexOf(cus);
                        break;
                    }
                }
                Customer customer = customers.get(customerIndex);
                //선택한 고객의 정보 표시
                System.out.println(customer.getCustomerNumber() + " " + customer.getCustomerName() + " "
                        + customer.getSex() + " " + customer.getJob() + " " + customer.getPhoneNumber() + " "
                        + customer.geteMail() + " " + customer.getAddress() + " " + customer.getDate());
                ArrayList<Contract> contracts = contractList.searchByCustomer(customer.getCustomerID());
                for (Contract contract : contracts) {
                    System.out.println(contract.getInsurance().getInsuranceName());
                }
                System.out.println("1-수정 2-나가기");
                int submenu = Integer.parseInt(objReader.readLine());
                switch (submenu) {
                    case 1: //수정 로직
                        while (true) {
                            Customer customertemp = new Customer();
                            System.out.println("수정 정보를 입력합니다.");
                            System.out.println("고객의 이름을 입력하세요.");
                            customertemp.setCustomerName(objReader.readLine());
                            System.out.println("고객의 직업을 입력하세요.");
                            customertemp.setJob(objReader.readLine());
                            System.out.println("고객의 전화번호를 입력하세요.");
                            customertemp.setPhoneNumber(objReader.readLine());
                            System.out.println("고객의 이메일을 입력하세요.");
                            customertemp.seteMail(objReader.readLine());
                            System.out.println("고객의 주소를 입력하세요.");
                            customertemp.setAddress(objReader.readLine());
                            System.out.println("1-저장 2-취소");
                            submenu = Integer.parseInt(objReader.readLine());
                            if (submenu == 1) {
                                System.out.println(customertemp.getCustomerName() + " " + customertemp.getJob() + " "
                                        + customertemp.getPhoneNumber() + " " + customertemp.geteMail() + " "
                                        + customertemp.getAddress());
                                System.out.println("해당 정보가 맞습니까? 1-확인 2-취소");
                                submenu = Integer.parseInt(objReader.readLine());
                                if (submenu == 1) {
                                    for (Accident accident : customer.getAccident()) {
                                        customertemp.setAccident(accident);
                                    }
                                    customertemp.setConsultContext(customer.getConsultContext());
                                    customertemp.setCustomerID(customer.getCustomerID());
                                    customertemp.setDate(customer.getDate());
                                    customertemp.setFileHref(customer.getFileHref());
                                    customertemp.setM_building(customer.getM_building());
                                    customertemp.setM_car(customer.getM_car());
                                    customertemp.setM_driver(customer.getM_driver());
                                    customertemp.setPCustomerID(customer.getPCustomerID());
                                    customertemp.setSex(customer.getSex());
                                    customers.set(customerIndex, customertemp);
                                    System.out.println("저장이 완료되었습니다.");
                                    break;
                                } else if (submenu == 2) {
                                    System.out.println("변경 사항이 저장되지 않습니다. 수정을 취소하시겠습니까?");
                                    System.out.println("1-예 2-아니오");
                                    submenu = Integer.parseInt(objReader.readLine());
                                    if (submenu == 1) {
                                        break;
                                    } else if (submenu == 2) {
                                        System.out.println("수정 정보 입력 화면으로 돌아갑니다.");
                                    }
                                }
                            } else if (submenu == 2) {
                                System.out.println("수정 정보 입력 화면으로 돌아갑니다.");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("관리할 고객 이름 선택 화면으로 돌아갑니다.");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // ================ 승연 수정 =======================================================


    // ================ 형근 수정 =======================================================
    private static void manageContract(BufferedReader objReader) throws IOException {
        //계약 관리하기

        Contract contract = new Contract();
        Insurance insurance = new Insurance();
        Customer customer = new Customer();

        //테스트 데이터
        contract.setContractID(70);
        contract.setDate("12");
        insurance.setInsuranceName("ee");
        contract.setInsurance(insurance);
        customer.setCustomerName("keun");
        customer.setCustomerNumber("14");
        insurance.setInsuranceCost("17");
        contract.setEndDate("16");
        customer.setAge("34");
        contract.setCustomer(customer);
        //테스트 데이터


        ContractList contractList = new ContractListImpl();
        contractList.add(contract);
        Employee employee = new Employee();



        System.out.println("------------계약 관리하기------------");


//해당 보험영업사원이 체결한 계약리스트 보여주기인데 해당 보험영업사원ID와 같은 것을 입력안해도 괜찮은가?


// 직원ID 입력 후 체결 계약리스트를 확인하는 경우
//        String semployeeID = objReader.readLine().trim();	//직원 ID입력
//        int employeeID = Integer.parseInt(semployeeID);
//        employee.setEmployeeID(employeeID);

//    	Contract contractss = contractList.searchByEmployee(employeeID);

//
//    	for(int i=0; i<contractList.getContractList().size(); i++) {
//    		System.out.println("계약 날짜: "+contractss.getDate()+"계약 ID: "+contractss.getContractID()+"보험 이름: "+contractss.getInsurance().getInsuranceName()+"고객 이름: "+contractss.getCustomer().getCustomerName());
//    	}



        //그냥 리스트 전체를 다 불러온 경우
        for(int i=0; i<contractList.getContractList().size();i++) {
            System.out.println("계약 날짜: "+contract.getDate()+"  계약 ID: "+contract.getContractID()+"  보험 이름: "+contract.getInsurance().getInsuranceName()+"  고객 이름: "+contract.getCustomer().getCustomerName());

        }
        System.out.println("관리하고자 하는 계약 ID를 입력하세요.");


        String scontractID = objReader.readLine().trim();	//계약 ID입력
        int contractID = Integer.parseInt(scontractID);
        contract.setContractID(contractID);

        while(true) { //BuildPath 4

            for(int i=0; i<contractList.getContractList().size();i++) {
                //시스템은 화면에 계약정보(계약 날짜, 보험명, 고객이름, 전화번호, 납입보험료, 만기일) 와 <신규 상품 체결>, <연장> 버튼을 보여준다.(A1)
                System.out.println("계약 날짜: "+contract.getDate()+"   보험명: "+contract.getInsurance().getInsuranceName()+"   고객 이름: "+contract.getCustomer().getCustomerName()+"   전화번호: "+contract.getCustomer().getCustomerNumber()+"   납입 보험료 :"+contract.getPrice()+"   만기일: "+contract.getEndDate());

            }


            System.out.println("1. 신규 상품 체결, 2. 연장");
            String button = objReader.readLine().trim();	//연장 입력


            if(button.equals("1")) {	//신규 상품 체결인 경우
                contract(objReader);	//계약 체결 usecase로 이동
                break;
            }
            else if(button.equals("2")) {	//연장 입력 경우

                if(contract==null) {	//<연장>버튼이 안눌렀을 경우 ?

                    System.out.println("현재 시스템 오류로 다시 시도하시기 바랍니다.");
                    System.out.println("1. 확인");
                    String okayButton = objReader.readLine().trim();	//확인 클릭
                    continue;	//buildPath4로 이동
                }
                else {
                    while(true) {	//buildPath5

                        System.out.println("1. 1년 연장, 2. 2년 연장, 3. 3년 연장 중 선택하세요.");

                        String syear = objReader.readLine().trim();	//연도 입력
                        if(syear.equals("1") || syear.equals("2") || syear.equals("3")) {

                            int year = Integer.parseInt(syear);		//연장 연도


                            String sendD = contract.getEndDate();	//만기일
                            int endD = Integer.parseInt(sendD);

                            int finalEndD = year+endD;	//최종 연장 연도

                            String sFinalEndD = Integer.toString(finalEndD);
                            contract.setEndDate(sFinalEndD);
                            System.out.println("만기일이"+syear+"추가되어 총:"+sFinalEndD+"입니다.");
                            break;	//종료

                        }
                        else { // 불가능한 갱신 연도를 입력하였을 경우. E2
                            System.out.println("해당 연도는 갱신 불가능한 연도 범위입니다. 다시 메뉴를 입력해주세요.");
                            System.out.println("1. 확인");

                            String okayButton = objReader.readLine().trim();	//확인 클릭


                            continue;	 //buildPath5로 이동

                        }
                    }
                }
            }
            break;
        }


    }


    private static void contract(BufferedReader objReader) throws IOException {
        //계약 체결하기

        System.out.println("------------계약 체결하기------------");


        InsuranceList insuranceList = new InsuranceListImpl();
        Insurance insurance = new Insurance();
        Contract contract = new Contract();
        CustomerList customerList = new CustomerListImpl();
        Customer customer = new Customer();
        Employee employee = new Employee();
        ContractList contractList = new ContractListImpl();

        //데이터
        customer.setCustomerID(0);
        customer.setCustomerName("keun");
        customer.setCustomerNumber("1232");
        customer.setConsultContext("aaa");
        customer.setDate("124");
        customer.setAddress("냠");
        customer.setJob("무직");
        customer.setSex("남");
        customer.setAge("34");
        employee.setName("wow");
        contract.setEmployee(employee);
        customerList.add(customer);
        contract.setContractID(1);
        insurance.setInsuranceID(5);
        insurance.setInsuranceName("ee");
        contract.setInsurance(insurance);
        contract.setPrice(333);
        contract.setEndDate("12");
        contractList.add(contract);
        insurance.setInsuranceID(0);
        insurance.setInsuranceName("보험1");
        insurance.setContract(contract);
        insurance.setInsuranceCost("120");
        insuranceList.add(insurance);


        while(true) {

            if(customerList.search(customer).getConsultContext()!=null) {
                Customer hasContextCustomer = customer;

                if(customerList.search(customer).getConsultContext()!=null) {
                    for(int i=0; i<customerList.getCustomerList().size();i++)
                        System.out.println("상담 날짜: "+hasContextCustomer.getDate()+"이름: "+hasContextCustomer.getCustomerName());
                }
                else {//상담 이력이 존재하는 고객이 없을 경우 A1
                    System.out.println("현재 보험 체결을 원하는 고객이 없습니다.");
                    break;	//종료면 break
                    //continue	다시 돌아가면
                }
            }
            System.out.println("이름을 입력하세요.");
            String customername = objReader.readLine().trim();	//Build Path 3: 고객 이름 입력

            customer.setCustomerName(customername);

            //BuildPath 4
            System.out.println("이름: "+customer.getCustomerName()+"  주민번호: "+customer.getCustomerNumber()+"  성별: "+customer.getSex()+"  주소: "+customer.getAddress()+"  현재 직업: "+customer.getJob()+"  상담 날짜: "+customer.getDate()+"  상담 내용:"+customer.getConsultContext());

            for(int i=0;i <insuranceList.getInsuranceList().size();i++) {
                System.out.println("보험 ID: "+insurance.getInsuranceID()+"  보험 이름: "+insurance.getInsuranceName()+"  만기일"+insurance.getContract().getEndDate()+"보험료: "+insurance.getInsuranceCost());

            }

            while(true) { //buildPath 5

                System.out.println("보험종류(ID), 보험 이름을 선택하고 만기일, 보험료를 입력하세요.");

                String sinsuranceID = objReader.readLine().trim();	// 보험의 종류(ID) 입력
                int insuranceID = Integer.parseInt(sinsuranceID);
                contract.setContractID(insuranceID);


                String insuranceName = objReader.readLine().trim();	//Build Path 5: 보험 이름 입력
                insurance.setInsuranceName(insuranceName);
                contract.setInsurance(insurance);

                String insuranceEndDate = objReader.readLine().trim();	//Build Path 5: 만기일 입력
                contract.setEndDate(insuranceEndDate);
                String sinsuranceCost = objReader.readLine().trim();	//Build Path 5: 보험료 입력
                int insuranceCost = Integer.parseInt(sinsuranceID);
                contract.setPrice(insuranceCost);

                if(sinsuranceID==null|| insuranceName==null || insuranceEndDate==null|| sinsuranceCost==null ) { //미입력 정보가 있는 경우. A3
                    System.out.println("입력되지 않은 정보가 있습니다.");
                    continue;
                }
                else {

                    System.out.println("해당 보험 계약을 체결하시겠습니까?");
                    System.out.println("1. 예 2. 보류");

                    String button  = objReader.readLine().trim();
                    if(button.equals("1")) {	//"예"를 누른 경우


                        if(contractList.add(contract)) {
                            customer.seteMail("직원 이름: "+contract.getEmployee().getName()+" "+"보험 종류: "+contract.getInsurance().getInsuranceID()+" "+"보험 이름: "+contract.getInsurance().getInsuranceName()+" "+"만기일: "+contract.getEndDate()+" "+"보험료: "+contract.getPrice());//이메일 전송
                            System.out.println("직원 이름: "+contract.getEmployee().getName()+" "+"보험 종류: "+contract.getInsurance().getInsuranceID()+" "+"보험 이름: "+contract.getInsurance().getInsuranceName()+" "+"만기일: "+contract.getEndDate()+" "+"보험료: "+contract.getPrice());
                            System.out.println("이메일을 전송을 완료하였습니다.");
                            break;
                        }
                        else {
                            System.out.println(" 메일 발송에 오류가 생겼습니다.");
                            break;	//종료

                        }


                    }else if(button.equals("2")) {	//"보류"를 누른 경우


                        System.out.println("보류하였습니다.");
                        System.out.println("고객 이름: "+customer.getCustomerName()+"고객 성별: "+customer.getSex()+" 고객 나이: "+customer.getAge());

                        continue;
                    }
                }
            }
            break;

        }


    }
    // ================ 형근 수정 =======================================================



    // =============== 선민 수정 ===========================================================
    private static void decideExemption(BufferedReader objReader) throws NumberFormatException, IOException {
        AccidentList accidentList = new AccidentListImpl();
        Accident accident = new Accident();
        Exemption exemption = new Exemption();
        ExemptionList exemptionList = new ExemptionListImpl();
        CustomerList customerList = new CustomerListImpl();
        Customer customer = new Customer();
        SiteInfo siteInfo = new SiteInfo();

        // 데이터
        customer.setCustomerID(0);
        customer.setCustomerName("CustomerName");
        customer.setCustomerNumber("CustomerNumber");
        customer.setConsultContext("ConsultContext");
        customer.setDate("Date");
        customer.setAddress("Address");
        customer.setJob("Job");
        customer.setSex("Sex");
        customer.setAge("Age");
        customerList.add(customer);

        accident.setAccidentComplete(0);
        accident.setAccidentDate("AccidentDate");
        accident.setAccidentPlace("AccidentPlace");
        accident.setAccidentSize(Accident.EAccidnetSize.EMiddle);
        accident.setAccidentTime("AccidentTime");
        accident.setCustomer(customer);
        accident.setM_siteInfo(siteInfo);
        accident.setJudgementComplete(0);
        accident.setAccidentID(0);
        accident.setAccidentName("AccidentName");
        accidentList.add(accident);

        // BP 2. 면/부책 심사 신청된 사고 정보(사고번호, 사고접수일, 사고위험정도(고, 중, 저), 심사여부(승인, 거절)) 목록과 하단에 <면/부책 심사하기> 버튼
        System.out.println("면/부책 심사하기");
        String list = "";
        if (accident.getJudgementComplete() == 0) { // 면부책 여부가 x인 것들만
            for (int i = 0; i < accidentList.getAccidentList().size(); i++) {
                list += accidentList.getAccidentList().get(i).getAccidentID() + " " + accidentList.getAccidentList().get(i).getAccidentDate()
                + " " + accidentList.getAccidentList().get(i).getAccidentSize() + " " + accidentList.getAccidentList().get(i).getAccidentComplete();
            }
        }
        System.out.println(list);

        // 4. 시스템은 사건 정보(사고일자, 사고시각, 사고위치, 사고위험정보(고, 중, 저), 현장정보(사건내용, 사건녹취록, 사진, 영상, 피해규모)를 보여주고, 하단에 면/부책 정보(면/부책 여부, 판단 사유, 참고자료, 관련법규) 입력창과 <확인> 버튼
        int select = Integer.parseInt(objReader.readLine());
        Accident selectedAccident = accidentList.search(select);
        System.out.println(selectedAccident.getAccidentDate() + " " + selectedAccident.getAccidentTime() + " "
                + selectedAccident.getAccidentSize() + " " + selectedAccident.getM_siteInfo());
        System.out.println("면/부책 여부를 입력하세요.");
        int judgementComplete = Integer.parseInt(objReader.readLine());
        accident.setJudgementComplete(judgementComplete);
        System.out.println("판단 사유를 입력하세요.");
        String reason = objReader.readLine();
        exemption.setReason(reason);
        System.out.println("참고자료를 입력하세요.");
        String subFile = objReader.readLine();
        exemption.setSubFile(subFile);
        // --> 파일 입력은 어떻게 할 것인지 --> 교수님 피드백대로 String으로 전환
        System.out.println("관련법규를 입력하세요.");
        String legacy = objReader.readLine();
        exemption.setLegacy(legacy);
        exemptionList.add(exemption);

        // 미입력 정보가 존재할 경우. A1
        // 1. 시스템은 "미입력 정보가 존재합니다"라는 메시지
        if (reason == null || legacy == null) {
            System.out.println("미입력정보가 존재합니다.");
        }

        // 6. 시스템은 면/부책 판단된 사건에 대해 고유번호를 부여하여 손해 사정 대기 목록에 추가하고 "손해조사 심사가 완료되었습니다"
        exemption.setExemptionID(exemptionList.getExemptionList().size() + 1);
        System.out.println("손해조사 심사가 완료되었습니다");
    }

    private static void damageAssessment(BufferedReader objReader) throws NumberFormatException, IOException {
        AccidentList accidentList = new AccidentListImpl();
        Accident accident = new Accident();
        Exemption exemption = new Exemption();
        ExemptionList exemptionList = new ExemptionListImpl();
        RewardInfo rewardInfo = new RewardInfo();
        CustomerList customerList = new CustomerListImpl();
        Customer customer = new Customer();
        SiteInfo siteInfo = new SiteInfo();

        // 데이터
        customer.setCustomerID(0);
        customer.setCustomerName("CustomerName");
        customer.setCustomerNumber("CustomerNumber");
        customer.setConsultContext("ConsultContext");
        customer.setDate("Date");
        customer.setAddress("Address");
        customer.setJob("Job");
        customer.setSex("Sex");
        customer.setAge("Age");
        customerList.add(customer);

        accident.setAccidentComplete(1);
        accident.setAccidentDate("AccidentDate");
        accident.setAccidentPlace("AccidentPlace");
        accident.setAccidentSize(Accident.EAccidnetSize.EMiddle);
        accident.setAccidentTime("AccidentTime");
        accident.setCustomer(customer);
        accident.setM_siteInfo(siteInfo);
        accident.setJudgementComplete(1);
        accident.setAccidentID(0);
        accident.setAccidentName("AccidentName");
        accidentList.add(accident);

        exemption.setCustomerID(0);
        exemption.setCustomer(customer);
        exemption.setExemptionID(0);
        exemption.setSubFile("subFile");
        exemption.setAccidentName("AccidentName");
        exemption.setAccidentSize(accident.getAccidentSize());
        exemption.setLegacy("legacy");
        exemption.setM_siteInfo(siteInfo);
        exemption.setReason("reason");
        exemption.setAccidentComplete(1);
        exemption.setAccidentDate(accident.getAccidentDate());
        exemption.setAccidentPlace(accident.getAccidentPlace());
        exemption.setJudgementComplete(1);
        exemptionList.add(exemption);

        //2. 시스템은 손해 사정 대기 목록(고유번호, 사고명, 사고일자)을 보여준다
        while (true) {
            // 면부책 여부가 o인 사고 목록 보기
            String list = "";
            if (accident.getJudgementComplete() == 1) {
                for (int i = 0; i < accidentList.getAccidentList().size(); i++) {
                    list += accidentList.getAccidentList().get(i).getAccidentID()
                            + " " + accidentList.getAccidentList().get(i).getAccidentName()
                            + " " + accidentList.getAccidentList().get(i).getAccidentDate();
                }
            }
            System.out.println(list);

            //3. 손해사정사는 손해사정 대기목록에서 1개의 사고를 클릭한다.
            int select = Integer.parseInt(objReader.readLine());

            //4. 시스템은 선택된 사고의 면/부책 정보(면/부책 여부, 판단사유, 참고자료, 관련법규)와 하단에 손해사정정보(지급액, 판단사유) 입력창과 <확인> 버튼을 보여준다.
            //5. 손해사정담당자는 면/부책 정보를 바탕으로 손해 사정 정보를 입력하고 <확인> 버튼을 누른다.
            Exemption selectedExemption = exemptionList.search(select);
            System.out.println(selectedExemption.getReason() + " " + selectedExemption.getSubFile() + selectedExemption.getLegacy());
            System.out.println("지급액");
            String payment = objReader.readLine();
            //미입력 정보가 존재할 경우. A2
            //1. 시스템은 "미입력정보가 존재합니다"라는 메시지가 담긴 알림창을 보여준다.
            if (payment == null) {
                System.out.println("미입력정보가 존재합니다");
                return;
            }
            rewardInfo.setPayment(payment);
            System.out.println("판단 사유");
            String assessReason = objReader.readLine();
            if (assessReason == null) {
                System.out.println("미입력정보가 존재합니다");
                return;
            }
            rewardInfo.setAssessReason(assessReason);

            //8. 시스템은 "손해 사정 내역을 등록하겠습니까?"라는 메시지를 보여주고, <네>, <아니요> 버튼을 보여준다
            System.out.println("손해사정을 등록하시겠습니까? 1. 네 2. 아니오");
            select = Integer.parseInt(objReader.readLine());
            if (select == 1) {
                // 손해사정내역 이메일 전송
                break;
            } else if (select == 2) {
                //<아니요> 버튼을 누른 경우. A1
                //1. 시스템은 "손해사정을 취소하였습니다"라는 메시지가 담긴 알림창을 보여준 뒤 Basic Path 2번으로 이동한다
                System.out.println("손해사정을 취소하였습니다.");
                continue;
            }
        }
    }

    private static void reward(BufferedReader objReader) throws NumberFormatException, IOException {
        AccidentList accidentList = new AccidentListImpl();
        Accident accident = new Accident();
        RewardInfo rewardInfo = new RewardInfo();

        // 데이터
        accident.setAccidentComplete(1);
        accident.setAccidentDate("AccidentDate");
        accident.setAccidentPlace("AccidentPlace");
        accident.setAccidentSize(Accident.EAccidnetSize.EMiddle);
        accident.setAccidentTime("AccidentTime");
        accident.setJudgementComplete(1);
        accident.setAccidentID(0);
        accident.setAccidentName("AccidentName");
        accident.setAccidentType("EBuilding");
        accidentList.add(accident);

        rewardInfo.setAccident(accident);
        rewardInfo.setPayment("payment");
        rewardInfo.setAssessReason("assessReason");

        //2. 시스템은 손해사정사에게 손해사정정보(지급액, 판단사유)와 <입금> 버튼을 보여준다
        //3. 손해사정사는 시스템의 <입금> 버튼을 누른다. (A1, A2, A3)
        //4. 시스템은 고객에게 “보상금이 입금되었습니다.”라는 이메일을 전송한다. (E1)
        System.out.println("입금 1. 예");
        int select = Integer.parseInt(objReader.readLine());
        if (select == 1) {
            if (accident.getAccidentType().equals("EBuilding")) {
                System.out.println("7일 이내에 실제 손해액(감가상각) 기준으로" +
                        " 주택 가재도구 복구 비용, 폐기물 운반 및 소각 비용에 대한 보험금을 지급 완료했습니다.");
            } else if (accident.getAccidentType().equals("EDriver")) {
                System.out.println("7일 이내에 대물보상-교환가액, 대체비용, 수리비, 대차료(비 사업용), 휴차료(사업용)" +
                        "/ 자기차량손해-사고발생 당시의 보험개발원이 정한 최근 차량기준가액, 가입금액 한도 내 차량 수리비에 대한 보험금을 지급 완료했습니다.");
            } else if (accident.getAccidentType().equals("ECar")) {
                System.out.println("3영업일 이내에 대인배상 I, II, 무보험차 상해-장례비, 위자료, 상실수익액, 위자료, 휴업손해, 치료관계비 " +
                        "/ 자기신체사고-가입금액에 따른 사망보험금, 가입금액에 따른 후유장애 또는 상해 등급별 한도금액 내 발생 보험금에 대한 보험금을 지급 완료했습니다.");
            }
        }
    }
    // ==================== 선민 수정 ============================================================




    // 클라이언트 서버 프로그래밍 강의에서 가져와서 남은 코드들~~~~~~~~~~~~~~ =================================================
    // (JUST 참고용)수업시간 진행 내용 .... Usecase 내용 토대로 그대로 가져와
    private static void registerCustomer(BufferedReader objReader) throws IOException {
        CustomerList customerList = new CustomerListImpl();
        Customer customer = new Customer();

        System.out.println("--------회원가입정보 입력하기---------");
        System.out.println("1. 회원 이름을 입력하세요: ");
        String customerName = objReader.readLine().trim();
        customer.setCustomerName(customerName);
        System.out.println("2. 회원 휴대번호를 입력하세요: ");
        String customerJob = objReader.readLine().trim();
        customer.setCustomerNumber(customerJob);
    }

//    private static void registrateCourse(ServerIF server, BufferedReader inputReader) throws IOException, LogFailException, NullDataException {
//        System.out.println("****************** Resistration *******************");
//        System.out.println("Course ID : ");
//        String courseId = inputReader.readLine().trim();
//        if (server.isRegisteredCourse(courseId)) {
//            String completedCourseList = server.getCompletedCourses(studentId);
//            ArrayList<String> needToTakeCourse = server.getneedToTakeCourseList(courseId);
//            if (server.checkTakeAllCourse(completedCourseList, needToTakeCourse)) {
//                if (server.Registrate(studentId, courseId)) System.out.println("SUCCESS");
//                else System.out.println("FAIL");
//            } else System.out.println("Need To Take Other Course");
//        } else System.out.println("Course Data Not Found");
//    }
//
//    private static boolean showLogin(ServerIF server, BufferedReader inputReader) throws IOException, NullDataException, LogFailException {
//        System.out.println("****************** LOGIN MENU *******************");
//        System.out.println("Student ID : ");
//        String inputId = inputReader.readLine().trim();
//        System.out.println("PW : ");
//        String inputpassword = inputReader.readLine().trim();
//        for (int i = 1; i < 5; i++) {
//            String loginToken = server.login(inputId, inputpassword);
//            if (loginToken == null || server.validateLoginToken(loginToken) == false) {
//                System.out.println("FAIL !! LOGIN CHANCE (" + i + "/5)");
//                System.out.println("Student ID : ");
//                inputId = inputReader.readLine().trim();
//                System.out.println("PW : ");
//                inputpassword = inputReader.readLine().trim();
//            } else if (server.validateLoginToken(loginToken)) {
//                System.out.println("Success");
//                studentId = inputId;
//                return true;
//            }
//        }
//        System.out.println("FAIL ! ReRun System");
//        return false;
//    }
//
//    private static void addCourse(ServerIF server, BufferedReader inputReader) throws IOException, RemoteException, LogFailException {
//        System.out.println("--------Course Info---------");
//        System.out.println("Course ID: ");
//        String courseId = inputReader.readLine().trim();
//        System.out.println("Professor Name: ");
//        String professorName = inputReader.readLine().trim();
//        System.out.println("Course Name: ");
//        String courseName = inputReader.readLine().trim();
//        System.out.println("NeedToTakeCourse List: ");
//        String courseList = inputReader.readLine().trim();
//        if (server.addCourse(courseId + " " + professorName + " " + courseName + " " + courseList))
//            System.out.println("SUCCESS");
//        else System.out.println("FAIL");
//    }
//
//
//    private static void deleteCourse(ServerIF server, BufferedReader inputReader) throws RemoteException, IOException, LogFailException {
//        System.out.println("Course ID: ");
//        if (server.deleteCourse(inputReader.readLine().trim())) System.out.println("Success");
//        else System.out.println("FAIL");
//    }
//
//    private static void deleteStudent(ServerIF server, BufferedReader inputReader) throws RemoteException, IOException, LogFailException {
//        System.out.println("Student ID: ");
//        if (server.deleteStudent(inputReader.readLine().trim())) System.out.println("Success");
//        else System.out.println("FAIL");
//    }
//
//    private static void showList(ArrayList<?> dataList) {
//        String list = "";
//        for (int i = 0; i < dataList.size(); i++) {
//            list += dataList.get(i) + "\n";
//        }
//        System.out.println(list);
//    }

}