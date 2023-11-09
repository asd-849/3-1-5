USE schema_1;

create table IF NOT EXISTS users
(
    id        bigint auto_increment
    primary key,
    age       tinyint      null,
    last_name varchar(255) null,
    name      varchar(255) null,
    password  varchar(255) null,
    username  varchar(255) null
    );
create table IF NOT EXISTS roles
(
    id        bigint auto_increment
    primary key,
    role_name varchar(255) null
    );

create table IF NOT EXISTS users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
    );
