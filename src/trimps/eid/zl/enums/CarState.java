package trimps.eid.zl.enums;

public enum CarState {

	Create("0"), Freeze("1"), Unfreeze("2"), Revoke("3");

	private String state;

	CarState(String state) {
		this.setState(state);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
