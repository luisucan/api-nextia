/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.repositories;

import mx.nextia.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luisucan
 */
public interface RepositoryTask extends JpaRepository<Task, Integer> {
    
}
