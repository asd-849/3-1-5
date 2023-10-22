-- Здравствуйте, в замечании было указано "добавь в проект sql скрипт или сделай храниму процедуру, будешь дергать ее из джава кода.".
--     единственный способ исполнить из джава кода sql-файл подразумевает использование mybatis, который в сборку клонируемого проекта, не входит. в предыдущей весрии инициализация осуществлялась джава классом, имплементирующем applicationrunner, который просто создает объекты.
--     в данной версии просто добавил вместо этого класса sql-скрипты с созданием таблиц и заполнением юзера и админа. прошу прояснить, каким образом я могу исоплнить sql-скрипт без использования дополнительных библиотек
USE schema_1;

INSERT INTO roles (role_name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (age, last_name, name, password, username)
VALUES
    (11, 'Aa', 'AAAA', '$2a$12$mvubr3cSCVF39s6MnUWCJe7hN62EitQ3pkhM6CoNNJbEaz2ffnDDe', 'admin'),
    (22, 'Uu', 'UUUU', '$2a$12$y5xaSw6.xWtcBsJ4SG7s/evLwyT4Qws6Y91XLtRML4V/GleINC8N6', 'user');

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);

#
-- # SELECT * FROM users;
-- # SELECT * FROM users_roles;
-- # SELECT * FROM roles;

-- TRUNCATE TABLE roles;
-- TRUNCATE TABLE schema_1.users_roles;
-- TRUNCATE TABLE schema_1.users;

-- # DROP TABLE users_roles;
-- # DROP TABLE users;
-- # DROP TABLE roles;
