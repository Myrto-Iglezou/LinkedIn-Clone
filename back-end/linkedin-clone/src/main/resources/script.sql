create table comment
(
    id              bigint auto_increment
        primary key,
    content         varchar(255) not null,
    timestamp       datetime     not null,
    post_id         bigint       null,
    user_made_by_id bigint       null
)
    engine = MyISAM;

create index FKs1slvnkuemjsq2kj4h3vhx7i1
    on comment (post_id);

create index FKtjfa2hhg8rdv00l60awdctu3s
    on comment (user_made_by_id);

create table hibernate_sequence
(
    next_val bigint null
)
    engine = MyISAM;

create table picture
(
    id    bigint auto_increment
        primary key,
    bytes mediumblob   not null,
    name  varchar(255) not null,
    type  varchar(255) not null
)
    engine = MyISAM;

create table post
(
    id        bigint auto_increment
        primary key,
    content   varchar(255) not null,
    timestamp datetime     not null,
    user_id   bigint       null
)
    engine = MyISAM;

create index FK72mt33dhhs48hf9gcqrq4fxte
    on post (user_id);

create table post_users_interested
(
    post_id             bigint not null,
    users_interested_id bigint not null,
    primary key (post_id, users_interested_id)
)
    engine = MyISAM;

create index FKt4r4rd9cc0nb85tg88ul1o37w
    on post_users_interested (users_interested_id);

create table role
(
    id   bigint       not null
        primary key,
    name varchar(255) null
)
    engine = MyISAM;

create table skills_and_experience
(
    id          bigint       not null
        primary key,
    description varchar(255) null,
    is_public   bit          null,
    type        int          null,
    user_id     bigint       null
)
    engine = MyISAM;

create index FKmwaleuhv02uyje6w7j00g8ig5
    on skills_and_experience (user_id);

create table user
(
    id                 bigint auto_increment
        primary key,
    city               varchar(255) null,
    name               varchar(255) not null,
    password           varchar(255) null,
    phone_number       varchar(255) null,
    surname            varchar(255) not null,
    email              varchar(255) not null,
    profile_picture_id bigint       null
)
    engine = MyISAM;

create index FKhl6ina2my866nv0rh1aipxi8u
    on user (profile_picture_id);

create table user_posts_interested
(
    user_id             bigint not null,
    posts_interested_id bigint not null,
    primary key (user_id, posts_interested_id)
)
    engine = MyISAM;

create index FKbqaqn4cboigrlnsecaem7oh4c
    on user_posts_interested (posts_interested_id);

create table user_roles
(
    users_id bigint not null,
    roles_id bigint not null,
    primary key (users_id, roles_id)
)
    engine = MyISAM;

create index FKj9553ass9uctjrmh0gkqsmv0d
    on user_roles (roles_id);

create table user_users_connected_with
(
    user_id                 bigint not null,
    users_connected_with_id bigint not null,
    primary key (user_id, users_connected_with_id)
)
    engine = MyISAM;

create index FKimidkhkps5ykn61o5ctxhausb
    on user_users_connected_with (users_connected_with_id);


