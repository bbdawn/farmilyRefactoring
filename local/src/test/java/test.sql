--관리자 회원가입
select * from FARMILY_AUTHORITIES
select * from FARMILY_MEMBER
insert into farmily_member(id,password,name,tel,region,enabled) values('master','1234','마스터','11','전남',1)

insert into farmily_authorities(username, authority)
values('qwer', 'ROLE_MASTER')
create table farmily_member(
	id varchar2(100) primary key,
	password varchar2(100) not null,
	name varchar2(100) not null,
	tel number not null,
	region varchar2(100) not null,
	enabled number default 1
);
insert into farmily_member(id,password,name,tel,region,enabled) values('java','a','감자깡','11','전남',1)
select * from farmily_member where id='java'

create table farmily_authorities(
	authority varchar2(100),
	username varchar2(100) not null,
	CONSTRAINT fk_farmily_authorities foreign key(username) references farmily_member (id)  --외래키 지정문
  --CONSTRAINT [FK명] foreign key([FK가 될 컬럼명]) references [PK가 위치하는 테이블] ([PK컬럼명])
)

create table farmily_board(
	board_no number primary key,
	title varchar2(100) not null,
	content clob not null,
	select_categori varchar2(100) not null,
	board_categori varchar2(100) not null,
	region varchar2(100) not null,
	created_date date,
	id varchar2(100) not null,
	CONSTRAINT fk_farmily_board foreign key(id) references farmily_member (id)  --외래키 지정문
)


select * from FARMILY_BOARD
ALTER TABLE farmily_board ADD(HIT NUMBER DEFAULT 0);
update farmily_board set hit=hit+1 where board_no=1;

--file 테이블 없애고 board에 추가함
ALTER TABLE farmily_board ADD filename varchar2(150) NULL;
ALTER TABLE farmily_board ADD filepath varchar2(300) NULL;
drop table farmily_file
---



select * from farmily_board
insert into farmily_board(board_no,title,content,select_categori,board_categori,region,created_date,id)
values(seq_farmily_board.nextval,'전남','test','농촌활동','일일알바','전남',sysdate,'java')

insert into farmily_board
values(seq_farmily_board.nextval,'팔아요','가나다','장터','중고거래','전남',sysdate,'java')


create table farmily_file(
	file_no number primary key,
	original_filename varchar2(100) not null,
	saved_filename varchar2(100) not null,
	filepath varchar2(100) not null,
	board_no number not null,
	CONSTRAINT fk_farmily_file foreign key(board_no) references farmily_board(board_no)  --외래키 지정문
)

create table farmily_reservation(
	reservation_no number primary key,
	month varchar2(50) not null,
	day varchar2(50) not null,
	board_no number not null,
	id varchar2(100),
	CONSTRAINT fk_reservation_board_no foreign key(board_no) references farmily_board(board_no),  --외래키 지정문
	CONSTRAINT fk_reservation_id foreign key(id) references farmily_member (id)  --외래키 지정문
)
-- 예약 테이블 수정
drop table farmily_reservation;

create table farmily_reservation(
	reservation_no number primary key,
	revervation_date varchar2(100) not null,
	board_no number not null,
	id varchar2(100),
	CONSTRAINT fk_reservation_board_no foreign key(board_no) references farmily_board(board_no),  --외래키 지정문
	CONSTRAINT fk_reservation_id foreign key(id) references farmily_member (id)  --외래키 지정문
)


create table farmily_jjim(
	board_no number ,
	id varchar2(100) ,
	CONSTRAINT pk_jjim PRIMARY KEY(board_no, id),
	CONSTRAINT fk_jjim_board_no foreign key(board_no) references farmily_board(board_no),  --외래키 지정문
	CONSTRAINT fk_jjim_id foreign key(id) references farmily_member (id)  --외래키 지정문
)
insert into farmily_jjim(board_no,id) values(7,'java1');
delete from farmily_jjim where board_no=7 and id='java1';
select count(*) from farmily_jjim where id='java' and board_no=2
select * from farmily_jjim
select b.board_no,b.title,b.content,b.select_categori,b.board_categori,b.region,b.created_date,b.id,j.board_no,j.id
from farmily_jjim j
inner join farmily_board b on j.board_no=b.board_no
where j.id='java' 

create table farmily_chat(
	sender varchar2(100),
	receiver varchar2(100),
	content clob not null,
	chat_date date,
	constraint fk_chat_sender foreign key(sender) references farmily_member(id),
	constraint fk_chat_receiver foreign key(receiver) references farmily_member(id),
	constraint pk_chat primary key(sender, receiver)
)


commit
commit commit
select * from farmily_board
select * from farmily_member
select * from farmily_reservation;

create sequence seq_farmily_board;
create sequence seq_farmily_file;
create sequence seq_farmily_reservation;

alter table farmily_board modify region null;

-- 컬럼명 오타ㅠ변경 부탁
ALTER TABLE farmily_reservation RENAME COLUMN revervation_date TO reservation_date
-- 주소 디테일 컬럼 추가 (vo에도 추가)
ALTER TABLE farmily_board ADD detail_region VARCHAR2(200);

-- 승인하는 관리자 insert (id명 master로 회원가입 먼저 하기)
insert into farmily_authorities(username,authority) values('master1','ROLE_MASTER');

select * from  farmily_authorities

select board_no,title,content,select_categori,board_categori,region,created_date,id,detail_region,hit 
from farmily_board 
where content like '%a%'

select count(*) from farmily_reservation where board_no = 131 and id is not null

delete from farmily_board where board_no = 132;

delete from farmily_reservation where board_no = 89;

-- delete 위해 cascade 옵션 추가
alter table farmily_jjim drop CONSTRAINT fk_jjim_board_no;
ALTER TABLE farmily_jjim
add CONSTRAINT fk_jjim_board_no foreign key(board_no) references farmily_board(board_no) ON DELETE CASCADE;

alter table farmily_reservation drop CONSTRAINT fk_reservation_board_no;
ALTER TABLE farmily_reservation
add CONSTRAINT fk_reservation_board_no foreign key(board_no) references farmily_board(board_no) ON DELETE CASCADE;

-- 나(농활관리자)의 농촌활동에 예약한 회원 조회
select r.reservation_date,r.id, r.board_no, b.board_no , b.title, b.content, b.select_categori,b.board_categori,b.region,b.detail_region
from farmily_reservation r , farmily_board b
where b.board_no = r.board_no
and r.id is not null
and b.id = 'ddww'
order by reservation_date

select r.reservation_date,r.id, r.board_no, b.board_no , b.title, b.content, b.select_categori,b.board_categori,b.region,b.detail_region
from farmily_reservation r , farmily_board b
where b.board_no = r.board_no
and r.id is not null
and b.id = 'ddww'
order by reservation_date

-- 예약취소 sql
update farmily_reservation set id = null where board_no = 131 and reservation_date = '2022-07-24'


-- 메시지 기능을 위한 테이블 생성
create table farmily_message(
	message_no number primary key,
	sender varchar2(100),
	receiver varchar2(100),
	content clob not null,
	message_check number default 0,
	sent_date date,
	constraint fk_message_sender foreign key(sender) references farmily_member(id),
	constraint fk_message_receiver foreign key(receiver) references farmily_member(id)
)
create sequence seq_farmily_message;

insert into farmily_message(message_no,sender,receiver,content,sent_date) values(seq_farmily_message.nextval,'iiiu','ddww','hi',sysdate);
insert into farmily_message(message_no,sender,receiver,content,sent_date) values(seq_farmily_message.nextval,'iiiu','ddww','예약관련문의드립니다.',sysdate);
insert into farmily_message(message_no,sender,receiver,content,sent_date) values(seq_farmily_message.nextval,'ddww','iiiu','안녕하세요',sysdate);
insert into farmily_message(message_no,sender,receiver,content,sent_date) values(seq_farmily_message.nextval,'ddww','zxcv','안녕하세요구매하고 싶어요',sysdate);

select sender,receiver,content,sent_date,message_check from farmily_message where sender='ddww' or receiver='ddww'
select sender,receiver,content,sent_date,message_check from farmily_message where sender='ddww' order by sent_date desc
select distinct receiver,sender from farmily_message where sender='ddww'
select distinct receiver,sender from farmily_message where receiver='iiiu'
select * from farmily_message where (sender='iiiu' and receiver='ddww') or (sender='ddww' and receiver='iiiu')
select * from farmily_message where receiver='iiiu'

select * from farmily_message where message_no = 1
delete from farmily_message where sender='ddww' and receiver='qwert'
update farmily_message set message_check=1 where message_no = 1;
select count(*) from farmily_message where message_check = 0 and receiver = 'ddww';
delete from farmily_message where message_no = 54

-- 댓글 테이블 생성 (aws에도 생성완료)
create table farmily_comment(
	comment_no number primary key,
	comment_date date not null,
	board_no number not null,
	id varchar2(100) not null,
	comment_content CLOB not null,
	CONSTRAINT fk_comment_board_no foreign key(board_no) references farmily_board(board_no),  --외래키 지정문
	CONSTRAINT fk_comment_id foreign key(id) references farmily_member (id)  --외래키 지정문
)
create sequence seq_farmily_comment;

select * from farmily_comment;
select comment_no,comment_date,board_no,id,comment_content from farmily_comment where board_no = 145;
insert into farmily_comment(comment_no,comment_date,board_no,id,comment_content) values(seq_farmily_comment.nextval,sysdate,145,'ddww','test!')
delete from farmily_comment where comment_no = 2
update farmily_comment set comment_content = '댓글수정테스트' where comment_no = 3
select comment_no,comment_date,board_no,id,comment_content from farmily_comment where comment_no = 3


