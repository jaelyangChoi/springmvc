package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.awt.SystemColor.info;

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

    //default: true
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

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }
}
