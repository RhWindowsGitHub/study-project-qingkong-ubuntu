package ubuntu.cola.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ubuntu.cola.entity.pojo.Account;
import ubuntu.cola.mapper.AccountMapper;
import ubuntu.cola.service.AuthoriseService;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Cola_Ubuntu
 * @name AuthoriseServiceImpl
 * @DATE 2023/4/27 下午5:43
 * @description AuthoriseServiceImpl
 */
@Service
@Slf4j
public class AuthoriseServiceImpl implements AuthoriseService {
    @Resource
    private AccountMapper accountMapper;

    @Resource
    private MailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    @Resource
    StringRedisTemplate stringRedisTemplate;
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

    /**
     * 发送验证邮件
     * @param email 邮件地址
     * @return 是否发送成功
     */
    @Override
    public boolean sendValidateEmail(String email, String sessionId) {

        String key = "email:" + sessionId + ":" + email;

        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(stringRedisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) return false;
        }

        Random random = new Random();
        int code = random.nextInt(899999)+100000;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("您的验证码为：");
        simpleMailMessage.setText("验证码为："+code);
        try {
            mailSender.send(simpleMailMessage);
            stringRedisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);

            return true;
        } catch (MailException e) {
            if (log.isDebugEnabled()){
                log.debug("邮件发送失败");
            }
            return false;
        }
        /*
          1. 先生成对应的验证码
          2. 把邮箱和对应的验证码放到 Redis 中（过期时间为 3 min P.S. 是要剩余时间低于 2 min 就可以再次发送一次邮件，然后重复此流程）
          3. 将验证码发送到指定邮箱
          4. 如果发送失败，将 Redis 中刚刚插入的数据清除
          5. 用户在注册时，再从 Redis 中取出相对应的键值对，对比是否一致
         */

    }
}
