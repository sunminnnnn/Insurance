package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import accident.Accident;
import accident.AccidentList;
import accident.AccidentListImpl;
import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import exemption.Exemption;
import exemption.ExemptionList;
import exemption.ExemptionListImpl;
import insurance.Coverage;
import insurance.Insurance;
import insurance.InsuranceList;
import insurance.InsuranceListImpl;
import reward.RewardInfo;

public class ISMain {

    public ISMain() {

    }

    public static void main(String[] args) throws IOException {
        boolean flag = false; // 프로그램 종료를 판단하는 flag 데이터.
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                checkEmployee(); // 직원의 유형(보험개발, 영업, 보상)별로 접속할 수 있는 메인 메뉴를 분리해주는 목적
                String sChoice = objReader.readLine().trim();
                switch (sChoice) {
                    case "1": // 보험개발
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
                                // approveInsurance();
                                break;
                            case 3:
                                System.out.println("******* 보험상품을 사후관리하는 페이지입니다 ********");
                                // manageInsurance();
                                break;
                            case 4:
                                System.out.println("프로그램을 종료합니다.");
                                flag = true;
                                break;
                        }
                        break;
                    case "2": // 보험영업및계약 파트
                        System.out.println("========== 보험 영업 및 계약 메뉴 ==========");

                        break;
                    case "3": // 보험보상 파트

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
                if (flag == true)
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void checkEmployee() { // 직원의 유형(보험개발, 영업, 보상)별로 접속할 수 있는 메인 메뉴를 분리해주는 목적
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

    private static void decideExemption(BufferedReader objReader) throws NumberFormatException, IOException {
        AccidentList accidentList = new AccidentListImpl();
        Accident accident = new Accident();

        // 사고 목록 보기
        System.out.println("======= 면/부책 판단하기를 시작하겠습니다. =======");
        System.out.println("판단하고자 하는 사고를 선택해주세요.");
        String list = "";
        if (accident.getJudgementComplete() == 1) { // 면부책 여부가 x인 것들만
            for (int i = 0; i < accidentList.getAccidentList().size(); i++) {
                list += accidentList.getAccidentList().get(i) + "\n";
            }
        }
        System.out.println(list);
        int select = Integer.parseInt(objReader.readLine());

        Accident selectedAccident = accidentList.search(select); // 하나의 사고만 보여줌
        // 사고일자, 사고시각, 사고위치, 사고위험정보(고, 중, 저), 현장정보
        System.out.println(selectedAccident.getAccidentDate() + " " + selectedAccident.getAccidentTime() + " "
                + selectedAccident.getAccidentSize() + " " + selectedAccident.getSiteInfo());

        // 면/부책 정보(면/부책 여부, 판단 사유, 참고자료, 관련법규) 입력창
        Exemption exemption = new Exemption();
        ExemptionList exemptionList = new ExemptionListImpl();

        System.out.println("면/부책 여부를 입력하세요.");
        int judgementComplete = Integer.parseInt(objReader.readLine());
        accident.setJudgementComplete(judgementComplete);

        System.out.println("판단 사유를 입력하세요.");
        String reason = objReader.readLine();
        exemption.setReason(reason);

        System.out.println("참고자료를 입력하세요.");
        // exemption.setSubFile(objReader.readLine());
        // --> 파일 입력은 어떻게 할 것인지

        System.out.println("관련법규를 입력하세요.");
        String legacy = objReader.readLine();
        exemption.setLegacy(legacy);

        if (reason == null || legacy == null) {
            System.out.println("미입력정보가 존재합니다.");
        }

        // 고유번호 부여
        exemption.setExemptionID(exemptionList.getExemptionList().size() + 1);
        System.out.println("손해조사 심사가 완료되었습니다");
    }

    private static void damageAssessment(BufferedReader objReader) throws NumberFormatException, IOException {
        AccidentList accidentList = new AccidentListImpl();
        Accident accident = new Accident();
        Exemption exemption = new Exemption();
        ExemptionList exemptionList = new ExemptionListImpl();
        RewardInfo rewardInfo = new RewardInfo();
        while (true) {
            // // 면부책 여부가 o인 사고 목록 보기
            System.out.println("======= 손해사정을 시작하겠습니다. =======");
            System.out.println("손해를 사정하고자 하는 사고를 선택해주세요.");
            String list = "";
            if (accident.getJudgementComplete() == 0) {
                for (int i = 0; i < accidentList.getAccidentList().size(); i++) {
                    list += accidentList.getAccidentList().get(i) + "\n";
                }
            }
            System.out.println(list);

            int select = Integer.parseInt(objReader.readLine());
            Exemption selectedExemption = exemptionList.search(select); // 하나의 사고만 보여줌

            // 면/부책 정보(면/부책 여부(?), 판단사유, 참고자료(?), 관련법규)
            System.out.println(selectedExemption.getReason() + " " + selectedExemption.getLegacy());

            // 손해사정정보(지급액, 판단사유) 입력창
            System.out.println("지급액을 입력하세요.");
            String payment = objReader.readLine();
            if (payment == null) {
                System.out.println("미입력정보가 존재합니다");
                return;
            }
            rewardInfo.setPayment(payment);

            System.out.println("판단 사유를 입력하세요.");
            String assessReason = objReader.readLine();
            if (assessReason == null) {
                System.out.println("미입력정보가 존재합니다");
                return;
            }
            rewardInfo.setAssessReason(assessReason);

            System.out.println("손해사정을 완료하시겠습니까? /n 1. 예 /n 2. 아니오");
            select = Integer.parseInt(objReader.readLine());
            if (select == 1) {
                // 손해사정내역 이메일 전송
                break;
            } else if (select == 2) {
                System.out.println("손해사정을 취소하였습니다.");
                continue;
            }
        }
    }
    private static void reward(BufferedReader objReader) throws NumberFormatException, IOException {
        System.out.println("입금하시겠습니까? /n 1. 예 /n 2. 아니오");
        int select = Integer.parseInt(objReader.readLine());
        if (select == 1) {
            // 보험 종류별로 출력문 다름
        } else if (select == 2) {
            System.out.println("보상금 지급을 취소하였습니다.");
        }
    }

}