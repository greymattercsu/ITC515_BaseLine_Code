/**
@author:Saurav Pradhan
@reviewer: Sanchay Gurung
@Mediator: Ashish Shrestha
@Scriber: Bijan Dhakal

*/

import java.util.ArrayList;
import java.util.List;


//this is the first iteration of our project.
public class BorrowBookControl {
	
	private BorrowBookUI ui;
	
	private library library; // renamed variable L to library
	private member member; // renamed variable M to member

	//changed formating for easier code reading
	private enum CONTROL_STATE { INITIALISED, 
		                         READY, 
		                         RESTRICTED, 
		                         SCANNING, 
		                         IDENTIFIED, 
		                         FINALISING, 
		                         COMPLETED, 
		                         CANCELLED };


	private CONTROL_STATE state; 
	
	private List<book> PENDING;
	private List<loan> COMPLETED;

	private book book; //renamed variable B to book
	
	//renamed all the instances of L to Library
	public BorrowBookControl() {


		this.library = library.INSTANCE();//renamed variable L to Library
		state = CONTROL_STATE.INITIALISED;

	}
	

	public void setUI(BorrowBookUI ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;


		ui.setState(BorrowBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}

		
	public void Swiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId); //renamed variable L to Library and changed M to member

		//renamed M to member
		if (member == null) {
			ui.display("Invalid memberId");
			return;
		}

		//renamed variable L to Library and renamed M to member
		if (library.memberCanBorrow(member)) {
			PENDING = new ArrayList<>();
			ui.setState(BorrowBookUI.UI_STATE.SCANNING);
			state = CONTROL_STATE.SCANNING; }
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UI_STATE.RESTRICTED); }}
	
	
	public void Scanned(int bookId) {
		
		book = null; //renamed variable B to book


		if (!state.equals(CONTROL_STATE.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}

		//renamed variable L to Library and B to book
		book = library.Book(bookId);


		//renamed variable B to book
		if (book == null) {
			ui.display("Invalid bookId");
			return;
		}


		//renamed variable B to book
		if (!book.Available()) {
			ui.display("Book cannot be borrowed");
			return;
		}


		PENDING.add(book);//renamed variable B to book


		for (book book : PENDING) {
			ui.display(book.toString());
		}

		//renamed variable L to Library and renamed M to member
		if (library.loansRemainingForMember(member) - PENDING.size() == 0) {
			ui.display("Loan limit reached");
			Complete();
		}
	}
	
	
	public void Complete() {


		if (PENDING.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (book book : PENDING) {
				ui.display(book.toString()); //renamed variable B to book
			}


			COMPLETED = new ArrayList<loan>();
			ui.setState(BorrowBookUI.UI_STATE.FINALISING);
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void commitLoans() {


		if (!state.equals(CONTROL_STATE.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}


		//renamed variable B to book
		for (book book : PENDING) {

			//renamed variable L to Library, renamed M to member and renamed variable B to book
			loan loan = library.issueLoan(book, member); //renamed variable B to book
			COMPLETED.add(loan);			
		}


		ui.display("Completed Loan Slip");


		for (loan loan : COMPLETED) {
			ui.display(loan.toString());
		}


		ui.setState(BorrowBookUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED;
	}

	
	public void cancel() {

		ui.setState(BorrowBookUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED;
	}
	
	
}
