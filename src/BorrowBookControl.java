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
	private Library library; // renamed variable L to library and change the library to Library
	private Member member; // renamed variable M to member and change the member to Member

	//changed formating for easier code reading
	//renamed the variable CONTROL_STATE to ControlState
	private enum ControlState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
								//changed as per the reveiwer's suggestion
	private ControlState state; 
	private List<Book> pending;		//renamed PENDING to pending
	private List<Loan> completed;	//renamed COMPLETED to completed
	private Book book; //renamed variable B to book and changed the b in book to Book
	
	//renamed all the instances of L to Library
	public BorrowBookControl() {
		this.library = library.INSTANCE();//renamed variable L to Library
		state = ControlState.INITIALISED;
	}
	

	public void setUI(BorrowBookUI ui) {
		//added curly braces
		if (!state.equals(ControlState.INITIALISED)) {
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(BorrowBookUI.UiState.READY); //changed UI_STATE to UiState
		state = ControlState.READY;		
	}

		
	public void Swiped(int memberId) {
		//added curly braces
		if (!state.equals(ControlState.READY)) {
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
		}	
		member = library.getMember(memberId); //renamed variable L to Library and changed M to member
		//renamed M to member
		if (member == null) {
			ui.display("Invalid memberId");
			return;
		}
		//renamed variable L to Library and renamed M to member
		if (library.memberCanBorrow(member)) {
			pending = new ArrayList<>();  //renamed PENDING to pending
			ui.setState(BorrowBookUI.UiState.SCANNING); //changed UI_STATE to UiState
			state = ControlState.SCANNING; 
		}else {
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UiState.RESTRICTED);  //changed UI_STATE to UiState
		} 		
	} 
	
	
	public void Scanned(int bookId) {	
		book = null; //renamed variable B to book
		if (!state.equals(ControlState.SCANNING)) {
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
		pending.add(book);//renamed variable B to book   //renamed PENDING to pending
		//changed the b in book to Book
		for (Book book : pending) {    //renamed PENDING to pending
			ui.display(book.toString());
		}
		//renamed variable L to Library and renamed M to member and renamed PENDING to pending
		if (library.loansRemainingForMember(member) - pending.size() == 0) {
			ui.display("Loan limit reached");
			Complete();
		}
	}
	
	
	public void Complete() {
		//renamed PENDING to pending
		if (pending.size() == 0) {
			cancel();
		}else {
			ui.display("\nFinal Borrowing List");

			for (book book : pending) {
				ui.display(book.toString()); //renamed variable B to book
			}
			completed = new ArrayList<loan>(); //renamed COMPLETED to completed
			ui.setState(BorrowBookUI.UiState.FINALISING); //changed UI_STATE to UiState
			state = ControlState.FINALISING;
		}
	}


	public void commitLoans() {
		if (!state.equals(ControlState.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}
		//renamed variable B to book and changed the b in book to Book
		for (Book book : pending) {

			//renamed variable L to Library, renamed M to member and renamed variable B to book
			loan loan = library.issueLoan(book, member); //renamed variable B to book
			completed.add(loan);			
		}
		ui.display("Completed Loan Slip");
		//changed the l in loan to Loan
		
		for (Loan loan : completed) {
			ui.display(loan.toString());
		}

		ui.setState(BorrowBookUI.UiState.COMPLETED); //changed UI_STATE to UiState
		state = ControlState.COMPLETED;
	}

	
	public void cancel() {
		ui.setState(BorrowBookUI.UiState.CANCELLED); //changed UI_STATE to UiState
		state = ControlState.CANCELLED;
	}
	
	
}
