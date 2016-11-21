package trimps.eid.zl.enums;

public enum ResultState {

	Success("0"), Fail("-1");

	private String state;

	ResultState(String state) {
		this.setState(state);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
