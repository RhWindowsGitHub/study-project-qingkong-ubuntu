package ubuntu.cola.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
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
    private final String USERNAME_REGEX = "^((?!\\\\|\\/|:|\\*|\\?|<|>|\\||'|%|@|#|&|\\$|\\^|&|\\*).){1,8}$";
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
        if (authoriseService.sendValidateEmail(email, httpSession.getId(),false).getSuccess()) {
            return R.success(null, ResultEnum.SUCCESS);
        }else {
            return R.fail(null, ResultEnum.ERROR_FORBIDDEN);
        }
    }

    @PostMapping("register")
    public R<String> registerAccount(
            @Pattern(regexp = USERNAME_REGEX)
            @Length(min = 2,max = 12)
            @RequestParam("username") String username,
            @Length(min = 6,max = 16)
            @RequestParam("password") String password,
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email") String email,
            @Length(min = 6,max = 6)
            @RequestParam("code") String code,
            HttpSession httpSession
    ){
        if (authoriseService.validateAndRegister(username,password,email,code,httpSession.getId()).getSuccess()){
            return R.success(null, ResultEnum.SUCCESS);
        }else {
        return R.fail(null, ResultEnum.ERROR_FORBIDDEN);
    }
    }

    @PostMapping("/start-reset")
    public R<String> startReset(
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email") String email,
            @Length(min = 6,max = 6)
            @RequestParam("code") String code,
            HttpSession httpSession
    ){
        if (authoriseService.startReset(email, code,httpSession.getId(),true).getSuccess()) {
            httpSession.setAttribute("reset-email",email);
            return R.success(null, ResultEnum.SUCCESS);
        }else {
            return R.fail(null, ResultEnum.ERROR_FORBIDDEN);
        }
    }
}
