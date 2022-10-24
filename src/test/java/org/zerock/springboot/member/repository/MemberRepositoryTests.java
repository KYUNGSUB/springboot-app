package org.zerock.springboot.member.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.springboot.member.entity.Member;

@SpringBootTest
public class MemberRepositoryTests {
	@Autowired
	private MemberRepository memberRepository;
	
//	@Test
	public void insertMembers() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder()
					.email("user" + i + "@zerock.org")
					.password("1111")
					.name("사용자" + i)
					.build();
			memberRepository.save(member);
		});
	}
	
//	@Test
    public void insertNicknameField() {
    	IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .mid(Long.valueOf(i))
                    .email("user"+i+"@zerock.org")
                    .password("1111")
                    .name("사용자"+i)
                    .nickname("똘이" + i)
                    .build();
            memberRepository.save(member);
        });
    }
}