BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_photos;
DROP TABLE IF EXISTS favorites;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE user_photos (
        id serial NOT NULL,
        user_id int4 NOT NULL,
        username varchar(50) NOT NULL,
        photo_url text NOT NULL,
        description text,
        date_added TIMESTAMP DEFAULT(CURRENT_TIMESTAMP),
        CONSTRAINT pk_user_photos_id PRIMARY KEY (id),
        CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE favorites (
        id serial NOT NULL,
        user_id int4 NOT NULL,
        photo_id int4 NOT NULL,
        CONSTRAINT pk_favorites_id PRIMARY KEY (id),
        CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
        CONSTRAINT fk_photo_id FOREIGN KEY (photo_id) REFERENCES user_photos(id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('frank','$2a$10$gqa/e0R0ccoGZqKq78iYiuZo5k5xGkN8Z66af1LaX182ma1NSWSX6','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('tracy','$2a$10$RKAPDSB17DkOFBvidtXE4.EzLMMhWuoAjsX9LuQ2.pfzArGdG01t6','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('brendan','$2a$10$OGPPitY1B/xdsSET1MOozuI63udSHazChDxT3MWw3R0Bdmv9fLT2i','ROLE-USER');
INSERT INTO users (username,password_hash,role) VALUES ('yitz','$2a$10$Tvy/YRhPays0DyD8Jz2opu6UbrTPFIzWKvWpvT0o4Ol/1UYO64xc.','ROLE_USER');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('3','frank','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486772/hot-air-balloon_l7xdvx.jpg','I never had time for hot air balloons. I would rather fly my plane.','06/20/20 10:36:02');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('6','yitz','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486773/tree_zvsuvv.jpg','Wish I could be camping right now...','06/25/20 15:09:54');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('5','brendan','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486773/pier_ovwfhv.jpg','Just spent some time at the ocean. It was pretty relaxing.','07/01/20 21:01:04');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('5','brendan','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486773/aeolian-islands_e80grd.jpg','Went out on a boating tour and saw this crazy rock!','07/04/20 10:01:04');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('3','frank','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486773/golden-gate-bridge_fdxn1t.jpg','I think I flew over San Francisco one time. I was pretty high in the sky though.','07/04/20 13:16:04');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('4','tracy','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486773/lake-tahoe_h6gpsg.jpg','Relaxing at Lake Tahoe!','07/10/20 16:21:55');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('6','yitz','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486774/wondering_hinonm.jpg','Summertime is meant to be spent by the ocean.','07/11/20 18:59:34');
INSERT INTO user_photos (user_id,username,photo_url,description,date_added) VALUES ('4','tracy','https://res.cloudinary.com/dkhepixjf/image/upload/v1596486775/utopia_hqw3ei.jpg','Working on some photoshop skills!','07/14/20 23:11:09');

COMMIT TRANSACTION;
