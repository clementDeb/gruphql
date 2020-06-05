DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250),
    lastname VARCHAR(250)
);

INSERT INTO user (id, name, lastname) VALUES (1, 'nameTest' , 'lastnameTest');