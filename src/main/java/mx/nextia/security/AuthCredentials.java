/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.security;

import lombok.Data;

/**
 *
 * @author luisucan
 */
@Data
public class AuthCredentials {
    private String username;
    private String password;
}
