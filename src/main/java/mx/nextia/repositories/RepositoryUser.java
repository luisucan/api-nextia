/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.repositories;

import java.util.Optional;
import mx.nextia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luisucan
 */
public interface RepositoryUser extends JpaRepository<User, Integer> {
    Optional<User> findOneByUsername(String username);
}
