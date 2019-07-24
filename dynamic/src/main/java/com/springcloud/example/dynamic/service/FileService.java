package com.springcloud.example.dynamic.service;

import com.springcloud.example.dynamic.dao.FileEntityMapper;
import com.springcloud.example.dynamic.model.FileEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/9/25 13:58
 */
@Service
public class FileService {
    @Resource
    private FileEntityMapper fileEntityMapper;
    private static final String ROOT_PATH = "C:\\software\\nginx-1.15.3\\html";

    /**
     * @param file
     * @param model
     * @return
     */
    @Transactional
    public String uploadFile(MultipartFile file, String model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        File rootUploadFile = new File(ROOT_PATH + File.separator + model);
        if (!rootUploadFile.exists()){
            rootUploadFile.mkdirs();
        }
        String filePath = System.currentTimeMillis() + "_" + originalFilename;
        File uploadFile = new File(rootUploadFile, filePath);
        String path = model + File.separator + filePath;

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilePath(path);
        fileEntity.setModel(model);
        fileEntity.setName(originalFilename);
        fileEntityMapper.insert(fileEntity);

        // 本地文件转换
        file.transferTo(uploadFile);
        return path;
    }
}
