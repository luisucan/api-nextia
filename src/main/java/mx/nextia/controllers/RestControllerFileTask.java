/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.UUID;
import mx.nextia.entities.FileTask;
import mx.nextia.entities.Task;
import mx.nextia.repositories.RepositoryFileTask;
import mx.nextia.repositories.RepositoryTask;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author luisucan
 */
@RestController
@RequestMapping("/files")
public class RestControllerFileTask {
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RepositoryFileTask repositoryFileTask;
    
    @Autowired
    private RepositoryTask repositoryTask;
    
    @Value("${path.files}")
    private String dir;
    
    @GetMapping("/{fileTaskId}")
    public ResponseEntity<?> findTask(
            @PathVariable int fileTaskId
    ) {
        try {
            FileTask file = repositoryFileTask.findById(fileTaskId)
                    .orElseThrow(()->new Exception("archivo no encontrado"));
            
            return ResponseEntity.ok(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> findAll(@PathVariable int taskId) {
        try {
            List<FileTask> files = repositoryFileTask.findByTaskTaskId(taskId);
            
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{taskId}")
    public ResponseEntity<?> create(
            @PathVariable int taskId,
            @RequestParam("files") MultipartFile[] files
    ) {
        try {
            Task task = repositoryTask.findById(taskId)
                    .orElseThrow(() -> new Exception("archivo no encontrado"));
            
            Path path = Paths.get(dir);
            
            FileTask file;
            String extension;
            String filename;
            for (MultipartFile fileItem : files) {
                filename = fileItem.getOriginalFilename();
                extension = filename.substring(filename.lastIndexOf(".") + 1);
                
                
                file = new FileTask();
                file.setTask(task);
                file.setOriginalName(fileItem.getOriginalFilename());
                file.setMineType(fileItem.getContentType());
                file.setSize(fileItem.getSize());
                file.setName(UUID.randomUUID() + "." +extension);
                
                Files.copy(fileItem.getInputStream(), path.resolve(file.getName()));
                
                file = repositoryFileTask.save(file);
            }
            
            return ResponseEntity.ok(true);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{fileTaskId}")
    public ResponseEntity<?> delete(@PathVariable int fileTaskId) {
        try {
            FileTask file = repositoryFileTask.findById(fileTaskId)
                    .orElseThrow(()->new Exception("archivo no encontrado"));
            
            repositoryFileTask.delete(file);
            
            Path path = Paths.get(dir);
            
            Boolean isDelete = Files.deleteIfExists(path.resolve(file.getName()));
            
            return ResponseEntity.ok(file);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
