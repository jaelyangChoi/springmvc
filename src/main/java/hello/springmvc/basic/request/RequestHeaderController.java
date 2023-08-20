package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/** 애노테이션 기반 컨트롤러는 다양한 파라미터를 받아들일 수 있다!
* */
//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestHeaderController.class);
@Slf4j //위 코드를 자동으로 생성해서 로그를 선언해준다. 개발자는 편리하게 log 라고 사용하면 된다.
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale, //언어정보
                          //MultiValueMap : 하나의 키에 여러 값을 받을 수 있다. HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용. ketA=value1 & keyA=value2
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "cookieName", required = false) String cookie) { //value : 쿠키 이름
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("cookie={}", cookie);
        return "ok";
    }
}
