package UI;

import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import employee.Employee;
import employee.EmployeeListImpl;
import insurance.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ISMain {
    private static InsuranceList insuranceList;

    public ISMain() {

    }

    public static void main(String[] args) throws IOException {
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


                        break;
                    case "3":   // 보험보상 파트
                        System.out.println("========== 보험 보상 메뉴 ==========");


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


    /// 변경
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
                    System.out.println("달성율 : " + ((double)saleRecord.getSaleCnt() / (double) saleRecord.getSaleCnt()) * 100);

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