/**
@author:Sanchay Gurung
@reviewer: Ashish Shrestha
@Mediator: Bijan Dhakal
@Scriber: Saurav Pradhan

*/

public class PayFineControl {
	
	private PayFineUI ui;
	private enum controlState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };			//changed enum name CONTROL_STATE to controlState
	private controlState state;							//changed type CONTROL_STATE to controlState
	
	private Library library;				//changed attribute type library to Library
	private Member member;					//changed attribute type member to Member


	public PayFineControl() {
		this.library = library.instance();	//changd INSTANCE() to instance()
		state = controlState.INITIALISED;	//changed CONTROL_STATE to controlState
	}
	
	
	public void setUI(PayFineUI ui) {
		if (!state.equals(controlState.INITIALISED)) {		//changed CONTROL_STATE to controlState
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(PayFineUI.uiState.READY);		//changed UI_STATE to uiState
		state = controlState.READY;					//changed CONTROL_STATE to controlState
	}


	public void cardSwiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		member = library.getMember(memberId);
		
		if (member == null) {
			ui.display("Invalid Member Id");
			return;
		}
		ui.display(member.toString());
		ui.setState(PayFineUI.UI_STATE.PAYING);
		state = CONTROL_STATE.PAYING;
	}
	
	
	public void cancel() {
		ui.setState(PayFineUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED;
	}


	public double payFine(double amount) {
		if (!state.equals(CONTROL_STATE.PAYING)) {
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		double change = member.payFine(amount);
		if (change > 0) {
			ui.display(String.format("Change: $%.2f", change));
		}
		ui.display(member.toString());
		ui.setState(PayFineUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED;
		return change;
	}
	


}
