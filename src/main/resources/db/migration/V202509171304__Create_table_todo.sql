create table todo (
                      id   bigint auto_increment primary key,
                      text varchar(255) default null,
                      done tinyint default 0 not null
);

