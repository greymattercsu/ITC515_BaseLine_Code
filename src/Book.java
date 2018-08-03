
/**
@author:Bijan Dhakal
@reviewer: Saurav Pradhan
@Mediator: Sanchay Gurung
@Scriber: Ashish Shrestha

*/

import java.io.Serializable;


@SuppressWarnings("serial") //it informs the compiler that it should not produce the serialversionuid warning.
public class Book implements Serializable {
	
	private String T;
	private String A;
	private String C;
	private int ID;	
	private enum STATE { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private STATE state;
	
	
	/**
	constructor initialization
	
	@param author
	@param title
	@param callNo
	@param id
	 */
	public Book(String author, String title, String callNo, int id) {
		this.A = author;
		this.T = title;
		this.C = callNo;
		this.ID = id;
		this.state = STATE.AVAILABLE;
	}
	
	/**
	overrides toSting method
	@return String description of Book
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(ID).append("\n")
		  .append("  Title:  ").append(T).append("\n")
		  .append("  Author: ").append(A).append("\n")
		  .append("  CallNo: ").append(C).append("\n")
		  .append("  State:  ").append(state);
		
		return sb.toString();
	}

	/**
	Getter for ID
	@return ID
	 */
	public Integer ID() {
		return ID;
	}

	/**
	Getter for Title
	@return title
	 */
	public String Title() {
		return T;
	}


	
	public boolean Available() {
		return state == STATE.AVAILABLE;
	}

	
	public boolean On_loan() {
		return state == STATE.ON_LOAN;
	}

	
	public boolean Damaged() {
		return state == STATE.DAMAGED;
	}

	
	public void Borrow() {
		if (state.equals(STATE.AVAILABLE)) {
			state = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}


	public void Return(boolean DAMAGED) {
		if (state.equals(STATE.ON_LOAN)) {
			if (DAMAGED) {
				state = STATE.DAMAGED;
			}
			else {
				state = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

	
	public void Repair() {
		if (state.equals(STATE.DAMAGED)) {
			state = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
