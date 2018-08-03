
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
	public String title() {
		return T;
	}


	/**
	@return true if book is available
	 */
	public boolean available() {
		return state == STATE.AVAILABLE;
	}

	/**
	@return true if book is on loan
	 */
	public boolean on_loan() {
		return state == STATE.ON_LOAN;
	}

	/**
	@return true if book is damaged
	 */
	public boolean damaged() {
		return state == STATE.DAMAGED;
	}

	/**
	borrow method
	change the state of book to on_loan if book was available else
	Display the message why book can not be borrowed
	 */
	public void borrow() {
		if (state.equals(STATE.AVAILABLE)) {
			state = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}

	/**
	return book method
	@param DAMAGED
	if book was on loan chnage the book state to wither damaged or availiable
	if book cannot be return display message why.
	 */
	public void returnBook(boolean DAMAGED) {
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