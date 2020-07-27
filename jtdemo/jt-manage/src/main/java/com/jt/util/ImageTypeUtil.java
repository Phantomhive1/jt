package com.jt.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource("classpath:/properties/image.properties")
public class ImageTypeUtil {
    @Value("${image.imageTypes}")
    private String imageTypes;
    private Set<String> typeSet = new HashSet<>();

    @PostConstruct
    public void init() {
        String[] typeArray = imageTypes.split(",");
        for (String type : typeArray) {
            typeSet.add(type);
        }
        System.out.println("set集合初始化完毕！"+typeSet);
    }

    public Set<String> getTypeSet() {
        return typeSet;
    }
}
