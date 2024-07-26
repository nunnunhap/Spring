/*
�ۼ��� : ����
������ : 2024-07-04
������Ʈ�� : ���θ�
*/
DROP TABLE MBSP_TBL;

SELECT
    mbsp_id,
    mbsp_name,
    mbsp_email,
    mbsp_password,
    mbsp_zipcode,
    mbsp_addr,
    mbsp_deaddr,
    mbsp_phone,
    mbsp_nick,
    mbsp_receive,
    mbsp_point,
    mbsp_lastlogin,
    mbsp_datesub,
    mbsp_updatedate,
    sns_login_type
FROM
    mbsp_tbl;


-- �ѱ� ������ ũ��?
SELECT LENGTHB('ȫ') FROM DUAL;

--1.ȸ������ ���̺�
CREATE TABLE MBSP_TBL(
        MBSP_ID             VARCHAR2(15),
        MBSP_NAME           VARCHAR2(30)            NOT NULL,
        MBSP_EMAIL          VARCHAR2(50)            NOT NULL,
        MBSP_PASSWORD       CHAR(60)               NOT NULL,        -- ��й�ȣ ��ȣȭ ó��.
        MBSP_ZIPCODE        CHAR(5)                 NOT NULL,
        MBSP_ADDR           VARCHAR2(100)            NOT NULL,
        MBSP_DEADDR         VARCHAR2(100)            NOT NULL,
        MBSP_PHONE          VARCHAR2(15)            NOT NULL,
        MBSP_NICK           VARCHAR2(30)            NOT NULL UNIQUE,
        MBSP_RECEIVE        CHAR(1) DEFAULT 0       NOT NULL,
        MBSP_POINT          NUMBER DEFAULT 0        NOT NULL,
        MBSP_LASTLOGIN      DATE                    NULL,
        MBSP_DATESUB        DATE DEFAULT SYSDATE    NOT NULL,
        MBSP_UPDATEDATE     DATE DEFAULT SYSDATE    NOT NULL
);

-- īī�� ȸ���÷� �߰�
ALTER TABLE MBSP_TBL
ADD SNS_LOGIN_TYPE VARCHAR2(10);

DROP TABLE KAKAO_TBL;
DROP TABLE SNS_USER_TBL;

CREATE TABLE KAKAO_TBL(
    ID          NUMBER          PRIMARY KEY,
    NICKNAME    VARCHAR2(50)    NOT NULL,
    EMAIL       VARCHAR2(100)   NOT NULL
);

CREATE TABLE SNS_USER_TBL(
    ID          VARCHAR2(100)   PRIMARY KEY,
    NAME        VARCHAR2(50)    NOT NULL,
    EMAIL       VARCHAR2(100)   NOT NULL,
    SNS_TYPE    VARCHAR2(10)    NOT NULL -- ��> kakao, naver, google ��
);

COMMIT;

SELECT
    id,
    name,
    email,
    sns_type
FROM
    sns_user_tbl;


ALTER TABLE MBSP_TBL
ADD CONSTRAINT PK_MBSP_ID PRIMARY KEY (MBSP_ID);


SELECT * FROM MBSP_TBL;
DELETE FROM MBSP_TBL;

-- ȸ�����

--1)ȸ������
INSERT INTO mbsp_tbl (mbsp_id,mbsp_name,mbsp_email, mbsp_password,mbsp_zipcode,mbsp_addr,mbsp_deaddr,mbsp_phone,mbsp_nick,mbsp_receive) 
VALUES ('user01','ȫ�浿','user01@abc.co.kr','1111','55555','����� ���α� â�ŵ�','�׸�����Ʈ 100��100ȣ','010-5555-5555','�ù�1','Y');   
INSERT INTO mbsp_tbl (mbsp_id,mbsp_name,mbsp_email, mbsp_password,mbsp_zipcode,mbsp_addr,mbsp_deaddr,mbsp_phone,mbsp_nick,mbsp_receive) 
VALUES ('user02','�̽¿�','user02@abc.co.kr','1111','55555','����� ���α� â�ŵ�','�׸�����Ʈ 100��100ȣ','010-5555-5555','�ù�2','Y');   
INSERT INTO mbsp_tbl (mbsp_id,mbsp_name,mbsp_email, mbsp_password,mbsp_zipcode,mbsp_addr,mbsp_deaddr,mbsp_phone,mbsp_nick,mbsp_receive) 
VALUES ('user03','�����','user03@abc.co.kr','1111','55555','����� ���α� â�ŵ�','�׸�����Ʈ 100��100ȣ','010-5555-5555','�ù�3','N');   
INSERT INTO mbsp_tbl (mbsp_id,mbsp_name,mbsp_email, mbsp_password,mbsp_zipcode,mbsp_addr,mbsp_deaddr,mbsp_phone,mbsp_nick,mbsp_receive) 
VALUES ('user04','������','user04@abc.co.kr','1111','55555','����� ���α� â�ŵ�','�׸�����Ʈ 100��100ȣ','010-5555-5555','�ù�4','Y');   
INSERT INTO mbsp_tbl (mbsp_id,mbsp_name,mbsp_email, mbsp_password,mbsp_zipcode,mbsp_addr,mbsp_deaddr,mbsp_phone,mbsp_nick,mbsp_receive) 
VALUES ('user05','����ȣ','user05@abc.co.kr','1111','55555','����� ���α� â�ŵ�','�׸�����Ʈ 100��100ȣ','010-5555-5555','�ù�5','N');   

COMMIT;

--2)���̵� �ߺ�üũ
-- ����Ÿ ���� �� ���
SELECT MBSP_ID FROM MBSP_TBL WHERE MBSP_ID = 'user01'; -- ���̵� ���Ұ���
-- ����Ÿ ���� �� �� ���
SELECT MBSP_ID FROM MBSP_TBL WHERE MBSP_ID = 'user06'; -- ���̵� ��밡��

SELECT COUNT(MBSP_ID) FROM MBSP_TBL WHERE MBSP_ID = 'user01';  -- 0 OR 1 �������.

--3)ȸ������
--3.1)��й�ȣȮ��
SELECT MBSP_ID FROM MBSP_TBL WHERE MBSP_ID = ? AND MBSP_PASSWORD = ?;

--3.2) ȸ������ ����������
SELECT MBSP_ID, MBSP_NAME, MBSP_PASSWORD, MBSP_ZIPCODE, MBSP_ADDR, MBSP_DEADDR, MBSP_PHONE, MBSP_NICK, MBSP_RECEIVE, MBSP_POINT, MBSP_DATESUB, MBSP_UPDATEDATE, MBSP_LASTLOGIN
FROM MBSP_TBL
WHERE MBSP_ID = ? AND MBSP_PASSWORD = ?;

--3.3) ȸ������ �����ϱ�
UPDATE MBSP_TBL
    SET MBSP_PASSWORD = ?, MBSP_ZIPCODE = ?, MBSP_ADDR = ?, MBSP_DEADDR = ?, MBSP_PHONE = ?, MBSP_RECEIVE = ?
WHERE MBSP_ID = ?;

--4) ȸ������ �����ϱ�
--4.1) ��й�ȣ Ȯ��
SELECT MBSP_ID FROM MBSP_TBL WHERE MBSP_ID = ? AND MBSP_PASSWORD = ?;

--4.2) �����ϱ�
DELETE FROM MBSP_TBL WHERE MBSP_ID = ? AND MBSP_PASSWORD = ?;

--5)���̵�׺�й�ȣã��
--5.1)���̵� ã��(���� ����)
SELECT MBSP_ID FROM MBSP_TBL WHERE MBSP_NAME = '�����' AND MBSP_EMAIL = 'user03@abc.co.kr';
--5.2)��й�ȣ ã��(���� ����)
SELECT MBSP_PASSWORD FROM MBSP_TBL WHERE MBSP_ID = 'user03' AND MBSP_EMAIL = 'user03@abc.co.kr';


--6)�α��� ���(��й�ȣ�� ��ȣȭ ó�� �� ��쿡�� �����߻�)
SELECT MBSP_ID FROM MBSP_TBL WHERE MBSP_ID = ? AND MBSP_PASSWORD = ?;

-- ��й�ȣ ��ȣȭ �� ���
SELECT MBSP_PASSWORD FROM MBSP_TBL WHERE MBSP_ID = ?;

-- ��� �÷����� ��ȣȭ �� ����Ÿ�� �о�ͼ�, ���ϴ� �޼��带 ���Ͽ� �۾��ϰԵȴ�.


--2.ī�װ� ���̺�

DROP TABLE CATEGORY_TBL;
CREATE TABLE CATEGORY_TBL(
        CAT_CODE            NUMBER    PRIMARY KEY,    -- ī�װ� �ڵ�
        CAT_PRTCODE         NUMBER    NULL,           -- ����ī�װ� �ڵ�
        CAT_NAME            VARCHAR2(50)    NOT NULL
);

ALTER TABLE CATEGORY_TBL
ADD CONSTRAINTS FK_CATEGORY_PCODE
FOREIGN KEY(CAT_PRTCODE)
REFERENCES CATEGORY_TBL(CAT_CODE);

-- / -> /

-- 1�� ī�װ� : TOP(1) PANTS(2) SHIRTS(3) OUTER(4) SHOES(5) BAG(6) ACC(7)
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (1,NULL,'TOP');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (2,NULL,'PANTS');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (3,NULL,'SHIRTS');    
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (4,NULL,'OUTER');        
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (5,NULL,'SHOES');    
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (6,NULL,'BAG');    
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (7,NULL,'ACC');    

-- 1��ī�װ� TOP : 1
-- 2�� ī�װ� : ����Ƽ ��Ʈ ������/�ĵ�Ƽ ������Ƽ ���� ����Ƽ/7��Ƽ
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (8,1,'����Ƽ');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
VALUES (9,1,'��Ʈ');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
VALUES (10,1,'������&#38;�ĵ�Ƽ');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
VALUES (11,1,'������Ƽ');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
VALUES (12,1,'����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
VALUES (13,1,'����Ƽ&#38;7��Ƽ');

-- 1��ī�װ� PANTS : 2
-- 2��ī�װ� : ������� û���� ������ ����� �ݹ���
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (14,2,'�������');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (15,2,'û����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (16,2,'������');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (17,2,'�����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (18,2,'�ݹ���');
    
-- 1��ī�װ� SHIRTS : 3
-- 2��ī�װ� : ���/���̳� ������ üũ/���� û���� ��Ʈ������ 

INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (19,3,'���&#38;���̳�');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (20,3,'������');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (21,3,'üũ&#38;����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (22,3,'û����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (23,3,'��Ʈ������'); 
    
    
-- 1��ī�װ� OUTER : 4
-- 2��ī�װ� : �е� ��Ʈ ��Ʈ/������ ���� �����/MA-1 �����/���� �ĵ�/����

INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (24,4,'�е�');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (25,4,'��Ʈ');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (26,4,'��Ʈ&#38;������');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (27,4,'����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (28,4,'�����&#38;MA-1');     
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (29,4,'�����&#38;����');     
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (30,4,'�ĵ�&#38;����');  
    
-- 1��ī�װ� SHOES : 5
-- 2��ī�װ� : ����Ŀ�� ����/���� Ű���̽Ź�/��â ������/�ɸ�/����
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (31,5,'����Ŀ��');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (32,5,'����&#38;����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (33,5,'Ű���̽Ź�&#38;��â');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (34,5,'������&#38;�ɸ�/����');
   
-- 1��ī�װ� BAG : 6
-- 2��ī�װ� : ���� ��Ʈ/����� ũ�ν��� Ŭ��ġ
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (35,6,'����'); 
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (36,6,'��Ʈ/�����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (37,6,'ũ�ν���');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (38,6,'Ŭ��ġ');    
-- 1��ī�װ� ACC : 7
-- 2��ī�װ� : �縻/��Ÿ�� ���� ���÷�/�尩 ���̿��� ��Ʈ/�ð� ��Ÿ
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (39,7,'�縻/��Ÿ��');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (40,7,'����');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (41,7,'���÷�&#38;�尩');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (42,7,'���̿���');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (43,7,'��Ʈ&#38;�ð�');
INSERT INTO category_tbl (cat_code,cat_prtcode,cat_name) 
    VALUES (44,7,'��Ÿ');
    

-- 1��ī�װ� ���
SELECT * FROM category_tbl WHERE cat_prtcode IS NULL;

-- 1��ī�װ� TOP �� 2��ī�װ� ���.
SELECT * FROM category_tbl WHERE cat_prtcode = ?;


-- 2��ī�װ� ���� ����϶�.
SELECT * FROM category_tbl WHERE cat_prtcode IS NOT NULL;



SELECT * FROM category_tbl;
COMMIT;
ROLLBACK;

SELECT 055 FROM DUAL;  -- �÷��� NUMBER
SELECT '055' FROM DUAL; -- �÷��� VARCHAR2(10)
    
--3.��ǰ���� ���̺�
DROP TABLE PRODUCT_TBL;

/*
����Ÿ�� �����Ѵٴ� �������ǿ���
���̺��� �÷��� 
    NOT NULL -> NUL �� ���� ����
    NULL -> NOT NULL �� ���� �Ұ���

*/
DROP TABLE PRODUCT_TBL;
CREATE TABLE PRODUCT_TBL(
        PRO_NUM             NUMBER,
        CAT_CODE            NUMBER            NULL, -- 2�� ī�װ�
        PRO_NAME            VARCHAR2(100)            NOT NULL,
        PRO_PRICE           NUMBER                  NOT NULL,
        PRO_DISCOUNT        NUMBER                  NOT NULL,
        PRO_PUBLISHER       VARCHAR2(100)            NOT NULL,
        PRO_CONTENT         VARCHAR2(4000)  /* CLOB */NOT NULL,-- ������ 4000BYTE �ʰ������Ǵ�?
        PRO_UP_FOLDER       VARCHAR(50)             NOT NULL, -- ��¥������� ��>2024\06\11
        PRO_IMG             VARCHAR(100)             NOT NULL,  -- �����̸�
        PRO_AMOUNT          NUMBER                  NOT NULL,
        PRO_BUY             CHAR(1)                 NOT NULL, -- 'Y' or 'N'
        PRO_DATE            DATE DEFAULT SYSDATE    NOT NULL,
        PRO_UPDATEDATE      DATE DEFAULT SYSDATE    NOT NULL
);

SELECT
    pro_num,
    cat_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy,
    pro_date,
    pro_updatedate
FROM
    product_tbl;

-- ��ǰ���� �̹����� �������� ��� �̹������̺� ���� ����
-- ������ ����
CREATE SEQUENCE SEQ_PRO_NUM;

ALTER TABLE PRODUCT_TBL
ADD CONSTRAINTS PK_PRO_NUM
PRIMARY KEY(PRO_NUM);

-- ��ǰ���� �� �۾�
-- 2�� ī�װ� ������ �̿��Ͽ� 1�� ī�װ� ���� ����
SELECT * FROM category_tbl WHERE cat_code = 8;

INSERT INTO product_tbl(
    pro_num,
    cat_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy)
VALUES(SEQ_PRO_NUM.NEXTVAL, 8,'C',10000,10,'����','<img alt="" src="/admin/product/display/selection.jpg" style="height:612px; width:634px" />','2024\07\04','152a419c-f1b1-4d33-8aee-f35d625821d1_selection.jpg',10,'Y');
COMMIT;
INSERT INTO product_tbl(
    pro_num,
    cat_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy)
VALUES(SEQ_PRO_NUM.NEXTVAL, 8,'A',10000,0.1,'����','��¼����¼��','2023\04\06',
's_9ba281ee-4744-43f5-99f7-9954164995ab_bear.jpg',10,'Y');




COMMIT;




SELECT * FROM 

SELECT
    *
FROM
    category_tbl
WHERE
    cat_prtcode = 1;


ALTER TABLE PRODUCT_TBL
ADD CONSTRAINTS FK_PRODUCT_PCODE
FOREIGN KEY(CAT_CODE)
REFERENCES CATEGORY_TBL(CAT_CODE);

-- ��ǰ���� �̹����� ������ �ٸ� ��� ������ ���̺��� ����(����)
-- ��ǰ���� �÷��� �������͸� �̿��� �±��ڵ� ������ ����ȴ�.

-- PRO_CONTENT�÷��� CLOB ����Ÿ Ÿ���� �񱳸�ɾ ��������.
SELECT * FROM product_tbl ORDER BY PRO_CONTENT ASC;


-- ��ǰ����۾�
-- pro_up_folder �÷� : ���ε������� ���� ��¥�����̸�.   �ü���� ��α�����  ����1) /2023/04/06/   ����2)\2023\04\06\ ��������


-- 1��ī�װ� : TOP (�ڵ� : 1)
-- 2��ī�װ� : ����Ƽ(�ڵ� : 8)
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_up_folder, pro_amount,pro_buy) 
VALUES (1,8,'A',10000,0.1,'����','��¼����¼��','\2023\04\06\','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code, pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_up_folder,pro_amount,pro_buy) 
VALUES (2,14,'A',10000,0.1,'����','��¼����¼��','\2023\04\06\','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_up_folder,pro_amount,pro_buy) 
VALUES (3,19,'A',10000,0.1,'����','��¼����¼��','\2023\04\06\','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_up_folder,pro_amount,pro_buy) 
VALUES (4,24,'A',10000,0.1,'����','��¼����¼��','\2023\04\06\','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_up_folder,pro_amount,pro_buy) 
VALUES (5,31,'A',10000,0.1,'����','��¼����¼��','\2023\04\06\','abc.gif',10,'Y');

-- 2��ī�װ� : ��Ʈ(�ڵ� : 9)
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (6,'9','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code, pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (7,'9','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (8,'9','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (9,'9','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (10,'9','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');

-- 2��ī�װ� : ������&�ĵ�Ƽ(�ڵ� : 10)
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (11,'10','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code, pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (12,'10','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (13,'10','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (14,'10','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (15,'10','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');

-- 2��ī�װ� : ������Ƽ(�ڵ� : 11)
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (16,'11','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code, pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (17,'11','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (18,'11','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (19,'11','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (20,'11','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');


-- 2��ī�װ� : ����(�ڵ� : 12)
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (21,'12','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code, pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (22,'12','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (23,'12','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (24,'12','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (25,'12','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');

-- 2��ī�װ� : ����Ƽ&7��Ƽ(�ڵ� : 13)
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (26,'13','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code, pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (27,'13','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (28,'13','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (29,'13','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');
INSERT INTO product_tbl (pro_num,cat_code,pro_name,pro_price,pro_discount,pro_publisher,pro_content,pro_img,pro_amount,pro_buy) 
VALUES (30,'13','A',10000,0.1,'����','��¼����¼��','abc.gif',10,'Y');

COMMIT;


/*********************************************************************
-- ��ǰ����Ʈ ���
*/

-- 1�� ī�װ��� ��ǰ��� ��ȸ.  ��) TOP(1) 1��ī�װ� ����
SELECT * FROM product_tbl WHERE cat_code IN (SELECT cat_code FROM  category_tbl WHERE CAT_PRTCODE = ? );

SELECT * FROM product_tbl WHERE cat_code IN (SELECT cat_code FROM  category_tbl WHERE CAT_PRTCODE = 1 );

-- 2�� ī�װ��� ��ǰ��� ��ȸ.
SELECT * FROM product_tbl WHERE cat_code = ?;

SELECT * FROM product_tbl WHERE cat_code = 8;




-- ��ǰ�󼼼���
/*
��ǰ����Ʈ���� ��ǰ �ϳ��� �����Ͽ��� �� ������ ������

*/
SELECT * FROM product_tbl WHERE PRO_NUM = ?;

SELECT * FROM product_tbl WHERE PRO_NUM = 1;

DROP TABLE CART_TBL;

--4.��ٱ��� ���̺�(��ٱ��� �߰�, ����, ����, ���, ���� ...)
DROP TABLE CART_TBL;
CREATE TABLE CART_TBL(
        CART_CODE        NUMBER,
        PRO_NUM         NUMBER          NOT NULL,
        MBSP_ID         VARCHAR2(15)    NOT NULL,
        CART_AMOUNT      NUMBER          NOT NULL,
        CART_DATE       DATE            DEFAULT SYSDATE
);


SELECT
    cart_code,
    pro_num,
    mbsp_id,
    cart_amount,
    cart_date
FROM
    cart_tbl;
CREATE SEQUENCE SEQ_CART_CODE;

ALTER TABLE CART_TBL
ADD CONSTRAINTS PK_CART_CODE
PRIMARY KEY(CART_CODE);

commit;

-- ��ٱ��� ��� join
SELECT
    c.mbsp_id, p.pro_up_folder, p.pro_img,
    p.pro_price, p.pro_name, c.cart_code,
    c.pro_num, c.cart_amount
FROM cart_tbl c INNER JOIN product_tbl P
ON c.pro_num = p.pro_num
WHERE c.mbsp_id = 'user01';


SELECT
    cart_code,
    pro_num,
    mbsp_id,
    cart_amount,
    cart_date
FROM
    cart_tbl;

SELECT
    pro_num,
    cat_code,
    pro_name,
    pro_price,
    pro_discount,
    pro_publisher,
    pro_content,
    pro_up_folder,
    pro_img,
    pro_amount,
    pro_buy,
    pro_date,
    pro_updatedate
FROM
    product_tbl;















ALTER TABLE CART_TBL
ADD CONSTRAINTS FK_CART_PRO_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);

ALTER TABLE CART_TBL
ADD CONSTRAINTS FK_CART_MBSP_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);


-- �α����� ���¿��� ��ٱ��� ����
SELECT * FROM CART_TBL WHERE MBSP_ID = ?;

-- ��ǰ������������ ��ٱ��� ��� - MERGE �������.
/*
����? ��ٱ��Ͽ� ������ ��ǰ�� �����ϸ�, ������ ����(������Ʈ)�۾� : UPDATE
                            �������� ������, ��ǰ�� ��ٱ��Ͽ� �߰� : INSERT
*/
MERGE
    INTO CART_TBL C
USING DUAL
    ON (C.MBSP_ID = ?) AND (C.PRO_NUM = ?)
 WHEN MATCHED THEN
    UPDATE
        SET C.CAT_AMOUNT = C.CAT_AMOUNT + ?
 WHEN NOT MATCHED THEN
    INSERT (C.CAT_CODE, C.PRO_NUM, C.MBSP_ID, C.CAT_AMOUNT)
    VALUES ( ?, ?, ?, ?);
    
    
-- ��ٱ��Ͽ� ���ϻ�ǰ�� �����ϴ� �� ���θ� Ȯ��    
SELECT COUNT(*) FROM CART_TBL WHERE (C.MBSP_ID = ?) AND (C.PRO_NUM = ?);

SELECT C.CAT_CODE FROM CART_TBL WHERE (C.MBSP_ID = ?) AND (C.PRO_NUM = ?);
    
    
    

INSERT INTO cart_tbl (cat_code,pro_num,mbsp_id,cat_amount) VALUES (1,1,'user01', 2 );
INSERT INTO cart_tbl (cat_code,pro_num,mbsp_id,cat_amount) VALUES (2,5,'user01', 3 );
INSERT INTO cart_tbl (cat_code,pro_num,mbsp_id,cat_amount) VALUES (3,7,'user01', 5 );
INSERT INTO cart_tbl (cat_code,pro_num,mbsp_id,cat_amount) VALUES (4,12,'user02', 1 );
INSERT INTO cart_tbl (cat_code,pro_num,mbsp_id,cat_amount) VALUES (5,30,'user02', 2 );

SELECT * FROM cart_tbl;
COMMIT;

/*
�ֹ��ϱ�
  - 1)��ٱ��� ����ϰ� ��ٱ��Ͽ��� ����.
  - 2)��ٱ��� ��� ���ϰ�, �ٷα���.
 
*/
-- ��ٱ��� ����Ʈ ��ȸ
SELECT rownum, P.pro_img, P.pro_name, P.pro_price, C.cat_amount, P.pro_price * C.cat_amount as unitprice
FROM product_tbl p INNER JOIN cart_tbl c
ON p.pro_num = c.pro_num
WHERE c.MBSP_ID = 'user01';

-- ��ü�ݾ� : 100000
SELECT SUM(P.pro_price * C.cat_amount) as totalprice
FROM product_tbl p INNER JOIN cart_tbl c
ON p.pro_num = c.pro_num
WHERE c.MBSP_ID = 'user01';

-- ��ٱ��Ͽ��� ��������
-- ������ ��������
UPDATE cart_tbl
    SET CAT_AMOUNT = 10
WHERE CAT_CODE = 1;

SELECT * FROM cart_tbl WHERE CAT_CODE = 1;
COMMIT;
-- ����!!!!(������ ����)
UPDATE cart_tbl
    SET CAT_AMOUNT = CAT_AMOUNT + 10
WHERE CAT_CODE = 1;

-- ��ٱ��� ��ǰ���� ( CAT_CODE : ��ٱ����ڵ� )
DELETE FROM cart_tbl WHERE CAT_CODE = 1;

SELECT * FROM cart_tbl WHERE CAT_CODE = 1;
COMMIT;

-- ��ٱ��� ���� : �α��� �� ����� ����Ÿ�� �����ؾ� �Ѵ�.(����)
DELETE FROM cart_tbl WHERE mbsp_id = 'user01';


-- �����ϱ� : Ʈ�����ó��.
/*
�ֹ����̺� : �ֹ���, �����(������) �� 
�ֹ������̺� : ���� ��ǰ����
��ٱ������̺� : �α��� ������� ��ǰ���� ����
ȸ�����̺� ����Ʈ ����.
*/
DROP TABLE ORDER_TBL;
--5.�ֹ����̺� : �ֹ��ڿ� ���� ����
CREATE TABLE ORDER_TBL(
        ORD_CODE            NUMBER,
        MBSP_ID             VARCHAR2(15)            NOT NULL,
        ORD_NAME            VARCHAR2(30)            NOT NULL,
        ORD_ADDR_ZIPCODE    CHAR(5)                 NOT NULL,
        ORD_ADDR_BASIC      VARCHAR2(50)            NOT NULL,
        ORD_ADDR_DETAIL     VARCHAR2(50)            NOT NULL,
        ORD_TEL             VARCHAR2(20)            NOT NULL,
        ORD_PRICE           NUMBER                  NOT NULL, -- ���ֹ��ݾ�. ����
        ORD_DESC            VARCHAR2(300)           NULL, -- �ֹ� �� ��û����
        ORD_REGDATE         DATE DEFAULT SYSDATE    NOT NULL
);

CREATE SEQUENCE SEQ_ORD_CODE;

ALTER TABLE ORDER_TBL
ADD CONSTRAINTS PK_ORD_CODE
PRIMARY KEY(ORD_CODE);

-- �÷��߰�
ALTER TABLE ORDER_TBL
ADD ORD_ADMIN_MEMO VARCHAR2(100);

pk_ord_code
seq_ord_code
SELECT
    ord_code,
    mbsp_id,
    ord_name,
    ord_addr_zipcode,
    ord_addr_basic,
    ord_addr_detail,
    ord_tel,
    ord_price,
    ord_desc,
    ord_regdate,
    ord_admin_memo
FROM
    order_tbl;
ALTER TABLE ORDER_TBL
ADD CONSTRAINTS FK_ORDER_MBSP_ID
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);



DROP TABLE ORDETAIL_TBL;
--6.�ֹ��� ���̺� : �ֹ���ǰ�� ���� ����
CREATE TABLE ORDETAIL_TBL(
        ORD_CODE        NUMBER      NOT NULL,
        PRO_NUM         NUMBER      NOT NULL,
        DT_AMOUNT       NUMBER      NOT NULL,
        DT_PRICE        NUMBER      NOT NULL  -- ������ ���� ������ȭ
);
SELECT
    ord_code,
    pro_num,
    dt_amount,
    dt_price
FROM
    ordetail_tbl;
    SELECT
    cart_code,
    pro_num,
    mbsp_id,
    cart_amount,
    cart_date
FROM
    cart_tbl;

INSERT INTO
    ordetail_tbl(ord_code, pro_num, dt_amount, dt_price)
    SELECT
        #{�ֹ���ȣ}, pro_num, cart_amount, pro_num * cart_amount
    FROM
        cart_tbl
    WHERE
        mbsp_id = #{mbsp_id}

-- ������ �ֹ�����
-- �ֹ�����
SELECT ot.ord_code, ot.pro_num, ot.dt_amount, ot.dt_price, p.pro_name, p.pro_up_folder, p.pro_img
FROM ordetail_tbl ot INNER JOIN product_tbl p
ON ot.pro_num = p.pro_num
WHERE ot.ord_code = 29;



ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS PK_ORDETAIL_CODE_NUM
PRIMARY KEY(ORD_CODE ,PRO_NUM);

ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS FK_ORDETAIL_CODE
FOREIGN KEY(ORD_CODE)
REFERENCES ORDER_TBL(ORD_CODE);

ALTER TABLE ORDETAIL_TBL
ADD CONSTRAINTS FK_ORDETAIL_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM));


-- ���ֹ��ݾ�
SELECT ORD_CODE, SUM(DT_AMOUNT * DT_PRICE) FROM ORDETAIL_TBL WHERE ORD_CODE = ?;

/*

��ٱ��� -> �ֹ��ϱ� Ŭ�� �� -> �ֹ����� ������

*/
-- �ֹ����� ������ ����

-- �ֹ��� ���� ����
SELECT  *
FROM mbsp_tbl
WHERE mbsp_id = 'user01';



/*
Ʈ�����(����ó������)����� �����ؾ� �Ǵ°��?
�ϳ��� ��ɿ� ������ �۾��� �� ���(INSERT, UPDATE, DELETE, MERGE ��)
*/


/*
�ֹ������� �Է� �� �ֹ��ϱ� ������ �� ��� �������� �۾��� ���ÿ� �̷������.

Ʈ�����(����ó������)����� �����ؾ��Ѵ�.
- �ֹ����� �Է�(�ֹ����̺�, �ֹ������̺�)
- ����Ʈ ����
- ��ٱ��� ����
- ����

*/

-- �ֹ����� ���.   1)������(SQL �������)���� Ʈ��������� �۾�  2) ���ν������� Ʈ����� ���� �۾�
--��ٱ��� ��������
SELECT * FROM cart_tbl WHERE mbsp_id = 'user01';

--1)�ֹ����̺� 
INSERT INTO order_tbl (ord_code,mbsp_id,ord_name,ord_addr_num,ord_addr_basic,ord_addr_detail,ord_tel,ord_price) 
VALUES (1,'user01','ȫ�浿','10050','����ϵ� ��â','����� 100��','010-555-5555',20000);

--2)�ֹ������̺� - ��ǰ���� ����Ÿ ����

INSERT INTO ordetail_tbl (ord_code,pro_num,dt_amount,dt_price) 
VALUES (1, 5, 3, 1000);
INSERT INTO ordetail_tbl (ord_code,pro_num,dt_amount,dt_price) 
VALUES (1, 7, 5, 1000);

--3)��ٱ������̺�
DELETE FROM cart_tbl WHERE mbsp_id = 'user01';

--4) ����Ʈ ���
UPDATE mbsp_tbl
SET mbsp_point = mbsp_point + 50    -- 50 ����Ʈ ��
WHERE mbsp_id = 'user01';

COMMIT;

-- �ֹ����� ��ȸ ����Ʈ

SELECT * FROM ORDER_TBL;
SELECT * FROM ORDETAIL_TBL;

-- ��ǰ, �ֹ�, �ֹ��� ������ �̿��� �ֹ�������ȸ
SELECT rownum, OD.ORD_REGDATE, P.PRO_NAME , OT.DT_AMOUNT * OT.DT_PRICE  as SALEPRICE
FROM ORDER_TBL od   INNER JOIN ORDETAIL_TBL ot
ON od.ord_code = ot.ord_code
INNER JOIN PRODUCT_TBL p
ON OT.PRO_NUM = P.PRO_NUM
WHERE od.mbsp_id = 'user01';


-- ���� 4���� �۾��� ���ν����� ����(Ʈ����� ����)
CREATE OR REPLACE PROCEDURE UDP_ORDERINFO_PROCESS
(
    p_ord_code          IN    order_tbl.ord_code%TYPE,
    p_mbsp_id           IN    mbsp_tbl.mbsp_id%TYPE,
    p_ord_name          IN    order_tbl.ord_name%TYPE,
    p_ord_addr_num      IN    order_tbl.ord_addr_num%TYPE,
    p_ord_addr_basic    IN    order_tbl.addr_basic%TYPE,
    p_ord_addr_detail   IN    order_tbl.ord_addr_detail%TYPE,
    p_ord_tel           IN    order_tbl.ord_tel%TYPE,
    p_ord_price         IN    order_tbl.ord_price%TYPE,
    
    
    /*
    �Ʒ� 3���� �Ű������� ��ٱ����� ��ǰ������ŭ ����Ÿ�� �����ؾ� �Ѵ�.
    ��) ��ٱ��� ����Ÿ 3�� �� ��� �Ű������� ���� 3���� ������ ������ �Ѵ�.
    */
    p_pro_num           IN    ,
    p_dt_amount         IN      ,
    p_dt_price          IN      ,
    
    
    
    p_mbsp_point        IN     mbsp_tbl.mbsp_point%TYPE 
)
IS

BEGIN
    --1)�ֹ����̺� �Է�
    INSERT INTO order_tbl (ord_code,mbsp_id,ord_name,ord_addr_num,ord_addr_basic,ord_addr_detail,ord_tel,ord_price) 
        VALUES (1,p_mbsp_id,'ȫ�浿','10050','����ϵ� ��â','����� 100��','010-555-5555',20000);
    --2)�ֹ������̺� �Է�   
    INSERT INTO ordetail_tbl (ord_code,pro_num,dt_amount,dt_price) 
    VALUES (1, 5, 3, 1000);
    INSERT INTO ordetail_tbl (ord_code,pro_num,dt_amount,dt_price) 
    VALUES (1, 7, 5, 1000);
    
    --3)��ٱ������̺� ����
    DELETE FROM cart_tbl WHERE mbsp_id = 'user01';
    
    --4)ȸ�����̺� ����Ʈ ����
    UPDATE mbsp_tbl
        SET mbsp_point = mbsp_point - 50    -- 50 ����Ʈ ��
    WHERE mbsp_id = 'user01';

    COMMIT;
    
    EXCEPTION WHEN OTHERS THEN
        ROLLBACK;
END;

DROP TABLE REVIEW_TBL;
--7.���� ���̺�
CREATE TABLE REVIEW_TBL(
        REV_CODE        NUMBER,
        MBSP_ID        VARCHAR2(15)                NOT NULL,
        PRO_NUM        NUMBER                      NOT NULL,
        REV_TITLE       VARCHAR2(50)               NOT NULL,
        REV_CONTENT     VARCHAR2(200)               NOT NULL,
        REV_RATE        NUMBER                      NOT NULL,
        REV_DATE     DATE DEFAULT SYSDATE
);
SELECT
    rev_code,
    mbsp_id,
    pro_num,
    rev_title,
    rev_content,
    rev_rate,
    rev_date
FROM
    review_tbl;
-- ������ ����
CREATE SEQUENCE SEQ_REVIEW_CODE;

ALTER TABLE REVIEW_TBL
ADD CONSTRAINTS PK_REVIEW_CODE
PRIMARY KEY(REV_CODE);

ALTER TABLE REVIEW_TBL
ADD CONSTRAINTS FK_RE_CODE
FOREIGN KEY(MBSP_ID)
REFERENCES MBSP_TBL(MBSP_ID);

ALTER TABLE REVIEW_TBL
ADD CONSTRAINTS FK_RE_NUM
FOREIGN KEY(PRO_NUM)
REFERENCES PRODUCT_TBL(PRO_NUM);



--��ǰ�ı� �Է�
SELECT * FROM ORDETAIL_TBL;

INSERT INTO review_tbl 
(   rev_code,
    mbsp_id,
    pro_num,
    rev_title,
    rev_content,
    rev_rate)
    VALUES (SEQ_REVIEW_CODE.nextval, 'user01',6,'�ı�3', '�ı⳻��333', 4);
    
INSERT INTO review_tbl (rew_num,mbsp_id,pro_num,rew_content,rew_score)
    VALUES (2, 'user01',7,'���� ����.', 5 );
    
    
COMMIT;    
SELECT * FROM  review_tbl; 

-- ��ǰ�󼼼���(�պκ�)/ ��ǰ�ı�(�޺κ�)

SELECT * FROM PRODUCT_TBL WHERE PRO_NUM = 5;

SELECT * FROM review_tbl WHERE PRO_NUM = 5;

DROP TABLE BOARD_TBL;

--8.�Խ��� ���̺�
CREATE SEQUENCE BOARD_NUM_SEQ;
CREATE TABLE BOARD_TBL(
        BRD_NUM         NUMBER,
        MBSP_ID         VARCHAR2(15)            NOT NULL,
        BRD_TITLE       VARCHAR2(100)           NOT NULL,
        BRD_CONTENT     VARCHAR2(4000)          NOT NULL,
        BRD_REGDATE     DATE DEFAULT SYSDATE    NOT NULL
);

ALTER TABLE BOARD_TBL
ADD CONSTRAINTS PK_BOARD_BRD_NUM
PRIMARY KEY(BRD_NUM);





-- ����Ű �߰�
ALTER TABLE BOARD_TBL ADD CONSTRAINT FK_BOARD_MBSP_ID
FOREIGN KEY (MBSP_ID) REFERENCES MBSP_TBL(MBSP_ID);

--9.������(ADMIN)���̺�
CREATE TABLE ADMIN_TBL (
    ADMIN_ID    VARCHAR2(15)    PRIMARY KEY,
    ADMIN_PW    CHAR(60)    NOT NULL,
    ADMIN_VISIT_DATE    DATE
);

-- ������ ���� ������ ���� (����)
INSERT INTO ADMIN_TBL(ADMIN_ID, ADMIN_PW)
VALUES ('admin', '$2a$10$Slq604R9DMTg/rbB5Td7Ueu2Qq1ntA6e1P1dpwFEj4C398yTfFbvS');
SELECT
    admin_id,
    admin_pw,
    admin_visit_date
FROM
    admin_tbl;
commit;

-- �ε��� ��Ʈ ��������
-- BOARD_TBL �Խ������̺� ���̵����� �۾�
INSERT INTO BOARD_TBL(BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE)
VALUES(BOARD_NUM_SEQ.NEXTVAL, 'USER01', '����1', '����1', SYSDATE);


INSERT INTO BOARD_TBL(BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE)
SELECT BOARD_NUM_SEQ.NEXTVAL, 'USER04', '����4', '����4', SYSDATE FROM BOARD_TBL;

COMMIT;


/*
�ε��� ��Ʈ ��ɾ�
INDEX : ��������.  INDEX_ASC
INDEX_DESC : ��������
*/
-- order by  �������
SELECT * FROM BOARD_TBL ORDER BY BRD_NUM DESC;

-- �ε��� ��Ʈ �������
SELECT /*+ INDEX_DESC(b PK_BOARD_BRD_NUM )  */
    BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
FROM 
    BOARD_TBL b;
    
SELECT /*+ INDEX_ASC(b PK_BOARD_BRD_NUM )  */
    BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
FROM 
    BOARD_TBL b;
    
-- �ε������� Ʋ�� ���   K_BOARD_BRD_NUM -> K_BOARD_BRD_NUM2 
SELECT /*+ INDEX_DESC(b PK_BOARD_BRD_NUM2 )  */
BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
FROM 
    BOARD_TBL b;


-- ����¡���� ����.
-- Ű���� ROWNUM ���
SELECT * FROM BOARD_TBL WHERE ROWNUM <= 10 ORDER BY BRD_NUM DESC;

SELECT * FROM BOARD_TBL WHERE ROWNUM >= 5 AND ROWNUM <=10; -- ��°���� ����. ROWNUM ���ۿ��� ������.

-- ���� ���ۿ��� ������ �ζ��� �並 ���
-- ������ 1 Ŭ��
SELECT RN, BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
FROM ( 
        SELECT /*+ INDEX_DESC(b PK_BOARD_BRD_NUM )  */ 
            ROWNUM RN, BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
            FROM BOARD_TBL b
    )
WHERE RN >=1 AND RN <=3;

-- ������ 2 Ŭ��
SELECT RN, BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
FROM ( 
        SELECT /*+ INDEX_DESC(b PK_BOARD_BRD_NUM )  */
            ROWNUM RN, BRD_NUM, MBSP_ID, BRD_TITLE, BRD_CONTENT, BRD_REGDATE
            FROM BOARD_TBL b
    )
WHERE RN >=4 AND RN <=6;



-- 10. �������� ���̺�
CREATE TABLE NOTICE(
    IDX         NUMBER,
    TITLE       VARCHAR2(50)    NOT NULL,
    CONTENT     VARCHAR2(1000)  NOT NULL,
    WRITER      VARCHAR2(15)    NOT NULL,
    READCOUNT   NUMBER  DEFAULT 0 NOT NULL,
    REGDATE     DATE    DEFAULT SYSDATE
);

ALTER TABLE NOTICE
ADD CONSTRAINTS PK_NOTICE_IDX
PRIMARY KEY (IDX);

ALTER TABLE NOTICE ADD CONSTRAINT FK_NOTICE_WRITER
FOREIGN KEY (MBSP_ID) REFERENCES ADMIN_TBL(ADMIN_ID);

-- 11. ���� ���̺�
DROP TABLE PAYINFO;
CREATE TABLE PAYINFO (
    P_ID        NUMBER  NOT NULL,
    ORD_CODE    NUMBER  NOT NULL,
    MBSP_ID     VARCHAR2(15)    NOT NULL,
    PAYMETHOD   VARCHAR2(50)    NOT NULL, -- īī������, �������Ա�, ī����� ��
    PAYINFO     VARCHAR2(100)   NULL, -- �������� ���, ����/ ���¹�ȣ/ ������
    P_PRICE     NUMBER  NOT NULL, -- �� �ݾ�
    P_STATUS    VARCHAR2(10)    NOT NULL, -- �ϳ�/�̳�
    P_DATE      DATE    DEFAULT SYSDATE
);
COMMIT;
  SELECT
    p_id,
    ord_code,
    mbsp_id,
    paymethod,
    payinfo,
    p_price,
    p_status,
    p_date
FROM
    payinfo;  
ALTER TABLE PAYINFO
ADD CONSTRAINTS PK_PAYINFO_IDX
PRIMARY KEY (P_ID);

ALTER TABLE PAYINFO ADD CONSTRAINT FK_PAYINFO_ORD_CODE
FOREIGN KEY (ORD_CODE) REFERENCES ORDER_TBL(ORD_CODE);

CREATE SEQUENCE SEQ_PAYINFO_ID;

-- 12. �Խ��� qna_tbl
CREATE TABLE qnaboard_tbl (
    qna_idx     NUMBER,
    pro_num     NUMBER  NOT NULL,
    question    VARCHAR2(100)   NOT NULL,
    answer      VARCHAR2(100)   NULL,
    anscheck    CHAR(1) DEFAULT 'n',
    question_date   DATE NOT NULL   DEFAULT SYSDATE,
    answer_date DATE NOT NULL
);

-- 13. ���Ϲ߼� mailmng_tbl(manage)
DROP TABLE MAILMNG_TBL;
CREATE TABLE MAILMNG_TBL (
    M_IDX     NUMBER,
    M_TITLE     VARCHAR2(200)   NOT NULL,
    M_CONTENT    VARCHAR2(4000)   NOT NULL,
    M_GUBUN      VARCHAR2(30)   NOT NULL, -- ����/�̺�Ʈ OR �Ϲ�
    M_sendcount NUMBER  DEFAULT 0,
    REG_DATE DATE DEFAULT SYSDATE
);

ALTER TABLE mailmng_tbl
ADD CONSTRAINTS PK_MAILMNG_IDX
PRIMARY KEY (M_IDX);

CREATE SEQUENCE seq_mailmng_tbl;
SELECT
    m_idx,
    m_title,
    m_content,
    m_gubun,
    reg_date
FROM
    mailmng_tbl;

commit;








