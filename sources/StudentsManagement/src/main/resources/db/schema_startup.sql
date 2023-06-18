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

INSERT INTO `configuration` (`id`, `createdDate`, `updatedDate`, `name`, `value`, `description`, `createdBy`, `updatedBy`) VALUES
(1, '2023-05-29 21:32:04', '2023-05-29 21:32:04', 'MIN_AGE_STUDENT', '15', 'This is the regulation about the minimum age for each student.', 1, 1),
(2, '2023-05-29 21:32:05', '2023-05-29 21:32:05', 'MAX_AGE_STUDENT', '20', 'This is the regulation about the maximum age for each student.', 1, 1),
(3, '2023-05-29 21:32:06', '2023-05-29 21:32:06', 'MAX_STUDENT_CLASS', '40', 'This is the regulation about the maximum student number in a class.', 1, 1),
(4, '2023-05-29 21:32:07', '2023-05-29 21:32:07', 'QUANTITY_GRADE', '3', 'This is the regulation about the grade quantity in a school.',  1, 1),
(5, '2023-05-29 21:32:08', '2023-05-29 21:32:08', 'QUANTITY_CLASS', '9', 'This is the regulation about the class quantity in a school.',  1, 1),
(6, '2023-05-29 21:32:09', '2023-05-29 21:32:09', 'QUANTITY_GRADE_10', '4', 'This is the regulation about the class quantity in grade 10.', 1, 1),
(7, '2023-05-29 21:32:10', '2023-05-29 21:32:10', 'QUANTITY_GRADE_11', '3', 'This is the regulation about the class quantity in grade 11.', 1, 1),
(8, '2023-05-29 21:32:11', '2023-05-29 21:32:11', 'QUANTITY_GRADE_12', '2', 'This is the regulation about the class quantity in grade 12.', 1, 1),
(9, '2023-05-29 21:32:12', '2023-05-29 21:32:12', 'QUANTITY_SUBJECT', '9', 'This is the regulation about the subject quantity in a school.', 1, 1),
(10, '2023-05-29 21:32:13', '2023-05-29 21:32:13', 'REQUIRED_MARK', '5', 'This is the regulation about the minimum mark to pass a subject', 1, 1);