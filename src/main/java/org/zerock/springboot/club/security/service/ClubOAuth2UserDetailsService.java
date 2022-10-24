package org.zerock.springboot.club.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.springboot.club.entity.ClubMemberRole;
import org.zerock.springboot.club.security.dto.ClubAuthMemberDTO;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {
	private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("--------------------------------------");
        log.info("userRequest:" + userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        log.info("==============================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k +":" + v);
        });
        String email = null;
        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }
        log.info("EMAIL: " + email);
        Member member = saveSocialMember(email); //조금 뒤에 사용
        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,   //fromSocial
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        clubAuthMember.setName(member.getName());
        return clubAuthMember;
    }
	
	private Member saveSocialMember(String email){
        //기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        Optional<Member> result = repository.findByEmail(email, true);
        if(result.isPresent()){
            return result.get();
        }
        //없다면 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로
        Member member = Member.builder().email(email)
                .name(email)
                .password( passwordEncoder.encode("1111") )
                .nickname("똘똘이")
                .fromSocial(true)
                .build();
        member.addMemberRole(ClubMemberRole.USER);
        repository.save(member);
        return member;
    }
}