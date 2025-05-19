CREATE TABLE drink(
    drink_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    code TEXT UNIQUE NOT NULL,
    description TEXT NOT NULL,
    unity_price FLOAT NOT NULL,
    sugar_flag TEXT NOT NULL
);

CREATE TABLE ingredient(
    ingredient_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    code TEXT UNIQUE NOT NULL,
    description TEXT NOT NULL,
    unity_price FLOAT NOT NULL,
    additional_flag TEXT NOT NULL
);

CREATE TABLE hamburger(
    hamburger_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    code TEXT UNIQUE NOT NULL,
    description TEXT NOT NULL,
    unity_price FLOAT NOT NULL
);

CREATE TABLE customer(
    customer_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    cell TEXT NOT NULL
);

CREATE TABLE hamburger_ingredients(
    hamburger_ingredients_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    hamburger_id TEXT NOT NULL,
    ingredient_id TEXT NOT NULL,
    CONSTRAINT fk_hamburger_ingredient FOREIGN KEY(hamburger_id)
    REFERENCES hamburger(hamburger_id),
    CONSTRAINT fk_ingredient FOREIGN KEY(ingredient_id)
    REFERENCES ingredient(ingredient_id)
    ON DELETE CASCADE
);

CREATE TABLE customer_order(
    customer_order_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    code TEXT UNIQUE NOT NULL,
    description TEXT NOT NULL,
    customer_id TEXT NOT NULL,
    created_at TEXT NOT NULL,
    final_price FLOAT NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY(customer_id)
    REFERENCES customer(customer_id)
);

CREATE TABLE customer_order_observations(
    customer_order_observation_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    customer_order_id TEXT NOT NULL,
    customer_order_observation TEXT NOT NULL,
    CONSTRAINT fk_customer_order_items FOREIGN KEY(customer_order_id)
    REFERENCES customer_order(customer_order_id)
    ON DELETE CASCADE
);

CREATE TABLE customer_order_item_hamburger(
    customer_order_item_hamburger_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    customer_order_id TEXT NOT NULL,
    hamburger_id TEXT NOT NULL,
    CONSTRAINT fk_customer_order_items FOREIGN KEY(customer_order_id)
    REFERENCES customer_order(customer_order_id),
    CONSTRAINT fk_hamburger_items FOREIGN KEY(hamburger_id)
    REFERENCES hamburger(hamburger_id)
    ON DELETE CASCADE
);

CREATE TABLE customer_order_item_drink(
    customer_order_item_drink_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    customer_order_id TEXT NOT NULL,
    drink_id TEXT NOT NULL,
    CONSTRAINT fk_customer_order_items FOREIGN KEY(customer_order_id)
    REFERENCES customer_order(customer_order_id),
    CONSTRAINT fk_drink_items FOREIGN KEY(drink_id)
    REFERENCES drink(drink_id)
    ON DELETE CASCADE
);

CREATE TABLE customer_order_additional(
    customer_order_additional_id TEXT PRIMARY KEY UNIQUE NOT NULL,
    customer_order_id TEXT NOT NULL,
    ingredient TEXT NOT NULL,
    CONSTRAINT fk_ingredient FOREIGN KEY(ingredient)
    REFERENCES ingredient(ingredient_id)
    ON DELETE CASCADE
);
