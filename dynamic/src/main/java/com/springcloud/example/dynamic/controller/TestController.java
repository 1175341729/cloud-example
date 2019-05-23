package com.springcloud.example.dynamic.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.fsg.uid.UidGenerator;
import com.springcloud.example.common.advice.exception.GlobalException;
import com.springcloud.example.common.annotation.AccessLimit;
import com.springcloud.example.common.annotation.Log;
import com.springcloud.example.common.enums.Singleton;
import com.springcloud.example.common.message.PageMessage;
import com.springcloud.example.common.util.HttpClientUtil;
import com.springcloud.example.common.util.RedisUtil;
import com.springcloud.example.dynamic.dao.SaleAreasMapper;
import com.springcloud.example.dynamic.dao.UserMapper;
import com.springcloud.example.dynamic.message.StudentReq;
import com.springcloud.example.dynamic.model.SaleAreas;
import com.springcloud.example.dynamic.model.SaleAreasExample;
import com.springcloud.example.dynamic.service.FileService;
import com.springcloud.example.dynamic.service.RetryService;
import com.springcloud.example.dynamic.service.SaleAreasService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/***
 *  @Author dengwei
 *  @Description: 测试
 *  @Date 2018/9/7 14:58
 *
 *  @Bean
        public Validator validator() {
            ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory();
            return validatorFactory.getValidator();
        }

        1、普通模式（默认是这个模式）
        　　普通模式(会校验完所有的属性，然后返回所有的验证失败信息)

        2、快速失败返回模式
        　　快速失败返回模式(只要有一个验证失败，则返回)
 */
@RestController
@Validated
@Slf4j
public class TestController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SaleAreasService saleAreasService;
    @Resource
    private FileService fileService;
    @Resource
    private RetryService retryService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SaleAreasMapper saleAreasMapper;
    @Resource
    private ListOperations listOperations;

    @GetMapping("/test")
    public Object test() {
        redisUtil.set("test", "邓伟", null);
        Object test = redisUtil.get("test");
        if (true)
            throw new GlobalException("1234");
        return test;
    }

    @GetMapping("/test2")
    public Object test(@NotEmpty(message = "name不能为空！") String name) {
        Object lock = redisUtil.get("lock");
        if (lock != null) {
            throw new GlobalException("请勿重复操作！");
        }
        redisUtil.set("lock", true, 60L);
        redisUtil.setHash("hash", "name", "邓伟");
        Object rsp = redisUtil.getHashByField("hash", "name");
        return rsp;
    }

    @GetMapping("/page")
    public List<SaleAreas> page(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize) {
        PageMessage pageMessage = saleAreasService.listSaleAreas(pageNum, pageSize);

        List<SaleAreas> list = pageMessage.getList();
        log.info(JSON.toJSONString(list));
        return list;
    }

    @GetMapping("/validateGet")
    public String validateGet(@NotBlank(message = "名称不能为空！") String name, @Min(value = 10, message = "age需大于{value}") @NotNull Integer age) {
        return name + "--" + age;
    }

    @PostMapping("/validatePost")
    public StudentReq validatePost(@Validated @RequestBody(required = false) StudentReq req) {
        return req;
    }

    @GetMapping("/{id}")
    @AccessLimit
    public String pathValidate(@Max(2) @PathVariable Integer id) {
        return id + "";
    }

    @GetMapping("/testVoid")
    public void testVoid(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        PrintWriter writer = response.getWriter();
        writer.print("1234");
        writer.flush();
        log.info("testVoid");
    }

    @GetMapping("/id/{id}")
    @Log(model = "测试模块",action = "搜索")
    public Integer pathValidateInteger(@Max(2) @PathVariable Integer id) {
        return id;
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, @Validated StudentReq req) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filePath = "C:\\文档\\" + File.separator + originalFilename;
        // 文件写入
        file.transferTo(new File(filePath));
        return filePath;
    }

    @GetMapping("/date/getDate")
    public Date listRedis(Date date) {
        return date;
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        String url = "http://localhost:8080/api/device/exportPointSnap?supplier=a0001&scrType=2&dataTime=1537113600";
        InputStream inputStream = HttpClientUtil.getInputStream(url);
        IOUtils.copy(inputStream,response.getOutputStream());
        response.setContentType("application/x-msdownload");
        // 设置头消息
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("供应商.xls".getBytes("gbk"), "iso-8859-1"));
    }

    @PostMapping("/fileUpload")
    public String fileUpload(MultipartFile file, @NotBlank(message = "文件模块不能为空！") String model) throws IOException {
        String filePath = fileService.uploadFile(file,model);
        return filePath;
    }

    @PostMapping("/branchFileUpload")
    public String branchFileUpload(HttpServletRequest request,String[] numbers){
        MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
        for(String number : numbers){
            MultipartFile file = mhr.getFile("file_" + number);
            if (file != null && !file.isEmpty()){
                String originalFilename = file.getOriginalFilename();
                log.info(number + "_" + originalFilename);
            }
        }
        return "SUCCESS";
    }

    @GetMapping("/student")
    public StudentReq student(){
        return new StudentReq();
    }

    @PostMapping("/retry")
    public String retry(@RequestBody JSONObject param){
        new Thread(() -> {
            param.put("uuid", IdUtil.simpleUUID());
            String retry = retryService.retry(param);
        }).start();
        return "OK";
    }

    @Transactional
    @GetMapping("/saveUser")
    public int saveUser(){
        /*User user = new User();
        user.setName("邓伟");
        user.setAge(27);
        int insert = userMapper.insert(user);
        return insert;*/
        int number = saleAreasMapper.countByExample(new SaleAreasExample());
        log.info("删除前：{}",number);
        saleAreasMapper.deleteByPrimaryKey("900000");
        number = saleAreasMapper.countByExample(new SaleAreasExample());
        log.info("删除前：{}",number);
        throw new RuntimeException("123");
    }

    @GetMapping("/cache/{key}")
    public List<String> cacheGet(@PathVariable String key) throws ExecutionException {
        return Singleton.INSTANCE.get(key);
    }

    @Resource
    private UidGenerator cachedUidGenerator;

    @GetMapping("/uid")
    public String uuid(){
        long uid = cachedUidGenerator.getUID();
        return uid + "";
    }

    @GetMapping("/list")
    public String list(String value){
        listOperations.leftPush("queue",value);

//        Object v = listOperations.leftPop("queue");
//        log.info("{}", v);
        return "OK";
    }
}
