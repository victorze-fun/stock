package com.victorze.stock.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.victorze.stock.services.CloudinaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public Map<String, Object> uploadFile(@RequestParam MultipartFile file) throws IOException {
        var result = cloudinaryService.uploadFile(file);
        System.out.println(result.get("url"));
        return result;
    }
}
