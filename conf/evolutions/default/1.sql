# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table action (
  id                            bigserial not null,
  description                   varchar(255),
  timestamp                     integer,
  est_time                      integer,
  preset_id                     bigint,
  script_id                     bigint,
  constraint pk_action primary key (id)
);

create table preset (
  id                            bigserial not null,
  name                          varchar(255),
  camera                        integer,
  pan                           float,
  tilt                          float,
  zoom                          float,
  focus                         float,
  constraint pk_preset primary key (id)
);

create table script (
  id                            bigserial not null,
  name                          varchar(255),
  creation_date                 timestamp,
  constraint pk_script primary key (id)
);

alter table action add constraint fk_action_preset_id foreign key (preset_id) references preset (id) on delete restrict on update restrict;
create index ix_action_preset_id on action (preset_id);

alter table action add constraint fk_action_script_id foreign key (script_id) references script (id) on delete restrict on update restrict;
create index ix_action_script_id on action (script_id);


# --- !Downs

alter table if exists action drop constraint if exists fk_action_preset_id;
drop index if exists ix_action_preset_id;

alter table if exists action drop constraint if exists fk_action_script_id;
drop index if exists ix_action_script_id;

drop table if exists action cascade;

drop table if exists preset cascade;

drop table if exists script cascade;

