/**
@author:Saurav Pradhan
@reviewer: Sanchay Gurung
@Mediator: Ashish Shrestha
@Scriber: Bijan Dhakal

*/

import java.util.Scanner;


public class FixBookUI {

	//changed UI_STATE to UiState to match the naming convention
	public static enum UiState { INITIALISED, 
		                          READY, 
		                          FIXING, 
		                          COMPLETED };

	private FixBookControl control;
	private Scanner input;
	private UiState state; //changed UI_STATE to UiState 

	
	public FixBookUI(FixBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED; //changed UI_STATE to UiState
		control.setUI(this);
	}


	//changed UI_STATE to UiState
	public void setState(UiState state) {
		this.state = state;
	}

	
	public void run() {
		output("Fix Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case READY:
				String bookStr = input("Scan Book (<enter> completes): ");
				if (bookStr.length() == 0) {
					control.scanningComplete();
				}
				else {
					try {
						int bookId = Integer.valueOf(bookStr).intValue();
						control.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}
				}
				break;	
				
			case FIXING:
				String ans = input("Fix Book? (Y/N) : ");
				boolean fix = false;
				if (ans.toUpperCase().equals("Y")) {
					fix = true;
				}
				control.fixBook(fix);
				break;
								
			case COMPLETED:
				output("Fixing process complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("FixBookUI : unhandled state :" + state);			
			
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
	
	
}
