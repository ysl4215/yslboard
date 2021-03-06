package com.swempire.web.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swempire.web.board.dao.BoardDAO;
import com.swempire.web.board.model.BoardVO;
import com.swempire.web.board.model.ReplyVO;
import com.swempire.web.board.model.Search;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO boardDAO;

	@Override
	public List<BoardVO> getBoardList(Search search) throws Exception {

		return boardDAO.getBoardList(search);
	}
	
	// 총 게시글 개수 확인
	@Override
	public int getBoardListCnt(Search search) throws Exception {
		return boardDAO.getBoardListCnt(search);
	}

	@Override
	public void insertBoard(BoardVO boardVO) throws Exception {

		boardDAO.insertBoard(boardVO);

	}

	// 트랜잭션이란 어떤 일련의 작업을 의미. 어떤 이련의 작업들은 모두 에러 없이 끝나야하며 만약 에러가 발생 한다면, 에러 발생
	// 이전 시점까지 작업되었던 내용은 모두 원상보구 되어야 함. 이렇게 데이터 무결성을 유치하기 위한 처리방법을 '트랜잭션 처리'라고 함.
	@Transactional
	@Override
	public BoardVO getBoardContent(int bid) throws Exception {
		BoardVO boardVO = new BoardVO();

		boardDAO.updateViewCnt(bid);

		boardVO = boardDAO.getBoardContent(bid);

		return boardVO;
	}

	@Override
	public void deleteBoard(int bid) throws Exception {
		boardDAO.deleteBoard(bid);

	}

	@Override
	public void updateBoard(BoardVO boardVO) throws Exception {
		boardDAO.updateBoard(boardVO);
	}
	
	// 댓글 리스트
		@Override
		public List<ReplyVO> getReplyList(int bid) throws Exception {
			return boardDAO.getReplyList(bid);
		}

		@Override
		public int saveReply(ReplyVO replyVO) throws Exception {
			return boardDAO.saveReply(replyVO);
		}

		@Override
		public int updateReply(ReplyVO replyVO) throws Exception {
			return boardDAO.updateReply(replyVO);
		}

		@Override
		public int deleteReply(int rid) throws Exception {
			return boardDAO.deleteReply(rid);
		}

}
