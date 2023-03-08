/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.dto;

import lombok.Data;

/**
 *
 * @author luisucan
 */
@Data
public class CreateUserDTO {
    private String username;
    
    private String name;
    private String lastName;
    
    private String password;
}
