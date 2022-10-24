package org.zerock.springboot.club.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.springboot.club.security.dto.ClubAuthMemberDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
	@PreAuthorize("permitAll()")
	@GetMapping("/all")
    public void exAll(){
        log.info("exAll..........");
    }

	@PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
	@GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        log.info("exMember..........");
        log.info("-------------------------------");
        log.info(clubAuthMember);
    }

	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin..........");
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/note")
    public void exNote(){
        log.info("exNote..........");
    }
}