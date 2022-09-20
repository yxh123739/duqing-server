package top.xing.duqingplus.service.impl;

import cn.hutool.extra.mail.MailUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.xing.duqingplus.service.MailService;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendEmail(String email, HttpSession session) {
        Assert.notNull(email, "邮箱不能为空");
        String code = randomCode();
        MailUtil.send(email, "邮箱验证码", "验证码为：" + code, false);
        session.setAttribute("email", email);
        session.setAttribute("code", code);
    }

    @Override
    public void judgeCode(HttpSession session, String code, String email) {
        Assert.notNull(code, "验证码不能为空");
        Assert.notNull(email, "邮箱不能为空");
        Assert.isTrue(code.equals(session.getAttribute("code")), "验证码错误");
        Assert.isTrue(email.equals(session.getAttribute("email")), "邮箱不一致");
        session.removeAttribute("email");
        session.removeAttribute("code");
    }

    private String randomCode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
