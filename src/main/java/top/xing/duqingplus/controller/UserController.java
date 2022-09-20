package top.xing.duqingplus.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.xing.duqingplus.pojo.User;
import top.xing.duqingplus.service.IUserService;
import top.xing.duqingplus.until.BackInfoUntil;
import top.xing.duqingplus.until.RequestUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping("/login")
    public BackInfoUntil<User> login(User user){
        User login = userService.login(user);
        return new BackInfoUntil<>("200","登录成功",login);
    }

    @RequestMapping("/register")
    public BackInfoUntil<String> register(User user){
        userService.register(user);
        return BackInfoUntil.success();
    }

    @RequestMapping("/getUser")
    public BackInfoUntil<User> getUser(Long id){
        User user = userService.getUserById(id);
        return new BackInfoUntil<>("200","获取用户信息成功",user);
    }

    @RequestMapping("/allUsers")
    public BackInfoUntil<List<User>> allUser(){
        List<User> users = userService.selectAll();
        return new BackInfoUntil<>("200","获取成功",users);
    }

    @RequestMapping("/changePermission")
    public BackInfoUntil<String> changePermission(Long uid,String type){
        int i = userService.changePermission(uid, type);
        return BackInfoUntil.success();
    }

    @RequestMapping("/find")
    public BackInfoUntil<User> findPassword(HttpSession session, String code,String email){
        User password = userService.findPassword(session, code,email);
        return new BackInfoUntil<>("200","验证成功",password);
    }

    @RequestMapping("/sendCode")
    public BackInfoUntil<String> sendCode(HttpSession session,String email){
        userService.sendCode(session,email);
        return BackInfoUntil.success();
    }
    @PostMapping("/changeImage")
    public BackInfoUntil<String> changeCover(@RequestParam("file") MultipartFile image, Long uid, String type, HttpServletRequest request){
        userService.changeCover(uid,image,type,request);
        return BackInfoUntil.success();
    }
    @RequestMapping("/changeInfo")
    public BackInfoUntil<String> changeInfo(User user){
        userService.changeInfo(user);
        return BackInfoUntil.success();
    }
    @RequestMapping("/signIn")
    public BackInfoUntil<String> signIn(Long uid) {
        userService.signIn(uid);
        return BackInfoUntil.success();
    }
}
