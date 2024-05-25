use superKinalDB;

delimiter $$
create trigger tg_restarStock
before insert on detalleFactura
for each row
begin
    if (select P.cantidadStock from Productos P where productoId = NEW.productoId) = 0 then
		signal sqlstate'45000'
			set message_text="No contamos con este producto en stock";
    else
		call sp_manejoStock(new.productoId);
	end if;
end $$
delimiter ;