package com.example.demo; //현재 폴더 위치

//index 실행 전 백그라운드에서 무조건 먼저 돌아감
import org.springframework.boot.SpringApplication; //스프링 부트 '어플리케이션'을 위한
import org.springframework.boot.autoconfigure.SpringBootApplication; //자동 기능 활성화

@SpringBootApplication //(기억) @로 선언을 해놓으면 이게 시작점이다 하고 명시
//어노테이션-특정 기능을 수행하게 하는 것

public class DemoApplication { //클래스 이름

	public static void main(String[] args) { //메인 메소드(프로그램 시작점)
		SpringApplication.run(DemoApplication.class, args); //run 메소드로 실행
	}

}	
