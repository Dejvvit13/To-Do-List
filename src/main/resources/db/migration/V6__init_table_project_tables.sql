CREATE TABLE projects
(
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(100) NOT NULL
);


CREATE TABLE project_steps
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    description      VARCHAR(100) NOT NULL,
    days_to_deadline INTEGER      NOT NULL,
    project_id       INTEGER      NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id)
);
alter table task_groups
    add column project_id INTEGER NOT NULL;
alter table task_groups
    add foreign key (project_id) references projects (id);
