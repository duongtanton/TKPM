INSERT INTO `user` (`id`, `createdDate`, `updatedDate`, `email`, `name`, `password`, `username`, `createdBy`, `updatedBy`,`isEnable`) VALUES
(1, '2023-05-29 21:32:04', '2023-05-29 21:32:04', 'admin@gmail.com', N'Admin', '$2a$10$3YY3bv9QpCNM3LpXjRy1Nuldh05ib9gTTdqblSBIoEiRsCpsavS9y', 'admin', 1, 1, true),
(2, '2023-05-29 21:32:05', '2023-05-29 21:32:05', 'user1@gmail.com', N'User 1', '$2a$04$g01O2E2YO743avN7tjKzKuMyp4UjOthnAE0s1zIVkjYIm186D7Fy2', 'user1', 1, 1, true),
(3, '2023-05-29 21:32:06', '2023-05-29 21:32:06', 'user2@gmail.com', N'User 2', '$2a$04$g01O2E2YO743avN7tjKzKuMyp4UjOthnAE0s1zIVkjYIm186D7Fy2', 'user2', 1, 1, true);

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'USER');

INSERT INTO `user_role` (`userId`, `roleId`) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 2);