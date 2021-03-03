package com.lrm.blog.dao;

import com.lrm.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Springboot 的jpa<dao操作对象，组件类型>
public interface UserRepository extends JpaRepository<User,Long>{

    User findByUsernameAndAndPassword(String username,String password);
}
