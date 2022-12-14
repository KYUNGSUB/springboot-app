package org.zerock.springboot.club.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.springboot.club.entity.ClubMemberRole;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;

@SpringBootTest
public class ClubMemberTests {
	@Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
    public void insertDummies() {
        //1 - 80까지는 USER만 지정
        //81- 90까지는 USER,MANAGER
        //91- 100까지는 USER,MANAGER,ADMIN

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .mid(Long.valueOf(i))
                    .email("user"+i+"@zerock.org")
                    .name("사용자"+i)
                    .nickname("똘이" + i)
                    .fromSocial(false)
                    .roleSet(new HashSet<ClubMemberRole>())
                    .password(  passwordEncoder.encode("1111") )
                    .build();
    
            //default role
            member.addMemberRole(ClubMemberRole.USER);
            if(i > 80){
                member.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i > 90){
                member.addMemberRole(ClubMemberRole.ADMIN);
            }
            repository.save(member);
        });
    }
    
    @Test
    public void testRead() {
        Optional<Member> result = repository.findByEmail("user95@zerock.org", false);
        Member member = result.get();
        System.out.println(member);
    }
}