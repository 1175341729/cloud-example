
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.springcloud.example.common.enums.Singleton;
import com.springcloud.example.common.util.ExcelUtil;
import com.springcloud.example.dynamic.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
}
