package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }


    @ResponseBody //view 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String username, @RequestParam int age) { //이름이 같으면 생략 가능
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * String, int, Integer 등 단순 타입이면 @RequestParam도 생략 가능..!
     * 다만 생략이 약간 과한 것 같다. 요청 파라미터를 읽는다는 것을 명확히 표현하는 것이 좋은 것 같다.
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(String username, int age) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    //required : default = true
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, @RequestParam(required = false) Integer age) { //null일 수 있으므로..!
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }


    //defaultValue는 null뿐 아니라 빈문자열도 기본값으로 치환!
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username, @RequestParam(defaultValue = "-1") int age) { //null일 수 있으므로..!
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    //파라미터를 Map, MultiValueMap으로 조회할 수 있다
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }


    /** @ModelAttribute
     * 실제 개발을 하면 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주어야 한다.
     * 보통 다음과 같이 코드를 작성할 것이다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttribueV1(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);

        return "ok";
    }

    /**
     * 스프링은 이 과정을 완전히 자동화해주는 @ModelAttribute 기능을 제공한다.
     * 그러려면 요청 파라미터를 바인딩 받을 클래스를 정의해야 한다.
     * 스프링MVC는 @ModelAttribute 가 있으면 다음을 실행
     * 1) HelloData 객체를 생성한다
     * 2) 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
     * 3) 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩)
     * 타입이 안맞으면 바인딩 오류를 발생시키는데, 이를 처리하는 방법은 검증 부분에서 다룬다
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttribueV2(@ModelAttribute HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);

        return "ok";
    }

    /** @ModelAttribute도 생략 가능
     * 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
     * String , int , Integer 같은 단순 타입 =>  @RequestParam
     * 나머지  =>  @ModelAttribute (argument resolver 로 지정해둔 예약된 타입 외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v3")
    public String modelAttribueV3(HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);

        return "ok";
    }
}
