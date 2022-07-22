package org.kosta.myproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
	private int commentNo;
	private String commentDate;
	private String boardNo;
	private String id;
	private String commentContent ;

}
