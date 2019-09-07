create table user_subscriptions (
    channel_id bigint not null PRIMARY KEY,
    subscriber_id bigint not null UNIQUE KEY,
    foreign key (channel_id) references users (id),
    foreign key (subscriber_id) references users (id)
    ) engine=MyISAM;
