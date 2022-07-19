package org.kosta.myproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {
	private int messageNo;
	private String sender;
	private String receiver;
	private String content;
	private int messageCheck;
	private String sentDate;
}
