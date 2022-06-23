package com.swempire.web.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.swempire.web.board.model.BoardVO;
import com.swempire.web.board.model.ReplyVO;
import com.swempire.web.board.model.Search;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	private SqlSession sqlSession;

	@Override
	public List<BoardVO> getBoardList(Search search) throws Exception {

		return sqlSession.selectList("com.swempire.web.board.boardMapper.getBoardList", search);

	}

	// 총 게시글 개수 확인
	@Override
	public int getBoardListCnt(Search search) throws Exception {
		return sqlSession.selectOne("com.swempire.web.board.boardMapper.getBoardListCnt", search);
	}

	@Override
	public BoardVO getBoardContent(int bid) throws Exception {

		return sqlSession.selectOne("com.swempire.web.board.boardMapper.getBoardContent", bid);

	}

	@Override

	public int insertBoard(BoardVO boardVO) throws Exception {

		return sqlSession.insert("com.swempire.web.board.boardMapper.insertBoard", boardVO);

	}

	@Override

	public int updateBoard(BoardVO boardVO) throws Exception {

		return sqlSession.update("com.swempire.web.board.boardMapper.updateBoard", boardVO);

	}

	@Override

	public int deleteBoard(int bid) throws Exception {

		return sqlSession.insert("com.swempire.web.board.boardMapper.deleteBoard", bid);

	}

	@Override

	public int updateViewCnt(int bid) throws Exception {

		return sqlSession.update("com.swempire.web.board.boardMapper.updateViewCnt", bid);

	}
	
	
	 // 댓글 리스트
		@Override
		public List<ReplyVO> getReplyList(int bid) throws Exception {
			return sqlSession.selectList("com.swempire.web.board.replyMapper.getReplyList", bid);
		}

		@Override
		public int saveReply(ReplyVO replyVO) throws Exception {
			return sqlSession.insert("com.swempire.web.board.replyMapper.saveReply", replyVO);
		}

		@Override
		public int updateReply(ReplyVO replyVO) throws Exception {
			return sqlSession.update("com.swempire.web.board.replyMapper.updateReply", replyVO);
		}

		@Override
		public int deleteReply(int rid) throws Exception {

			return sqlSession.delete("com.swempire.web.board.replyMapper.deleteReply", rid);
		}

}
