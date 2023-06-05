BEGIN TRANSACTION;

DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    user_id integer,
    email varchar(100),
    CONSTRAINT FK_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

CREATE TABLE favorites (
	favorites_id SERIAL PRIMARY KEY,
	movie_id int NOT NULL,
	watched boolean NOT NULL,
	rank int,
	user_id int NOT NULL,
	CONSTRAINT FK_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

COMMIT TRANSACTION;

