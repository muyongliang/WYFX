package com.wyfx.upms.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，验证用户是否登陆
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        //当所访问的地址是首页，或者是 登陆表彰的form acion 路径时，我们不对他进行拦截

        if (requestURI.endsWith("/wyfx/")) {

            response.sendRedirect(contextPath + "/sc/index.html");
            return false;


        }
//        if(requestURI.endsWith("/wyfx/user/getLoginUser")){
//
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            PrintWriter out = null ;
//            try{
//                JSONObject res = new JSONObject();
//                res.put("success","false");
//                res.put("msg","xxxx");
//                out = response.getWriter();
//                out.append(res.toString());
//                return false;
//            }
//            catch (Exception e){
//                e.printStackTrace();
//                response.sendError(500);
//                return false;
//            }
//
//
//        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
