package com.WalkiePaw.presentation.domain.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/uploads")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        String uploadURL = uploadService.upload(file);
        return ResponseEntity.ok(uploadURL);
    }
}
