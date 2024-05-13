/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luispichiya.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.luispichiya.dao.Conexion;
import org.luispichiya.dto.EmpleadoDTO;
import org.luispichiya.model.Cargo;
import org.luispichiya.model.Empleado;
import org.luispichiya.system.Main;

/**
 * FXML Controller class
 *
 * @author Luis Pichiya
 */
public class FormEmpleadosController implements Initializable {

    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    /**
     * Initializes the controller class.
     */
    @FXML
    TextField tfEmpleadoId, tfNombreEmpleado, tfApellidoEmpleado, tfSueldo,tfHoraEntrada,tfHoraSalida;        
    @FXML
    Button btnAgregar,btnCancelar,btnRegresar;           
    @FXML
    ComboBox cmbCargoId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCargoId.setItems(listarCargos());
        if(EmpleadoDTO.getEmpleadoDTO().getEmpleado() != null){
            cargarDatos(EmpleadoDTO.getEmpleadoDTO().getEmpleado());
        }
    }   
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuEmpleadosView();
            EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
        }else if(event.getSource() == btnAgregar){
            if(op == 1){
                agregarEmpleado();
                stage.menuEmpleadosView();   
            } else if(op == 2){
                editarEmpleado();
                EmpleadoDTO.getEmpleadoDTO().setEmpleado(null);
                stage.menuEmpleadosView();
            }
        }
    }
    
    public void cargarDatos(Empleado empleado){
        tfEmpleadoId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombreEmpleado.setText(empleado.getNombreEmpleado());
        tfApellidoEmpleado.setText(empleado.getApellidoEmpleado());
        double sueldo = empleado.getSueldo();
        tfSueldo.setText(Double.toString(sueldo));
        tfHoraEntrada.setText(empleado.getHoraEntrada());
        tfHoraSalida.setText(empleado.getHoraSalida());
        cmbCargoId.getSelectionModel().select(ObtenerIndexEmpleado());
        
        
    }
    
    
    public int ObtenerIndexEmpleado(){
       int index = 0;
        for(int i = 0 ; i <= cmbCargoId.getItems().size() ; i++){
            String cargoIdCmb = cmbCargoId.getItems().get(i).toString();
            String empleadoDto = EmpleadoDTO.getEmpleadoDTO().getEmpleado().getCargo();
            if(cargoIdCmb.equals(empleadoDto)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void cargarCmbEstatus(){
        cmbCargoId.getItems().add("");
    }
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombreEmpleado.getText());
            statement.setString(3, tfApellidoEmpleado.getText());
            double sueldo = Double.parseDouble(tfSueldo.getText());
            statement.setDouble(4, sueldo);
            statement.setString(5, tfHoraEntrada.getText());
            statement.setString(6, tfHoraSalida.getText());
            statement.setInt(7, ((Cargo)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();

        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql =  "call sp_agregarEmpleado(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreEmpleado.getText());
            statement.setString(2, tfApellidoEmpleado.getText());
            double sueldo = Double.parseDouble(tfSueldo.getText());
            statement.setDouble(3, sueldo);
            statement.setString(4, tfHoraEntrada.getText());
            statement.setString(5, tfHoraSalida.getText());
            statement.setInt(6, ((Cargo)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public ObservableList<Cargo> listarCargos(){
         ArrayList<Cargo> cargos = new ArrayList<>();
         try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableList(cargos);
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }      
}
