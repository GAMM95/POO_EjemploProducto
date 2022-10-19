create database bdejemplo1;
use bdejemplo1;

create table producto(
	idproducto char(6) not null,
    descripcion varchar(60) not null,
    precio double not null,
    stock int not null,
    primary key(idproducto)
);    


DELIMITER $$
create procedure spInsertarProducto(_idproducto char(6), _descripcion varchar(60), _precio double, _stock int, out estado tinyint)
begin
	if (select count(idproducto) from producto where idproducto=_idproducto) = 1 then
		select 0 into estado; -- Ya existe no se puede agregar
	else
      insert into producto values(_idproducto,_descripcion,_precio,_stock);
      select 1 into estado;
    end if;
end
$$
DELIMITER ;

DELIMITER $$
create procedure spBuscarProducto(_idproducto char(6))
begin
	select * from producto where idproducto=_idproducto;
end
$$
DELIMITER ;


DELIMITER $$
create procedure spModificarProducto(_idproducto char(6), _descripcion varchar(60), _precio double, _stock int)
begin
	update producto set descripcion=_descripcion,precio=_precio,stock=_stock where idproducto=_idproducto;
end
$$
DELIMITER ;

DELIMITER $$
create procedure spEliminarProducto(_idproducto char(6))
begin
	delete from producto where idproducto=_idproducto;
end
$$
DELIMITER ;

DELIMITER $$
create procedure spMostrarProductos()
begin
	select * from producto; 
end
$$
DELIMITER ;

create view vproductos as
select * from producto;

select * from vproductos;


