create table task_groups
(
    id          int primary key auto_increment,
    description varchar(100) not null,
    done        bit          not null default 0,
    created_on  datetime     null,
    updated_on  datetime     null
);
alter table tasks
    add column task_group_id int null;
alter table tasks
    add FOREIGN KEY (task_group_id) REFERENCES task_groups (id);