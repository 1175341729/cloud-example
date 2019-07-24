
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.springcloud.example.common.enums.Singleton;
import com.springcloud.example.common.util.ExcelUtil;
import com.springcloud.example.dynamic.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/11/22 16:48
 */
@Slf4j
public class JunitTestDemo {

    @Test
    public void test1() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("C:\\Users\\deng\\Desktop\\test.xlsx");
        Map<String,String> titleMap = Maps.newHashMap();
        titleMap.put("地址","address");
        titleMap.put("姓名","name");
        titleMap.put("年龄","age");
        List<User> users = ExcelUtil.importExcel(fis, titleMap, User.class);
        log.info(JSON.toJSONString(users));
    }

    @Test
    public void test2() throws InterruptedException {
        Date date1 = Singleton.INSTANCE.date;
        Thread.sleep(5000L);
        Date date2 = Singleton.INSTANCE.date;
        log.info("singleton：{}",date1 == date2);
    }

    @Test
    public void encryptor(){
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        basicTextEncryptor.setPassword("dengwei");
        //要加密的数据（数据库的用户名或密码）
        String username = basicTextEncryptor.encrypt("root");
        String password = basicTextEncryptor.encrypt("root");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }
}
