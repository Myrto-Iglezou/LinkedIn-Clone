
    create table chat (
       id bigint not null auto_increment,
        timestamp datetime,
        primary key (id)
    ) engine=MyISAM

    create table chat_users (
       chats_id bigint not null,
        users_id bigint not null,
        primary key (chats_id, users_id)
    ) engine=MyISAM

    create table comment (
       id bigint not null auto_increment,
        content varchar(255) not null,
        timestamp datetime,
        notification_id bigint,
        post_id bigint,
        user_made_by_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table connection (
       id bigint not null auto_increment,
        is_accepted bit,
        notification_id bigint,
        user_followed_id bigint,
        user_following_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table hibernate_sequence (
       next_val bigint
    ) engine=MyISAM

    insert into hibernate_sequence values ( 1 )

    create table interest_reaction (
       id bigint not null auto_increment,
        timestamp datetime,
        notification_id bigint,
        post_id bigint,
        user_made_by_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table job (
       id bigint not null auto_increment,
        description varchar(1500),
        timestamp datetime,
        title varchar(255),
        user_made_by_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table job_users_applied (
       job_id bigint not null,
        users_applied_id bigint not null,
        primary key (job_id, users_applied_id)
    ) engine=MyISAM

    create table message (
       id bigint not null auto_increment,
        content varchar(255) not null,
        timestamp datetime not null,
        chat_id bigint,
        user_made_by_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table notification (
       id bigint not null auto_increment,
        is_shown bit,
        type integer not null,
        connection_request_id bigint,
        new_comment_id bigint,
        new_interest_id bigint,
        user_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table picture (
       id bigint not null auto_increment,
        bytes mediumblob,
        is_compressed bit,
        name varchar(255),
        type varchar(255),
        post_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table post (
       id bigint not null auto_increment,
        content varchar(255) not null,
        timestamp datetime,
        owner_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table role (
       id bigint not null,
        name varchar(255),
        primary key (id)
    ) engine=MyISAM

    create table skills_and_experience (
       id bigint not null,
        description varchar(255),
        is_public integer,
        type integer,
        user_edu_id bigint,
        user_exp_id bigint,
        user_sk_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table user (
       id bigint not null auto_increment,
        city varchar(255),
        current_company varchar(255),
        current_job varchar(255),
        github varchar(255),
        name varchar(255) not null,
        password varchar(255),
        phone_number varchar(255),
        surname varchar(255) not null,
        twitter varchar(255),
        email varchar(255) not null,
        website varchar(255),
        profile_picture_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table user_interest_reactions (
       user_id bigint not null,
        interest_reactions_id bigint not null,
        primary key (user_id, interest_reactions_id)
    ) engine=MyISAM

    create table user_job_applied (
       user_id bigint not null,
        job_applied_id bigint not null,
        primary key (user_id, job_applied_id)
    ) engine=MyISAM

    create table user_recommended_jobs (
       recommended_to_id bigint not null,
        recommended_jobs_id bigint not null
    ) engine=MyISAM

    create table user_recommended_posts (
       recommended_to_id bigint not null,
        recommended_posts_id bigint not null
    ) engine=MyISAM

    create table user_roles (
       users_id bigint not null,
        roles_id bigint not null,
        primary key (users_id, roles_id)
    ) engine=MyISAM

    alter table user_interest_reactions 
       add constraint UK_snu16flij533yafv16ehycml0 unique (interest_reactions_id)

    alter table chat_users 
       add constraint FKorvljukoxcj3j8l0vryq2sme5 
       foreign key (users_id) 
       references user (id)

    alter table chat_users 
       add constraint FK2u22ypd249k2f48bkaou6v1kd 
       foreign key (chats_id) 
       references chat (id)

    alter table comment 
       add constraint FKlk4o90b8dpywn0h666n5er8hs 
       foreign key (notification_id) 
       references notification (id)

    alter table comment 
       add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 
       foreign key (post_id) 
       references post (id)

    alter table comment 
       add constraint FKtjfa2hhg8rdv00l60awdctu3s 
       foreign key (user_made_by_id) 
       references user (id)

    alter table connection 
       add constraint FKofc2hur5991abofdc63ihm3ji 
       foreign key (notification_id) 
       references notification (id)

    alter table connection 
       add constraint FK4ynwmox53ddku090dpeiom6n0 
       foreign key (user_followed_id) 
       references user (id)

    alter table connection 
       add constraint FKkh3k88mmemqgqyb4pefw8s7gg 
       foreign key (user_following_id) 
       references user (id)

    alter table interest_reaction 
       add constraint FKn5ho85typb1ldl61nwu57xwbr 
       foreign key (notification_id) 
       references notification (id)

    alter table interest_reaction 
       add constraint FK6nh25kdjdheletms9sk3yy9kj 
       foreign key (post_id) 
       references post (id)

    alter table interest_reaction 
       add constraint FKl5ceqokl69twcaoirjdwry4kh 
       foreign key (user_made_by_id) 
       references user (id)

    alter table job 
       add constraint FKtivyhmwtis44ql36sx20mx7aq 
       foreign key (user_made_by_id) 
       references user (id)

    alter table job_users_applied 
       add constraint FKll5fr85w9bibdd02hthjfipjq 
       foreign key (users_applied_id) 
       references user (id)

    alter table job_users_applied 
       add constraint FKdvqwbkqn5l7e28gyd766dm3f7 
       foreign key (job_id) 
       references job (id)

    alter table message 
       add constraint FKmejd0ykokrbuekwwgd5a5xt8a 
       foreign key (chat_id) 
       references chat (id)

    alter table message 
       add constraint FKph40sp8gk60xcbtbldqcmx2m0 
       foreign key (user_made_by_id) 
       references user (id)

    alter table notification 
       add constraint FKt1tmir8vl370dd6rifq5fom2x 
       foreign key (connection_request_id) 
       references connection (id)

    alter table notification 
       add constraint FKrm62op4qf3svdgema50y6iums 
       foreign key (new_comment_id) 
       references comment (id)

    alter table notification 
       add constraint FK8o0xnpjn4hkbmoe0aexfr6h2 
       foreign key (new_interest_id) 
       references interest_reaction (id)

    alter table notification 
       add constraint FKb0yvoep4h4k92ipon31wmdf7e 
       foreign key (user_id) 
       references user (id)

    alter table picture 
       add constraint FK24liocg7lhfngonriw16m0usw 
       foreign key (post_id) 
       references post (id)

    alter table post 
       add constraint FKed26xsbw5klvkdyqql60f7xbp 
       foreign key (owner_id) 
       references user (id)

    alter table skills_and_experience 
       add constraint FKibknby0wnncpqrrfntalwq131 
       foreign key (user_edu_id) 
       references user (id)

    alter table skills_and_experience 
       add constraint FKm1ui6grsw6llg59dd7hwy4es0 
       foreign key (user_exp_id) 
       references user (id)

    alter table skills_and_experience 
       add constraint FK6y3v3gunv27snin9ksfcqulhs 
       foreign key (user_sk_id) 
       references user (id)

    alter table user 
       add constraint FKhl6ina2my866nv0rh1aipxi8u 
       foreign key (profile_picture_id) 
       references picture (id)

    alter table user_interest_reactions 
       add constraint FKirmcjcehl7pe8snr0bpmgvpq8 
       foreign key (interest_reactions_id) 
       references interest_reaction (id)

    alter table user_interest_reactions 
       add constraint FKmau6eu1ltfk11exswdqcxgude 
       foreign key (user_id) 
       references user (id)

    alter table user_job_applied 
       add constraint FKel84vhj5ul33if47riahuj906 
       foreign key (job_applied_id) 
       references job (id)

    alter table user_job_applied 
       add constraint FK7ejpvsl034stgva85pqqxo5ow 
       foreign key (user_id) 
       references user (id)

    alter table user_recommended_jobs 
       add constraint FKmdasm21qi370cc663r6552mc2 
       foreign key (recommended_jobs_id) 
       references job (id)

    alter table user_recommended_jobs 
       add constraint FK92ev7e0gaofyg0v71pekgg2af 
       foreign key (recommended_to_id) 
       references user (id)

    alter table user_recommended_posts 
       add constraint FK3lfwqxe6ay74midbabkb3coqf 
       foreign key (recommended_posts_id) 
       references post (id)

    alter table user_recommended_posts 
       add constraint FK8b6ha9evugm10f86bya7kolbs 
       foreign key (recommended_to_id) 
       references user (id)

    alter table user_roles 
       add constraint FKj9553ass9uctjrmh0gkqsmv0d 
       foreign key (roles_id) 
       references role (id)

    alter table user_roles 
       add constraint FK7ecyobaa59vxkxckg6t355l86 
       foreign key (users_id) 
       references user (id)
