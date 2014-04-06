# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table board (
  board_id                  bigint auto_increment not null,
  board_title               varchar(255),
  board_owner_id            varchar(255),
  board_owner_name          varchar(255),
  board_content             longtext,
  constraint pk_board primary key (board_id))
;

create table account (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table board;

drop table account;

SET FOREIGN_KEY_CHECKS=1;

