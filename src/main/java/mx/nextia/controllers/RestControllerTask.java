/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.controllers;

import java.util.Date;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import mx.nextia.dto.CreateTaskDTO;
import mx.nextia.dto.UpdateTaskDTO;
import mx.nextia.entities.Task;
import mx.nextia.entities.User;
import mx.nextia.repositories.RepositoryTask;
import mx.nextia.repositories.RepositoryUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{userId}")
    public ResponseEntity<?> findTask(
            @PathVariable int userId,
            @RequestParam(name = "page", required = false) int page,
            @RequestParam(name = "take", required = false) int take
    ) {
        return null;
    }

    @PostMapping("/{userId}")
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
            task.setUpdatedAt(new Date());
            
            task = repositoryTask.save(task);
            
            return ResponseEntity.ok(task);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int taskId) {
        try {
            Task task = repositoryTask.findById(taskId)
                    .orElseThrow(()->new Exception("tarea no encontrada"));
            
            repositoryTask.delete(task);
            
            return ResponseEntity.ok(task);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
