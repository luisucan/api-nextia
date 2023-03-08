/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.repositories;

import java.util.List;
import mx.nextia.entities.FileTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luisucan
 */
public interface RepositoryFileTask extends JpaRepository<FileTask, Integer> {
    public List<FileTask> findByTaskTaskId(int taskId);
}
