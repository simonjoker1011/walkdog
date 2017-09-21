 

    drop table if exists User;

    create table User (
       id bigint not null,
        name varchar(255),
        firstname varchar(64),
        lastname varchar(64),
        agerange int,
        link varchar(1024),
        picture varchar(1024),
        primary key (id)
    );

    drop table if exists hibernate_sequence;

    create table hibernate_sequence (
       next_val bigint
    );
    
    insert into hibernate_sequence values ( 1 );
