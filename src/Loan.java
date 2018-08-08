
/**
@author:Bijan Dhakal
@reviewer: Saurav Pradhan
@Mediator: Sanchay Gurung
@Scriber: Ashish Shrestha

*/

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable { 	//changed class name loan to Loan
	
	public static enum loanState { CURRENT, OVER_DUE, DISCHARGED };		//change LOAN_STATE to loanState
	
	private int id;				//changed ID to id
	private Book book;			//changed type book to Book and and attribute name B to book 
	private Member member;		//changed type member to Member and and attribute name M to member 
	private Date date;			//changed attribute name D to date 
	private loanState state;	//changed attribute type LOAN_STATE to loanState

	
	public Loan(int loanId, Book book, Member member, Date dueDate) {	//change constructor loan to Loan and argument types book and member to Loan and Member
		this.id = loanId;				//change ID to id
		this.book = book;				//this.B to this.book
		this.member = member;			//this.M to this.member
		this.date = dueDate;			//this.D to this.date
		this.state = loanState.CURRENT;	//LOAN_STATE.CURRENT to loanState.CURRENT
	}

	
	public void checkOverDue() {
		if (state == loanState.CURRENT &&
			Calendar.getInstance().Date().after(date)) {		//change D to date
			this.state = loanState.OVER_DUE;					//change LOAN_STATE to loanState		
		}
	}

	
	public boolean isOverDue() {
		return state == loanState.OVER_DUE;				//change LOAN_STATE to loanState
	}

	
	public int getId() {					//change return type Integer to int
		return id;							//change ID to id
	}


	public Date getDueDate() {
		return D;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(ID).append("\n")
		  .append("  Borrower ").append(M.getId()).append(" : ")
		  .append(M.getLastName()).append(", ").append(M.getFirstName()).append("\n")
		  .append("  Book ").append(B.ID()).append(" : " )
		  .append(B.Title()).append("\n")
		  .append("  DueDate: ").append(sdf.format(D)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public member Member() {
		return M;
	}


	public book Book() {
		return B;
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
