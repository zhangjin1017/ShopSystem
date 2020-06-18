package userTest;

import com.my.pojo.User;
import com.my.pojo.UserExample;
import com.my.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserTest {

    @Resource
    UserService userService;

    @Test
    public void testInsert(){
        userService.insertSelective(new User(null,"admin","admin","12345678912","123"));
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("user_id ASC");
        userService.selectByExample(userExample);
    }

}
