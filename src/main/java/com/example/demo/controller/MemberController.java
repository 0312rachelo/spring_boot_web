package com.example.demo.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberController {
    BlogService blogService;

    @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }
    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
    }
    return "join_end"; // .HTML 연결
}

@GetMapping("/member_login") // 로그인 페이지 연결
public String member_login() {
    return "login"; // .HTML 연결
}

@PostMapping("/api/login_check") // 아이디, 패스워드 로그인 체크
public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, HttpServletRequest request2, HttpServletResponse response) {
    try {
        HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
        if (session != null) {
            session.invalidate(); // 기존 세션 무효화
            Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID 초기화
            cookie.setPath("/"); // 쿠키 경로
            cookie.setMaxAge(0); // 쿠키 삭제(0으로 설정)
            response.addCookie(cookie); // 응답으로 쿠키 전달
        }
        session = request2.getSession(true); // 새로운 세션 생성
        Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
        String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성
        String email = request.getEmail(); // 이메일 얻기
        Session.setAttribute("userId", sessionId); // 아이디 이름 설정
        Session.setAttribute("email", email); // 이메일 설정
        model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달

        return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
    } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
        return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
    }
}

@GetMapping("/api/logout") // 로그아웃버튼동작
public String member_logout(Model model, HttpServletRequestrequest2, HttpServletResponseresponse) {
    try {
        HttpSessionsession = request2.getSession(false); // 기존세션가져오기(존재하지않으면null 반환)
        session.invalidate(); // 기존세션무효화
        Cookie cookie= new Cookie("JSESSIONID", null); // JSESSIONID is the default session cookie name
        cookie.setPath("/"); // Set the path for the cookie
        cookie.setMaxAge(0); // Set cookie expiration to 0 (removes the cookie)
        response.addCookie(cookie); // Add cookie to the response
        session = request2.getSession(true); // 새로운세션생성
        System.out.println("세션userId: " + session.getAttribute("userId" )); // 초기화후IDE 터미널에세션값출력
        return "login"; // 로그인페이지로리다이렉트   
    } catch (IllegalArgumentExceptione) {
        model.addAttribute("error", e.getMessage()); // 에러메시지전달
        return "login"; // 로그인실패시로그인페이지로리다이렉트
    }
}
    


