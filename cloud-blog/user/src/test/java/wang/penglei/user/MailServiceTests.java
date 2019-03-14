package wang.penglei.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.penglei.user.service.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTests {

    @Autowired
    MailService mailService;

    @Test
    public void contextLoads() {
        mailService.sendSimpleMail("910722178@qq.com", "test", "2018");
    }

}
