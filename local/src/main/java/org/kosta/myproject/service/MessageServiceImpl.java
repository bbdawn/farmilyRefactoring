package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.mapper.MessageMapper;
import org.kosta.myproject.vo.MessageVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	
	private final MessageMapper messageMapper;
	
	@Override
	public List<MessageVO> findMyMessageList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(MessageVO mvo) {
		messageMapper.sendMessage(mvo);
	}

	@Override
	public List<MessageVO> getMessageList(String id) {
		return messageMapper.getMessageList(id);
	}

	@Override
	public List<MessageVO> sendMessageList(String id) {
		return messageMapper.sendMessageList(id);
	}

	@Override
	public MessageVO messageDetail(String messageNo) {
		return messageMapper.messageDetail(messageNo);
	}

	@Override
	public void updateCheckMessage(String messageNo) {
		messageMapper.updateCheckMessage(messageNo);
	}

	@Override
	public int countUncheckedMessage(String id) {
		return messageMapper.countUncheckedMessage(id);
	}

	

}
