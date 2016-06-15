# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table action (
  id                            bigint not null,
  index                         integer,
  description                   varchar(255),
  timestamp                     integer,
  duration                      integer,
  preset_id                     bigint,
  script_id                     bigint,
  constraint pk_action primary key (id)
);
create sequence action_seq;

create table active_script (
  id                            integer not null,
  script_id                     bigint,
  running_time                  bigint,
  action_index                  integer,
  constraint uq_active_script_script_id unique (script_id),
  constraint pk_active_script primary key (id)
);
create sequence active_script_seq;

create table camera (
  id                            bigint not null,
  name                          varchar(255),
  ip                            varchar(255),
  location_id                   bigint,
  constraint pk_camera primary key (id)
);
create sequence camera_seq;

create table location (
  id                            bigint not null,
  name                          varchar(255),
  constraint pk_location primary key (id)
);
create sequence location_seq;

create table preset (
  id                            bigint not null,
  name                          varchar(255),
  camera_id                     bigint,
  real_preset_id                integer,
  constraint pk_preset primary key (id)
);
create sequence preset_seq;

create table script (
  id                            bigint not null,
  name                          varchar(255),
  creation_date                 timestamp,
  location_id                   bigint,
  constraint pk_script primary key (id)
);
create sequence script_seq;

alter table action add constraint fk_action_preset_id foreign key (preset_id) references preset (id) on delete restrict on update restrict;
create index ix_action_preset_id on action (preset_id);

alter table action add constraint fk_action_script_id foreign key (script_id) references script (id) on delete restrict on update restrict;
create index ix_action_script_id on action (script_id);

alter table active_script add constraint fk_active_script_script_id foreign key (script_id) references script (id) on delete restrict on update restrict;

alter table camera add constraint fk_camera_location_id foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_camera_location_id on camera (location_id);

alter table preset add constraint fk_preset_camera_id foreign key (camera_id) references camera (id) on delete restrict on update restrict;
create index ix_preset_camera_id on preset (camera_id);

alter table script add constraint fk_script_location_id foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_script_location_id on script (location_id);


# --- !Downs

alter table action drop constraint if exists fk_action_preset_id;
drop index if exists ix_action_preset_id;

alter table action drop constraint if exists fk_action_script_id;
drop index if exists ix_action_script_id;

alter table active_script drop constraint if exists fk_active_script_script_id;

alter table camera drop constraint if exists fk_camera_location_id;
drop index if exists ix_camera_location_id;

alter table preset drop constraint if exists fk_preset_camera_id;
drop index if exists ix_preset_camera_id;

alter table script drop constraint if exists fk_script_location_id;
drop index if exists ix_script_location_id;

drop table if exists action;
drop sequence if exists action_seq;

drop table if exists active_script;
drop sequence if exists active_script_seq;

drop table if exists camera;
drop sequence if exists camera_seq;

drop table if exists location;
drop sequence if exists location_seq;

drop table if exists preset;
drop sequence if exists preset_seq;

drop table if exists script;
drop sequence if exists script_seq;

