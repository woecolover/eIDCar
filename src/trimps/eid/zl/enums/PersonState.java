package trimps.eid.zl.enums;

public enum PersonState {

	Normal("0"), Except("1");

	private String state;

	PersonState(String state) {
		this.setState(state);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
