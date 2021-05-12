package it.polito.tdp.borders.model;

public class Adiacenza {
	
	private Integer state1no;
	private Integer state2no;
	
	public Adiacenza(Integer state1no, Integer state2no) {
		super();
		this.state1no = state1no;
		this.state2no = state2no;
	}

	public Integer getState1no() {
		return state1no;
	}

	public void setState1no(Integer state1no) {
		this.state1no = state1no;
	}

	public Integer getState2no() {
		return state2no;
	}

	public void setState2no(Integer state2no) {
		this.state2no = state2no;
	}
	

}
