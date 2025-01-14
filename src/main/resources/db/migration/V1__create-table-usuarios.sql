
create table usuarios(

                         id serial not null,
                         login varchar(300) not null,
                         clave varchar(300) not null,

                         primary key (id)

);