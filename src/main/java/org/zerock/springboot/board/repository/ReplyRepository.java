package org.zerock.springboot.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.springboot.board.entity.Board;
import org.zerock.springboot.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	@Modifying
	@Query("delete from Reply r where r.board.bno =:bno ")
    void deleteByBno(@Param("bno") Long bno);

	// 게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);

}