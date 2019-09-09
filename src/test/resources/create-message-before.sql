delete from  message;
delete from hibernate_sequence;

insert into message (id, text, tag, user_id) values
    (1, 'first', 'tag', 1),
    (2, 'second', 'tag', 1),
    (3, 'third', 'tag3', 1),
    (4, 'fourth', 'tag4', 1);

insert into hibernate_sequence values ( 10 );