package ubuntu.cola.config;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import ubuntu.cola.entity.result.R;
import ubuntu.cola.entity.result.ResultEnum;

import java.io.IOException;

/**
 * @author Cola_Ubuntu
 * @name SecurityConfiguration
 * @DATE 2023/4/27 下午12:27
 * @description SecurityConfiguration SpringSecurity 配置类
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                // 所有请求都需要进行验证
                .anyRequest().authenticated()
                .and()
                // 自定义登陆接口
                .formLogin()
                .loginProcessingUrl("/v1/api/auth/login")
                // Security 默认对登陆成功的处理是 302 重定向添加 successHandler 对登陆成功进行自定义处理
                .successHandler(this::onAuthenticationSuccess)
                // Security 默认对登陆失败的处理是 302 重定向添加 failureHandler 对登陆失败进行自定义处理
                .failureHandler(this::onAuthenticationFailure)
                .and()
                // 自定义退出接口
                .logout()
                .logoutUrl("/v1/api/auth/logout")
                .and()
                .csrf()
                // 关闭 CSRF
                .disable()
                .exceptionHandling()
                // Security 默认对没有权限的处理是 抛出 AuthenticationException 异常添加 failureHandler 对没有权限进行自定义处理
                .authenticationEntryPoint(this::onAuthenticationFailure)
                .and()
                .build();

    }


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(R.success("login successful", ResultEnum.SUCCESS)));
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(R.success("login failure", ResultEnum.ERROR_FORBIDDEN)));
    }

}
