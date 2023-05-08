package UI;

import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import employee.Employee;
import employee.EmployeeListImpl;
import insurance.Coverage;
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

import accident.Accident;
import contract.Contract;
import contract.ContractList;
import contract.ContractListImpl;

public class ISMain {

	private static PCustomerList pCustomerList;
	private static CustomerList customerList;
	private static ContractList contractList;
    public ISMain() {
    	pCustomerList=new PCustomerListImpl();
    	customerList=new CustomerListImpl();
    	contractList=new ContractListImpl();
    }

    public static void main(String[] args) throws IOException {
    	ISMain main=new ISMain();//생성
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
                    case "2":   // 보험영업및계약 파트
                        System.out.println("========== 보험 영업 및 계약 메뉴 ==========");
                        System.out.println("3. 잠재 고객 관리하기");
                        System.out.println("4. 고객 관리하기");
                        
                        //submenu 선언은 아예 위로 빼기 제안
                        submenu = Integer.parseInt(objReader.readLine());
                        switch(submenu) {
                        case 3:
                        	////테스트용 임시정보입력
                        	PCustomer pcustomer=new PCustomer();
                        	pcustomer.setPCustomerID(1);
                        	pcustomer.setDate("2023.05.08");
                        	pcustomer.setPhoneNumber("01058830056");
                        	pcustomer.setConsultContext("");
                        	pcustomer.setCustomerName("박승연");
                        	pCustomerList.add(pcustomer);
                        	////
                        	consult(objReader); break;
                        case 4: 
                        	manageCustomer(objReader); break;
                        }


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
    
    //잠재 고객 관리 로직
    private static void consult(BufferedReader objReader) {
//    	boolean consultFlag=true; //닫기를 할 때까지 프로세스 반복할 경우 추가
//    	while(consultFlag) {
    		//잠재고객 선택
        	ArrayList<PCustomer> customers=pCustomerList.getCustomerList();
        	System.out.println("======= 잠재 고객 관리 =======");
        	System.out.println("관리할 잠재 고객의 ID를 선택하세요.");
        	for(PCustomer customer:customers) {
        		System.out.println(customer.getPCustomerID()+" "+customer.getCustomerName()+" "+customer.getPhoneNumber()+" "+customer.getDate());
        	}
        	int selectionPCustomer = 0;//상담을 진행할 고객 ID
        	int submenu = 0;//서브메뉴
        	try {
        		while(true) {//<상담을 진행한다> 버튼과 아이디 재입력
        			selectionPCustomer = Integer.parseInt(objReader.readLine());
        			System.out.println("1. 상담을 진행한다.");
        			System.out.println("2. 아이디를 바꾼다.");
        			submenu=Integer.parseInt(objReader.readLine());
        			if(submenu==1) {break;}
        			else {System.out.println("상담할 고객 아이디 재입력을 진행합니다.");}
        		}
        		//상담 진행
        		PCustomer pCustomer=pCustomerList.search(selectionPCustomer);
        		System.out.println(pCustomer.getCustomerName()+" "+pCustomer.getPhoneNumber()+" "+"현재 상담 진행중.");
        		System.out.println("1. 상담 종료");
        		System.out.println("2. 고객 부재");
//        		System.out.println("3. 보험 가입"); 없어진 요소
        		submenu=Integer.parseInt(objReader.readLine());
        		        		switch(submenu) {
        		//상담 이력 저장, PCustomer->Customer로 바꿈
        		case 1: System.out.println("상담 이력을 저장합니다.");
        		Customer customer=new Customer();
        		customer.setCustomerID(pCustomer.getPCustomerID());
        		customer.setCustomerName(pCustomer.getCustomerName());
        		customer.setDate(pCustomer.getDate());
        		customer.setPhoneNumber(pCustomer.getPhoneNumber());
        		while(true) {//<저장하기> 버튼과 내용 재입력
            		System.out.println("고객의 주민번호를 입력하세요.");
            		customer.setCustomerNumber(objReader.readLine());
            		System.out.println("고객의 성별을 입력하세요.");
            		customer.setSex(objReader.readLine());
            		System.out.println("고객의 직업을 입력하세요.");
            		customer.setJob(objReader.readLine());
            		System.out.println("상담 이력을 입력하세요.");
            		customer.setConsultContext(objReader.readLine());
            		System.out.println("저장하시겠습니까? 1-예 2-아니오");
            		submenu=Integer.parseInt(objReader.readLine());
            		if(submenu==1) {
            			////테스트용 임시정보입력
            			Insurance insurance=new Insurance();
            			insurance.setInsuranceName("보험1");
            			Contract contract=new Contract();
            			contract.setCustomer(customer);
            			contract.setInsurance(insurance);
            			contractList.add(contract);
                    	////
            			customerList.add(customer);
            			System.out.println("상담 내용을 정상적으로 저장하였습니다!");
            			break;}
            		else if(submenu==2) {System.out.println("상담 내용 저장 재입력을 진행합니다.");}
        			}
        			break; //저장 확인까지 되어야(while) 종료
        		//고객 부재인 경우
        		case 2: 
        			System.out.println("고객이 부재중입니다.");
        			System.out.println("작업중인 직원의 이름을 입력하세요.");
        			String employeeName=objReader.readLine();
        			System.out.println("안녕하세요. 신동아화재 직원 "+employeeName+"입니다. 고객님께 꼭 필요하실 보험 상품이 출시되어 안내차 연락 드립니다. 상담을 원하실 경우 1588-3333으로 연락 부탁드립니다.");
        			System.out.println("알림을 전송하시겠습니까? 1-예 2-아니오");
        			submenu=Integer.parseInt(objReader.readLine());
        			if(submenu==1) {
        				System.out.println("알림이 성공적으로 전송되었습니다!"); break;
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
    	boolean manageCustomerFlag=true;
    	while(manageCustomerFlag) {
    		try {
	    		//고객 선택
	        	ArrayList<Customer> customers=customerList.getCustomerList();
	        	System.out.println("======= 고객 관리 =======");
	        	//여기 관리를 그만두고 나가게 해야 할 거 같음!! 시나리오엔 없으나 추가 필요
	        	System.out.println("관리할 고객의 이름을 선택하세요.");
	        	//ID가 아니라 이름을 선택하도록 되어 있음.. 계약을 진행한 직원 정보는 없어도 되나?
	        	//각 고객과 계약한 보험의 목록
	        	for(Customer customer:customers) {
	        		ArrayList<Contract> contracts=contractList.searchByCustomer(customer.getCustomerID());
	        		System.out.println("---"+customer.getCustomerName());
	        		for(Contract contract:contracts) {
	        			System.out.println(contract.getInsurance().getInsuranceName()+" "+contract.getDate());
	        		}
	        	}
	        	//고객 선택(이름으로)
    			String customerName=objReader.readLine();
    			int customerIndex=0;
    			for(Customer cus:customers) {
    				if(cus.getCustomerName().equals(customerName)) {
    					customerIndex=customers.indexOf(cus); break;}}
    			Customer customer=customers.get(customerIndex);
    			//선택한 고객의 정보 표시
    			System.out.println(customer.getCustomerNumber()+" "+customer.getCustomerName()+" "+customer.getSex()+" "+customer.getJob()+" "+customer.getPhoneNumber()+" "+customer.geteMail()+" "+customer.getAddress()+" "+customer.getDate());
    			ArrayList<Contract> contracts=contractList.searchByCustomer(customer.getCustomerID());
    			for(Contract contract:contracts) {
        			System.out.println(contract.getInsurance().getInsuranceName());
        		}
    			System.out.println("1-수정 2-나가기");
    			int submenu=Integer.parseInt(objReader.readLine());
    			switch(submenu) {
    			case 1: //수정 로직
    				while(true) {
    					Customer customertemp=new Customer();
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
        				submenu=Integer.parseInt(objReader.readLine());
        				if(submenu==1) {
        					System.out.println(customertemp.getCustomerName()+" "+customertemp.getJob()+" "+customertemp.getPhoneNumber()+" "+customertemp.geteMail()+" "+customertemp.getAddress());
        					System.out.println("해당 정보가 맞습니까? 1-확인 2-취소");
        					submenu=Integer.parseInt(objReader.readLine());
        					if(submenu==1) {
        						for(Accident accident:customer.getAccident()) {customertemp.setAccident(accident);}
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
        					}else if(submenu==2) {
        						System.out.println("변경 사항이 저장되지 않습니다. 수정을 취소하시겠습니까?");
        						System.out.println("1-예 2-아니오");
        						submenu=Integer.parseInt(objReader.readLine());
        						if(submenu==1) {break;}
        						else if(submenu==2) {System.out.println("수정 정보 입력 화면으로 돌아갑니다.");}
        					}
        				}
        				else if(submenu==2) {System.out.println("수정 정보 입력 화면으로 돌아갑니다.");}
    				}
    				break;
    			case 2: System.out.println("관리할 고객 이름 선택 화면으로 돌아갑니다.");
    			break;
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
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