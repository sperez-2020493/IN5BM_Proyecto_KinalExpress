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

create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_EmailProveedor(codigoEmailProveedor),
    constraint FK_EmailProveedor_Proveedores foreign key EmailProveedor(codigoProveedor)
		references Proveedores(codigoProveedor) on delete cascade
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_TelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_TelefonoProveedor_Proveedores foreign key TelefonoProveedor(codigoProveedor)
		references Proveedores(codigoProveedor) on delete cascade
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

create table Productos(
	codigoProducto varchar(15),
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int(11),
	CodigoTipoProducto int,
	CodigoProveedor int,
	primary key PK_codigoProducto(codigoProducto),
	constraint FK_Productos_TipoProducto foreign key Productos(CodigoTipoProducto)
		references TipoProducto(codigoTipoProducto)on delete cascade,
	constraint FK_Productos_Proveedores foreign key Productos(CodigoProveedor)
		references Proveedores(codigoProveedor) on delete cascade
);


create table Compras(
	numeroDocumento int,
	fechaDocumento date,
	descripcion varchar(60),
	totalDocumento decimal(10,2),
	primary key PK_numeroDocumento(numeroDocumento)
);

create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_DetalleCompra(codigoDetalleCompra),
    constraint FK_DetalleCompra_Productos foreign key DetalleCompra(codigoProducto)
		references Productos(codigoProducto) on delete cascade,
	constraint FK_DetalleCompra_Compras foreign key DetalleCompra(numeroDocumento)
		references	Compras(numeroDocumento) on delete cascade
);

create table Factura(
	numeroFactura int not null,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_Factura(numeroFactura),
    constraint FK_Factura_Clientes foreign key Factura(codigoCliente)
		references Clientes(codigoCliente) on delete cascade,
	constraint FK_Factura_Empleados foreign key Factura(codigoEmpleado)
		references Empleados(codigoEmpleado) on delete cascade
);

create table DetalleFactura(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_DetalleFactura(codigoDetalleFactura),
    constraint FK_DetalleFactura_Factura foreign key DetalleFactura(numeroFactura)
		references Factura(numeroFactura) on delete cascade,
	constraint FK_DetalleFactura foreign key DetalleFactura(codigoProducto)
		references Productos(codigoProducto) on delete cascade
);

alter user 'root'@'localhost' identified with mysql_native_password by 'F6fvGtCf';

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
call sp_AgregarCompra(3,'1960-12-31','Refresco',725.5);


delimiter $$
create procedure sp_ListarCompras()
begin
	select * from Compras;
end $$
delimiter ;

call sp_ListarCompras();

delimiter $$
create procedure sp_BuscarCompra(in numeroDocumento int)
begin
	select * from Compras where Compras.numeroDocumento = numeroDocumento;
end $$
delimiter ;

call sp_BuscarCompra(2);

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
call sp_AgregarClientes(1,'5454852121','Samuel Aexander','Perez Cap','7A-15 calle-Zona 18','32145678','sperez@gmail.com');
call sp_AgregarClientes(3,'6985784512','Juan Carlos','Pérez García','15A-9 calle-zona 13','12547896',' juanpg@gmail.com');
call sp_AgregarClientes(4,'9874523654',' María Alejandra',' López Torres','8A-7 calle-zona 8','36524785','marialt@gmail.com');
call sp_AgregarClientes(5,'6665844412','Carlos David','Rodríguez Méndez','4A-8 calle-zona 9','78951254','carlosrm@gmail.com');
call sp_AgregarClientes(6,'6985589611','Luis Miguel','Orlando Tubac','12A-12 calle-Zona 10','45678901','luiso@gmail.com');
call sp_AgregarClientes(7,'7778541123','Pedro Luis','Gómez Sánchez',' 9A-7calle-Zona 5','56789012','pedrogs@gmail.com');


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
 


-- TIPO PRDUCTO
delimiter $$
create procedure sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
begin
	insert into TipoProducto(codigoTipoProducto, descripcion)
    values (codigoTipoProducto, descripcion);
end $$
delimiter ;

call sp_AgregarTipoProducto(1,'Bebidas');
call sp_AgregarTipoProducto(2,'Carnes frias');
call sp_AgregarTipoProducto(3,'Lacteos');


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


delimiter $$
create procedure sp_EliminarTipoProducto(in codigoTipoProducto int)
begin
	delete from TipoProducto where TipoProducto.codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;
 


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

-- EMPLEADOS
delimiter $$
create procedure sp_AgregarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
begin 
	insert into Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    values (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
end $$
delimiter ;

call sp_AgregarEmpleados(1,'Samuel','Perez','5000','zona 18','Matutino',1);
call sp_AgregarEmpleados(2,'Samuel','Perez','5000','zona 18','Matutino',1);


delimiter $$
create procedure sp_ListarEmpleados()
begin 
	select * from Empleados;
end $$
delimiter ;

call sp_ListarEmpleados();

delimiter $$
create procedure sp_BuscarEmpleados(in codigoEmpleado int)
begin 
	select * from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

call sp_BuscarEmpleados(1);

delimiter $$
create procedure sp_ActualizarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
begin 
	update Empleados
    set	
		Empleados.nombresEmpleado = nombresEmpleado,
		Empleados.apellidosEmpleado = apellidosEmpleado,
        Empleados.sueldo = sueldo,
        Empleados.direccion = direccion,
        Empleados.turno = Empleados.turno,
        Empleados.codigoCargoEmpleado = codigoCargoEmpleado
	where
		Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

call sp_ActualizarEmpleados(1,'Samuel Alexander','Perez Cap','5000','zona 18','Matutino',1);

delimiter $$
create procedure sp_EliminarEmpleados(in codigoEmpleado int)
begin 
	delete from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

-- PROVEDORES
delimiter $$
create procedure sp_AgregarProveedores(in codigoProveedor int, in NITProveedor varchar(10), in nombresProveedor varchar(60), in apellidosProveedor varchar(60), direccionProveedor varchar(150), in razonSocial varchar(100), in contactoPrincipal varchar(100), in paginaWeb varchar(50))
begin
	insert into Proveedores(codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (codigoProveedor, NITProveedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
end $$
delimiter ;

call sp_AgregarProveedores(1,'7895415','Carlos Luis','Tubac Gomez','4 avenida-12 calle-Zona 10','Pepsico','5478-5546','PePsico.com');
call sp_AgregarProveedores(2,'1236454','Diego Ramiro','Zapata Tubac','5 avenida-10 calle-Zona 11','Toledo','5478-5546','Toledo.com');
call sp_AgregarProveedores(3,'9635221','Oscar Daniel','Torrez Torrez','12 avenida-3 calle-Zona 9','Bremen','8884-5632','Bremen.com');
call sp_AgregarProveedores(4,'9845636','Samuel Alexander','Perez Cap','9 avenida-5 calle-Zona 3','Grupo Lala','1777-8222','LecheLala.com');
call sp_AgregarProveedores(5,'4512666','Luis Aaron','Gomez Giyen','Zona 8 avenida-9 calle-Zona 1','Cervecería CA','7845-9999','CerveceriaCentro@Americano.com');


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

call sp_BuscarProveedores(1);


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


delimiter $$
create procedure sp_EliminarProveedores(in codigoProveedor int)
begin
	delete from Proveedores where Proveedores.codigoProveedor = codigoProveedor;
end $$
delimiter ;
 

-- Email Proveedor

delimiter $$
create procedure sp_AgregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
begin
	insert into EmailProveedor(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    values(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
end $$
delimiter ;

call sp_AgregarEmailProveedor(1,'PepsicoGT@gmail.com','Correo de cadena de suministros',1);
call sp_AgregarEmailProveedor(2,'Toledo@gmail.com','Correo de cadena de suministros',2);
call sp_AgregarEmailProveedor(3,'Bremen@gmail.com','Correo de cadena de suministros',3);
call sp_AgregarEmailProveedor(4,'GrupoLalaGT@gmail.com','Correo de cadena de suministros',4);
call sp_AgregarEmailProveedor(5,'CerveseriaCA@gmail.com','Correo de cadena de suministros',5);


delimiter $$
create procedure sp_ListarEmailProveedor()
begin
	select * from EmailProveedor;
end $$
delimiter ;

call sp_ListarEmailProveedor;

delimiter $$
create procedure sp_BuscarEmailProveedor(in codigoEmailProveedor int)
begin
	select*from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

call sp_BuscarEmailProveedor(1);

delimiter $$
create procedure sp_ActualizarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
begin
	update EmailProveedor
	set
		EmailProveedor.emailProveedor = emailProveedor,
        EmailProveedor.descripcion = descripcion,
        EmailProveedor.codigoProveedor = codigoProveedor
	where
		EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarEmailProveedor(in codigoEmailProveedor int)
begin
	delete from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

-- Telefono Proveedor

delimiter $$
create procedure sp_agregarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
begin
	insert into TelefonoProveedor(codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
end $$
delimiter ;

call sp_agregarTelefonoProveedor(1,'52526968','47582145','502',1);
call sp_agregarTelefonoProveedor(2,'47855821','63638989','502',2);
call sp_agregarTelefonoProveedor(3,'96965421','21546254','502',3);
call sp_agregarTelefonoProveedor(4,'17754745','78945123','502',4);
call sp_agregarTelefonoProveedor(5,'78541254','36528418','502',5);


delimiter $$
create procedure sp_ListarTelefonoProveedor()
begin
	select * from TelefonoProveedor;
end $$
delimiter ;

call sp_ListarTelefonoProveedor;

delimiter $$
create procedure sp_BuscarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
	select * from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

call sp_BuscarTelefonoProveedor(1);

delimiter $$
create procedure sp_ActualizarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
begin
	update TelefonoProveedor
    set
		TelefonoProveedor.numeroPrincipal = numeroPrincipal,
        TelefonoProveedor.numeroSecundario = numeroSecundario, 
        TelefonoProveedor.observaciones = observaciones,
        TelefonoProveedor.codigoProveedor = codigoProveedor
	where 
		TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
	delete from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

-- Productos

delimiter $$
create procedure sp_AgregarProductos(in codigoProducto varchar(15), in descripcionProducto varchar(15), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(45), in existencia int(11), in CodigoTipoProducto int(11), in CodigoProveedor int(11))
begin
	insert into Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, CodigoTipoProducto, CodigoProveedor)
    values (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, CodigoTipoProducto, CodigoProveedor);
end $$
delimiter ;

call sp_AgregarProductos('1','Pepsi 3L',15.5,181.8,363.6,'Litro de cola',4,1,1);
call sp_AgregarProductos('2','Jamon 600g',8.5,96.5,192.2,'jamon',15,1,2);
call sp_AgregarProductos('3','Salchichas 100g',12,144,288,'paquete de salquichas',30,2,3);
call sp_AgregarProductos('4','Lech 1L',12,144,288,'Litro de leche',15,3,4);
call sp_AgregarProductos('5','6 PACK cerveza',55,660,1320,'Latas cerveza',9,1,5);


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
 
-- Factura
delimiter $$
create procedure sp_AgregarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in codigoCliente int, in codigoEmpleado int)
begin
	insert into Factura(numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado)
    values (numeroFactura,estado,totalFactura,fechaFactura,codigoCliente,codigoEmpleado);
end $$
delimiter ;

call sp_AgregarFactura(1,'Pagado',100.0,'2024-05-10',1,1);
call sp_AgregarFactura(2,'Pagado',100.0,'2024-05-10',3,2);


delimiter $$
create procedure sp_ListarFactura()
begin
	select * from Factura;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarFactura(in numeroFactura int)
begin
	select * from Factura where Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

call sp_BuscarFactura(1);

delimiter $$
create procedure sp_ActualizarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in codigoCliente int, in codigoEmpleado int)
begin
	update Factura
    set
		Factura.estado = estado,
        Factura.totalFactura = totalFactura,
        Factura.fechaFactura = fechaFactura,
        Factura.codigoCliente = codigoCliente,
        Factura.codigoEmpleado = codigoEmpleado
	where
		Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarFactura(in numeroFactura int)
begin
	delete from Factura where Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

-- Detalle Compra

delimiter $$
create procedure sp_AgregarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	insert into DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento)
    values (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento);
end $$
delimiter ;

call sp_AgregarDetalleCompra(1,50.3,15,'1',2);


delimiter $$
create procedure sp_ListarDetalleCompra()
begin
	select * from DetalleCompra;
end $$
delimiter ;

call sp_ListarDetalleCompra();

delimiter $$
create procedure sp_BuscarDetalleCompra(in codigoDetalleCompra int)
begin
	select * from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

call sp_BuscarDetalleCompra(1);


delimiter $$
create procedure sp_ActualizarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	update DetalleCompra
    set
		DetalleCompra.costoUnitario = costoUnitario,
        DetalleCompra.cantidad = cantidad,
        DetalleCompra.codigoProducto = codigoProducto,
        DetalleCompra.numeroDocumento = numeroDocumento
	where
		DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

delimiter $$


create procedure sp_EliminarDetalleCompra(in codigoDetalleCompra int)
begin
	delete from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;


-- Detalle Factura
delimiter $$
create procedure sp_agregarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
begin
	insert into DetalleFactura(codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    values (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto);
end $$
delimiter ;

call sp_agregarDetalleFactura(1,12,3,1,'1');
call sp_agregarDetalleFactura(2,0.00,3,2,'1');

delimiter $$
create procedure sp_listarDetallesFacturas()
begin
	select * from DetalleFactura;
end $$
delimiter ;

call sp_listarDetallesFacturas();

delimiter $$
create procedure sp_buscarDetalleFactura(in codigoDetalleFactura int)
begin 
	select * from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;


delimiter $$
create procedure sp_actualizarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
begin
	update DetalleFactura
		set
			DetalleFactura.precioUnitario = precioUnitario,
            DetalleFactura.cantidad = cantidad,
            DetalleFactura.numeroFactura = numeroFactura,
            DetalleFactura.codigoProducto = codigoProducto
		where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarDetalleFactura(in codigoDetalleFactura int)
begin
	delete from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

-- TRIGGERS

delimiter $$
create trigger tr_PrecioProductos
after insert on DetalleCompra
for each row 
begin
	declare total decimal(10,2);
    declare cantidad int;
    set total = new.costoUnitario * new.cantidad;
	update Productos
	set precioUnitario = total * 0.40,
		precioDocena  = total * 0.35 * 12,
        precioMayor = total * 0.25
    where Productos.codigoProducto = new.codigoProducto;
 	update Productos
        set Productos.existencia = Productos.existencia - new.cantidad
	where Productos.codigoProducto = new.codigoProducto;
end $$
delimiter ;

delimiter $$
create trigger tr_PrecioUnitario
after insert on DetalleCompra
for each row
begin
	declare precioP decimal(10,2);
    set precioP = (select precioUnitario from Productos where codigoProducto = new.codigoProducto);
    update DetalleFactura
    set DetalleFactura.precioUnitario = precioP
    where DetalleFactura.codigoProducto = new.codigoProducto;
end $$
delimiter ;

delimiter $$
create trigger tr_TotalDocumento
after insert on DetalleCompra
for each row
begin
    declare total decimal(10,2);
     select sum(costoUnitario * cantidad) into total from DetalleCompra 
    where numeroDocumento = new.numeroDocumento;
    update Compras 
		set totalDocumento = total 
	where numeroDocumento = new.numeroDocumento;
end $$
delimiter ;

delimiter $$
create trigger tr_TotalFactura
after update on DetalleFactura
for each row
begin
	declare totalFactura decimal(10,2);
     select sum(precioUnitario * cantidad) into totalFactura from DetalleFactura
    where numeroFactura = new.numeroFactura;
      update Factura
		set Factura.totalFactura = totalFactura
	where Factura.numeroFactura = new.numeroFactura;
end $$
delimiter ;


create view vw_ProductosTP as
select Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario, Productos.precioDocena, Productos.precioMayor, Productos.existencia, TipoProducto.Descripcion, Proveedores.razonSocial
from Productos
inner join TipoProducto on Productos.codigoTipoProducto = TipoProducto.codigoTipoProducto
inner join Proveedores on Productos.codigoProveedor = Proveedores.codigoProveedor;

select * from vw_ProductosTP;

create view vw_ProveedoresP as
select Proveedores.codigoProveedor, Proveedores.NITProveedor, Proveedores.direccionProveedor, Proveedores.razonSocial, TelefonoProveedor.numeroPrincipal, EmailProveedor.emailProveedor
from Proveedores
inner join TelefonoProveedor on Proveedores.codigoProveedor = TelefonoProveedor.codigoProveedor
inner join EmailProveedor on Proveedores.codigoProveedor = EmailProveedor.codigoProveedor;

select * from vw_ProveedoresP;

select * from DetalleFactura
	inner join Factura on DetalleFactura.numeroFactura = Factura.numeroFactura
    inner join Clientes on Factura.codigoCliente = Clientes.codigoCliente
    inner join Productos on DetalleFactura.codigoProducto = Productos.codigoProducto
    where Factura.numeroFactura = 1;