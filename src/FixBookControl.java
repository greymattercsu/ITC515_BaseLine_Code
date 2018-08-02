/**
@author:Saurav Pradhan
@reviewer: Sanchay Gurung
@Mediator: Ashish Shrestha
@Scriber: Bijan Dhakal

*/

public class FixBookControl {
	
	private FixBookUI ui;

	//changed CONTROL_STATE to ControlState
	private enum ControlState { INITIALISED, 
	                            READY, 
	                            FIXING }; 

	private ControlState state; //changed CONTROL_STATE to ControlState
	
	private Library library; //changed l in librart to L for Library since it is class
	private Book currentBook; //changed b in book to B for Book since it is class


	public FixBookControl() {
		this.library = library.INSTANCE();
		state = ControlState.INITIALISED; //changed CONTROL_STATE to ControlState
	}
	
	
	public void setUI(FixBookUI ui) {

		//changed CONTROL_STATE to ControlState
		if (!state.equals(ControlState.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
		}	

		
		this.ui = ui;
		ui.setState(FixBookUI.UiState.READY); //changed UI_STATE to UiState
		state = ControlState.READY;	 //changed CONTROL_STATE to ControlState	
	}


	public void bookScanned(int bookId) {
		if (!state.equals(ControlState.READY)) {
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(bookId);
		
		if (currentBook == null) {
			ui.display("Invalid bookId");
			return;
		}


		if (!currentBook.Damaged()) {
			ui.display("\"Book has not been damaged");
			return;
		}

		ui.display(currentBook.toString());
		ui.setState(FixBookUI.UiState.FIXING); //changed UI_STATE to UiState
		state = ControlState.FIXING;  //changed CONTROL_STATE to ControlState		
	}


	public void fixBook(boolean fix) {

		//changed CONTROL_STATE to ControlState
		if (!state.equals(ControlState.FIXING)) {
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (fix) {
			library.repairBook(currentBook);
		}
		currentBook = null;
		ui.setState(FixBookUI.UiState.READY); //changed UI_STATE to UiState
		state = ControlState.READY; //changed CONTROL_STATE to ControlState		
	}

	
	public void scanningComplete() {

		//changed CONTROL_STATE to ControlState
		if (!state.equals(ControlState.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	

		ui.setState(FixBookUI.UiState.COMPLETED); //changed UI_STATE to UiState		
	}


}
