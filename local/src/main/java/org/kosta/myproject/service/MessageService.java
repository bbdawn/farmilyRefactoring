package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.vo.MessageVO;

public interface MessageService {
	
	//나의 메시지 리스트 조회
	List<MessageVO> findMyMessageList(String id);

	//메시지 보내기
	void sendMessage(MessageVO mvo);
	
	//받은 메시지 조회
	List<MessageVO> getMessageList(String id);
	
	//보낸 메시지 조회
	List<MessageVO> sendMessageList(String id);	
	
	//메시지 상세보기
	MessageVO messageDetail(String messageNo);
	
	//메세지 확인시 messageCheck컬럼 update
	void updateCheckMessage(String messageNo);
	
	//미확인 메시지가 있는지 조회
	int countUncheckedMessage(String id);
	
}
