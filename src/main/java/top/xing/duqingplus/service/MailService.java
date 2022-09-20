package top.xing.duqingplus.service;

import javax.servlet.http.HttpSession;

public interface MailService {
    void sendEmail(String email, HttpSession session);
    void judgeCode(HttpSession session, String code,String email);
}
