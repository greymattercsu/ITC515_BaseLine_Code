
/**
@author:Bijan Dhakal
@reviewer: Saurav Pradhan
@Mediator: Sanchay Gurung
@Scriber: Ashish Shrestha

*/

import java.io.Serializable;


@SuppressWarnings("serial") 
public class Book implements Serializable { // Renaming book to Book
	
	private String title; // changing variable T to title
	private String author; // changign variable A to author
	private String callNo; // changign variable C to callNo
	private int id;	// changing ID to id
	private enum State { AVAILABLE, ON_LOAN, DAMAGED, RESERVED }; // renaming STATE to state
	private State state; // renaming type STATE to state
	
	
	public Book(String author, String title, String callNo, int id) {
		this.author = author; // changing variable this.T to this.title
		this.title = title; // changign variable this.A to this.author
		this.callNo = callNo; // changign variable this.C to this.callNo
		this.id = id; // changing this.ID to this.id
		this.state = State.AVAILABLE; // Renaming enum STATE to state
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

	
	public String title() { // renaming the Title to title
		return T;
	}


	
	public boolean available() { // renaming Available to available
		return state == State.AVAILABLE; // Renaming enum variable STATE to state
	}

	
	public boolean onLoan() { // renaming on_loan to onLoan
		return state == State.ON_LOAN; // Renaming enum variable STATE to state
	}


	public boolean damaged() { // renaming Damaged to damaged
		return state == State.DAMAGED;
	}

	
	public void borrow() { // renaimng Borrow to borrow
		if (state.equals(State.AVAILABLE)) {
			state = State.ON_LOAN; // Renaming enum variable STATE to state
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}

	
	public void returnBook(boolean isDamaged) { // renaming Return to returnBook
		if (state.equals(State.ON_LOAN)) { // Renaming enum variable STATE to state
			if (isDamaged) {
				state = State.DAMAGED; // Renaming enum variable STATE to state
			}
			else {
				state = State.AVAILABLE; // Renaming enum variable STATE to state
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

		
	public void repair() { // renaming Repair to repair
		if (state.equals(State.DAMAGED)) { // Renaming enum variable STATE to state
			state = State.AVAILABLE; // Renaming enum variable STATE to state
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
