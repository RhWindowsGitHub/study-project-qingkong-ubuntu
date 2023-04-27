package ubuntu.cola.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ubuntu.cola.entity.pojo.Account;
import ubuntu.cola.entity.result.R;
import ubuntu.cola.entity.result.ResultEnum;

/**
 * @author Cola_Ubuntu
 * @name AuthController
 * @DATE 2023/4/27 下午4:08
 * @description AuthController
 */
@RestController
@Slf4j
@RequestMapping("/v1/api/auth/")
public class AuthController {

    @PostMapping("login")
    public R<Account> login(Account account){
        log.info("account: {}",account);
        return R.success(account, ResultEnum.SUCCESS);
    }

    @GetMapping("logout")
    public R<Account> logout(){
        log.info("logout");
        return R.success(null, ResultEnum.SUCCESS);
    }
}
