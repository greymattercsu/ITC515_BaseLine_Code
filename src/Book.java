
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
	private enum State { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private State state;
	
	
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
		this.state = State.AVAILABLE;
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
		return state == State.AVAILABLE;
	}

	/**
	@return true if book is on loan
	 */
	public boolean onLoan() {
		return state == State.ON_LOAN;
	}

	/**
	@return true if book is damaged
	 */
	public boolean damaged() {
		return state == State.DAMAGED;
	}

	/**
	borrow method
	change the state of book to on_loan if book was available else
	Display the message why book can not be borrowed
	 */
	public void borrow() {
		if (state.equals(State.AVAILABLE)) {
			state = State.ON_LOAN;
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
	public void returnBook(boolean isDamaged) {
		if (state.equals(State.ON_LOAN)) {
			if (isDamaged) {
				state = State.DAMAGED;
			}
			else {
				state = State.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

	/**
	repair method
	if book was damaged changed it's state to available
	else display message why book cannot be repair
	*/	
	public void repair() {
		if (state.equals(State.DAMAGED)) {
			state = State.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
