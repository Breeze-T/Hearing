package com.bootdo.system.config;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.redis.shiro.RedisManager;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2019/6/24 0024.
 * @desc 自定义拦截器，用户判断用户是否登陆，同一用户限制一个地方登陆
 */
public class UniqueUserInfoInterceptor implements HandlerInterceptor {
    private static Logger LOGGER = LoggerFactory.getLogger (UniqueUserInfoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 可以成功获取到Bean啦
        RedisManager redisManager = new RedisManager();
        Cookie[] cookies = request.getCookies();
        String servletPath = request.getServletPath();
        if(servletPath.contains("\\/login") || "/".equals(servletPath)){
            System.out.println("############################################################");
            return true;
        }
//遍历所有的cookie,然后根据cookie的key值来获取value值
        if (cookies!=null && redisManager != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String cookeValue = cookie.getValue();
                    byte[] b = redisManager.get(("userCode|"+cookeValue.split("_")[0]).getBytes());
                    String redisValue = "";
                    if(b!= null && b.length > 0){
                        redisValue = new String(redisManager.get(("userCode|"+cookeValue.split("_")[0]).getBytes()));
                    }

                    if(!cookeValue.split("_")[1].equals(redisValue.split("_")[0])){
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("text/html;charset=UTF-8");
                        JSONObject res = new JSONObject();
                        res.put("status","-1");
                        res.put("msg","您的账号已在其它地方登陆，若不是本人操作，请注意账号安全！");

                        StringBuffer sb = new StringBuffer("<!DOCTYPE html><html><head><meta charset=\"utf-8\">\n" +
                                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                                "    <title>账户异常</title>\n" +
                                "    <meta name=\"keywords\" content=\"\">\n" +
                                "    <meta name=\"description\" content=\"\">\n" +
                                "    <link rel=\"shortcut icon\" href=\"favicon.ico\"> <link href=\"/css/bootstrap.min.css?v=3.3.6\" rel=\"stylesheet\">\n" +
                                "    <link href=\"/css/font-awesome.css?v=4.4.0\" rel=\"stylesheet\">\n" +
                                "    <link href=\"/css/animate.css\" rel=\"stylesheet\">\n" +
                                "    <link href=\"/css/style.css?v=4.1.0\" rel=\"stylesheet\">\n" +
                                "</head><body class=\"gray-bg\">\n" +
                                "    <div style='max-width: 450px;padding-top: 260px;' class=\"middle-box text-center animated fadeInDown\">\n" +
                                "        <h3>账户异常</h3>\n" +
                                "        <h4 class=\"font-bold\">您的账号已在其它地方登陆，若不是本人操作，请注意账号安全！</h4>\n" +
                                "        <div class=\"error-desc\">\n" +
                                "            <a href=\"/\" class=\"btn btn-primary m-t\">重新登陆</a>\n" +
                                "        </div>\n" +
                                "    </div>\n" +
                                "    <!-- 全局js -->\n" +
                                "    <script src=\"/js/jquery.min.js?v=2.1.4\"></script>\n" +
                                "    <script src=\"/js/bootstrap.min.js?v=3.3.6\"></script>\n" +
                                "</body>\n" +
                                "</html>");
                        PrintWriter out = null ;
                        out = response.getWriter();
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
