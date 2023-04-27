package ubuntu.cola.service.impl;

import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ubuntu.cola.entity.pojo.Account;
import ubuntu.cola.mapper.AccountMapper;

/**
 * @author Cola_Ubuntu
 * @name AuthoriseServiceImpl
 * @DATE 2023/4/27 下午5:43
 * @description AuthoriseServiceImpl
 */
@Service
public class AuthoriseServiceImpl implements UserDetailsService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("用户名不能为空");
        Account account = accountMapper.findAccountByUsernameOrEmail(username);
        if (account == null) throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }
}
