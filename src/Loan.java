
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
		return date;						//change D to date
	}
	
	
	public String toString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");		//change variable name sdf to simpleDateFormat

		StringBuilder stringBuilder = new StringBuilder();				//change sb to stringBuilder
		stringBuilder.append("Loan:  ").append(id).append("\n")			//change sb to stringBuilder and ID to id
		  .append("  Borrower ").append(member.getId()).append(" : ")	//change M to member
		  .append(member.getLastName()).append(", ").append(member.getFirstName()).append("\n")
		  .append("  Book ").append(book.ID()).append(" : " )			//change B to book
		  .append(book.Title()).append("\n")
		  .append("  DueDate: ").append(simpleDateFormat.format(date)).append("\n")		//change sdf to simpleDateFormat and D to date
		  .append("  State: ").append(state);		
		return stringBuilder.toString();
	}


	public Member member() {	//change return type member to Member and method name Member to member
		return member;			//change M to member
	}


	public Book book() {			//change return type book to Book and method name Book to book
		return book;				//change B to book
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
