package ubuntu.cola.config;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ubuntu.cola.entity.result.R;
import ubuntu.cola.entity.result.ResultEnum;
import ubuntu.cola.service.AuthoriseService;
import ubuntu.cola.service.impl.AuthoriseServiceImpl;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Cola_Ubuntu
 * @name SecurityConfiguration
 * @DATE 2023/4/27 下午12:27
 * @description SecurityConfiguration SpringSecurity 配置类
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    AuthoriseServiceImpl authoriseService;

    @Resource
    DataSource dataSource;
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity,PersistentTokenRepository repository) throws Exception {
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
                .logoutSuccessHandler(this::onAuthenticationSuccess)
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(3600 * 24 * 7)
                .and()
                .csrf()
                // 关闭 CSRF
                .disable()
                .cors()
                .configurationSource(this.corsConfigurationSource())
                .and()
                .exceptionHandling()
                // Security 默认对没有权限的处理是 抛出 AuthenticationException 异常添加 failureHandler 对没有权限进行自定义处理
                .authenticationEntryPoint(this::onAuthenticationFailure)
                .and()
                .build();

    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动时创建表
        tokenRepository.setCreateTableOnStartup(false);

        return tokenRepository;
    }

    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",cors);
        return source;
    }

    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .getSharedObject(AuthenticationManagerBuilder.class)
                        .userDetailsService(authoriseService)
                        .and()
                        .build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        if (request.getRequestURI().endsWith("/login")){
            response.getWriter().write(JSONObject.toJSONString(R.success("登陆成功", ResultEnum.SUCCESS)));
        }else if (request.getRequestURI().endsWith("/logout")){
            response.getWriter().write(JSONObject.toJSONString(R.success("退出成功", ResultEnum.SUCCESS)));
        }
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        response.getWriter().write(JSONObject.toJSONString(R.fail("登陆失败（用户名或密码错误）", ResultEnum.ERROR_FORBIDDEN)));
    }

}
