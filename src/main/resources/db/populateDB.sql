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

INSERT INTO meals (user_id, description, calories) VALUES
  (100000, 'Milk', 300),
  (100000, 'Burger', 500),
  (100000, 'Bread', 350),
  (100000, 'Borsch', 400);
