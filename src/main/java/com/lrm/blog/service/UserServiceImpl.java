package com.lrm.blog.service;

import com.lrm.blog.dao.UserRepository;
import com.lrm.blog.po.User;
import com.lrm.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  //表示可用service
public class UserServiceImpl implements UserService{

    @Autowired  //注入
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndAndPassword(username,/* MD5Utils.code(password)*/password);
        return user;
    }
}
