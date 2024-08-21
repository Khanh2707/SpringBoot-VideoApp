package com.phuckhanh.VideoApp.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlukqdtvv",
                "api_key", "921524138239421",
                "api_secret", "lrX7HktnCsVkEMgzBId1wPGcG-8",
                "secure", true
        ));
    }
}
