  CREATE TABLE "TJOEUNIT"."TB_SCORE" 
   (	"SCORE_ID" NUMBER(*,0) NOT NULL ENABLE, 
	"PARTY_ID" NUMBER(*,0) NOT NULL ENABLE, 
	"MEMBER_ID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"SCORE" NUMBER, 
	 CONSTRAINT "TB_SCORE_PK" PRIMARY KEY ("SCORE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE, 
	 CONSTRAINT "TB_SCORE_FK1" FOREIGN KEY ("MEMBER_ID")
	  REFERENCES "TJOEUNIT"."TB_MEMBER" ("MEMBER_ID") ON DELETE CASCADE ENABLE, 
	 CONSTRAINT "TB_SCORE_FK2" FOREIGN KEY ("PARTY_ID")
	  REFERENCES "TJOEUNIT"."TB_PARTY" ("PARTY_ID") ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;


delete from TB_SCORE;
drop sequence TB_SCORE_idx_seq;
create sequence TB_SCORE_idx_seq;
