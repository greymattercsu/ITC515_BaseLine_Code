
/*
@author:Ashish Shrestha
@reviewer: Bijan Dhakal
@Mediator: Saurav Pradhan
@Scriber: Sanchay Gurung

*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Member implements Serializable {  //capitalising the first letter of class member

	private String lName;  //renamed variable LN to lName
	private String fName;  //renamed variable name FN to fName
	private String email;  //renamed variable EM to email
	private int pNum;  //renamed variable PN to pNum
	private int id;  //renamed variable named ID to id
	private double fines;  //renamed variable named FINES to fines
	
	private Map<Integer, loan> LNS;

	
	public member(String lastName, String firstName, String email, int phoneNo, int id) {
		this.lName = lastName;
		this.fName = firstName;
		this.email = email;
		this.pNum = phoneNo;
		this.id = id;
		
		this.LNS = new HashMap<>();
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Member:  ").append(id).append("\n")
		  .append("  Name:  ").append(lName).append(", ").append(fName).append("\n")
		  .append("  Email: ").append(email).append("\n")
		  .append("  Phone: ").append(pNum)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", fines))
		  .append("\n");
		
		for (loan loan : LNS.values()) {
			sb.append(loan).append("\n");
		}		  
		return sb.toString();
	}

	
	public int getId() {
		return id;
	}

	
	public List<loan> getLoans() {
		return new ArrayList<loan>(LNS.values());
	}

	
	public int getNumberOfCurrentLoans() {
		return LNS.size();
	}

	
	public double getFinesOwed() {
		return fines;
	}

	
	public void takeOutLoan(loan loan) {
		if (!LNS.containsKey(loan.getId())) {
			LNS.put(loan.getId(), loan);
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getLastName() {
		return lName;
	}

	
	public String getFirstName() {
		return fName;
	}


	public void addFine(double fine) {
		fines += fine;
	}
	
	public double payFine(double amount) {
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > fines) {
			change = amount - fines;
			fines = 0;
		}
		else {
			fines -= amount;
		}
		return change;
	}


	public void dischargeLoan(loan loan) {
		if (LNS.containsKey(loan.getId())) {
			LNS.remove(loan.getId());
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}
