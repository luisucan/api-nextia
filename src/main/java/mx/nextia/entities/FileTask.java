/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author luisucan
 */
@Entity
@Table(name = "files_tasks")
@Data
public class FileTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileTaskId;
    
    private String originalName;
    private Long size;
    private String mineType;
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Task task;
}
