package it.polito.tdp.borders.model;

public class Border {
	
	int state1No;
	int state2No;
	public Border(int state1No, int state2No) {
		super();
		this.state1No = state1No;
		this.state2No = state2No;
	}
	public int getState1No() {
		return state1No;
	}
	public void setState1No(int state1No) {
		this.state1No = state1No;
	}
	public int getState2No() {
		return state2No;
	}
	public void setState2No(int state2No) {
		this.state2No = state2No;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state1No;
		result = prime * result + state2No;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		if (state1No != other.state1No)
			return false;
		if (state2No != other.state2No)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Border [state1No=" + state1No + ", state2No=" + state2No + "]";
	}
	
	
	

}
