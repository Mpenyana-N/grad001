package com.enviro.assessment.grad001.NickMpenyana.grad001.controller;

import com.enviro.assessment.grad001.NickMpenyana.grad001.entity.FileData;
import com.enviro.assessment.grad001.NickMpenyana.grad001.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }
            FileData uploadedFile = fileService.storeFile(file);

            return ResponseEntity.ok("File uploaded successfully. Name: " + uploadedFile.getFileName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable Long id) throws RuntimeException {
        try {
            FileData fileData = fileService.getFileById(id);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileData.getFileName() + "\"")
                    .body(fileData.getData());

        } catch (RuntimeException e) {
            throw new RuntimeException("File Not found");
        }
    }
}