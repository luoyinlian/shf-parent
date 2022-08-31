package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author luoyin
 * @Date 11:40 2022/8/27
 **/

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 必须指定加密方式，上下加密方式要一致
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(new BCryptPasswordEncoder().encode("root"))
                .roles("");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //必须调用父类的方法，否则就不需要认证即可访问
        //super.configure(http);
        //允许iframe嵌套显示
        http.headers().frameOptions().disable();
        //1 设置资源放行
        http.authorizeRequests()
                //放行静态资源,login.html页面
                .antMatchers("/static/**","/login").permitAll()
                //其他资源登录后即可访问
                .anyRequest().authenticated();
        //设置登录
        http.formLogin()
                //登录页面访问路径
                .loginPage("/login")
                //登录功能请求路径
                .loginProcessingUrl("/login_process")
                //username参数名字
                .usernameParameter("username")
                .passwordParameter("password")
                //登录成功跳转路径
                .defaultSuccessUrl("/")
                //登录失败转发路径
                .failureForwardUrl("/login");
        //3 注销登录
        http.logout()
                //注销功能路径
                .logoutUrl("/logout")
                //注销成功路径
                .logoutSuccessUrl("/login");
        //4 禁用 csrf
        http.csrf().disable();

        //5 自定义没有权限页面
        http.exceptionHandling()
                .accessDeniedHandler(new AtguiguAccessDeniendHandle());
    }
}
