drop database if exists DBKinalExpress2020493;
create database DBKinalExpress2020493;
use DBKinalExpress2020493;
set global time_zone = '-6:00';

create table Clientes(
 codigoCliente int, 
 NITClientes varchar(10), 
 nombresCliente varchar(50), 
 apellidosCliente varchar(50), 
 direccionCliente varchar(150), 
 telefonoCliente varchar(8),
 correoCliente varchar(45),
 primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedor int,
	NITProveedor varchar(10),
	nombresProveedor varchar(60),
	apellidosProveedor varchar(60),
	direccionProveedor varchar(150),
	razonSocial varchar(100),
	contactoPrincipal varchar(100),
	paginaWeb varchar(50),
	primary key PK_codigoProveedor(codigoProveedor)
);

create table TipoProducto(
	codigoTipoProducto int,
	descripcion varchar(45),
	primary key PK_codigoProducto(codigoTipoProducto)
);

create table CargoEmpleado(
	codigoCargoEmpleado int,
	nombreCargo varchar(45),
	descripcionCargo varchar(45),
	primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table Empleados(
	codigoEmpleado int,
	nombresEmpleado varchar(45),
	apellidosEmpleado varchar(45),
	sueldo decimal(10,2),
	direccion varchar(150),
	turno varchar(15),
	CodigoCargoEmpleado int,
	primary key PK_codigoCargoEmpleado(codigoEmpleado ),
	constraint FK_Empleados_CargoEmpleado foreign key Empleados(CodigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);

create table Compras(
	numeroDocumento int,
	fechaDocumento date,
	descripcion varchar(60),
	totalDocumento decimal(10,2),
	primary key PK_numeroDocumento(numeroDocumento)
);

create table Productos(
	codigoProducto varchar(15),
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
	CodigoTipoProducto int,
	CodigoProveedor int,
	primary key PK_codigoProducto(codigoProducto),
	constraint FK_Productos_TipoProducto foreign key Productos(CodigoTipoProducto)
		references TipoProducto(codigoTipoProducto),
	constraint FK_Productos_Proveedores foreign key Productos(CodigoProveedor)
		references Proveedores(codigoProveedor)
);

-- COMPRAS
delimiter $$
create procedure sp_AgregarCompra(in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
begin
	insert into Compras(numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    values(numeroDocumento, fechaDocumento, descripcion, totalDocumento);
end $$
delimiter ;

call sp_AgregarCompra(1,'1960-12-31','Refrescos y lacteos',100.5);
call sp_AgregarCompra(2,'1960-12-31','Refresco',725.5);


delimiter $$
create procedure sp_ListarCompras()
begin
	select * from Compras;
end $$
delimiter ;

call sp_ListarCompras();

delimiter $$
create procedure sp_ActualizarCompras(in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
begin
	update Compras
    set
		Compras.fechaDocumento = fechaDocumento,
        Compras.descripcion = descripcion,
        Compras.totalDocumento = totalDocumento
	where
		Compras.numeroDocumento = numeroDocumento;
end $$
delimiter ;

call sp_ActualizarCompras(2,'2035-05-05','Alcohol  cigarros',452.36);


delimiter $$
create procedure sp_EliminarCompra(in numeroDocumento int)
begin
	delete from Compras where Compras.numeroDocumento = numeroDocumento;
end $$
delimiter ;

call sp_EliminarCompra(1);
-- CLIENTES
delimiter $$
create procedure sp_AgregarClientes(in codigoCliente int, in NITClientes varchar(10), in nombresCliente varchar(50), in apellidosCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
begin
	insert into Clientes(codigoCliente, NITClientes, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente)
    values (codigoCliente, NITClientes, nombresCliente, apellidosCliente, direccionCliente, telefonoCliente, correoCliente);
end $$
delimiter ;
call sp_AgregarClientes(1,'5454852121','Samuel','Perez','12 Calle y 6 Avenida','3441421','Sperez');
call sp_AgregarClientes(2,'4444444444','Luis','Orlando','13 Calle 12 Avenida','3441421','Sperez');


delimiter $$
create procedure sp_ListarClientes()
begin
	select * from Clientes;
end $$
delimiter ;
call sp_ListarClientes();


delimiter $$
create procedure sp_BuscarClientes(in codigoCliente int)
begin
	select * from Clientes where Clientes.codigoCliente = codigoCliente;
end $$
delimiter ;
call sp_BuscarClientes(1);

 
delimiter $$
create procedure sp_ActualizarClientes(in codigoCliente int, in NITClientes varchar(10), in nombresCliente varchar(50), in apellidosCliente varchar(50), in direccionCliente varchar(150), telefonoCliente varchar(8), in correoCliente varchar(45))
begin
	update Clientes
    set
		Clientes.NITClientes = NITClientes,
        Clientes.nombresCliente = nombresCliente,
        Clientes.apellidosCliente = apellidosCliente,
        Clientes.direccionCliente = direccionCliente,
        Clientes.telefonoCliente = telefonoCliente,
        Clientes.correoCliente = correoCliente
	where
		Clientes.codigoCliente = codigoCliente;
end $$
delimiter ;

call sp_ActualizarClientes(1,'5434534','Orlando','Gomez','11 Calle y 10 Avenida','12345678','ogomez');

delimiter $$
create procedure sp_EliminarClientes(in codigoCliente int)
begin
	delete from Clientes where Clientes.codigoCliente = codigoCliente;
end $$
delimiter ;
 
call sp_EliminarClientes(78);


-- TIPO PRDUCTO
delimiter $$
create procedure sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
begin
	insert into TipoProducto(codigoTipoProducto, descripcion)
    values (codigoTipoProducto, descripcion);
end $$
delimiter ;

call sp_AgregarTipoProducto(1,'Producto lactio');
call sp_AgregarTipoProducto(2,'Carnes frias');


delimiter $$
create procedure sp_ListarTipoProducto()
begin
	select * from TipoProducto;
end $$
delimiter ;

call sp_ListarTipoProducto();

delimiter $$
create procedure sp_BuscarTipoProducto(in codigoTipoProducto int)
begin
	select * from TipoProducto where TipoProducto.codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;

call sp_BuscarTipoProducto(2);

delimiter $$
create procedure sp_ActualizarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
begin
	update TipoProducto
    set
		TipoProducto.descripcion = descripcion
	where
		TipoProducto.codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;

call sp_ActualizarTipoProducto(1,'Frutas');


delimiter $$
create procedure sp_EliminarTipoProducto(in codigoTipoProducto int)
begin
	delete from TipoProducto where TipoProducto.codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;
 
call sp_EliminarTipoProducto(2);


-- CARGO EMPLEADO
delimiter $$
create procedure sp_AgregarCargoEmpleado(in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
begin
	insert into CargoEmpleado(codigoCargoEmpleado, nombreCargo, descripcionCargo)
    values (codigoCargoEmpleado, nombreCargo, descripcionCargo);
end $$
delimiter ;

call sp_AgregarCargoEmpleado(1,'Gerente de Ventas','Encargado del area de ventas');
call sp_AgregarCargoEmpleado(2,'Mantenimiento','Encargado del Mantenimiento del edificio');


delimiter $$
create procedure sp_ListarCargoEmpleado()
begin
	select * from CargoEmpleado;
end $$
delimiter ;

call sp_ListarCargoEmpleado();


delimiter $$
create procedure sp_BuscarCargoEmpleado(in codigoCargoEmpleado int)
begin
	select * from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;

call sp_BuscarCargoEmpleado(2);


delimiter $$
create procedure sp_ActualizarCargoEmpleado(in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
begin
	update CargoEmpleado
    set
		CargoEmpleado.nombreCargo = nombreCargo,
        CargoEmpleado.descripcionCargo = descripcionCargo
	where
		CargoEmpleado.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;

call sp_ActualizarCargoEmpleado(1,'Mensajero', 'Encargado');


delimiter $$
create procedure sp_EliminarCargoEmpleado(in codigoCargoEmpleado int)
begin
	delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;
 
call sp_EliminarCargoEmpleado(2);


-- PROVEDORES
delimiter $$
create procedure sp_AgregarProveedores(in codigoProveedor int, in NITProveedor varchar(10), in nombresProveedor varchar(60), in apellidosProveedor varchar(60), direccionProveedor varchar(150), in razonSocial varchar(100), in contactoPrincipal varchar(100), in paginaWeb varchar(50))
begin
	insert into Proveedores(codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
end $$
delimiter ;

call sp_AgregarProveedores(1,'4587152','Carlos Luis','Tubac Gomez','Mi Casa 1 zona 3','Pepsico Iberia Servicios Centrales','5478-5546','PePsico.com');
call sp_AgregarProveedores(2,'4587152','Carlos Luis','Tubac Gomez','Mi Casa 1 zona 3','Pepsico Iberia Servicios Centrales','5478-5546','PePsico.com');


delimiter $$
create procedure sp_ListarProveedores()
begin
	select * from Proveedores;
end $$
delimiter ;

call sp_ListarProveedores();


delimiter $$
create procedure sp_BuscarProveedores(in codigoProveedor int)
begin
	select * from Proveedores where Proveedores.codigoProveedor = codigoProveedor;
end $$
delimiter ;

call sp_BuscarProveedores(2);


delimiter $$
create procedure sp_ActualizarProveedores(in codigoProveedor int, in NITProveedor varchar(10), in nombresProveedor varchar(60), in apellidosProveedor varchar(60), direccionProveedor varchar(150), in razonSocial varchar(100), in contactoPrincipal varchar(100), in paginaWeb varchar(50))
begin
	update Proveedores
    set
		Proveedores.NITProveedor = NITProveedor,
        Proveedores.nombresProveedor = nombresProveedor,
        Proveedores.apellidosProveedor = apellidosProveedor,
        Proveedores.direccionProveedor = direccionProveedor,
        Proveedores.razonSocial = razonSocial,
        Proveedores.contactoPrincipal = contactoPrincipal,
        Proveedores.paginaWeb = paginaWeb
 	where
		Proveedores.codigoProveedor = codigoProveedor;
end $$
delimiter ;

call sp_ActualizarProveedores(1,'5555888971','Carlos Luis','Tubac Gomez','Mi Casa 45 zona 18','Pepsico Iberia Servicios Centrales','7878-4545','Pepsico.pepsi.com');


delimiter $$
create procedure sp_EliminarProveedores(in codigoProveedor int)
begin
	delete from Proveedores where Proveedores.codigoProveedor = codigoProveedor;
end $$
delimiter ;
 
call sp_EliminarProveedores(2);


-- Productos

delimiter $$
create procedure sp_AgregarProductos(in codigoProducto varchar(15), in descripcionProducto varchar(15), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(45), in existencia int(11), in CodigoTipoProducto int(11), in CodigoProveedor int(11))
begin
	insert into Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, CodigoTipoProducto, CodigoProveedor)
    values (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, CodigoTipoProducto, CodigoProveedor);
end $$
delimiter ;

call sp_AgregarProductos('1','Coca-Cola de 3L',15.5,192,14,'Coca cola espuma',4,1,1);


delimiter $$
create procedure sp_ListarProductos()
begin
	select * from Productos;
end $$
delimiter ;

call sp_ListarProductos();

delimiter $$
create procedure sp_BuscarProductos(in codigoProducto int)
begin
	select * from Productos where Productos.codigoProducto = codigoProducto;
end $$
delimiter ;

call sp_BuscarProductos('1');


delimiter $$
create procedure sp_ActualizarProductos(in codigoProducto varchar(15), in descripcionProducto varchar(15), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(45), in existencia int(11), in CodigoTipoProducto int(11), in CodigoProveedor int(11))
begin
	update Productos
    set
		Productos.descripcionProducto = descripcionProducto,
        Productos.precioUnitario = precioUnitario,
        Productos.precioDocena = precioDocena,
        Productos.precioMayor = precioMayor,
        Productos.imagenProducto = imagenProducto,
        Productos. existencia = existencia
 	where
		Productos.codigoProducto = codigoProducto;
end $$
delimiter ;

call sp_ActualizarProductos('1','Pepsi Cola',12,144,11,'Pepsi Cola espuma',4,1,1);


delimiter $$
create procedure sp_EliminarProductos(in codigoProducto int)
begin
	delete from Productos where Productos.codigoProducto = codigoProducto;
end $$
delimiter ;
 
call sp_EliminarProductos(1);