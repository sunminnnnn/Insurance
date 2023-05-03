package UI;

import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import insurance.Insurance;
import insurance.InsuranceList;
import insurance.InsuranceListImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

public class ISMain {
    public static void main(String[] args) throws IOException {
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                printMenu();
                String sChoice = objReader.readLine().trim();
                switch (sChoice) {
                    case "1":
                        registerCustomer(objReader); // 될 수 있으면 레포트에 명사한 내용을 그대로 가져와서 메소드 명을 지어라.
                        break;
                    case "2":
                        designInsurance(objReader);
                        break;
                    case "3":
//						showList(server.getAllRegistrationData());
                        break;
                    case "4":
//						addStudent(server, objReader);
                        break;
                    case "5":
//						deleteStudent(server, objReader);
                        break;
                    case "6":
//						addCourse(server, objReader);
                        break;
                    case "7":
//						deleteCourse(server, objReader);
                        break;
                    case "8":
//						registrateCourse(server, objReader);
                        break;
                    case "x":
                        return;
                    default:
                        System.out.println("Invalid Choice !!!");
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // 시나리오대로 작성해오면 돼
    private static void printMenu() {
        System.out.println("****************** MENU *******************");
        System.out.println("1. 회원가입하기");
        System.out.println("2. 보험설계하기");
        System.out.println("3. List Registration");
        System.out.println("4. Add Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Add Courses");
        System.out.println("7. Delete Courses");
        System.out.println("8. Registrate");
        System.out.println("x. Exit");
    }

    // Usecase 내용 토대로 그대로 가져와
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

    private static void designInsurance(BufferedReader objReader) throws IOException {
        InsuranceList insuranceList = new InsuranceListImpl();
        Insurance insurance = new Insurance();

        System.out.println("--------보험 정보 입력하기---------");
        System.out.println("1. 보험 이름을 입력하세요: ");
        String insuranceName = objReader.readLine().trim();
        insurance.setInsuranceName(insuranceName);
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