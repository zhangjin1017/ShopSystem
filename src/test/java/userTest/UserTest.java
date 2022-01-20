package userTest;

import com.my.pojo.User;
import com.my.pojo.UserExample;
import com.my.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void testInsert() {
        userService.insertSelective(new User(null, "admin", "admin", "12345678912", "123"));
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("user_id ASC");
        userService.selectByExample(userExample);
    }

    @Test
    public void tetUUID() {
        int num = (int) (Math.random() * 100000000 + 1);
        System.out.println(num);
    }

    @Test
    public void fun1() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo("zhangjin");
        criteria.andPasswordEqualTo("zhangjin");
        List<User> list = userService.selectByExample(userExample);
        System.out.println(list);
    }
}
