package wang.penglei.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Yuicon
 */
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail(String to, String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件发送者
        message.setFrom(from);
        // 邮件接受者
        message.setTo(to);
        // 主题
        message.setSubject(subject);
        // 内容
        message.setText(content);
        javaMailSender.send(message);
    }


}