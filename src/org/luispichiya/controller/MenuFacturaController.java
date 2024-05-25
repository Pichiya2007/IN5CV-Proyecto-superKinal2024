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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.luispichiya.dao.Conexion;
import org.luispichiya.model.Cliente;
import org.luispichiya.model.Empleado;
import org.luispichiya.model.Factura;
import org.luispichiya.system.Main;

/**
 * FXML Controller class
 *
 * @author Luis Pichiya
 */
public class MenuFacturaController implements Initializable {

    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox cmbEmpleadoId, cmbClienteId;
    @FXML
    Button btnRegresar, btnEliminar, btnGuardar, btnVaciar;
    @FXML
    TableView tblFacturas;
    @FXML
    TextField tfFacturaId;
    @FXML
    TableColumn colFacturaId, colFecha, colHora, colClienteId, colEmpleadoId, colTotal;
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbEmpleadoId.setItems(listarEmpleados());
        cmbClienteId.setItems(listarClientes());
        cargarDatos();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        } else if(event.getSource() == btnGuardar){
            if(tfFacturaId.getText().equals("")){
                agregarFactura();
                cargarDatos();
            } else{
                editarFactura();
                cargarDatos();
            }
        } else if(event.getSource() == btnVaciar){
            vaciarDatos();
        }
    }
    
    public void vaciarDatos(){
        tfFacturaId.clear();
        cmbClienteId.getSelectionModel().clearSelection();
        cmbEmpleadoId.getSelectionModel().clearSelection();
    }
    
    public void agregarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarFactura(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Cliente)cmbClienteId.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(2, ((Empleado)cmbEmpleadoId.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
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
    }
    
    public void cargarDatos(){
        tblFacturas.setItems(listarFactura());
        colFacturaId.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("facturaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, String>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<Factura, String>("hora"));
        colClienteId.setCellValueFactory(new PropertyValueFactory<Factura, String>("cliente"));
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Factura, String>("empleado"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Double>("total"));
        tblFacturas.getSortOrder().add(colFacturaId);
    }
    
    public void cargarDatosEditar(){
        Factura ts = (Factura)tblFacturas.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfFacturaId.setText(Integer.toString(ts.getFacturaId()));
            cmbClienteId.getSelectionModel().select(obtenerIndexCliente());
            cmbEmpleadoId.getSelectionModel().select(obtenerIndexEmpleado());           
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        for(int i = 0 ; i <= cmbClienteId.getItems().size() ; i++){
            String clienteIdCmb = cmbClienteId.getItems().get(i).toString();
            String clienteTbl = ((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getCliente();
            if(clienteIdCmb.equals(clienteTbl)){
                index = i;
                break;
            }
        }        
        return index;
    }
    
    public int obtenerIndexEmpleado(){
        int index = 0;
        for(int i = 0 ; i <= cmbEmpleadoId.getItems().size() ; i++){
            String empleadoIdCmb = cmbEmpleadoId.getItems().get(i).toString();
            String empleadoTbl = ((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getEmpleado();
            if(empleadoIdCmb.equals(empleadoTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<Factura> listarFactura(){
        ArrayList<Factura> factura = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                String fecha = resultSet.getString("fecha");
                String hora = resultSet.getString("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
                
                factura.add(new Factura(facturaId,fecha,hora,cliente,empleado,total));
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
        
        return FXCollections.observableList(factura);
    }
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarclientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        
        return FXCollections.observableList(clientes);
    }
    
    public ObservableList<Empleado> listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_listarEmpleados()";
           statement = conexion.prepareStatement(sql);
           resultSet = statement.executeQuery();
           while(resultSet.next()){
               int empleadoId = resultSet.getInt("empleadoId");
               String nombreEmpleado = resultSet.getString("nombreEmpleado");
               String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
               Double sueldo = resultSet.getDouble("sueldo");
               String horaEntrada = resultSet.getString("horaEntrada");
               String horaSalida = resultSet.getString("horaSalida");
               String cargo = resultSet.getString("cargo");
               String encargado = resultSet.getString("encargado");
               
               empleados.add(new Empleado(empleadoId,nombreEmpleado,apellidoEmpleado,sueldo,horaEntrada,horaSalida,cargo,encargado));        
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
        
        return FXCollections.observableList(empleados);
    }
    
    public void editarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarFactura(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            statement.setInt(2, ((Cliente)cmbClienteId.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, ((Empleado)cmbEmpleadoId.getSelectionModel().getSelectedItem()).getEmpleadoId());
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
     
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }      
}
