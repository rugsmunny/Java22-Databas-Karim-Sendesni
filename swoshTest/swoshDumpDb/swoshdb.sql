/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int NOT NULL,
  `balance` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `recipient` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `signature_number` int unsigned NOT NULL,
  `ssn` bigint unsigned NOT NULL,
  `logged_in` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ssn` (`ssn`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `accounts` (`id`, `account_created`, `phone_number`, `user_id`, `balance`) VALUES
(1, '2023-06-05 00:27:44', '0709090909', 6, 7450);
INSERT INTO `accounts` (`id`, `account_created`, `phone_number`, `user_id`, `balance`) VALUES
(2, '2023-06-05 00:28:33', '0700909090', 5, 3595);
INSERT INTO `accounts` (`id`, `account_created`, `phone_number`, `user_id`, `balance`) VALUES
(3, '2023-06-05 00:46:30', '0709999999', 1, 2150);
INSERT INTO `accounts` (`id`, `account_created`, `phone_number`, `user_id`, `balance`) VALUES
(4, '2023-06-05 00:47:48', '0708080808', 2, 2305);

INSERT INTO `transactions` (`id`, `sender`, `recipient`, `date_time`, `amount`) VALUES
(1, '0700909090', '0709090909', '2023-06-05 00:43:37', 1300);
INSERT INTO `transactions` (`id`, `sender`, `recipient`, `date_time`, `amount`) VALUES
(2, '0700909090', '0709090909', '2023-06-05 00:44:08', 300);
INSERT INTO `transactions` (`id`, `sender`, `recipient`, `date_time`, `amount`) VALUES
(3, '0700909090', '0709090909', '2023-06-05 00:45:36', 2300);
INSERT INTO `transactions` (`id`, `sender`, `recipient`, `date_time`, `amount`) VALUES
(4, '0709999999', '0709090909', '2023-06-05 00:46:57', 1200),
(5, '0709999999', '0700909090', '2023-06-05 00:47:19', 1150),
(6, '0708080808', '0709090909', '2023-06-05 00:48:10', 350),
(7, '0708080808', '0700909090', '2023-06-05 00:48:27', 2345);

INSERT INTO `users` (`id`, `account_created`, `password`, `first_name`, `last_name`, `signature_number`, `ssn`, `logged_in`) VALUES
(1, '2023-06-05 00:22:06', '$2a$12$ZonQS1RXLmjriluzeA8MY.tQjNSpx/.8jRLsfu6ujEQlymFJtC4Sm', 'Karin', 'Barin', 1111, 198012190000, 0);
INSERT INTO `users` (`id`, `account_created`, `password`, `first_name`, `last_name`, `signature_number`, `ssn`, `logged_in`) VALUES
(2, '2023-06-05 00:22:39', '$2a$12$1M38EcstmrYpy1GQlXYIF.Y3gROLb6guLFD.oDZpcgsfwjlWVHjzK', 'Karim', 'Simsalabim', 1234, 198103180000, 0);
INSERT INTO `users` (`id`, `account_created`, `password`, `first_name`, `last_name`, `signature_number`, `ssn`, `logged_in`) VALUES
(3, '2023-06-05 00:23:16', '$2a$12$/bJIOOMmfMpuvctJyfqNAe1vPGlXOmVH7XzYR.EvJvOK0g6LW2xce', 'Morris', 'Morrisson', 4738, 199508174677, 0);
INSERT INTO `users` (`id`, `account_created`, `password`, `first_name`, `last_name`, `signature_number`, `ssn`, `logged_in`) VALUES
(4, '2023-06-05 00:23:50', '$2a$12$iHddDe2fF.rIN/PT.vVZ6eDbTv/rLGE9aFptKzVkSBmLzTQL1EhAm', 'Erik', 'Eriksson', 4444, 197503040077, 0),
(5, '2023-06-05 00:24:24', '$2a$12$sqJnRtCQJcc.MF7614MVCuW0Tyy3AmAe4Ift2eUp2OxerL33b3zYm', 'Oskar', 'Nilsson', 4342, 200506047880, 0),
(6, '2023-06-05 00:24:55', '$2a$12$6iEtoT1EFFpPwYN16xLS0enjBPjEasthTfTARao7TxCWiV1jKAzoa', 'Olle', 'Ohlsson', 1111, 199909090000, 0);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;