
create table topicos(

                        id serial not null,
                        titulo varchar(100) not null,
                        mensaje varchar(300) not null,
                        fecha date not null,
                        status boolean not null,
                        autor integer not null,
                        curso varchar(100) not null,

                        primary key (id),
                        foreign key (autor) references usuarios(id)
);