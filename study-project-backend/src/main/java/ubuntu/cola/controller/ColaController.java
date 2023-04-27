package ubuntu.cola.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ubuntu.cola.entity.result.R;
import ubuntu.cola.entity.result.ResultEnum;

/**
 * @author Cola_Ubuntu
 * @name ColaController
 * @DATE 2023/4/27 下午1:18
 * @description ColaController
 */
@RestController
@Slf4j
@RequestMapping("/v1/api/cola/")
public class ColaController {

    @PostMapping("sayHello")
    public R<String> sayHello(){
        return R.success("Hello I am Cola", ResultEnum.SUCCESS);
    }
}
