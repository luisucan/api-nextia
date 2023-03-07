/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 *
 * @author luisucan
 */
@Entity
@Data
public class FileTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskId;
    
    private String originalName;
    private Long size;
    private String mineType;
}
