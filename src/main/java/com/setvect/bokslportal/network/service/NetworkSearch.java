package com.setvect.bokslportal.network.service;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NetworkSearch extends SearchListVo {
	private String searchTitle;

	public NetworkSearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
	}

}
