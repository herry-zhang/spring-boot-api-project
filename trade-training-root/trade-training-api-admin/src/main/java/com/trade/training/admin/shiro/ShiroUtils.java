package com.trade.training.admin.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author herryzhang
 * @date 2018-07-18 21:59:05
 */
public class ShiroUtils {

    public static Long getUserID() {
        return (Long) SecurityUtils.getSubject().getPrincipal();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static Object removeSessionAttribute(Object key) {
        return getSession().removeAttribute(key);
    }

}
