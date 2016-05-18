# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table action (
  id                            bigint not null,
  description                   varchar(255),
  timestamp                     integer,
  duration                      integer,
  preset_id                     bigint,
  script_id                     bigint,
  constraint pk_action primary key (id)
);
create sequence action_seq;

create table active_script (
  script_id                     bigint,
  running_time                  bigint,
  action_index                  integer,
  constraint uq_active_script_script_id unique (script_id)
);

create table preset (
  id                            bigint not null,
  name                          varchar(255),
  camera                        integer,
  pan                           integer,
  tilt                          integer,
  zoom                          integer,
  focus                         integer,
  constraint pk_preset primary key (id)
);
create sequence preset_seq;

create table script (
  id                            bigint not null,
  name                          varchar(255),
  creation_date                 timestamp,
  constraint pk_script primary key (id)
);
create sequence script_seq;

alter table action add constraint fk_action_preset_id foreign key (preset_id) references preset (id) on delete restrict on update restrict;
create index ix_action_preset_id on action (preset_id);

alter table action add constraint fk_action_script_id foreign key (script_id) references script (id) on delete restrict on update restrict;
create index ix_action_script_id on action (script_id);

alter table active_script add constraint fk_active_script_script_id foreign key (script_id) references script (id) on delete restrict on update restrict;


# --- !Downs

alter table action drop constraint if exists fk_action_preset_id;
drop index if exists ix_action_preset_id;

alter table action drop constraint if exists fk_action_script_id;
drop index if exists ix_action_script_id;

alter table active_script drop constraint if exists fk_active_script_script_id;

drop table if exists action;
drop sequence if exists action_seq;

drop table if exists active_script;

drop table if exists preset;
drop sequence if exists preset_seq;

drop table if exists script;
drop sequence if exists script_seq;

