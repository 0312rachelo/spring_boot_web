
package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional // 트랜잭션처리(클래스내모든메소드대상)
@RequiredArgsConstructor

public class MemberService{
   private final MemberRepositorymemberRepository;
   private final PasswordEncoderpasswordEncoder; // 스프링버전5 이후, 단방향해싱알고리즘지원
   private void validateDuplicateMember(AddMemberRequestrequest) {
      Member findMember= memberRepository.findByEmail(request.getEmail()); // 이메일존재유무
      if(findMember!= null){
         throw new IllegalStateException("이미가입된회원입니다."); // 예외처리
      }
   }

   public Member saveMember(AddMemberRequest request){
      validateDuplicateMember(request); // 이메일 체크
      String encodedPassword = passwordEncoder.encode(request.getPassword());
      request.setPassword(encodedPassword); // 암호화된 비밀번호 설정
      return memberRepository.save(request.toEntity());
   }

   public Member loginCheck(String email, String rawPassword) {
      Member member = memberRepository.findByEmail(email); // 이메일 조회
      if (member == null) {
         throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
      }
      if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // 비밀번호 확인
         throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
      }
      return member; // 인증 성공 시 회원 객체 반환
   }
       
}
