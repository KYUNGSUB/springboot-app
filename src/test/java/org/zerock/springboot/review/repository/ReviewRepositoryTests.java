package org.zerock.springboot.review.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;
import org.zerock.springboot.review.entity.Movie;
import org.zerock.springboot.review.entity.Review;

@SpringBootTest
public class ReviewRepositoryTests {
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private MemberRepository memberRepository;
	
//	@Test
	public void insertMovieReviews() {
		IntStream.rangeClosed(1, 200).forEach(i -> {
			//영화 번호
            Long mno = (long)(Math.random()*100) + 1;
            //리뷰어 번호
            Long mid  =  ((long)(Math.random()*100) + 1 );
            Member member = Member.builder().mid(mid).build();
            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()* 5) + 1)
                    .text("이 영화에 대한 느낌..."+i)
                    .build();
            reviewRepository.save(movieReview);
		});
	}
	
//	@Test
    public void testGetMovieReviews() {
        Movie movie = Movie.builder().mno(99L).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        result.forEach(movieReview -> {
            System.out.print(movieReview.getReviewnum());
            System.out.print("\t"+movieReview.getGrade());
            System.out.print("\t"+movieReview.getText());
            System.out.print("\t"+movieReview.getMember().getEmail());
            System.out.println("---------------------------");
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {
        Long mid = 7L; //Member의 mid
        Member member = Member.builder().mid(mid).build();

        // 순서 주의
        // 7번 사용자가 작성한 댓글 삭제
        // 7번 사용자가 작성한 게시글에 대한 댓글 삭제
        // 7번 사용자가 작성한 게시글 삭제
        reviewRepository.deleteByMember(member);
//        memberRepository.deleteById(mid);
    }
}