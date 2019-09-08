create table user_subscriptions (
    channel_id bigint not null,
    subscriber_id bigint not null,
    primary key (channel_id, subscriber_id)
    ) engine=MyISAM;

alter table user_subscriptions
    add constraint user_subscriptions_channel_id_users_fk
    foreign key (channel_id) references users (id);

alter table user_subscriptions
    add constraint user_subscriptions_subscriber_id_users_fk
    foreign key (subscriber_id) references users (id);