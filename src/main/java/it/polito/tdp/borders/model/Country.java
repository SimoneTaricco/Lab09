package it.polito.tdp.borders.model;

public class Country {
	
	private Integer ccode;
	private String stateAbb;
	private String stateName;
	
	public Country(Integer ccode, String stateAbb, String stateName) {
		super();
		this.ccode = ccode;
		this.stateAbb = stateAbb;
		this.stateName = stateName;
	}

	public Integer getCcode() {
		return ccode;
	}

	public void setCcode(Integer ccode) {
		this.ccode = ccode;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return stateName;
	}

	

}
