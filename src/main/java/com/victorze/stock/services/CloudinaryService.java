package com.victorze.stock.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map<String, Object> uploadFile(MultipartFile file) throws IOException {
        var params = Map.of("folder", "stock", "resource_type", "auto");
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        return uploadResult;
    }

}
