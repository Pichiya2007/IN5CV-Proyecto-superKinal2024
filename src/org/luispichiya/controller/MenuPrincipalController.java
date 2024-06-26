/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luispichiya.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.luispichiya.system.Main;

/**
 *
 * @author Luis Pichiya
 */
public class MenuPrincipalController implements Initializable {
    
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnTicketSoporte, btnCargos, btnDistribuidor, btnCategoriaProducto, btnPromociones, btnProducto, btnEmpleados, btnFacturas;  
    @Override
    public void initialize(URL location, ResourceBundle resources){
    
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    } 
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            stage.menuClientesView();
        }else if(event.getSource() == btnTicketSoporte){
            stage.menuTicketSoporteView();
        }else if (event.getSource() == btnCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnDistribuidor){
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnCategoriaProducto){
            stage.menuCategoriaProductoView();
        }else if(event.getSource() == btnPromociones){
            stage.menuPromocionesView();
        }else if(event.getSource() == btnProducto){
            stage.menuProductosView();
        }else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnFacturas){
            stage.menuFacturasView();
        }
    }
}
