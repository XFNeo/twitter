create table message_likes(
    user_id bigint not null,
    message_id bigint not null,
    primary key (user_id, message_id)
)  engine=MyISAM;

alter table message_likes
    add constraint message_likes_user_fk
    foreign key (user_id) references users (id);

alter table message_likes
    add constraint message_likes_message_fk
    foreign key (user_id) references message (id);