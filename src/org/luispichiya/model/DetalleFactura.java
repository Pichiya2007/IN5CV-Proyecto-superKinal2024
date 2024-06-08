/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luispichiya.model;

/**
 *
 * @author Luis Pichiya
 */
public class DetalleFactura {
    private int detalleFacturaId;
    private String factura;
    private int facturaId;
    private String producto;
    private int productoId;

    public DetalleFactura() {
    }

    public DetalleFactura(int detalleFacturaId, String factura, String producto) {
        this.detalleFacturaId = detalleFacturaId;
        this.factura = factura;
        this.producto = producto;
    }

    public DetalleFactura(int detalleFacturaId, int facturaId, int productoId) {
        this.detalleFacturaId = detalleFacturaId;
        this.facturaId = facturaId;
        this.productoId = productoId;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" + "detalleFacturaId=" + detalleFacturaId + ", factura=" + factura + ", facturaId=" + facturaId + ", producto=" + producto + ", productoId=" + productoId + '}';
    }
}
