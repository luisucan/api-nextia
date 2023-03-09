/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import mx.nextia.dto.CreateTaskDTO;
import mx.nextia.dto.UpdateTaskDTO;
import mx.nextia.entities.FileTask;
import mx.nextia.entities.Task;
import mx.nextia.entities.User;
import mx.nextia.repositories.RepositoryFileTask;
import mx.nextia.repositories.RepositoryTask;
import mx.nextia.repositories.RepositoryUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author luisucan
 */
@RestController
@RequestMapping("/tasks")
public class RestControllerTask {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RepositoryTask repositoryTask;

    @Autowired
    private RepositoryUser repositoryUser;
    
    @Autowired
    private RepositoryFileTask repositoryFileTask;
    
    @Value("${path.files}")
    private String dir;

    @GetMapping("/{taskId}")
    public ResponseEntity<?> findTask(
            @PathVariable int taskId
    ) {
        try {
            Task task = repositoryTask.findById(taskId)
                    .orElseThrow(()->new Exception());
            
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/page/{userId}")
    public ResponseEntity<?> findTask(
            @PathVariable int userId,
            Pageable pageable
    ) {
        try {
            Page<Task> tasks = repositoryTask.findAllByUserUserId(pageable, userId);
            
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> create(
            @PathVariable int userId,
            @RequestBody CreateTaskDTO taskDTO
    ) {
        try {
            User user = repositoryUser.findById(userId)
                    .orElseThrow(() -> new Exception("usuario no encontrado"));

            Task task = modelMapper.map(taskDTO, Task.class);
            task.setCreatedAt(new Date());
            task.setEstatus(true);
            task.setUser(user);

            task = repositoryTask.save(task);

            return ResponseEntity.ok(task);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> edit(
            @PathVariable int taskId, 
            @RequestBody UpdateTaskDTO taskDTO
    ) {
        try {
            Task task = repositoryTask.findById(taskId)
                    .orElseThrow(()->new Exception("tarea no encontrada")); 
            
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setEstatus(taskDTO.isEstatus());
            task.setUpdatedAt(new Date());
            
            repositoryTask.save(task);
            
            return ResponseEntity.ok(task);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@PathVariable int taskId) {
        try {
            Task task = repositoryTask.findById(taskId)
                    .orElseThrow(()->new Exception("tarea no encontrada"));
            
            List<FileTask> files = repositoryFileTask.findByTaskTaskId(taskId);
            for (FileTask file : files) {
                repositoryFileTask.delete(file);
                Path path = Paths.get(dir);
                Boolean isDelete = Files.deleteIfExists(path.resolve(file.getName()));
            }
            
            repositoryTask.delete(task);
            
            return ResponseEntity.ok(task);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
