package com.phuckhanh.VideoApp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CloudinaryService {
    Cloudinary cloudinary;

    public Map uploadFile(MultipartFile file, String folderName, String oldUrl) throws IOException {
        destroyFile(folderName, oldUrl, "auto");

        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "auto",
                        "folder", folderName
                ));
    }

    public void destroyFile(String folderName, String oldUrl, String resource_type) throws IOException {
        String oldPublicId = extractPublicIdFromUrl(oldUrl);

        log.info(oldUrl);
        log.info(oldPublicId);

        if (oldPublicId != null) {
            log.info(cloudinary.uploader().destroy(folderName + "/" + oldPublicId, ObjectUtils.asMap(
                    "invalidate", true,
                    "type", "upload",
                    "resource_type", resource_type
            )).toString());
        }
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        // Tìm vị trí của dấu / cuối cùng trong URL
        int lastSlashIndex = imageUrl.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            // Lấy phần public id sau dấu / cuối cùng
            String publicId = imageUrl.substring(lastSlashIndex + 1);

            // Kiểm tra nếu có dấu chấm trong phần public id để cắt bớt phần mở rộng của định dạng ảnh
            int dotIndex = publicId.lastIndexOf('.');
            if (dotIndex != -1) {
                publicId = publicId.substring(0, dotIndex);
            }

            return publicId;
        } else {
            return null;
        }
    }
}
