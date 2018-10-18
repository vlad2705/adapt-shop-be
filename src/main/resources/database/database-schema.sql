DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS person_role;
DROP TABLE IF EXISTS role_privilege;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS product_order;
DROP TABLE IF EXISTS shipping;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS privilege;
DROP TABLE IF EXISTS picture CASCADE;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS product_storage;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS user_model_attribute;
DROP TABLE IF EXISTS content_unit_model_attribute;
DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 101;


CREATE TABLE IF NOT EXISTS person (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  token_expired BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS role (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS privilege (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS person_role (
  person_id BIGINT REFERENCES person(id),
  role_id BIGINT REFERENCES role(id),
  PRIMARY KEY (person_id, role_id)
);

CREATE TABLE IF NOT EXISTS role_privilege (
  role_id BIGINT REFERENCES role(id),
  privilege_id BIGINT REFERENCES privilege(id),
  PRIMARY KEY (role_id, privilege_id)
);

CREATE TABLE IF NOT EXISTS address (
  id BIGSERIAL PRIMARY KEY,
  address VARCHAR(255) NOT NULL,
  postal_code VARCHAR(10) NOT NULL,
  city VARCHAR(50) NOT NULL,
  country VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
  id BIGSERIAL PRIMARY KEY,
  person BIGINT NOT NULL UNIQUE REFERENCES person(id),
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  phone VARCHAR(50),
  address BIGINT NOT NULL REFERENCES address(id)
);

CREATE TABLE IF NOT EXISTS category (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  parent BIGINT REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS product (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  category BIGINT NOT NULL REFERENCES category(id),
  price DECIMAL(30, 2) NOT NULL,
  description TEXT
);

CREATE TABLE IF NOT EXISTS order_status (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS shipping (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  cost NUMERIC(30, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_order (
  id BIGSERIAL PRIMARY KEY,
  session_id VARCHAR(50) NOT NULL,
  person BIGINT NOT NULL REFERENCES person(id),
  shipping BIGINT REFERENCES shipping(id),
  order_status BIGINT NOT NULL REFERENCES order_status(id)
);

CREATE TABLE IF NOT EXISTS order_item (
  id BIGSERIAL PRIMARY KEY,
  product_order BIGINT NOT NULL REFERENCES product_order(id),
  product BIGINT NOT NULL REFERENCES product(id),
  quantity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS picture (
  id BIGSERIAL PRIMARY KEY,
  product BIGINT NOT NULL REFERENCES product(id),
  name VARCHAR(255) NOT NULL,
  content BYTEA NOT NULL,
  type VARCHAR(20) NOT NULL,
  size BIGINT NOT NULL
);

ALTER TABLE product ADD COLUMN picture BIGINT REFERENCES picture(id);

CREATE TABLE IF NOT EXISTS product_storage (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS inventory (
  id BIGSERIAL PRIMARY KEY,
  product_storage BIGINT NOT NULL REFERENCES product_storage(id),
  product BIGINT NOT NULL REFERENCES product(id),
  quantity INTEGER NOT NULL,
  UNIQUE (product_storage, product)
);

CREATE TABLE IF NOT EXISTS rating (
  id BIGSERIAL PRIMARY KEY,
  person BIGINT NOT NULL REFERENCES person(id),
  product BIGINT NOT NULL REFERENCES product(id),
  rating INTEGER NOT NULL,
  description TEXT,
  date DATE NOT NULL,
  UNIQUE (person, product)
);

CREATE TABLE IF NOT EXISTS user_model_attribute (
  id_user_model_attribute BIGSERIAL PRIMARY KEY,
  id_user VARCHAR(255),
  domain_model_class_name VARCHAR(255),
  domain_model_instance_id VARCHAR(255),
  attribute_name VARCHAR(255),
  attribute_value VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS content_unit_model_attribute (
  id_content_unit_model_attribute BIGSERIAL PRIMARY KEY,
  domain_model_class_name VARCHAR(255),
  domain_model_instance_id VARCHAR(255),
  attribute_name VARCHAR(255),
  attribute_value VARCHAR(255)
);

INSERT INTO privilege (name)
    VALUES
        ('product:view'),
        ('product:create'),
        ('product:edit'),
        ('product:detail'),
        ('product:picture'),
        ('product:delete'),
        ('category:view'),
        ('category:create'),
        ('category:edit'),
        ('category:detail'),
        ('category:delete'),
        ('order:view'),
        ('order:detail'),
        ('order:edit'),
        ('order:delete'),
        ('order:item:create'),
        ('order:item:detail'),
        ('order:item:edit'),
        ('order:item:delete'),
        ('account:view'),
        ('account:create'),
        ('account:edit'),
        ('account:detail'),
        ('account:delete'),
        ('role:view'),
        ('role:create'),
        ('role:edit'),
        ('role:detail'),
        ('role:delete');

INSERT INTO order_status (name)
    VALUES
        ('PRE ORDER'),
        ('ACCEPTED'),
        ('SHIPPED'),
        ('DONE'),
        ('CANCELED');

INSERT INTO shipping (name, cost)
    VALUES
      ('Pick up in store', 0),
      ('By mail', 80),
      ('Courier', 250);

-- TEST DATA --
INSERT INTO category (id, name)
    VALUES
      (1, 'Category 1'),
      (2, 'Category 2'),
      (3, 'Category 3'),
      (4, 'Category 4'),
      (5, 'Category 5'),
      (6, 'Category 6'),
      (7, 'Category 7'),
      (8, 'Category 8'),
      (9, 'Category 9');

INSERT INTO category (id, name, parent)
VALUES
  (10, 'Category 1.1', 1),
  (11, 'Category 1.2', 1),
  (12, 'Category 1.3', 1),
  (13, 'Category 1.4', 1),
  (14, 'Category 1.5', 1),
  (15, 'Category 1.1.1', 10),
  (16, 'Category 1.1.2', 10),
  (17, 'Category 1.2.1', 11),
  (18, 'Category 2.1', 2),
  (19, 'Category 3.1', 3),
  (20, 'Category 4.1', 4),
  (21, 'Category 5.1', 5),
  (22, 'Category 6.1', 6),
  (23, 'Category 7.1', 7),
  (24, 'Category 8.1', 8),
  (25, 'Category 9.1', 9);

INSERT INTO product (name, category, price, description)
VALUES
  ('Product 1', 1, 1000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 2', 2, 2000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 3', 3, 3000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 4', 4, 4000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 5', 5, 5000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 6', 6, 6000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 7', 7, 7000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 8', 8, 8000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 9', 9, 9000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.1', 10, 1100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.2', 11, 1200, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.3', 12, 1300, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.4', 13, 1400, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.5', 14, 1500, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.1.1', 15, 1110, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.1.2', 16, 1120, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 1.2.1', 17, 1210, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 2.1', 18, 2100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 3.1', 19, 3100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 4.1', 20, 4100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 5.1', 21, 5100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 6.1', 22, 6100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 7.1', 23, 7100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 8.1', 24, 8100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
  ('Product 9.1', 25, 9100, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');