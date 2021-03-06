package com.swempire.web.board.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.swempire.web.board.model.BoardVO;
import com.swempire.web.board.model.Pagination;
import com.swempire.web.board.model.ReplyVO;
import com.swempire.web.board.model.Search;
import com.swempire.web.board.service.BoardService;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Inject
	private BoardService boardService;
	
	
	//(required = false)  반드시 화면에서 넘어올 필요는 없으며
	//(defaultValue="1") 데이터가 없을 경우 기본값으로 "1"을 셋팅 합니다
	@RequestMapping(value = "/getBoardList", method = RequestMethod.GET)
	public String getBoardList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range, @RequestParam(required = false, defaultValue = "title") String searchType
			, @RequestParam(required = false) String keyword
			,@ModelAttribute("search") Search search) throws Exception {
		
		//검색
		model.addAttribute("search", search);
		search.setSearchType(searchType);
		search.setKeyword(keyword);
		
		//전체 게시글 개수
		int listCnt = boardService.getBoardListCnt(search);
		
		//검색
		search.pageInfo(page, range, listCnt);

		//Pagination
		model.addAttribute("pagination", search);
		
		model.addAttribute("boardList", boardService.getBoardList(search));
		
		return "board/index";
	}

	@RequestMapping("/boardForm")
	public String boardForm(@ModelAttribute("boardVO") BoardVO vo, Model model) {

		return "board/boardForm";

	}
	
	
	//RedirectAttributes 를 사용하는 또 다른 이유는 브라우저의 '뒤로가기' 버튼에 대한 대응책
	//글을 쓰고 저장 버튼을 눌러 리스트 화면까지 보이는 단계를 생각해 보면 글쓰기화면 => 저장 단계(서버) => 리스트 화면
	//따라서 '뒤로가기' 버튼을 클릭하면 다시 한 번 '저장하기' 단계로 감
	//이 말은 일명 '게시물 도배'의 여지를 남겨 둘 수 있음.
	//이와 같은 일을 방지하기 위해 RedirectAttributes를 하게 되면 '저장단계' 를 지나 글쓰기 폼으로 돌아 감
	@RequestMapping(value = "/saveBoard", method = RequestMethod.POST)

	public String saveBoard(@ModelAttribute("boardVO") BoardVO boardVO,
			@RequestParam("mode") String mode, RedirectAttributes rttr) throws Exception {
		if (mode.equals("edit")) {
			boardService.updateBoard(boardVO);
		} else {
			boardService.insertBoard(boardVO);
		}
		return "redirect:/board/getBoardList";
	}

	
	
	@RequestMapping(value = "/getBoardContent", method = RequestMethod.GET)
	public String getBoardContent(Model model, @RequestParam("bid") int bid) throws Exception {

		model.addAttribute("boardContent", boardService.getBoardContent(bid));
		
		model.addAttribute("replyVO", new ReplyVO());
		
		return "board/boardContent";
	}
	
	
	
	@RequestMapping(value = "/editForm", method = RequestMethod.GET)
	public String editForm(@RequestParam("bid") int bid, @RequestParam("mode") String mode, Model model) throws Exception {

		model.addAttribute("boardContent", boardService.getBoardContent(bid));

		model.addAttribute("mode", mode);

		model.addAttribute("boardVO", new BoardVO());

		return "board/boardForm";
	}
	
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.GET)

	public String deleteBoard(RedirectAttributes rttr, @RequestParam("bid") int bid) throws Exception {

		boardService.deleteBoard(bid);

		return "redirect:/board/getBoardList";

	}
	
	//@ExceptionHandler 어노테이션 속성으로 사용된 RuntimeException 은 데이터베이스에처 발생하는 에러를 처리하기 위해 사용
	@ExceptionHandler(RuntimeException.class)
	public String exceptionHandler(Model model, Exception e, Logger logger){

	logger.info("exception : " + e.getMessage());

	model.addAttribute("exception", e);

	return "error/exception";

	}

}
