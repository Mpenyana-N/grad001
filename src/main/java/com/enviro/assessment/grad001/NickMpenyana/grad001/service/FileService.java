package com.enviro.assessment.grad001.NickMpenyana.grad001.service;

import com.enviro.assessment.grad001.NickMpenyana.grad001.entity.FileData;
import com.enviro.assessment.grad001.NickMpenyana.grad001.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileData storeFile(MultipartFile file) throws RuntimeException {
        String fileName = file.getOriginalFilename();
        try {
            FileData fileData = new FileData();
            fileData.setFileName(fileName);
            fileData.setFileType(file.getContentType());
            fileData.setData(file.getBytes());
            return fileRepository.save(fileData);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + fileName, e);
        }
    }
    public FileData getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + id));
    }
}
