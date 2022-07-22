package org.kosta.myproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.myproject.vo.MessageVO;

@Mapper
public interface MessageMapper {
	
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
	
	//메시지 삭제
	void deleteMessage(String messageNo);

}
