package com.mindiqo.backend.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.lang.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        log.info("Get token from header:{}", token);
        if (!StringUtils.hasLength(token)) {
            sendErrorResponse(response, "NOT LOGIN");
            return false;
        }
        try {
            Claims claims = JwtUtils.parseJWT(token);
            if (claims.get("id") == null || claims.get("username") == null) {
                sendErrorResponse(response, "INVALID TOKEN");
                return false;
            }
            if (JwtUtils.isTokenExpired(claims)) {
                sendErrorResponse(response, "TOKEN EXPIRED");
                return false;
            }
            request.setAttribute("userId", claims.get("id"));
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.info("Token is expired");
            sendErrorResponse(response, "TOKEN EXPIRED\rPlease login again");
            return false;
        } catch (io.jsonwebtoken.SignatureException e) {
            log.info("Token signature does not match");
            sendErrorResponse(response, "INVALID TOKEN");
            return false;
        } catch (Exception e) {
            log.info("Invalid token");
            sendErrorResponse(response, "NOT LOGIN");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        Result responseResult = Result.error(errorMessage);
        String json = JSONObject.toJSONString(responseResult);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
