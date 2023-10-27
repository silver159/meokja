package com.meokja.vo;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberList {
	
	ArrayList<MemberVO> list = new ArrayList<MemberVO>();
}
