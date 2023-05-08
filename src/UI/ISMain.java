package UI;

import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import employee.Employee;
import insurance.Insurance;
import insurance.InsuranceList;
import insurance.InsuranceListImpl;
import pCustomer.PCustomer;
import pCustomer.PCustomerList;
import pCustomer.PCustomerListImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;

import contract.Contract;
import contract.ContractList;
import contract.ContractListImpl;

public class ISMain {
    public static void main(String[] args) throws IOException {
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                printMenu();
                String sChoice = objReader.readLine().trim();
                switch (sChoice) {
                    case "1":
                    	manageContract(objReader);
                        break;
                    case "2":
                    	contract(objReader);
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
        System.out.println("1. 계약관리하기");
        System.out.println("2. 계약체결하기");
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
    
    private static void manageContract(BufferedReader objReader) throws IOException {

    
    
    	Contract contract = new Contract();
    	Insurance insurance = new Insurance();
    	Customer customer = new Customer();
    	contract.setContractID(0);
    	contract.setDate("12");
    	insurance.setInsuranceName("ee");
        contract.setInsurance(insurance);
        customer.setCustomerName("keun");
        customer.setCustomerNumber("14");
        insurance.setInsuranceCost("17");
        contract.setEndDate("16");
        customer.setCustomerAge("34");
        contract.setCustomer(customer);
       
        
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
        int contractID = Integer.parseInt(scontractID);		//계약 ID int타입으로 변경 
        contract.setContractID(contractID);					//contractID로 set 
//         contract = contractList.search(contractID);	//contractID로 리스트 찾기
        
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
					        System.out.println("만기일이 총:"+sFinalEndD+"입니다.");
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
    	
    	System.out.println("------------계약 체결하기------------");

    	
    	InsuranceList insuranceList = new InsuranceListImpl();
    	Insurance insurance = new Insurance();
    	Contract contract = new Contract();
    	CustomerList customerList = new CustomerListImpl();
    	Customer customer = new Customer();
    	Employee employee = new Employee();
    	ArrayList<Insurance> insurances = insuranceList.getInsuranceList();
    	ContractList contractList = new ContractListImpl();
    	PCustomerList pCustomerList = new PCustomerListImpl();
    	PCustomer pCustomer = new  PCustomer();
    	

    	
    	PCustomer pcustomer=new PCustomer();
    	customer.setCustomerID(0);
    	customer.setCustomerName("keun");
    	customer.setCustomerNumber("1232");
    	customer.setConsultContext("aaa");
//        customer.setCustomerAge("34");
    	customer.setDate("124");
    	customer.setAddress("냠");
    	customer.setJob("무직");
    	customer.setSex("남");
    	customer.setCustomerAge("34");
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
        
//       


    	

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
    	System.out.println("이름을 입력하세요.");	//이름을 안보고 검색하기 힘들길래 추가 
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
		        	break;
	        	}	
	        	else {
	        		System.out.println(" 메일 발송에 오류가 생겼습니다.");
	        		break;	//종료
	        		
	        	}
	        	
	        	
	        }else if(button.equals("2")) {	//"보류"를 누른 경우

	        	
	        	System.out.println("보류하였습니다.");
	        	//고객의 나이를 contract.calculateCustomerAge()로 설정을 해야하는건지?
	        	System.out.println("고객 이름: "+customer.getCustomerName()+"고객 성별: "+customer.getSex()+" 고객 나이: "+customer.geCustomertAge());
	        	
	        	continue;
	        	}
        	}
        }
        	break;
        
    	}
    	  	
    	
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