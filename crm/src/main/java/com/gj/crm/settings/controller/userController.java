package com.gj.crm.settings.controller;

import com.gj.crm.commons.contants.Contants;
import com.gj.crm.commons.domian.ReturnObject;
import com.gj.crm.commons.utils.DataUtils;
import com.gj.crm.settings.entity.User;
import com.gj.crm.settings.service.impl.userServiceImpl;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/9/13 - 17:49
 */
@Controller
public class userController {

    /**
     * URL :要和当前controller方法返回的资源目录保持一致
     * @return
     */
    @Autowired
    private userServiceImpl userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){

        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public  Object login(String loginAct, String loginPwd, String isRemPwd , HttpServletRequest request, HttpSession session, HttpServletResponse response){

        //封装map
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        // map.put("isRemPwd",isRemPwd); sql语句不需要，不需要传
        //调用service的方法查找user
        User user =userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject=new ReturnObject();

        //根据查询结果生成响应信息
        if(user==null){
            //登陆失败
            returnObject.setCode("0");
            returnObject.setMessage("用户名密码错误");
        }else{
            //进一步判断是否合法
            //user.getExpireTime(); 2019-11-11
            //new Date(); 2022-9-16
            //自创工具类DataUtils
            String nowTime= DataUtils.formateDataTime(new Date());
            if(nowTime.compareTo(user.getExpireTime())>0){
                //登陆失败,账号过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if("0".equals(user.getLockState())){
                //登陆失败，状态锁定，1锁0开
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("状态被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                System.out.println(request.getRemoteAddr());
                //登录失败，ip受限
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip受限");
            }else{
                //登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //把user存到session当中
                session.setAttribute(Contants.SSESSION_USER_KEY,user);
                Cookie [] cookies=request.getCookies();
                if(cookies!=null){
                    for (Cookie cookie : cookies) {
                        if(cookie.getName().equals("loginAct")){
                            session.setAttribute("cookieAct",cookie.getValue());
                        }
                        if(cookie.getName().equals("loginPwd")){
                            session.setAttribute("cookiePwd",cookie.getValue());
                        }
                    }
                }
                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    //账号
                    Cookie cookieLoginAct=new Cookie("loginAct",user.getLoginAct());
                    //保存10天
                    cookieLoginAct.setMaxAge(10*24*60*60);
                    cookieLoginAct.setPath(request.getContextPath());
                    response.addCookie(cookieLoginAct);
                    //密码
                    Cookie cookieLoginPwd=new Cookie("loginPwd",user.getLoginPwd());
                    //保存10天
                    cookieLoginPwd.setMaxAge(10*24*60*60);
                    cookieLoginPwd.setPath(request.getContextPath());
                    response.addCookie(cookieLoginPwd);
                }else {
                    //把没有过期的cookie删除，由于不能删除只能覆盖，可以重新写入，将cookie生命周期设为0
                    //账号
                    Cookie cookieLoginAct=new Cookie("loginAct",user.getLoginAct());
                    //保存10天
                    cookieLoginAct.setMaxAge(0);
                    cookieLoginAct.setPath(request.getContextPath());
                    response.addCookie(cookieLoginAct);
                    //密码
                    Cookie cookieLoginPwd=new Cookie("loginPwd",user.getLoginPwd());
                    //保存10天
                    cookieLoginPwd.setMaxAge(0);
                    cookieLoginPwd.setPath(request.getContextPath());
                    response.addCookie(cookieLoginPwd);
                }
            }
        }
    return returnObject;
    }

    @RequestMapping("/setting/qx/user/loginOut.do")
    public String loginOut(HttpServletResponse response,HttpServletRequest request,HttpSession session){
        //把没有过期的cookie删除，由于不能删除只能覆盖，可以重新写入，将cookie生命周期设为0
        //账号
        Cookie cookieLoginAct=new Cookie("loginAct","1");
        //保存10天
        cookieLoginAct.setMaxAge(0);
        cookieLoginAct.setPath(request.getContextPath());
        response.addCookie(cookieLoginAct);
        //密码
        Cookie cookieLoginPwd=new Cookie("loginPwd","1 ");
        //保存10天
        cookieLoginPwd.setMaxAge(0);
        cookieLoginPwd.setPath(request.getContextPath());
        response.addCookie(cookieLoginPwd);
        //销毁session
        session.invalidate();
        return "redirect:/";
    }
}
