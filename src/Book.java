
/**
@author:Bijan Dhakal
@reviewer: Saurav Pradhan
@Mediator: Sanchay Gurung
@Scriber: Ashish Shrestha

*/

import java.io.Serializable;


@SuppressWarnings("serial") 
public class Book implements Serializable {
	
	private String T;
	private String A;
	private String C;
	private int ID;	
	private enum State { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private State state;
	
	
	public Book(String author, String title, String callNo, int id) {
		this.A = author;
		this.T = title;
		this.C = callNo;
		this.ID = id;
		this.state = State.AVAILABLE;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(ID).append("\n")
		  .append("  Title:  ").append(T).append("\n")
		  .append("  Author: ").append(A).append("\n")
		  .append("  CallNo: ").append(C).append("\n")
		  .append("  State:  ").append(state);
		
		return sb.toString();
	}

	
	public Integer ID() {
		return ID;
	}

	
	public String title() {
		return T;
	}


	
	public boolean available() {
		return state == State.AVAILABLE;
	}

	
	public boolean onLoan() {
		return state == State.ON_LOAN;
	}


	public boolean damaged() {
		return state == State.DAMAGED;
	}

	
	public void borrow() {
		if (state.equals(State.AVAILABLE)) {
			state = State.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}

	
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

		
	public void repair() {
		if (state.equals(State.DAMAGED)) {
			state = State.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
