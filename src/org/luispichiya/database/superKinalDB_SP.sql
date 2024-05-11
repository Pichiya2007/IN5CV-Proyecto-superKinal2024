use superKinalDB;

-- *********************** CRUD Cliente ************************** --
-- AGREGAR
DELIMITER $$	
create procedure sp_agregarClientes(nom varchar(30), ape varchar(30), tel varchar(15), dir varchar(150), nit varchar(15))
begin
	insert into Clientes(nombre, apellido, telefono, direccion, nit) values
		(nom, ape, tel, dir, nit);
end $$
DELIMITER ;

-- LISTAR
DELIMITER $$
create procedure sp_listarClientes()
begin
	select * from clientes;
end $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
create procedure sp_EliminarClientes(cliId int)
begin
	delete from Clientes
		where clienteId = cliId;
end $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
create procedure sp_BuscarCliente(cliId int)
begin
	select * from Clientes
		where clienteId = cliId;
end $$
DELIMITER ;

call sp_buscarCliente(1);

-- EDITAR
DELIMITER $$
create procedure sp_EditarCliente(cliId int, nom varchar(30), ape varchar(30), tel varchar(15), dir varchar(150), nt varchar(15))
begin
	update Clientes set
		nombre = nom,
        apellido = ape,
        telefono = tel,
        direccion = dir,
        nit = nt
			where clienteId = cliId;
end $$
DELIMITER ;

-- *********************** CRUD Distribuidores ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarDistribuidor(nom varchar(30),dir varchar(200),nit varchar(15),tel varchar(15),web varchar(50))
BEGIN
    INSERT INTO Distribuidores(nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web)
    VALUES (nom, dir, nit, tel, web);
END $$
DELIMITER 

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarDistribuidores()
BEGIN
    SELECT * FROM Distribuidores;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarDistribuidor(disId int)
BEGIN
    DELETE FROM Distribuidores WHERE distribuidorId = disId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarDistribuidor(disId int)
BEGIN
    SELECT * FROM Distribuidores WHERE distribuidorId = disId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarDistribuidor(disId int,nom varchar(30),dir varchar(200),nit varchar(15),tel varchar(15),web varchar(50))
BEGIN
    UPDATE Distribuidores
    SET
        nombreDistribuidor = nom,
        direccionDistribuidor = dir,
        nitDistribuidor = nit,
        telefonoDistribuidor = tel,
        web = web
    WHERE distribuidorId = disId;
END $$
DELIMITER ;


-- *********************** CRUD CategoriaProductos ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarCategoriaProducto(nom varchar(30),des varchar(100))
BEGIN
    INSERT INTO CategoriaProductos(nombreCategoria, descripcionCategoria)
    VALUES (nom, des);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarCategoriaProductos()
BEGIN
    SELECT * FROM CategoriaProductos;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarCategoriaProducto(catId int)
BEGIN
    DELETE FROM CategoriaProductos WHERE categoriaProductosId = catId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarCategoriaProducto(catId int)
BEGIN
    SELECT * FROM CategoriaProductos WHERE categoriaProductosId = catId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarCategoriaProducto(catId int,nom varchar(30),des varchar(100))
BEGIN
    UPDATE CategoriaProductos
    SET
        nombreCategoria = nom,
        descripcionCategoria = des
    WHERE categoriaProductosId = catId;
END $$
DELIMITER ;

-- *********************** CRUD Productos ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarProducto(nom varchar(50),des varchar(100),cant int,pvu decimal(10,2),pvm decimal(10,2),pc decimal(10,2),img BLOB,disId int,catId int)
BEGIN
    INSERT INTO Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId)
    VALUES (nom, des, cant, pvu, pvm, pc, img, disId, catId);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarProductos()
BEGIN
    SELECT * FROM Productos;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarProducto(prodId int)
BEGIN
    DELETE FROM Productos WHERE productoId = prodId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarProducto(prodId int)
BEGIN
    SELECT * FROM Productos WHERE productoId = prodId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarProducto(prodId int,nom varchar(50),des varchar(100),cant int,pvu decimal(10,2),pvm decimal(10,2),pc decimal(10,2),img BLOB,disId int,catId int)
BEGIN
    UPDATE Productos
    SET
        nombreProducto = nom,
        descripcionProducto = des,
        cantidadStock = cant,
        precioVentaUnitario = pvu,
        precioVentaMayor = pvm,
        precioCompra = pc,
        imagenProducto = img,
        distribuidorId = disId,
        categoriaProductosId = catId
    WHERE productoId = prodId;
END $$
DELIMITER ;


-- *********************** CRUD Cargo ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarCargo(nom varchar(30),des varchar(100))
BEGIN
    INSERT INTO Cargos(nombreCargo, descripcionCargo)
    VALUES (nom, des);
END $$
DELIMITER ;

call sp_agregarCargo('Gerente de Ventas', 'Encargado del departamento de ventas y marketing.');

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarCargos()
BEGIN
    SELECT * FROM Cargos;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarCargo(cargoId int)
BEGIN
    DELETE FROM Cargos WHERE cargoId = cargoId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarCargo(cargoId int)
BEGIN
    SELECT * FROM Cargos WHERE cargoId = cargoId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarCargo(cargoId int,nom varchar(30),des varchar(100))
BEGIN
    UPDATE Cargos
    SET
        nombreCargo = nom,
        descripcionCargo = des
    WHERE cargoId = cargoId;
END $$
DELIMITER ;


-- *********************** CRUD Empleados ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarEmpleado(nom varchar(30),ape varchar(30),sue decimal(10,2),he time,hs time,carId int,encId int)
BEGIN
    INSERT INTO Empleados(nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId)
    VALUES (nom, ape, sue, he, hs, carId, encId);
END $$
DELIMITER ;

call sp_agregarEmpleado('Juan', 'Pérez', 2000.00, '08:00:00', '17:00:00', 1, NULL);

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarEmpleados()
BEGIN
    SELECT * FROM Empleados;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarEmpleado(empId int)
BEGIN
    DELETE FROM Empleados WHERE empleadoId = empId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarEmpleado(empId int)
BEGIN
    SELECT * FROM Empleados WHERE empleadoId = empId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarEmpleado(empId int,nom varchar(30),ape varchar(30),sue decimal(10,2),he time,hs time,carId int,encId int)
BEGIN
    UPDATE Empleados
    SET
        nombreEmpleado = nom,
        apellidoEmpleado = ape,
        sueldo = sue,
        horaEntrada = he,
        horaSalida = hs,
        cargoId = carId,
        encargadoId = encId
    WHERE empleadoId = empId;
END $$
DELIMITER ;

-- ASIGNAR ENCARGADO
DELIMITER $$
CREATE PROCEDURE sp_asignarEncargado(empId int, encId int)
BEGIN
	UPDATE empleados 
		set encargadoId = encId
			where empleadoId = empId;
END $$
DELIMITER ;

-- *********************** CRUD Facturas ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarFactura(cliId int,empId int)
BEGIN
    INSERT INTO Facturas(fecha, hora, clienteId, empleadoId)
    VALUES (DATE(NOW()), TIME(NOW()), cliId, empId);
END $$
DELIMITER ;

call sp_agregarFactura(1, 1);

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarFacturas()
BEGIN
    SELECT * FROM Facturas;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarFactura(facId int)
BEGIN
    DELETE FROM Facturas WHERE facturaId = facId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarFactura(facId int)
BEGIN
    SELECT * FROM Facturas WHERE facturaId = facId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarFactura(facId int,fec date,hor time,cliId int,empId int,tot decimal(10,2))
BEGIN
    UPDATE Facturas
    SET
        fecha = fec,
        hora = hor,
        clienteId = cliId,
        empleadoId = empId,
        total = tot
    WHERE facturaId = facId;
END $$
DELIMITER ;

-- *********************** CRUD DetalleFactura ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarDetalleFactura(facId int,prodId int)
BEGIN
    INSERT INTO DetalleFactura(facturaId, productoId)
    VALUES (facId, prodId);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarDetalleFacturas()
BEGIN
    SELECT * FROM DetalleFactura;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarDetalleFactura(detFacId int)
BEGIN
    DELETE FROM DetalleFactura WHERE detalleFacturaId = detFacId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarDetalleFactura(detFacId int)
BEGIN
    SELECT * FROM DetalleFactura WHERE detalleFacturaId = detFacId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarDetalleFactura(detFacId int,facId int,prodId int)
BEGIN
    UPDATE DetalleFactura
    SET
        facturaId = facId,
        productoId = prodId
    WHERE detalleFacturaId = detFacId;
END $$
DELIMITER ;

-- *********************** CRUD Compras ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarCompra(fec date,tot decimal(10,2))
BEGIN
    INSERT INTO Compras(fechaCompra, totalCompra)
    VALUES (fec, tot);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarCompras()
BEGIN
    SELECT * FROM Compras;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarCompra(comId int)
BEGIN
    DELETE FROM Compras WHERE compraId = comId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarCompra(comId int)
BEGIN
    SELECT * FROM Compras WHERE compraId = comId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarCompra(comId int,fec date,tot decimal(10,2))
BEGIN
    UPDATE Compras
    SET
        fechaCompra = fec,
        totalCompra = tot
    WHERE compraId = comId;
END $$
DELIMITER ;


-- *********************** CRUD DetalleCompras ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarDetalleCompra(cant int,prodId int,comId int)
BEGIN
    INSERT INTO DetalleCompra(cantidadCompra, productoId, compraId)
    VALUES (cant, prodId, comId);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarDetalleCompras()
BEGIN
    SELECT * FROM DetalleCompra;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarDetalleCompra(detComId int)
BEGIN
    DELETE FROM DetalleCompra WHERE detalleCompraId = detComId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarDetalleCompra(detComId int)
BEGIN
    SELECT * FROM DetalleCompra WHERE detalleCompraId = detComId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarDetalleCompra(detComId int,cant int,prodId int,comId int)
BEGIN
    UPDATE DetalleCompra
    SET
        cantidadCompra = cant,
        productoId = prodId,
        compraId = comId
    WHERE detalleCompraId = detComId;
END $$
DELIMITER ;


-- *********************** CRUD Promociones ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarPromocion(pre decimal(10,2),des varchar(100),fecIni date,fecFin date,prodId int)
BEGIN
    INSERT INTO Promociones(precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId)
    VALUES (pre, des, fecIni, fecFin, prodId);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarPromociones()
BEGIN
    SELECT P.promocionId,  P.precioPromocion, P.descripcionPromocion, P.fechaInicio, P.fechaFinalizacion,
    CONCAT('Id: ', Pr.productoId, ' | ' , Pr.nombreProducto) AS 'producto' from Promociones P
    JOIN Productos Pr on P.productoId = Pr.productoId;
END $$
DELIMITER ;

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarPromocion(promId int)
BEGIN
    DELETE FROM Promociones WHERE promocionId = promId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarPromocion(promId int)
BEGIN
    SELECT * FROM Promociones WHERE promocionId = promId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarPromocion(promId int,pre decimal(10,2),des varchar(100),fecIni date,fecFin date,prodId int)
BEGIN
    UPDATE Promociones
    SET
        precioPromocion = pre,
        descripcionPromocion = des,
        fechaInicio = fecIni,
        fechaFinalizacion = fecFin,
        productoId = prodId
    WHERE promocionId = promId;
END $$
DELIMITER ;

-- *********************** CRUD TicketSoporte ************************** --
-- AGREGAR
DELIMITER $$
CREATE PROCEDURE sp_agregarTicketSoporte(des varchar(250), cliId int, facId int)
BEGIN
    INSERT INTO TicketSoporte(descripcionTicket, estatus, clienteId, facturaId)
    VALUES (des, 'Recién Creado', cliId, facId);
END $$
DELIMITER ;

-- LISTAR
DELIMITER $$
CREATE PROCEDURE sp_listarTicketsSoporte()
BEGIN
    select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus,
			CONCAT('Id: ', C.clienteId, ' | ', C.nombre, ' ', C.apellido) AS 'cliente',
            TS.facturaId from TicketSoporte TS
    join Clientes C on TS.clienteId = C.clienteId;
END $$
DELIMITER ;

call sp_listarTicketsSoporte();

-- ELIMINAR
DELIMITER $$
CREATE PROCEDURE sp_eliminarTicketSoporte(ticSopId int)
BEGIN
    DELETE FROM TicketSoporte WHERE ticketSoporteId = ticSopId;
END $$
DELIMITER ;

-- BUSCAR
DELIMITER $$
CREATE PROCEDURE sp_buscarTicketSoporte(ticSopId int)
BEGIN
    SELECT * FROM TicketSoporte WHERE ticketSoporteId = ticSopId;
END $$
DELIMITER ;

-- EDITAR
DELIMITER $$
CREATE PROCEDURE sp_editarTicketSoporte(ticSopId int,des varchar(250),est varchar(30),cliId int,facId int)
BEGIN
    UPDATE TicketSoporte
    SET
        descripcionTicket = des,
        estatus = est,
        clienteId = cliId,
        facturaId = facId
    WHERE ticketSoporteId = ticSopId;
END $$
DELIMITER ;


call sp_listarTicketsSoporte;