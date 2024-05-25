/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luispichiya.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.luispichiya.dao.Conexion;
import org.luispichiya.model.CategoriaProducto;
import org.luispichiya.model.Distribuidor;
import org.luispichiya.model.Producto;
import org.luispichiya.system.Main;
import org.luispichiya.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Luis Pichiya
 */
public class MenuProductoController implements Initializable {
    
    
    private Main stage;    
    private int op;
        
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private List<File> files = null;
    
    @FXML
    ComboBox cmbDistribuidores, cmbCategorias;
    @FXML
    Button btnRegresar, btnGuardar, btnBuscar, btnEliminar;
    @FXML
    TextField tfProductoId, tfNombreProducto, tfUnidad, tfMayor, tfCompra, tfDistribuidor, tfCategoria, tfStock;
    @FXML
    TableView tblProductos;
    @FXML
    TableColumn colProductoId, colNombre, colDescripcion, colUnidad, colMayor, colStock, colDistribuidor, colCategoria;
    @FXML
    TextArea taDescripcionProducto;
    @FXML
    ImageView imgCargar, imgMostrar;
    @FXML
    Label lblNombreProducto, lblStock, lblUnitario, lblMayor, lblCompra;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarProductos();
        cmbDistribuidores.setItems(listarDistribuidores());
        cmbCategorias.setItems(listarCategorias());
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        try{
            if(event.getSource() == btnRegresar){
                stage.menuPrincipalView();
            }else if(event.getSource() == btnGuardar){
                agregarProducto();
                cargarDatos();
            }else if(event.getSource() == btnBuscar){
                Producto producto = buscarProducto();
                if(producto != null){
                    lblNombreProducto.setText(producto.getNombreProducto());
                    lblStock.setText(Integer.toString(producto.getCantidadStock()));
                    lblUnitario.setText(Double.toString(producto.getPrecioVentaUnitario()));
                    lblMayor.setText(Double.toString(producto.getPrecioVentaMayor()));
                    lblCompra.setText(Double.toString(producto.getPrecioCompra()));
                    InputStream file = producto.getImagenProducto().getBinaryStream();
                    Image image = new Image(file);
                    imgMostrar.setImage(image);
                }
            }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                int proId = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getProductoId();
                eliminarProducto(proId);
                cargarDatos();
            }    
        }           
        }catch(Exception e){
            e.printStackTrace();
        }             
    }
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new  FileInputStream(files.get(0));
            Image image = new Image(file);    
            imgCargar.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void cargarDatos(){
        tblProductos.setItems(listarProductos());
        colProductoId.setCellValueFactory(new PropertyValueFactory<Producto, Integer> ("productoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Producto, String> ("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String> ("descripcionProductos"));
        colStock.setCellValueFactory(new PropertyValueFactory<Producto, String> ("cantidadStock"));
        colUnidad.setCellValueFactory(new PropertyValueFactory<Producto, String> ("precioVentaUnitario"));
        colMayor.setCellValueFactory(new PropertyValueFactory<Producto, String> ("precioVentaMayor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String> ("distribuidor"));
        colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto, String> ("categoriaProducto"));
        tblProductos.getSortOrder().add(colProductoId);
    }
    
    public ObservableList<Producto> listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProductos";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int stock = resultSet.getInt("cantidadStock");
                double unidad = resultSet.getDouble("precioVentaUnitario");
                double mayor = resultSet.getDouble("precioVentaMayor");
                String distribuidor = resultSet.getString("Distribuidor");
                String categoria = resultSet.getString("Categoria");

                productos.add(new Producto(productoId, nombre, descripcion, stock, unidad, mayor, distribuidor,categoria));
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
        
        return FXCollections.observableList(productos);
    }
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreProducto.getText());
            statement.setString(2, taDescripcionProducto.getText());
            statement.setString(3, tfStock.getText());
            statement.setString(4, tfUnidad.getText());
            statement.setString(5, tfMayor.getText());
            statement.setString(6, tfCompra.getText());
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(7,img);
            statement.setInt(8,((Distribuidor)cmbDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId());
            //statement.setString(8, tfDistribuidor.getText());
            statement.setInt(9,((CategoriaProducto)cmbCategorias.getSelectionModel().getSelectedItem()).getCategoriaProductoId());
            //statement.setString(9, tfCategoria.getText());
            statement.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Producto buscarProducto(){
        Producto producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
               int productoId =  resultSet.getInt("productoId");
               String nombre = resultSet.getString("nombreProducto");
               int stock =  resultSet.getInt("cantidadStock");
               double  unitario = resultSet.getDouble("precioVentaUnitario");
               double  mayor = resultSet.getDouble("precioVentaMayor");
               double  compra = resultSet.getDouble("precioCompra");
               Blob imagenProducto = resultSet.getBlob("imagenProducto");
               
               producto = new Producto(productoId, nombre,stock,unitario,mayor,compra, imagenProducto);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(conexion != null){
                    conexion.close();
                }else if(statement != null){
                    statement.close();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return producto;
    }
    
    public void eliminarProducto(int proId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, proId);
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
    
    public ObservableList<Distribuidor> listarDistribuidores(){
        ArrayList<Distribuidor> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores.add(new Distribuidor(distribuidorId,nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web));
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
        
        return FXCollections.observableList(distribuidores);
    }
    
    public ObservableList<CategoriaProducto> listarCategorias(){
        ArrayList<CategoriaProducto> categorias = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categorias.add(new CategoriaProducto(categoriaProductosId,nombreCategoria,descripcionCategoria));
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
        return FXCollections.observableList(categorias);
    }
 
    public int obtenerIndexDistribuidor(){
        int index = 0;
        for(int i = 0 ; i <= cmbDistribuidores.getItems().size() ; i++){
            String distribuidoresCmb = cmbDistribuidores.getItems().get(i).toString();
            String distribuidorTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItems()).getDistribuidor();
            if(distribuidoresCmb.equals(distribuidorTbl)){
                index = i;
                break;
            }           
        }
        return index;
    }
    
    public int obtenerIndexCategoria(){
        int index = 0;
        for(int i = 0 ; i <= cmbCategorias.getItems().size() ; i++){
            String categoriaCmb = cmbCategorias.getItems().get(i).toString();
            String categoriaTbl = ((Producto)tblProductos.getSelectionModel().getSelectedItems()).getCategoriaProducto();
            if(categoriaCmb.equals(categoriaTbl)){
                index = i;
                break;
            }           
        }
        return index;
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
