/**
@author:Bijan Dhakal
@reviewer: Saurav Pradhan
@Mediator: Sanchay Gurung
@Scriber: Ashish Shrestha
*/

import java.util.Scanner;


public class ReturnBookUI {

	public static enum UiState { INITIALISED, READY, INSPECTING, COMPLETED }; //renaming the UI_STATE enum varibale to UiState

	private ReturnBookControl control;
	private Scanner input;
	private UiState state; //renaming the UI_STATE enum varibale to UiState

	
	public ReturnBookUI(ReturnBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED; //renaming the UI_STATE enum varibale to UiState t
		control.setUI(this);
	}


	public void run() {		
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case INITIALISED:
				break;
				
			case READY:
				String bookString = input("Scan Book (<enter> completes): "); //using proper variable name bookString instead of bookStr
				if (bookString.length() == 0) { //using proper variable name bookString instead of bookStr
					control.scanningComplete();
				}
				else {
					try {
						int bookId = Integer.valueOf(bookString).intValue(); //using proper variable name bookString instead of bookStr
						control.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String answer = input("Is book damaged? (Y/N): "); //using proper variable name answer instead of abbreviation ans
				boolean isDamaged = false;
				if (answer.toUpperCase().equals("Y")) {	 //using proper variable name answer instead of abbreviation ans				
					isDamaged = true;
				}
				control.dischargeLoan(isDamaged);
			
			case COMPLETED:
				output("Return processing complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + state);			
			}
		}
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	
			
	public void display(Object object) {
		output(object);
	}
	
	public void setState(UiState state) { //renaming the UI_STATE enum varibale to UiState t
		this.state = state;
	}

	
}
