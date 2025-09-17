create table if not exists todo_list.todo
(
    id   bigint auto_increment
    primary key,
    text varchar(255) default '""' null,
    done tinyint(1)   default 0    not null
    );

