package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리가 있다.
 *  출력 포맷 : 시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지
 *  LEVEL: TRACE > DEBUG > INFO > WARN > ERROR
 */
@Slf4j  //롬복 사용 가능 -> private final Logger log = LoggerFactory.getLogger(getClass()); 코드를 넣어줌
@RestController //반환값이 String일 때 View를 찾는 것이 아니라 HTTP 메시지 바디에 바로 입력. RestApi 만들때 활용
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); //클래스를 지정

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log={}", name); //로컬
        log.debug("debug log={}", name); //개발 서버
        log.info(" info log={}", name);  //운영 서버
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
/*  올바른 로그 사용법
    log.debug("data="+data)
    => 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 의미없는 연산 발생.

 *  로그에 대해서 더 자세한 내용은 slf4j, logback을 검색해보자.
    SLF4J - http://www.slf4j.org
    Logback - http://logback.qos.ch
    스프링 부트가 제공하는 로그 기능은 다음을 참고하자.
    https://docs.spring.io/spring-boot/docs/current/reference/html/spring-bootfeatures.html#boot-features-logging
* */