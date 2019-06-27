DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

DELETE FROM meals;
ALTER SEQUENCE meals_seq RESTART WITH 100000;

INSERT INTO meals (user_id, description, calories, datetime) VALUES
  (100000, 'Milk', 300, TIMESTAMP '2019-06-20 15:30:00'),
  (100000, 'Burger', 500, TIMESTAMP '2019-06-20 20:00:00'),
  (100000, 'Bread', 350, TIMESTAMP '2019-06-21 8:15:00'),
  (100000, 'Borsch', 400, TIMESTAMP '2019-06-21 12:10:00');
