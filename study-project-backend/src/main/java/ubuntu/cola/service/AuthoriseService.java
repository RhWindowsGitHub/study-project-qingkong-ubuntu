package ubuntu.cola.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Cola_Ubuntu
 * @name AuthoriseService
 * @DATE 2023/4/27 下午5:42
 * @description AuthoriseService
 */
public interface AuthoriseService extends UserDetailsService {

    boolean sendValidateEmail(String email, String sessionId);
}
