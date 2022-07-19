package org.kosta.myproject.controller;

import java.util.List;

import org.kosta.myproject.service.MessageService;
import org.kosta.myproject.vo.MemberVO;
import org.kosta.myproject.vo.MessageVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {
	private final MessageService messageService;
	
	@RequestMapping("getMessageList")
	public String getMessageList(@AuthenticationPrincipal MemberVO membervo,Model model) {
		String id = membervo.getId();
		List<MessageVO> list = messageService.getMessageList(id);
		model.addAttribute("getMessageList", list);
		return "message/getMessageList";
	}
	
	@RequestMapping("sendMessageList")
	public String sendMessageList(@AuthenticationPrincipal MemberVO membervo,Model model) {
		String id = membervo.getId();
		List<MessageVO> list = messageService.sendMessageList(id);
		model.addAttribute("sendMessageList", list);
		return "message/sendMessageList";
	}
	
	@RequestMapping("messageDetail")
	public String messageDetail(@AuthenticationPrincipal MemberVO membervo,String messageNo,Model model) {
		MessageVO messageVO = messageService.messageDetail(messageNo);
		messageService.updateCheckMessage(messageNo);
		membervo.setCountUncheckedMessage(messageService.countUncheckedMessage(membervo.getId()));
		model.addAttribute("messageVO",messageVO);
		model.addAttribute("memberVO", membervo);
		return "message/messageDetail";
	}
	
	
	@GetMapping("sendMessage")
	@ResponseBody
	public String sendMessage(@AuthenticationPrincipal MemberVO membervo,String receiver,String content) {
		MessageVO mvo = new MessageVO();
		mvo.setSender(membervo.getId());
		mvo.setReceiver(receiver);
		mvo.setContent(content);
		messageService.sendMessage(mvo);
		return "메시지가 전송되었습니다";
	}
	
}
