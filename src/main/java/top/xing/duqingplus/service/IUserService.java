package top.xing.duqingplus.service;

import org.springframework.web.multipart.MultipartFile;
import top.xing.duqingplus.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxh
 * @since 2022-04-18
 */
public interface IUserService extends IService<User> {

    void register(User user);
    User login(User user);
    List<User> selectAll();
    User selectByUP(String username,String password);
    User selectByName(String name);
    User selectByEmail(String email);
    User selectByQq(String qq);
    void updatePassword(String username, String oldPassword,String newPassword, String confirmPassword);
    User findPassword(HttpSession session, String code,String email);
    int changePermission(Long uid,String type);
    void changeCover(Long uid, MultipartFile image, String type, HttpServletRequest request);
    void changeInfo(User user);
    void signIn(Long uid);
    void sendCode(HttpSession session, String email);

    User getUserById(Long id);
}
