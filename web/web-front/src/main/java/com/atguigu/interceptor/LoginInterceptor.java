package com.atguigu.interceptor;

import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author luoyin
 * @Date 10:52 2022/8/26
 **/
@Controller
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("existUser");
        if(userInfo == null){
            WebUtil.writeJSON(response,Result.build(null, ResultCodeEnum.LOGIN_AUTH));
            return false;
        }
        return true;
    }
}
