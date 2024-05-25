/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luispichiya.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Luis Pichiya
 */
public class SuperKinalAlert {
    
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
        
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
            
        }
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("Algunos campos son necesarios para el registro estas pendientes");
            alert.showAndWait();
        }else if(code == 401){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion De Registro");
            alert.setHeaderText("Confirmacion De Registro");
            alert.setContentText("El Registro Se Ha Creado Con Exito");
            alert.showAndWait();
        }else if(code == 2007){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario incorrecto");
            alert.setHeaderText("Usuario incorrecto");
            alert.setContentText("Verifique el usuario");
            alert.showAndWait();
        }else if(code == 2008){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña incorrecta");
            alert.setHeaderText("Contraseña incorrecta");
            alert.setContentText("Verifique la contraseña");
            alert.showAndWait();
        }
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional <ButtonType> action = null;
        if(code == 405){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminacion De Registro");
        alert.setHeaderText("Eliminacion De Registro");
        alert.setContentText("¿Desea Confirmar La Eliminacion De Registro?");
        action = alert.showAndWait();
        }else if(code == 406){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edicion De Registro");
        alert.setHeaderText("Edicion De Registro");
        alert.setContentText("¿Desea Confirmar La Edicion De Registro?");
        action = alert.showAndWait();
        }
        return action;
    }
    
    public void alertaSaludo(String usuario){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText("Bienvenido " + usuario);
        alert.showAndWait();
    }
}
