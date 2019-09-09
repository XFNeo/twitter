delete from user_role;
delete from users;

insert into users(id, active, password, username) values
    (1, true, '$2a$08$jmx3okPTSYFKPl9MBpuwfO4neHXzmm73doi/pdY/otcmXoXEbhErq', 'Neo'),
    (2, true, '$2a$08$jmx3okPTSYFKPl9MBpuwfO4neHXzmm73doi/pdY/otcmXoXEbhErq', 'Bob');

insert into user_role(user_id, roles) values
    (1, 'USER'), (1,'ADMIN'),
    (2, 'USER');