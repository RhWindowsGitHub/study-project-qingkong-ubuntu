package ubuntu.cola.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ubuntu.cola.entity.result.R;

/**
 * @author Cola_Ubuntu
 * @name AuthoriseService
 * @DATE 2023/4/27 下午5:42
 * @description AuthoriseService
 */
public interface AuthoriseService extends UserDetailsService {

    R<String> sendValidateEmail(String email, String sessionId,boolean hasAccount);

    R<String> validateAndRegister(String username, String password, String email, String code,String sessionId);

    R<String> startReset(String email, String code,String sessionId,boolean hasAccount);
}
