package com.cfe.response;

import org.codehaus.jackson.annotate.JsonIgnoreType;

@JsonIgnoreType
//2015-11-09
public class AbstractResponse {
	
	protected int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
