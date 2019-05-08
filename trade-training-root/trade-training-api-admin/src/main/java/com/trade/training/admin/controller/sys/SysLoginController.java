package com.trade.training.admin.controller.sys;


import com.google.code.kaptcha.Producer;
import com.trade.training.admin.shiro.ShiroUtils;
import com.trade.training.common.utils.IPUtils;
import com.trade.training.config.ErrorCode;
import com.trade.training.config.SourceCode;
import com.trade.training.model.dto.request.sys.LoginRequestDTO;
import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.model.entity.sys.SysUser;
import com.trade.training.service.sys.SysUserService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author herry-zhang
 * @date 2018-7-23 16:52:02
 */
@RestController
@RequestMapping("/sys")
public class SysLoginController {
    private final static String VERIFICATION_CODE = "verificationCode";

    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证码
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        String code = producer.createText();
        ShiroUtils.setSessionAttribute(VERIFICATION_CODE, code);
        BufferedImage image = producer.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public BaseResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        String sessionCode = (String) ShiroUtils.getSessionAttribute(VERIFICATION_CODE);
        if (sessionCode == null || loginRequestDTO.getVerificationCode() == null || !loginRequestDTO.getVerificationCode().equals(sessionCode)) {
            return BaseResponseDTO.error(ErrorCode.SUPPORT_VERIFICATION_CODE_INVALID.getCode(), ErrorCode.SUPPORT_VERIFICATION_CODE_INVALID.getMessage());
        }
        SysUser user = sysUserService.login(loginRequestDTO, (byte) SourceCode.PC.getCode(), IPUtils.getIpAddr(request));
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), new Sha256Hash(loginRequestDTO.getPassword(), user.getSalt()).toHex());
        subject.login(token);
        subject.getSession().removeAttribute(VERIFICATION_CODE);
        return BaseResponseDTO.success(sysUserService.getLoginUser(ShiroUtils.getUserID()));
    }

    /**
     * 获取登录的简要信息
     */
    @GetMapping("/login-user")
    public BaseResponseDTO loginUser() {
        return BaseResponseDTO.success(sysUserService.getLoginUser(ShiroUtils.getUserID()));
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public BaseResponseDTO logout() {
        ShiroUtils.getSubject().logout();
        return BaseResponseDTO.success("退出成功");
    }

    /**
     * 获取登录用户的个人信息
     */
    @GetMapping("/login-user-profile")
    public BaseResponseDTO loginUserProfile() {
        return BaseResponseDTO.success(sysUserService.getSysUserProfile(ShiroUtils.getUserID()));
    }

}
