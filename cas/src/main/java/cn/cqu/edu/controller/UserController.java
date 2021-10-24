package cn.cqu.edu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cqu.edu.domain.User;
import cn.cqu.edu.domain.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/user/getPwd") // 必须是网页才会访问数据库？
    public String getPwd(String userId){
        System.out.println("正在查找"+userId+"的密码");
        User user=userRepository.findById(userId).get();
        if(user!=null) return user.getPassword();
        return null;
    }

    @RequestMapping(value = "/user/add")
    public User add(User user) {
        return userRepository.insert(user);
    }

    @RequestMapping(value = "/user/findAll")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/user/findById")
    public Optional<User> findById(String userId) {
        System.out.println(userId);
        return userRepository.findById(userId);
    }

    @RequestMapping(value = "/user/update")
    public User update(User user) {
        if (userRepository.existsById(user.getUserId()))
            return userRepository.save(user);
        else
            return null;
    }

    @RequestMapping(value = "/user/delete")
    public boolean delete(String userId) {
        userRepository.deleteById(userId);
        if (userRepository.existsById(userId))
            return false;
        else
            return true;
    }
}
