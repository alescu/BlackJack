
DROP DATABASE IF EXISTS blackjack_db;
CREATE DATABASE blackjack_db;
USE blackjack_db;

CREATE TABLE players (
	id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	player_name varchar(50) NOT NULL,
	account decimal (5, 2) NOT NULL DEFAULT '0',	
	profit_balance decimal (5, 2) NOT NULL DEFAULT '0',
	games_started int(11) NOT NULL DEFAULT '0',
	games_won int(11) NOT NULL DEFAULT '0',
	games_lost int(11) NOT NULL DEFAULT '0',		
	date_reg timestamp NOT NULL DEFAULT current_timestamp(),	
    date_modify timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cards (
	id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	type varchar(50) NOT NULL,
	date_reg timestamp NOT NULL DEFAULT current_timestamp(),	
    date_modify timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO cards (name, type) VALUES
('2 of Spades', 'poker'),
('3 of Spades', 'poker'),
('4 of Spades', 'poker'),
('5 of Spades', 'poker'),
('6 of Spades', 'poker'),
('7 of Spades', 'poker'),
('8 of Spades', 'poker'),
('9 of Spades', 'poker'),
('10 of Spades', 'poker'),
('Jack of Spades', 'poker'),
('Queen of Spades', 'poker'),
('King of Spades', 'poker'),
('Ace of Spades', 'poker'),
('2 of Hearts', 'poker'),
('3 of Hearts', 'poker'),
('4 of Hearts', 'poker'),
('5 of Hearts', 'poker'),
('6 of Hearts', 'poker'),
('7 of Hearts', 'poker'),
('8 of Hearts', 'poker'),
('9 of Hearts', 'poker'),
('10 of Hearts', 'poker'),
('Jack of Hearts', 'poker'),
('Queen of Hearts', 'poker'),
('King of Hearts', 'poker'),
('Ace of Hearts', 'poker'),
('2 of Diamonds', 'poker'),
('3 of Diamonds', 'poker'),
('4 of Diamonds', 'poker'),
('5 of Diamonds', 'poker'),
('6 of Diamonds', 'poker'),
('7 of Diamonds', 'poker'),
('8 of Diamonds', 'poker'),
('9 of Diamonds', 'poker'),
('10 of Diamonds', 'poker'),
('Jack of Diamonds', 'poker'),
('Queen of Diamonds', 'poker'),
('King of Diamonds', 'poker'),
('Ace of Diamonds', 'poker'),
('2 of Clubs', 'poker'),
('3 of Clubs', 'poker'),
('4 of Clubs', 'poker'),
('5 of Clubs', 'poker'),
('6 of Clubs', 'poker'),
('7 of Clubs', 'poker'),
('8 of Clubs', 'poker'),
('9 of Clubs', 'poker'),
('10 of Clubs', 'poker'),
('Jack of Clubs', 'poker'),
('Queen of Clubs', 'poker'),
('King of Clubs', 'poker'),
('Ace of Clubs', 'poker');