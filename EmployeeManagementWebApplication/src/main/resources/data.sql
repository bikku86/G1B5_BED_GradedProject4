insert into roles (role_id,name) values (1,'ADMIN');

insert into users (user_id,password,username) values (1,'$2a$12$nqLx7znhOvwkNtpyx1Tk0uiTUni5/3KeIz8GpNQ4K8n27NEjiRRnG','vikas');

insert into users_roles (user_id,role_id) values (1,1);