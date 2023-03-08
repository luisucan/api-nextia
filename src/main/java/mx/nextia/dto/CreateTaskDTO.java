/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.nextia.dto;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author luisucan
 */
@Data
public class CreateTaskDTO {
    private String title;
    private String description;
    private boolean estatus;
    private Date createdAt;
}
