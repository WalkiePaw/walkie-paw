package com.WalkiePaw.presentation.domain.upload;

import com.WalkiePaw.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/uploads")
public class UploadController {

    private final UploadService uploadService;
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<UploadUrlResponse> uploadImage(@RequestParam MultipartFile file) {
        String uploadURL = uploadService.upload(file);
        return ResponseEntity.ok(new UploadUrlResponse(uploadURL));
    }

    @PostMapping("board/{id}")
    public ResponseEntity<UploadUrlResponse> uploadBoardImage(
            @PathVariable("id") Integer boardId,
            @RequestParam MultipartFile file) {
        String uploadURL = uploadService.upload(file);
        boardService.uploadPhoto(boardId, uploadURL, file.getOriginalFilename());
        return ResponseEntity.ok(new UploadUrlResponse(uploadURL));
    }
}
