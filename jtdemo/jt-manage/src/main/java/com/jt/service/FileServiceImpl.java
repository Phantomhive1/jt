package com.jt.service;

import com.jt.util.ImageTypeUtil;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

    @Value("${image.localDir}")
    private String localDir;

    @Value("${image.imageUrl}")
    private String imageUrl;

    @Autowired
    private ImageTypeUtil imageTypeUtil;

    @Override
    public ImageVO uploadFile(MultipartFile uploadFile) {
        //1.校验上传的信息是否为图片
        Set<String> typeSet = imageTypeUtil.getTypeSet();

        String fileName = uploadFile.getOriginalFilename();
        fileName = fileName.toLowerCase();
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        if (!typeSet.contains(fileType)) {
            return ImageVO.fail();
        }

        //2.准备文件时上传的目录结构
        String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        String dirPath = localDir + dateDir;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        //3.重新指定文件名称
        String uuid = UUID.randomUUID().toString();
        String realFileName = uuid + fileType;
        File imageFile = new File(dirPath + realFileName);
        try {
            uploadFile.transferTo(imageFile);
            String url = imageUrl + dateDir + realFileName;
            return ImageVO.success(url);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return ImageVO.fail();
        }

    }
}
