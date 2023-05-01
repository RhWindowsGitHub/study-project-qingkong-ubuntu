package ubuntu.cola.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ubuntu.cola.entity.pojo.Account;
import ubuntu.cola.entity.result.R;
import ubuntu.cola.entity.result.ResultEnum;
import ubuntu.cola.service.AuthoriseService;

/**
 * @author Cola_Ubuntu
 * @name AuthController
 * @DATE 2023/4/27 下午4:08
 * @description AuthController
 */
@Validated
@RestController
@Slf4j
@RequestMapping("/v1/api/auth/")
public class AuthController {

    @Resource
    AuthoriseService authoriseService;

    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
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

    @PostMapping("validate-email")
    public R<String> sendValidateEmail(
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email") String email,
            HttpSession httpSession){
        if (authoriseService.sendValidateEmail(email, httpSession.getId())) {
            return R.success(null, ResultEnum.SUCCESS);
        }else {
            return R.fail(null, ResultEnum.ERROR_FORBIDDEN);
        }
    }
}
