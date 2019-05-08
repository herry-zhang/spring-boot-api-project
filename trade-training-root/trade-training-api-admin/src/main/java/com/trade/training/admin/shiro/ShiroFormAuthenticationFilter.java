package com.trade.training.admin.shiro;

import com.google.gson.Gson;
import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.common.utils.HttpContextUtils;
import com.trade.training.config.ErrorCode;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        httpResponse.getWriter().print(new Gson().toJson(BaseResponseDTO.error(ErrorCode.SYS_USER_AUTHENTICATION_FAILED.getCode(), ErrorCode.SYS_USER_AUTHENTICATION_FAILED.getMessage())));
    }
}
