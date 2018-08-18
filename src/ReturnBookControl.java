/**
@author:Bijan Dhakal
@reviewer: Saurav Pradhan
@Mediator: Sanchay Gurung
@Scriber: Ashish Shrestha
*/

public class ReturnBookControl {

	private ReturnBookUI ui;
	private enum ControlState { INITIALISED, READY, INSPECTING }; // Renaming CONTROL_STATE to ControlSatte


	private ControlState state; // Renaming CONTROL_STATE to ControlSatte
	
	private Library library; // Captializing the first letter of class name during object declaration
	private Loan currentLoan; // Captializing the first letter of class name during object declaration


	public ReturnBookControl() {
		this.library = library.INSTANCE();
		state = ControlState.INITIALISED; // Renaming CONTROL_STATE to ControlSatte
	}
	

	public void setUI(ReturnBookUI ui) {
		if (!state.equals(ControlState.INITIALISED)) { //Renaming CONTROL_STATE to ControlSatte
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(ReturnBookUI.UiState.READY); // Renaming the variable UI_STATE to UiState
		state = ControlState.READY;	//Renaming CONTROL_STATE to ControlSatte	
	}


	public void bookScanned(int bookId) {
		if (!state.equals(ControlState.READY)) { //Renaming CONTROL_STATE to ControlSatte
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}	
		Book currentBook = library.Book(bookId); //changing book to Book to match the Class
		
		if (currentBook == null) {
			ui.display("Invalid Book Id");
			return;
		}
		if (!currentBook.onLoan()) { //changing on_loan method to onLoan to match from Book class
			ui.display("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByBookId(bookId);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue()) {
			overDueFine = library.calculateOverDueFine(currentLoan);
		}
		ui.display("Inspecting");
		ui.display(currentBook.toString());
		ui.display(currentLoan.toString());
		
		if (currentLoan.isOverDue()) {
			ui.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		}
		ui.setState(ReturnBookUI.UiState.INSPECTING); // Renaming the variable UI_STATE to UiState
		state = ControlState.INSPECTING; //Renaming CONTROL_STATE to ControlSatte		
	}


	public void scanningComplete() {
		if (!state.equals(ControlState.READY)) { //Renaming CONTROL_STATE to ControlSatte
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(ReturnBookUI.UiState.COMPLETED); // Renaming the variable UI_STATE to UiState		
	}

	
	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(ControlState.INSPECTING)) { //Renaming CONTROL_STATE to ControlSatte
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}	
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		ui.setState(ReturnBookUI.UiState.READY); // Renaming the variable UI_STATE to UiState
		state = ControlState.READY;	//Renaming CONTROL_STATE to ControlSatte			
	}


}
