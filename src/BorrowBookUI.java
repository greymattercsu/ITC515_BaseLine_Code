/**
@author:Saurav Pradhan
@reviewer: Sanchay Gurung
@Mediator: Ashish Shrestha
@Scriber: Bijan Dhakal

*/

import java.util.Scanner;

//this is UI for BorrowBook (comment)
public class BorrowBookUI {
	
	//changed the formating of the constants
	//changed enum variable name from UI_STATE to UiState 
	public static enum UiState { INITIALISED,READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED};

	private BorrowBookControl control;
	private Scanner input;
	private UiState state;  //changed variable name from UI_STATE to UiState

	public BorrowBookUI(BorrowBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED;  //changed variable name from UI_STATE to UiState
		control.setUI(this);
	}

	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
	private void output(Object object) {
		System.out.println(object);
	}
	
	//changed variable name from UI_STATE to UiState		
	public void setState(UiState state) {
		this.state = state;
	}

	//changed the spacing of the line for the switch cases to match the formating convention of the java
	public void run() {
		output("Borrow Book Use Case UI\n");
		while (true) {
			switch (state) {			
				case CANCELLED:
					output("Borrowing Cancelled");
					return;
				//renamed all memberStr to memberString 
				case READY:
					String memberString = input("Swipe member card (press <enter> to cancel): ");//changed memberStr to memberString
					if (memberString.length() == 0) {
						control.cancel();
						break;
					}

					try {
						int memberId = Integer.valueOf(memberString).intValue();
						control.Swiped(memberId);
					}
					catch (NumberFormatException e) {
						output("Invalid Member Id");
					}

					break;

					
				case RESTRICTED:
					input("Press <any key> to cancel");
					control.cancel();
					break;
				
				//renamed all bookStr to bookString	
				case SCANNING:
					String bookString = input("Scan Book (<enter> completes): ");//changed bookStr to bookString
					if (bookString.length() == 0) {
						control.Complete();
						break;
					}

					try {
						int bookId = Integer.valueOf(bookString).intValue();
						control.Scanned(bookId);
						
					} catch (NumberFormatException e) {
						output("Invalid Book Id");
					}
					break;
				//renamed all ans to answer	
				case FINALISING:
					String answer = input("Commit loans? (Y/N): "); //changed ans to answer
					if (answer.toUpperCase().equals("N")) {
						control.cancel();
						
					} else {
						control.commitLoans();
						input("Press <any key> to complete ");
					}
					break;
				case COMPLETED:
					output("Borrowing Completed");
					return;
				default:
					output("Unhandled state");
					throw new RuntimeException("BorrowBookUI : unhandled state :" + state);			
			}
		}		
	}

	public void display(Object object) {
		output(object);		
	}
}
