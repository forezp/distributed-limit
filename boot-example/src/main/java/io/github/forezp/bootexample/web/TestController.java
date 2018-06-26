package io.github.forezp.bootexample.web;

import io.github.forezp.distributedlimitcore.annotation.Limit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    @Limit(identifier = "forezp", key = "test", limtNum = 10, seconds = 1)
    public String Test() {
        return "11";
    }
}
