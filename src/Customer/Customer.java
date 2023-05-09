package customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Customer extends FamilyHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	private String address;
	private int customerID;
	private String customerName;
	private String job;
	private int pnumber;
	private int SSN;
	private ArrayList<Customer> customerList;
	// 만기계약대상자/미납대상자/부활대상자 리스트
	private ArrayList<Customer> expiredContracts;
	private ArrayList<Customer> unpaidCustomers;
	private ArrayList<Customer> resurrectCandidates;

	public Customer(String inputString) {
		super(inputString);
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.address = stringTokenizer.nextToken();
		this.customerName = stringTokenizer.nextToken();
		this.job = stringTokenizer.nextToken();

	}
// 1. deleteCustomer
	public boolean deleteCustomer(int customerID) {
		Customer customerToRemove = null;
		for (Customer customer : customerList) {
			if (customer.getCustomerID() == customerID) {
				customerToRemove = customer;
				break;
			}
		}
		if (customerToRemove != null) {
			customerList.remove(customerToRemove);
			return true;
		} else {
			return false;
		}
	}

	// 만기계약대상자/미납대상자/부활대상자 enum
	public enum TargetType {
		EXPIRED_CONTRACTS, UNPAID_CUSTOMERS, RESURRECT_CANDIDATES
	}
//2. exceptCustomer
	public boolean exceptCustomer(TargetType targetType, int customerID) {
		Customer customerToRemove = null;
		List<Customer> targetList = getTargetList(targetType);

		// 대상 목록에서 지정된 ID를 가진 고객 찾기
		for (Customer customer : targetList) {
			if (customer.getCustomerID() == customerID) {
				customerToRemove = customer;
				break;
			}
		}

		// 고객을 찾았는지 확인
		if (customerToRemove != null) {
			// 대상 목록에서 고객 제거
			targetList.remove(customerToRemove);
			// 고객이 성공적으로 제거
			return true;
		} else {
			// 고객 없음
			return false;
		}
	}

	// 대상 유형을 기준으로 대상 목록을 결정하는 메서드.
	private List<Customer> getTargetList(TargetType targetType) {
		switch (targetType) {
		case EXPIRED_CONTRACTS:
			return expiredContracts;
		case UNPAID_CUSTOMERS:
			return unpaidCustomers;
		case RESURRECT_CANDIDATES:
			return resurrectCandidates;
		default:
			throw new IllegalArgumentException("Invalid target type: " + targetType);
		}
	}
//3. retrieveMaturityCustomer
	public ArrayList<Customer> retrieveMaturityCustomer() {
		ArrayList<Customer> maturityCustomers = new ArrayList<>();
		// 만기 계약이 있는 고객을 목록에 추가
		for (Customer customer : customerList) {
			if (customer.hasMaturityContract()) {
				maturityCustomers.add(customer);
			}
		}

		return maturityCustomers;
	}

	private boolean hasMaturityContract() {
		return false;
	}

//4. retrieveNonPaymentCustomer
	public ArrayList<Customer> retrieveNonPaymentCustomer() {
		ArrayList<Customer> nonPaymentCustomers = new ArrayList<>();
		// 미결제 고객을 목록에 추가
		for (Customer customer : customerList) {
			if (customer.hasOutstandingPayment()) {
				nonPaymentCustomers.add(customer);
			}
		}

		return nonPaymentCustomers;
	}

	private boolean hasOutstandingPayment() {
		return false;
	}
//5. retrieveResurrectionCustomer

    public ArrayList<Customer> retrieveResurrectionCustomer() {
        ArrayList<Customer> resurrectionCustomers = new ArrayList<>();
     // 목록에 부활 대상 고객 추가
        for (Customer customer : customerList) {
            if (customer.isEligibleForResurrection()) {
                resurrectionCustomers.add(customer);
            }
        }

        return resurrectionCustomers;
    }

	private boolean isEligibleForResurrection() {
		return false;
	}
//6. retrieveCustomer
	public Customer retrieveCustomer(int customerID) {
		// 지정된 ID를 가진 고객 찾기
		for (Customer customer : customerList) {
			if (customer.getCustomerID() == customerID) {
				return customer; 
			}
		}
		// Customer가 없을 때
		return null;
	}
	 public boolean updateCustomer(Customer updatedCustomer) {
	        for (Customer customer : customerList) {
	            if (customer.getCustomerID() == updatedCustomer.getCustomerID()) {
	                // customer 정보를 업데이트한다.
	                customer.setCustomerName(updatedCustomer.getCustomerName());
	                customer.setPnumber(updatedCustomer.getPnumber());
	                //성공적으로 업데이트
	                return true;
	            }
	        }
//customer가 없거나 update가 성공적이지 않음.
	        return false; 
	    }
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getPnumber() {
		return pnumber;
	}

	public void setPnumber(int pnumber) {
		this.pnumber = pnumber;
	}

	public int getSSN() {
		return SSN;
	}

	public void setSSN(int sSN) {
		SSN = sSN;
	}

}
