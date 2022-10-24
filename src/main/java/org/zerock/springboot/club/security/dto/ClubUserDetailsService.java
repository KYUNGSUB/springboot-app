package org.zerock.springboot.club.security.dto;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("ClubUserDetailsService loadUserByUsername " + username);
        Optional<Member> result = memberRepository.findByEmail(username, false);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check User Email or from Social ");
        }

        Member member = result.get();
        log.info("-----------------------------");
        log.info(member);
        
        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );
        clubAuthMember.setName(member.getName());
        clubAuthMember.setFromSocial(member.isFromSocial());
//        return null;
        return clubAuthMember;
	}
}