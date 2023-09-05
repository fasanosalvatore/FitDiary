/* Default Roles */
INSERT INTO `ruolo` (`id`, `data_aggiornamento`, `data_creazione`, `nome`) VALUES (1, '2000-01-01 00:00:01', '2000-01-01 00:00:01', 'Admin');
INSERT INTO `ruolo` (`id`, `data_aggiornamento`, `data_creazione`, `nome`) VALUES (2, '2000-01-01 00:00:01', '2000-01-01 00:00:01', 'Preparatore');
INSERT INTO `ruolo` (`id`, `data_aggiornamento`, `data_creazione`, `nome`) VALUES (3, '2000-01-01 00:00:01', '2000-01-01 00:00:01', 'Cliente');

/* Users Examples with password Password123! */
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (1, b'1', NULL, NULL, 'Admin', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '2000-01-01 00:00:01', 'admin@fitdiary.it', 'Admin', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', NULL, NULL, NULL, NULL, 1);
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (2, b'1', '84085', 'Mercato San Severino', 'Giaquinto', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '1989-02-25', 'giaqui@gmail.com', 'Daniele', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', 'M', '3406683793', 'Via Antinori, 2', NULL, 2);
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (3, b'1', '84084', 'Fisciano', 'Paloso', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '1974-01-28', 'paloso@info.it', 'Peppe', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', 'M', '3335689745', 'Via De Prisco, 13', 5, 3);
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (4, b'1', '84100', 'Salerno', 'Trascendentina', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '2001-08-13', 'inapina@libero.it', 'Costantina', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', 'F', '3356895789', 'Corso Vittorio Emanuele, 25/B', 2, 3);
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (5, b'1', NULL, NULL, 'Nella Roccia', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '1990-01-01', 'preparatore@fitdiary.it', 'Scolpito', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', 'M', NULL, NULL, NULL, 2);
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (6, b'1', '84085', 'Mercato San Severino', 'Monaco', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '1989-02-25', 'lmonaco@gmail.com', 'Leonardo', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', 'M', '3368958789', 'Via Giovanni Paolo II, 12', NULL, 2);
INSERT INTO `utente` (`id`, `attivo`, `cap`, `citta`, `cognome`, `data_aggiornamento`, `data_creazione`, `data_nascita`, `email`, `nome`, `password`, `sesso`, `telefono`, `via`, `preparatore_id`, `ruolo_id`) VALUES (7, b'1', '84100', 'Salerno', 'Melmosa', '2000-01-01 00:00:01', '2000-01-01 00:00:01', '2001-08-13', 'cliente@fitdiary.it', 'Tiziana', '$2a$10$TKBoxenFl92P6mKsu3QGPu/09eiSwr3khj35gjGSJqrqlrgRjJBga', 'F', '3356895789', 'Corso Vittorio Emanuele, 25/B', 5, 3);

/*Protocolli*/
INSERT INTO `protocollo` (`id`, `data_aggiornamento`, `data_creazione`, `data_scadenza`, `cliente_id`, `preparatore_id`) VALUES (1, '2022-01-09 20:40:59', '2022-01-09 20:41:00', '2022-02-24', 4, 2);

/*Scheda Alimentare*/
INSERT INTO `scheda_alimentare` (`id`, `kcal_assunte`, `nome`,`utente_id`) VALUES (1, 100, 'scheda da 100kcla',2);
INSERT INTO `scheda_alimentare` (`id`, `kcal_assunte`, `nome`,`utente_id`) VALUES (2, 400, 'scheda da 400kcla',2);


/*Scheda Allenamento*/
INSERT INTO `scheda_allenamento` (`id`, `frequenza`, `protocollo_id`) VALUES (1, '4', 1);


/*Istanze Alimento*/
INSERT INTO `istanza_alimento` (`id`, `grammi`, `giorno_della_settimana`, `alimento_id`, `scheda_alimentare_id`,`pasto`) VALUES (1,100, 2, 1, 1,1);
INSERT INTO `istanza_alimento` (`id`, `grammi`, `giorno_della_settimana`, `alimento_id`, `scheda_alimentare_id`,`pasto`) VALUES (2,200, 0, 2, 1,0);
INSERT INTO `istanza_alimento` (`id`, `grammi`, `giorno_della_settimana`, `alimento_id`, `scheda_alimentare_id`,`pasto`) VALUES (3,400, 3, 3, 2,2);
INSERT INTO `istanza_alimento` (`id`, `grammi`, `giorno_della_settimana`, `alimento_id`, `scheda_alimentare_id`,`pasto`) VALUES (4,200, 4, 1, 2,3);



/* Esercizi*/
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (1, 'pettorali', 'Chest press', '1', '15\'\' + 90\'', '8 MAX', '4', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (2, 'pettorali', 'Manubri inclinata', '1', '15\'\' + 90\'', '8 MAX', '4', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (3, 'pettorali', 'Cavi incrociati', '1', '60\'\'', '6 - 12 -50% MAX-20%', '3', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (4, 'braccio', 'Tricipiti poliercolina', '1', '15\'\' + 60\'', '8 MAX', '4', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (5, 'braccio', 'French press bilanciere', '1', '60\'\'', '12 1/2 colpi', '4', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (6, 'braccio', 'Pull-up', '2', '15\'\' + 90\'', '8 MAX', '5', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (7, 'spalle', 'Rematore bilanciere', '2', '2\'', '12-10-8-8-6', '5', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (8, 'spalle', 'Pull down', '2', '60\'\'', '15', '5', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (9, 'gambe', 'Leg curl', '2', '90\'\'', '6 - 12 -50% MAX-20%', '5', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (10, 'lombari', 'Panca lombari', '2', '30\'\'', '20', '4', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (11, 'gambe', 'Camminata', '2', '0', '35\' 6km/h 5% pedenza', '1', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (12, 'gambe', 'Leg press', '3', '2\'', '10', '10', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (13, 'gambe ', 'Stacchi semitese', '3', '2\'', '10', '8', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (14, 'pettorali', 'Lento avanti mp', '4', '90\'\'', '6 - 12 -50% MAX-20%', '5', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (15, 'spalle', 'Alzate laterali', '4', '15\'\' + 90\'', '8 MAX', '5', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (16, 'spalle', 'Alzate 90°', '4', '15\'\' + 60\'', '8 MAX', '4', 1);
INSERT INTO `esercizio` (`id`, `categoria`, `nome`, `numero_allenamento`, `recupero`, `ripetizioni`, `serie`, `scheda_allenamento_id`) VALUES (17, 'braccio', 'Curl manubri seduto', '4', '90\'\'', '6 - 12 -50% MAX-20%', '6', 1);

/* report*/
INSERT INTO `report` (`id`, `crf_addome`, `crf_bicipite`, `crf_quadricipite`, `data_aggiornamento`, `data_creazione`, `peso`, `peso_stimato`, `cliente_id`) VALUES (1, 30, 30, 30, '2022-01-10 22:36:03', '2022-01-10 22:36:03', 100, 100, 4);

/*immaginiReport*/
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (1, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--J9CYotxd--/v1641863408/evssjeyaofzzdrf8yywq.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (2, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--XeZpEzAu--/v1641863409/xi8pg8u9y5prdvf69ild.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (3, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--9GWTvtks--/v1641863410/qwih8qpvc39lyuw8l0m5.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (4, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--FvFZllyI--/v1641863411/ylrrm8gkcvvp2uvu5ujo.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (5, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--SGmo-p_X--/v1641863413/pdimpx91p5cyz86hppee.png', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (6, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--a8dfKwK9--/v1641863414/zoveedipdtfcz9zazdgz.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (7, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--DuJJQu8E--/v1641863415/fpugynqoqg2nbwbkfjwp.jpg', 1);
