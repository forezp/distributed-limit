package io.github.forezp.bootexample.web;

import io.github.forezp.distributedlimitcore.annotation.Limit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Limit(identifier = "T(io.github.forezp.bootexample.web.Constant).EMAIL_ROUTINE.concat(#test)", key = "test", limtNum = 10, seconds = 1)
    public String Test(@RequestParam(value = "test", defaultValue = "t111") String test,
                       @RequestParam(value = "test2", defaultValue = "t222") String test2) {
        return "11";
    }
}
