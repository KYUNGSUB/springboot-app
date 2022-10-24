package org.zerock.springboot.board.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.springboot.board.entity.Board;
import org.zerock.springboot.board.entity.Reply;

@SpringBootTest
public class ReplyRepositoryTests {
	@Autowired
    private ReplyRepository replyRepository;

//    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            //1부터 100까지의 임의의 번호
            long bno  = getRandomNumber();
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply......." +i)
                    .board(board)
                    .mid(getRandomNumber())
                    .build();
            replyRepository.save(reply);
        });
    }

    private long getRandomNumber() {
    	return (long)(Math.random() * 100) + 1;
    }
    
//    @Test
    public void readReply1() {
        Optional<Reply> result = replyRepository.findById(1L);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }
    
//    @Test
    public void testListByBoard() {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
        		Board.builder().bno(97L).build());
        replyList.forEach(reply -> System.out.println(reply));
    }
}