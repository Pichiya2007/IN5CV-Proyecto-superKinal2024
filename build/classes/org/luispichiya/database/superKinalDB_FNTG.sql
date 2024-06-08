use superKinalDB;

DELIMITER $$
create function fn_calcularTotal(facId int) returns decimal (10,2)
deterministic
BEGIN
	declare totalFinal decimal(10,2) default 0.0;
    declare Iva decimal(10,2) default 0.0;
    declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    
    declare cursorDetalleFactura cursor for
		select DF.facturaId, DF.productoId from DetalleFactura DF ;
        
	open cursorDetalleFactura;
    
    totalLoop : loop
    
    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    
     if facId = curFacturaId then
		set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
    
    set totalFinal = totalFinal + precio;
    set Iva = totalFinal * 0.12;
    set total = totalFinal + Iva;
    
    end if;
        
	if i = (select count(*) from DetalleFactura) then
			leave totalLoop;
		end if;
	
    set i = i + 1;
    
    end loop totalLoop;
    
    call sp_asignarTotal(total, facId);
    
    return total;
END $$
DELIMITER ;

-- trigger
DELIMITER $$
create trigger tg_totalFactura
after insert on DetalleFactura
for each row
BEGIN
	DECLARE total Decimal(10,2);
    set total = fn_calcularTotal(NEW.facturaId);
END $$
DELIMITER ;

-- Manejo Stock
DELIMITER $$
create trigger tg_manejoStock
before insert on detalleFactura
for each row
BEGIN
	if(select Productos.cantidadStock FROM Productos where productoId = NEW.productoId) = 0 then
		SIGNAL sqlstate '45000'
			SET message_text = 'Lo siento. Ya no hay productos para comprar';
	else
		update Productos P
			set P.cantidadStock = (P.cantidadStock - 1) where productoId = NEW.productoId;
		end if;
END $$
DELIMITER ;

-- ------------------------------
DELIMITER $$
CREATE trigger tg_manejoStockCompra
after insert on DetalleCompra
for each row
Begin
	Update Productos 
		Set cantidadStock = cantidadStock + NEW.cantidadCompra
        Where productoId = NEW.productoId;
        
END $$
DELIMITER ;