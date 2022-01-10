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
INSERT INTO `scheda_alimentare` (`id`, `kcal_assunte`, `protocollo_id`) VALUES (1, 0, 1);

/*Scheda Allenamento*/
INSERT INTO `scheda_allenamento` (`id`, `frequenza`, `protocollo_id`) VALUES (1, '4', 1);

/*Alimenti*/
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (1, '1', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (2, '1', '30g', 102, 'Cereali muesli con frutta e nocciole', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (3, '1', '250g', 152.5, 'Kiwi', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (4, '1', '90g', 291.6, 'Pasta di semola integrale', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (5, '1', '100g', 24, 'Salsa di pomodoro', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (6, '1', '10g', 39.2, 'Parmigiano grattugiato', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (7, '1', '100g', 18, 'Lattuga', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (8, '1', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (9, '1', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (10, '1', '200g', 220, 'Petto di pollo alla piastra', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (11, '1', '200g', 32, 'Zucchine', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (12, '1', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (13, '1', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (14, '2', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (15, '2', '30g', 108, 'Corn flakes', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (16, '2', '250g', 130, 'Mela, con buccia', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (17, '2', '80g', 296, 'Riso a chicco lungo integrale', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (18, '2', '100g', 25, 'Rucola', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (19, '2', '10g', 39.2, 'Parmigiano grattugiato', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (20, '2', '100g', 23, 'Radicchio', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (21, '2', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (22, '2', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (23, '2', '220g', 237.6, 'Filetto di tonno pinne gialle alla piastra', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (24, '2', '200g', 34, 'Indivia', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (25, '2', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (26, '2', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (27, '3', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (28, '3', '30g', 102, 'Cereali muesli con frutta e nocciole', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (29, '3', '250g', 157.5, 'Arance', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (30, '3', '250g', 192.5, 'Patate lesse', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (31, '3', '180g', 186, 'fagioli secchi(60g) bolliti', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (32, '3', '10g', 39.2, 'Parmigiano grattugiato', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (33, '3', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (34, '3', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (35, '3', '200g', 172, 'Fiocchi da latte a ridotto contenuto di grassi 2%', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (36, '3', '200g', 32, 'Pomodori arancioni', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (37, '3', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (38, '3', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (39, '4', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (40, '4', '30g', 108, 'Corn flakes', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (41, '4', '250g', 145, 'Pere', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (42, '4', '150g', 406.5, 'Pizza margherita al taglio', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (43, '4', '30g', 64.5, 'Prosciutto cotto', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (44, '4', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (45, '4', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (46, '4', '200g', 96, 'Frittata di albumi d\'uovo', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (47, '4', '200g', 94, 'Carciofi', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (48, '4', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (49, '4', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (50, '5', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (51, '5', '30g', 102, 'Cereali muesli con frutta e nocciole', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (52, '5', '250g', 152.5, 'Kiwi', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (53, '5', '90g', 291.6, 'Pasta di semola integrale', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (54, '5', '100g', 16, 'Zucchine', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (55, '5', '10g', 39.2, 'Parmigiano grattugiato', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (56, '5', '100g', 18, 'Lattuga', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (57, '5', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (58, '5', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (59, '5', '200g', 222, 'Petto di tacchino alla piastra', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (60, '5', '200g', 48, 'Melanzana', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (61, '5', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (62, '5', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (63, '6', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (64, '6', '30g', 108, 'Corn flakes', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (65, '6', '250g', 145, 'Pere', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (66, '6', '80g', 296, 'Riso a chicco lungo integrale', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (67, '6', '100g', 22, 'Funghi champignon', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (68, '6', '10g', 39.2, 'Parmigiano grattugiato', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (69, '6', '100g', 23, 'Radicchio', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (70, '6', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (71, '6', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (72, '6', '200g', 242.5, 'Spigola al forno', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (73, '6', '200g', 34, 'Indivia', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (74, '6', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (75, '6', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (76, '7', '250ml', 125.5, 'Latte a ridotto contenuto di grassi, 2%', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (77, '7', '30g', 102, 'Cereali muesli con frutta e nocciole', 'Colazione', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (78, '7', '250g', 130, 'Mela, con buccia', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (79, '7', '250g', 192.5, 'Patate lesse', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (80, '7', '180g', 183.6, 'Piselli secchi(60g) bolliti', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (81, '7', '10g', 39.2, 'Parmigiano grattugiato', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (82, '7', '10g', 90, 'Olio extravergine di oliva ', 'Pranzo', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (83, '7', '250g', 140, 'Yogurt da latte scremato', 'Spuntino', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (84, '7', '200g', 232, 'Vitello, lombo, carne magra', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (85, '7', '100g', 25, 'Rucola', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (86, '7', '60g', 145.8, 'Pane integrale', 'Cena', 1);
INSERT INTO `alimento` (`id`, `giorno`, `grammi`, `kcal`, `nome`, `pasto`, `scheda_alimentare_id`) VALUES (87, '7', '15g', 135, 'Olio extravergine di oliva ', 'Cena', 1);

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
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (1, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--HXODmb5F--/v1641850547/kekubv0cfancvjbozddu.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (2, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--QR4rcqxE--/v1641850548/xxdazt3mhy9wlbi0y2ou.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (3, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--9QHwbnCh--/v1641850549/eztvpf5shkdowapjdept.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (4, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--DsdAP9j2--/v1641850549/cax8c65h9wr3pewghuyp.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (5, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--3Eyqe8vy--/v1641850550/wlcham11nx7mlmjzqfzx.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (6, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--oQMH9Twx--/v1641850551/jhzbjoes58edmzaqchgb.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (7, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--ifeJ8xyE--/v1641850552/nbjlnwwkcoavshcyninl.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (8, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--fOGi0MCj--/v1641850553/r2qylhgpg71pycbkql5q.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (9, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--dmvqsq3r--/v1641850554/yfzoizjq5nmnthw6yh7l.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (10, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--AFuuPcwG--/v1641850554/v1b2wt9xee6xihivvhzo.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (11, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--GGUVtinN--/v1641850555/igsjcda2elwy30azk3x0.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (12, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--N1L7z3hy--/v1641850556/eu5zzgqh0pwcmzwdzl3e.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (13, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--xpg58nIo--/v1641850557/gtzsjnfhpoyjptbtql7n.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (14, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--iXZOd67t--/v1641850558/ulkefg5ismw98emj9kio.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (15, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--RCTyPTRB--/v1641850559/zyviu1qpmuffxkbqgbgu.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (16, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--pbkEPYqa--/v1641850560/qhejkbtknuc3rv5ceknz.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (17, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--AnqKiQ4W--/v1641850560/aansb0hrhj5ie6gckkhg.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (18, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--pLWbEj4q--/v1641850562/lbhzercbjhiqrtq2qtyf.jpg', 1);
INSERT INTO `immagini_report` (`id`, `url`, `report_id`) VALUES (19, 'https://res.cloudinary.com/hdjxm4zyg/image/upload/s--Cdv4fEVZ--/v1641850562/d1ckb9tidbexn0gozqx2.jpg', 1);
