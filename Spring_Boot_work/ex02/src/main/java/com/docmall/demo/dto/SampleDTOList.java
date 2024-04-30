package com.docmall.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class SampleDTOList {
	private List<SampleDTO> list; // list[0], list[1]
	
	public SampleDTOList() {
		list = new ArrayList<>(); // 중요
	}

	public List<SampleDTO> getList() {
		return list;
	}

	public void setList(List<SampleDTO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SampleDTOList [list=" + list + "]";
	}
}
