package com.lrm.blog.web.admin;

import com.lrm.blog.po.User;
import com.lrm.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

//登录控制器
@Controller
@RequestMapping("/admin") //全局路径映射，里面的路径是我们后台控制的部分，所以前面都有个admin
public class LoginController {

    @Autowired
    private UserService userService;

    //跳转到登录页面
    @GetMapping  //默认使用的是/admin
    public String loginPage(){
        return "admin/login";
    }

    //登录
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user !=null){
            session.setAttribute("user",user);
            //不要把密码传到前端去
            user.setPassword(null);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
