drop table if exists tasks;
create table tasks(
    id int auto_increment PRIMARY KEY,
    description VARCHAR(100) not null,
    done bit
);