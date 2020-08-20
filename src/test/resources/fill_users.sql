delete from users;

insert into users(id, first_name, last_name, password, active, created, updated) values
(1, 'V', 'T', '1a1dc91c907325c69271ddf0c944bc72', true, '2020-08-13 10:14:00', null),
(2, 'K', 'V', '25d55ad283aa400af464c76d713c07ad', false, '2020-08-13 15:02:00', '2020-08-18 16:42:43'),
(3, 'D', 'UpdatedLastName', '6dd2e8d3dd702cbeb196b60a542eab08', true, '2020-08-13 14:36:23', '2020-08-13 14:53:32'),
(4, 'K', 'Tis', 'c78036d23335477b6b339e0b938d74a6', false, '2020-08-13 15:14:33', null),
(5, 'Jul', 'Korol', '5f4dcc3b5aa765d61d8327deb882cf99', true, '2020-08-14 10:36:47', '2020-08-14 10:41:06'),
(6, 'Pavel', 'Skripnik', 'd8578edf8458ce06fbc5bb76a58c5ca4', true, '2020-08-16 16:43:08', '2020-08-16 16:43:31'),
(7, 'Igor', 'S', '7a614fd06c325499f1680b9896beedeb', false, '2020-08-19 06:44:05', null);
