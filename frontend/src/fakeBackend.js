let user = {
    "data": {
        "utente": {
            "id": 4,
            "nome": "John",
            "cognome": "Dohe",
            "email": "giaqui@gmail.com",
            "password": "$2a$10$cDlRv6/3qzj79BmSU4X.Hu5rcyUT6v4oczfrwJFCC/APgMajQugMu",
            "attivo": false,
            "dataNascita": "2000-12-12",
            "dataCreazione": "2021-01-01T00:00:01",
            "dataAggiornamento": "2021-12-31T00:10:18",
            "sesso": "M",
            "telefono": "3347274753",
            "via": "Corso Diaz, 140",
            "cap": "84084",
            "citta": "Fisciano (NA)",
            "preparatore": {
                "id": 1,
                "nome": "Rambo",
                "cognome": "Potente",
                "email": "iosonorambo@rambo.com",
                "password": "$2a$10$cDlRv6/3qzj79BmSU4X.Hu5rcyUT6v4oczfrwJFCC/APgMajQugMu",
                "attivo": true,
                "dataNascita": "1990-12-12",
                "dataCreazione": "2021-01-01T00:00:01",
                "dataAggiornamento": "2021-12-31T00:10:18",
                "sesso": "M",
                "telefono": "3365895875",
                "via": null,
                "cap": null,
                "citta": null,
                "altezza": null,
            },
            "ruolo": {
                "id": 1,
                "nome": "PREPARATORE",
                "dataCreazione": "2021-12-31T00:10:18",
                "dataAggiornamento": "2021-12-31T00:10:18"
            },
            "listaProtocolli": null,
            "listaReport": null
        },
    },
    "status": "success"
};

const schedaAlimentare = {
    "data": {
        "scheda_alimentare": {
            "id": 1,
            "nome": "scheda da 100kcla",
            "kcalAssunte": 100.0,
            "listaAlimenti": [
                {
                    "id": 1,
                    "giornoDellaSettimana": "MERCOLEDI",
                    "pasto": "SPUNTINO_COLAZIONE",
                    "grammi": 100,
                    "alimento": {
                        "id": 1,
                        "nome": "cereali",
                        "kcal": 20.0,
                        "proteine": 4.0,
                        "grassi": 40.0,
                        "carboidrati": 5.0,
                        "pathFoto": "foto/path"
                    },
                    "schedaAlimentare": 1
                },
                {
                    "id": 2,
                    "giornoDellaSettimana": "LUNEDI",
                    "pasto": "COLAZIONE",
                    "grammi": 200,
                    "alimento": {
                        "id": 2,
                        "nome": "panckakus",
                        "kcal": 24.0,
                        "proteine": 20.0,
                        "grassi": 20.0,
                        "carboidrati": 5.0,
                        "pathFoto": "foto/path"
                    },
                    "schedaAlimentare": 1
                }
            ],
            "preparatore": {
                "id": 2,
                "nome": "Daniele",
                "cognome": "Giaquinto",
                "email": "giaqui@gmail.com",
                "attivo": true,
                "dataNascita": "1989-02-25",
                "sesso": "M",
                "telefono": "3406683793",
                "via": "Via Antinori, 2",
                "cap": "84085",
                "citta": "Mercato San Severino",
                "preparatore": null,
                "ruolo": {
                    "id": 2,
                    "nome": "Preparatore",
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2000-01-01T00:00:01",
                "dataAggiornamento": "2000-01-01T00:00:01"
            },
            "dataCreazione": null,
            "dataAggiornamento": null
        }
    },
    "status": "success"
};

const trainer = {
    "data": {
        "utente": {
            "id": 4,
            "nome": "Daniele",
            "cognome": "Giaquinto",
            "email": "Daniele.Giaq@gmail.it",
            "password": "$2a$10$cDlRv6/3qzj79BmSU4X.Hu5rcyUT6v4oczfrwJFCC/APgMajQugMu",
            "attivo": false,
            "dataNascita": "2000-12-12",
            "dataCreazione": null,
            "dataAggiornamento": null,
            "sesso": null,
            "telefono": "3349677013",
            "via": "P.zza Antonio Sant'elmo 2",
            "cap": "84128",
            "citta": "Salerno",
            "preparatore": null,
            "ruolo": {
                "id": 1,
                "nome": "PREPARATORE",
                "dataCreazione": "2021-12-31T00:10:18",
                "dataAggiornamento": "2021-12-31T00:10:18"
            },
            "listaProtocolli": null,
            "listaReport": null
        },
    }, "status": "success"
};

const protocollo = {
    "data": {
        "protocollo": {
            "id": 1,
            "dataScadenza": "2024-02-12",
            "schedaAlimentare": {
                "id": 1,
                "kcalAssunte": 2200,
                "listaAlimenti": [
                    {
                        "id": 1,
                        "nome": "Biscotti",
                        "pasto": "Colazione",
                        "giorno": "1",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 2,
                        "nome": "Pasta",
                        "pasto": "Pranzo",
                        "giorno": "1",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 3,
                        "nome": "Carne",
                        "pasto": "Cena",
                        "giorno": "1",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 4,
                        "nome": "pasta e fagioli",
                        "pasto": "colazione",
                        "giorno": "2",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 5,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "2",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 6,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "2",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 7,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "3",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 8,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "3",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 9,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "3",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 10,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "4",
                        "kcal": 200,
                        "grammi": 100.0
                    },
                    {
                        "id": 11,
                        "nome": "Pasta",
                        "pasto": "pranzo",
                        "giorno": "4",
                        "kcal": 200,
                        "grammi": 100.0
                    }
                ]
            },
            "schedaAllenamento": {
                "id": 1,
                "frequenza": "3",
                "listaEsercizi": [
                    {
                        "id": 1,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "1",
                        "categoria": "petto"
                    },
                    {
                        "id": 2,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "1",
                        "categoria": "petto"
                    },
                    {
                        "id": 3,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "1",
                        "categoria": "petto"
                    },
                    {
                        "id": 4,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "1",
                        "categoria": "petto"
                    },
                    {
                        "id": 5,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "2",
                        "categoria": "petto"
                    },
                    {
                        "id": 6,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "2",
                        "categoria": "petto"
                    },
                    {
                        "id": 7,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "2",
                        "categoria": "petto"
                    },
                    {
                        "id": 8,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "2",
                        "categoria": "petto"
                    },
                    {
                        "id": 9,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "3",
                        "categoria": "petto"
                    },
                    {
                        "id": 10,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "3",
                        "categoria": "petto"
                    },
                    {
                        "id": 11,
                        "nome": "pushup",
                        "serie": "3",
                        "ripetizioni": "10",
                        "recupero": "1",
                        "numeroAllenamento": "3",
                        "categoria": "petto"
                    }
                ]
            },
            "cliente": {
                "id": 4,
                "nome": "Costantina",
                "cognome": "Trascendentina",
                "email": "inapina@libero.it",
                "attivo": true,
                "dataNascita": "2001-08-13",
                "sesso": "F",
                "telefono": "3356895789",
                "via": "Corso Vittorio Emanuele, 25/B",
                "cap": "84100",
                "citta": "Salerno",
                "preparatore": {
                    "id": 2,
                    "nome": "Daniele",
                    "cognome": "Giaquinto",
                    "email": "giaqui@gmail.com",
                    "attivo": true,
                    "dataNascita": "1989-02-25",
                    "sesso": "M",
                    "telefono": "3406683793",
                    "via": "Via Antinori, 2",
                    "cap": "84085",
                    "citta": "Mercato San Severino",
                    "preparatore": null,
                    "ruolo": {
                        "id": 2,
                        "nome": "Preparatore",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "ruolo": {
                    "id": 3,
                    "nome": "Cliente",
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "listaReport": [],
                "dataCreazione": "2000-01-01T00:00:01",
                "dataAggiornamento": "2000-01-01T00:00:01"
            },
            "preparatore": {
                "id": 2,
                "nome": "Daniele",
                "cognome": "Giaquinto",
                "email": "giaqui@gmail.com",
                "attivo": true,
                "dataNascita": "1989-02-25",
                "sesso": "M",
                "telefono": "3406683793",
                "via": "Via Antinori, 2",
                "cap": "84085",
                "citta": "Mercato San Severino",
                "preparatore": null,
                "ruolo": {
                    "id": 2,
                    "nome": "Preparatore",
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "listaReport": [],
                "dataCreazione": "2000-01-01T00:00:01",
                "dataAggiornamento": "2000-01-01T00:00:01"
            },
            "dataCreazione": "2022-01-04T23:44:29.6665767",
            "dataAggiornamento": "2022-01-04T23:44:29.7350083"
        }
    },
    "status": "success"
}

let userFull = {
    "data": {
        "utente": {
            "id": 4,
            "nome": "John",
            "cognome": "Dohe",
            "email": "giaqui@gmail.com",
            "password": "$2a$10$cDlRv6/3qzj79BmSU4X.Hu5rcyUT6v4oczfrwJFCC/APgMajQugMu",
            "attivo": false,
            "dataNascita": "2000-12-12",
            "dataCreazione": "2021-01-01T00:00:01",
            "dataAggiornamento": "2021-12-31T00:10:18",
            "sesso": "M",
            "telefono": "3347274753",
            "via": "Corso Diaz, 140",
            "cap": "84084",
            "citta": "Fisciano (NA)",
            "preparatore": {
                "id": 1,
                "nome": "Rambo",
                "cognome": "Potente",
                "email": "iosonorambo@rambo.com",
                "password": "$2a$10$cDlRv6/3qzj79BmSU4X.Hu5rcyUT6v4oczfrwJFCC/APgMajQugMu",
                "attivo": true,
                "dataNascita": "1990-12-12",
                "dataCreazione": "2021-01-01T00:00:01",
                "dataAggiornamento": "2021-12-31T00:10:18",
                "sesso": "M",
                "telefono": "3365895875",
                "via": "Via Roma",
                "cap": "80808",
                "citta": "Fisciano",
                "altezza": "120",
            },
            "ruolo": {
                "id": 1,
                "nome": "PREPARATORE",
                "dataCreazione": "2021-12-31T00:10:18",
                "dataAggiornamento": "2021-12-31T00:10:18"
            },
            "listaProtocolli": null,
            "listaReport": null
        },
    },
    "status": "success"
};

const listaProtocolli = {
    "data": {
        "protocollo": [
            {
                "id": 2,
                "dataScadenza": "2027-02-02",
                "schedaAlimentare": {
                    "id": 1,
                    "kcalAssunte": 2200,
                    "listaAlimenti": [
                        {
                            "id": 1,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 2,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 3,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 4,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 5,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 6,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 7,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 8,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 9,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 10,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 11,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        }
                    ]
                },
                "schedaAllenamento": {
                    "id": 1,
                    "frequenza": "3",
                    "listaEsercizi": [
                        {
                            "id": 1,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 2,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 3,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 4,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 5,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 6,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 7,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 8,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 9,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 10,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 11,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        }
                    ]
                },
                "cliente": {
                    "id": 4,
                    "nome": "Costantina",
                    "cognome": "Trascendentina",
                    "email": "inapina@libero.it",
                    "attivo": true,
                    "dataNascita": "2001-08-13",
                    "sesso": "F",
                    "telefono": "3356895789",
                    "via": "Corso Vittorio Emanuele, 25/B",
                    "cap": "84100",
                    "citta": "Salerno",
                    "preparatore": {
                        "id": 2,
                        "nome": "Daniele",
                        "cognome": "Giaquinto",
                        "email": "giaqui@gmail.com",
                        "attivo": true,
                        "dataNascita": "1989-02-25",
                        "sesso": "M",
                        "telefono": "3406683793",
                        "via": "Via Antinori, 2",
                        "cap": "84085",
                        "citta": "Mercato San Severino",
                        "preparatore": null,
                        "ruolo": {
                            "id": 2,
                            "nome": "Preparatore",
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "listaReport": [],
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "ruolo": {
                        "id": 3,
                        "nome": "Cliente",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "preparatore": {
                    "id": 2,
                    "nome": "Daniele",
                    "cognome": "Giaquinto",
                    "email": "giaqui@gmail.com",
                    "attivo": true,
                    "dataNascita": "1989-02-25",
                    "sesso": "M",
                    "telefono": "3406683793",
                    "via": "Via Antinori, 2",
                    "cap": "84085",
                    "citta": "Mercato San Severino",
                    "preparatore": null,
                    "ruolo": {
                        "id": 2,
                        "nome": "Preparatore",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2022-01-07T00:39:35",
                "dataAggiornamento": "2022-01-07T00:39:35"
            },
            {
                "id": 1,
                "dataScadenza": "2027-02-02",
                "schedaAlimentare": {
                    "id": 1,
                    "kcalAssunte": 2200,
                    "listaAlimenti": [
                        {
                            "id": 1,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 2,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 3,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 4,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 5,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 6,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 7,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 8,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 9,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 10,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 11,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        }
                    ]
                },
                "schedaAllenamento": {
                    "id": 1,
                    "frequenza": "3",
                    "listaEsercizi": [
                        {
                            "id": 1,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 2,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 3,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 4,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 5,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 6,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 7,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 8,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 9,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 10,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 11,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        }
                    ]
                },
                "cliente": {
                    "id": 4,
                    "nome": "Costantina",
                    "cognome": "Trascendentina",
                    "email": "inapina@libero.it",
                    "attivo": true,
                    "dataNascita": "2001-08-13",
                    "sesso": "F",
                    "telefono": "3356895789",
                    "via": "Corso Vittorio Emanuele, 25/B",
                    "cap": "84100",
                    "citta": "Salerno",
                    "preparatore": {
                        "id": 2,
                        "nome": "Daniele",
                        "cognome": "Giaquinto",
                        "email": "giaqui@gmail.com",
                        "attivo": true,
                        "dataNascita": "1989-02-25",
                        "sesso": "M",
                        "telefono": "3406683793",
                        "via": "Via Antinori, 2",
                        "cap": "84085",
                        "citta": "Mercato San Severino",
                        "preparatore": null,
                        "ruolo": {
                            "id": 2,
                            "nome": "Preparatore",
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "listaReport": [],
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "ruolo": {
                        "id": 3,
                        "nome": "Cliente",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "preparatore": {
                    "id": 2,
                    "nome": "Daniele",
                    "cognome": "Giaquinto",
                    "email": "giaqui@gmail.com",
                    "attivo": true,
                    "dataNascita": "1989-02-25",
                    "sesso": "M",
                    "telefono": "3406683793",
                    "via": "Via Antinori, 2",
                    "cap": "84085",
                    "citta": "Mercato San Severino",
                    "preparatore": null,
                    "ruolo": {
                        "id": 2,
                        "nome": "Preparatore",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2022-01-07T00:39:35",
                "dataAggiornamento": "2022-01-07T00:39:35"
            },
            {
                "id": 1,
                "dataScadenza": "2027-02-02",
                "schedaAlimentare": {
                    "id": 1,
                    "kcalAssunte": 2200,
                    "listaAlimenti": [
                        {
                            "id": 1,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 2,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 3,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 4,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 5,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 6,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 7,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 8,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 9,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 10,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 11,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        }
                    ]
                },
                "schedaAllenamento": {
                    "id": 1,
                    "frequenza": "3",
                    "listaEsercizi": [
                        {
                            "id": 1,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 2,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 3,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 4,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 5,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 6,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 7,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 8,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 9,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 10,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 11,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        }
                    ]
                },
                "cliente": {
                    "id": 4,
                    "nome": "Costantina",
                    "cognome": "Trascendentina",
                    "email": "inapina@libero.it",
                    "attivo": true,
                    "dataNascita": "2001-08-13",
                    "sesso": "F",
                    "telefono": "3356895789",
                    "via": "Corso Vittorio Emanuele, 25/B",
                    "cap": "84100",
                    "citta": "Salerno",
                    "preparatore": {
                        "id": 2,
                        "nome": "Daniele",
                        "cognome": "Giaquinto",
                        "email": "giaqui@gmail.com",
                        "attivo": true,
                        "dataNascita": "1989-02-25",
                        "sesso": "M",
                        "telefono": "3406683793",
                        "via": "Via Antinori, 2",
                        "cap": "84085",
                        "citta": "Mercato San Severino",
                        "preparatore": null,
                        "ruolo": {
                            "id": 2,
                            "nome": "Preparatore",
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "listaReport": [],
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "ruolo": {
                        "id": 3,
                        "nome": "Cliente",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "preparatore": {
                    "id": 2,
                    "nome": "Daniele",
                    "cognome": "Giaquinto",
                    "email": "giaqui@gmail.com",
                    "attivo": true,
                    "dataNascita": "1989-02-25",
                    "sesso": "M",
                    "telefono": "3406683793",
                    "via": "Via Antinori, 2",
                    "cap": "84085",
                    "citta": "Mercato San Severino",
                    "preparatore": null,
                    "ruolo": {
                        "id": 2,
                        "nome": "Preparatore",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2022-01-07T00:39:35",
                "dataAggiornamento": "2022-01-07T00:39:35"
            },
            {
                "id": 1,
                "dataScadenza": "2027-02-02",
                "schedaAlimentare": {
                    "id": 1,
                    "kcalAssunte": 2200,
                    "listaAlimenti": [
                        {
                            "id": 1,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 2,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 3,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 4,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 5,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 6,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 7,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 8,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 9,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 10,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        },
                        {
                            "id": 11,
                            "nome": "Pasta",
                            "pasto": "pranzo",
                            "giorno": "1",
                            "kcal": 200,
                            "grammi": 100.0
                        }
                    ]
                },
                "schedaAllenamento": {
                    "id": 1,
                    "frequenza": "3",
                    "listaEsercizi": [
                        {
                            "id": 1,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 2,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 3,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 4,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "1",
                            "categoria": "petto"
                        },
                        {
                            "id": 5,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 6,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 7,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 8,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "2",
                            "categoria": "petto"
                        },
                        {
                            "id": 9,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 10,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        },
                        {
                            "id": 11,
                            "nome": "pushup",
                            "serie": "3",
                            "ripetizioni": "10",
                            "recupero": "1",
                            "numeroAllenamento": "3",
                            "categoria": "petto"
                        }
                    ]
                },
                "cliente": {
                    "id": 4,
                    "nome": "Costantina",
                    "cognome": "Trascendentina",
                    "email": "inapina@libero.it",
                    "attivo": true,
                    "dataNascita": "2001-08-13",
                    "sesso": "F",
                    "telefono": "3356895789",
                    "via": "Corso Vittorio Emanuele, 25/B",
                    "cap": "84100",
                    "citta": "Salerno",
                    "preparatore": {
                        "id": 2,
                        "nome": "Daniele",
                        "cognome": "Giaquinto",
                        "email": "giaqui@gmail.com",
                        "attivo": true,
                        "dataNascita": "1989-02-25",
                        "sesso": "M",
                        "telefono": "3406683793",
                        "via": "Via Antinori, 2",
                        "cap": "84085",
                        "citta": "Mercato San Severino",
                        "preparatore": null,
                        "ruolo": {
                            "id": 2,
                            "nome": "Preparatore",
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "listaReport": [],
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "ruolo": {
                        "id": 3,
                        "nome": "Cliente",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "preparatore": {
                    "id": 2,
                    "nome": "Daniele",
                    "cognome": "Giaquinto",
                    "email": "giaqui@gmail.com",
                    "attivo": true,
                    "dataNascita": "1989-02-25",
                    "sesso": "M",
                    "telefono": "3406683793",
                    "via": "Via Antinori, 2",
                    "cap": "84085",
                    "citta": "Mercato San Severino",
                    "preparatore": null,
                    "ruolo": {
                        "id": 2,
                        "nome": "Preparatore",
                        "dataCreazione": "2000-01-01T00:00:01",
                        "dataAggiornamento": "2000-01-01T00:00:01"
                    },
                    "listaReport": [],
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2022-01-07T00:39:35",
                "dataAggiornamento": "2022-01-07T00:39:35"
            }
        ]
    },
    "status": "success"
}

const listaReport = {
    "data":
        {
            "report":
                [
                    {
                        "id": 2,
                        "peso": 100.0,
                        "pesoStimato": 120.0,
                        "crfBicipite": 30.0,
                        "crfAddome": 30.0,
                        "crfQuadricipite": 30.0,
                        "cliente": {
                            "id": 2,
                            "nome": "Daniele",
                            "cognome": "Giaquinto",
                            "email": "giaqui@gmail.com",
                            "attivo": true,
                            "dataNascita": "1989-02-25",
                            "sesso": "M",
                            "telefono": "3406683793",
                            "via": "Via Antinori, 2",
                            "cap": "84085",
                            "citta": "Mercato San Severino",
                            "preparatore": null,
                            "ruolo": {
                                "id": 2,
                                "nome": "Preparatore",
                                "dataCreazione": "2000-01-01T00:00:01",
                                "dataAggiornamento": "2000-01-01T00:00:01"
                            },
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "dataCreazione": "2022-01-11T00:18:08",
                        "dataAggiornamento": "2022-01-11T00:18:08",
                        "immaginiReports": [
                            {
                                "id": 20,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--8jYubMtY--/v1641856673/htvgmz3fou4pl7wboovg.jpg"
                            },
                            {
                                "id": 21,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--0Rws0KCE--/v1641856674/vh5sejqeacujhrna7ipb.jpg"
                            },
                            {
                                "id": 22,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--cxcyG9yr--/v1641856675/c4bprrmwgeqeantlbtsu.jpg"
                            },
                            {
                                "id": 23,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--cNgJ9eqE--/v1641856676/ymrm9mzmaet0mgs10oqn.jpg"
                            },
                            {
                                "id": 24,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--C8h4EmTg--/v1641856676/vult8ueo3fnfgjxm7abj.jpg"
                            },
                            {
                                "id": 25,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--bbmEoVae--/v1641856677/baigaywjqrsgy5cpc9fz.jpg"
                            },
                            {
                                "id": 26,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--Eg7zEkou--/v1641856678/twqjitrbql4uzmoz6bnj.jpg"
                            },
                            {
                                "id": 27,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--1DCnlKF1--/v1641856679/et9dar3ww3t5mttigzzg.jpg"
                            },
                            {
                                "id": 28,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--KIkYfaqp--/v1641856680/oodvfngl9gvzlw2rdmzu.jpg"
                            },
                            {
                                "id": 29,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--mlqFYGm2--/v1641856681/upv2blv1t5httrp5oxut.jpg"
                            },
                            {
                                "id": 30,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--l1Hyx8aE--/v1641856682/sa1tmjqnucgoewrheb2m.jpg"
                            },
                            {
                                "id": 31,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--YDvjmjlz--/v1641856682/h8jao9gnfow6i2ouokxf.jpg"
                            },
                            {
                                "id": 32,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--8DuFRQwQ--/v1641856683/ku3xq6vtvaeg114kytxr.jpg"
                            },
                            {
                                "id": 33,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--MXpk_5TA--/v1641856684/agzkneauvo5vmyjsvdsz.jpg"
                            },
                            {
                                "id": 34,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--d0yUYyq1--/v1641856685/szzcutbl5foavwdpbd0t.jpg"
                            },
                            {
                                "id": 35,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--iWtu7IVN--/v1641856686/qplufcjosck5tqsmq3yp.jpg"
                            },
                            {
                                "id": 36,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--F3AtGjOy--/v1641856686/iiyaavs2n6ly5yfizelx.jpg"
                            },
                            {
                                "id": 37,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--Frch37J2--/v1641856687/gukalckreue2xvbokvie.jpg"
                            },
                            {
                                "id": 38,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--RcSwhcIs--/v1641856688/h1qec9mqwdkszgvn5kgs.jpg"
                            }
                        ]
                    },
                    {
                        "id": 3,
                        "peso": 80.0,
                        "pesoStimato": 100.0,
                        "crfBicipite": 30.0,
                        "crfAddome": 30.0,
                        "crfQuadricipite": 30.0,
                        "cliente": {
                            "id": 2,
                            "nome": "Daniele",
                            "cognome": "Giaquinto",
                            "email": "giaqui@gmail.com",
                            "attivo": true,
                            "dataNascita": "1989-02-25",
                            "sesso": "M",
                            "telefono": "3406683793",
                            "via": "Via Antinori, 2",
                            "cap": "84085",
                            "citta": "Mercato San Severino",
                            "preparatore": null,
                            "ruolo": {
                                "id": 2,
                                "nome": "Preparatore",
                                "dataCreazione": "2000-01-01T00:00:01",
                                "dataAggiornamento": "2000-01-01T00:00:01"
                            },
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "dataCreazione": "2022-01-11T00:18:26",
                        "dataAggiornamento": "2022-01-11T00:18:26",
                        "immaginiReports": [
                            {
                                "id": 39,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--0IVeATm5--/v1641856691/wxdg2jd3nihcfj5q1cow.jpg"
                            },
                            {
                                "id": 40,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--dpWoWZ2_--/v1641856692/g0tmmts03yu8nxyzcf6a.jpg"
                            },
                            {
                                "id": 41,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--ovDPb-uB--/v1641856693/dthcewe34pnrv3l67c8c.jpg"
                            },
                            {
                                "id": 42,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--t1ItHCEi--/v1641856693/dsthwjwn6vd1yg7r2cnu.jpg"
                            },
                            {
                                "id": 43,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--UwHIf_hG--/v1641856694/qjakuc6pdczmrhs3gqhq.jpg"
                            },
                            {
                                "id": 44,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--LWh0SW4P--/v1641856695/xoy81vy8f29kif5tl3st.jpg"
                            },
                            {
                                "id": 45,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--OjSkVu2m--/v1641856696/dfwzzawd7glddi5x51z5.jpg"
                            },
                            {
                                "id": 46,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--6PU1Q7YS--/v1641856696/pogt7jm2u9huofu9ybu2.jpg"
                            },
                            {
                                "id": 47,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--thi9hOpX--/v1641856697/s2su5mcepf2svaadceox.jpg"
                            },
                            {
                                "id": 48,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--82Om8jI9--/v1641856698/aabtv1xkzqsy6hry962m.jpg"
                            },
                            {
                                "id": 49,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--qvhPe5ov--/v1641856699/vso2hznngfphuldtvhxe.jpg"
                            },
                            {
                                "id": 50,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--Mf8UAWue--/v1641856699/maxt16p2xv3pxdrfhxy3.jpg"
                            },
                            {
                                "id": 51,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--fgeZfgtU--/v1641856700/ghhde4gymreh9dadzfni.jpg"
                            },
                            {
                                "id": 52,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--qeVYS1BB--/v1641856701/htc2sw6idkqprqopidae.jpg"
                            },
                            {
                                "id": 53,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--wbLXkXGU--/v1641856702/z87srw0japrnmyxbvott.jpg"
                            },
                            {
                                "id": 54,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--mRsAdsZG--/v1641856703/ho0blxbumg7ri5d2n5nk.jpg"
                            },
                            {
                                "id": 55,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--6JsEv1-r--/v1641856704/zjfzu4bdhwqts9a0mxfb.jpg"
                            },
                            {
                                "id": 56,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--PL7ZqpbH--/v1641856704/onoru3jzzgu6bshulddd.jpg"
                            },
                            {
                                "id": 57,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--idsrYTON--/v1641856705/q2srmodorufidzq8vbvk.jpg"
                            }
                        ]
                    },
                    {
                        "id": 4,
                        "peso": 130.0,
                        "pesoStimato": 100.0,
                        "crfBicipite": 30.0,
                        "crfAddome": 30.0,
                        "crfQuadricipite": 30.0,
                        "cliente": {
                            "id": 2,
                            "nome": "Daniele",
                            "cognome": "Giaquinto",
                            "email": "giaqui@gmail.com",
                            "attivo": true,
                            "dataNascita": "1989-02-25",
                            "sesso": "M",
                            "telefono": "3406683793",
                            "via": "Via Antinori, 2",
                            "cap": "84085",
                            "citta": "Mercato San Severino",
                            "preparatore": null,
                            "ruolo": {
                                "id": 2,
                                "nome": "Preparatore",
                                "dataCreazione": "2000-01-01T00:00:01",
                                "dataAggiornamento": "2000-01-01T00:00:01"
                            },
                            "dataCreazione": "2000-01-01T00:00:01",
                            "dataAggiornamento": "2000-01-01T00:00:01"
                        },
                        "dataCreazione": "2022-01-11T00:18:43",
                        "dataAggiornamento": "2022-01-11T00:18:43",
                        "immaginiReports": [
                            {
                                "id": 58,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--dLjKfGWS--/v1641856709/qzz48b8wg1w26hg8plu5.jpg"
                            },
                            {
                                "id": 59,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--dZPNFLIt--/v1641856710/aat4bljg4n6mjj0k0lcy.jpg"
                            },
                            {
                                "id": 60,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--kvbeG7KS--/v1641856711/pgnsegqfpmylmzg3bk7m.jpg"
                            },
                            {
                                "id": 61,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--d-22JQEF--/v1641856711/e1gw1tbf09tw7exe02ia.jpg"
                            },
                            {
                                "id": 62,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--rRTQmOBW--/v1641856712/xe3g23lvcfygkeck39wp.jpg"
                            },
                            {
                                "id": 63,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--W1DcTDLh--/v1641856713/iownio3tge9t7o4wb70m.jpg"
                            },
                            {
                                "id": 64,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--mXy61Y-N--/v1641856714/gvkhgl4zda1pg5shy25y.jpg"
                            },
                            {
                                "id": 65,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--r8TxSqVK--/v1641856715/y7scljpaflcikgafi8zw.jpg"
                            },
                            {
                                "id": 66,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--m0YRqXHQ--/v1641856715/bnx5umnghllkroequqhg.jpg"
                            },
                            {
                                "id": 67,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--WSwU6Byj--/v1641856716/iwm34bljoqahvndfdqbw.jpg"
                            },
                            {
                                "id": 68,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--YS_2Kppw--/v1641856717/frzcil15isg88uskdc28.jpg"
                            },
                            {
                                "id": 69,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--K9cO8Lg4--/v1641856718/pwmguohlf0o8y3jzfs13.jpg"
                            },
                            {
                                "id": 70,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--5jlyLnQA--/v1641856718/s9dotzkihaxj3qwazrku.jpg"
                            },
                            {
                                "id": 71,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--zpk5UUJ1--/v1641856719/x6eja4egwc6sxgv03rww.jpg"
                            },
                            {
                                "id": 72,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--IZVDEAXy--/v1641856720/jhynmzxolmpbqim18rau.jpg"
                            },
                            {
                                "id": 73,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--ivoM83j2--/v1641856721/erqz0b8bgrkcpw2tl3en.jpg"
                            },
                            {
                                "id": 74,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--f0RGggQE--/v1641856721/dhu11ewe4hupwo4vfte6.jpg"
                            },
                            {
                                "id": 75,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--sJXZ9ze9--/v1641856722/xfa9xqcfftptibjjpqa1.jpg"
                            },
                            {
                                "id": 76,
                                "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--fCGdm7if--/v1641856723/mnvisrzqhxww2u7vgokh.jpg"
                            }
                        ]
                    }
                ]
        }
    ,
    "status":
        "success"
}

const Utente= {
    "data": {
    "clienti": [
        {
            "id": 4,
            "nome": "Costantina",
            "cognome": "Trascendentina",
            "email": "inapina@libero.it",
            "attivo": true,
            "dataNascita": "2001-08-13",
            "sesso": "F",
            "telefono": "3356895789",
            "via": "Corso Vittorio Emanuele, 25/B",
            "cap": "84100",
            "citta": "Salerno",
            "preparatore": {
                "id": 2,
                "nome": "Daniele",
                "cognome": "Giaquinto",
                "email": "giaqui@gmail.com",
                "attivo": true,
                "dataNascita": "1989-02-25",
                "sesso": "M",
                "telefono": "3406683793",
                "via": "Via Antinori, 2",
                "cap": "84085",
                "citta": "Mercato San Severino",
                "preparatore": null,
                "ruolo": {
                    "id": 2,
                    "nome": "Preparatore",
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2000-01-01T00:00:01",
                "dataAggiornamento": "2000-01-01T00:00:01"
            },
            "ruolo": {
                "id": 3,
                "nome": "Cliente",
                "dataCreazione": "2000-01-01T00:00:01",
                "dataAggiornamento": "2000-01-01T00:00:01"
            },
            "dataCreazione": "2000-01-01T00:00:01",
            "dataAggiornamento": "2000-01-01T00:00:01"
        },
    ]
},
    "status": "success"
}


const report = {
    "data": {
        "report": {
            "id": 3,
            "peso": 100.0,
            "pesoStimato": 100.0,
            "crfBicipite": 30.0,
            "crfAddome": 30.0,
            "crfQuadricipite": 30.0,
            "cliente": {
                "id": 2,
                "nome": "Daniele",
                "cognome": "Giaquinto",
                "email": "giaqui@gmail.com",
                "attivo": true,
                "dataNascita": "1989-02-25",
                "sesso": "M",
                "telefono": "3406683793",
                "via": "Via Antinori, 2",
                "cap": "84085",
                "citta": "Mercato San Severino",
                "preparatore": null,
                "ruolo": {
                    "id": 2,
                    "nome": "Preparatore",
                    "dataCreazione": "2000-01-01T00:00:01",
                    "dataAggiornamento": "2000-01-01T00:00:01"
                },
                "dataCreazione": "2000-01-01T00:00:01",
                "dataAggiornamento": "2000-01-01T00:00:01"
            },
            "dataCreazione": "2022-01-10T22:50:11.9569611",
            "dataAggiornamento": "2022-01-10T22:50:11.978902",
            "immaginiReports": [
                {
                    "id": 39,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--x7pX8LhW--/v1641851397/hhs0lf48vlx425sgbwii.jpg"
                },
                {
                    "id": 40,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--xAkMOAai--/v1641851398/tc42i4pbb3m79nxsak5c.jpg"
                },
                {
                    "id": 41,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--m4lJP2Pv--/v1641851399/xni3eizy6eyfm3ipxx4s.jpg"
                },
                {
                    "id": 42,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--0bdhMYeB--/v1641851400/d9lczoh9rockpuseew2d.jpg"
                },
                {
                    "id": 43,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--HLIZL2mk--/v1641851400/mxfvksiqvtyhlyr9grlq.jpg"
                },
                {
                    "id": 44,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--IKRdk-ZM--/v1641851401/xhjuar9cx0vplqma9t91.jpg"
                },
                {
                    "id": 45,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--kF5IJH4A--/v1641851402/sw0wytnnf1tf3zej7yad.jpg"
                },
                {
                    "id": 46,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--F_x-mrci--/v1641851403/wx1tfj91cebispbrpwun.jpg"
                },
                {
                    "id": 47,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--JFiB5spy--/v1641851403/cjmp9u8u3oylr6ibreoe.jpg"
                },
                {
                    "id": 48,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--6nF9aO_F--/v1641851404/eu5wzbp9ttn6sdpaeztw.jpg"
                },
                {
                    "id": 49,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--NbeZQNjb--/v1641851405/cm8v1ot0ktaphxg9ymgt.jpg"
                },
                {
                    "id": 50,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--n912yuxU--/v1641851406/jrg59nrwqdvge9lthey6.jpg"
                },
                {
                    "id": 51,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--upUvzDei--/v1641851407/brmcudpoutdnsbiptltr.jpg"
                },
                {
                    "id": 52,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--yC2uBGDU--/v1641851407/nwmz7foy09r7dkgqlkdz.jpg"
                },
                {
                    "id": 53,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--gsEHBpnq--/v1641851408/qu9ohhvmqrbqxeb1qyqb.jpg"
                },
                {
                    "id": 54,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--rSzJZ4E3--/v1641851409/eyzb4l5e2t2qvkweexfy.jpg"
                },
                {
                    "id": 55,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--0-gWV_tR--/v1641851410/ivzrn05ksbhxemf7ldom.jpg"
                },
                {
                    "id": 56,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s--OcL5nkAZ--/v1641851410/kmotyammmmaaalx5drzj.jpg"
                },
                {
                    "id": 57,
                    "url": "https://res.cloudinary.com/hdjxm4zyg/image/upload/s---ORsyA5t--/v1641851411/lnednkhvwklkbmxlmf3n.jpg"
                }
            ]
        }
    },
    "status": "success"
}

let currentUser = {
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aWFxdWlAZ21haWwuY29tIiwicm9sZXMiOlsiUFJFUEFSQVRPUkUiXSwiaXNzIjoiL2FwaS92MS91dGVudGkvbG9naW4iLCJleHAiOjE2NDEwODQzNzh9.EkW-6T5Pso5ttM6gKJXwnP03iUdWNbvXDK7tRQOsLU4",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aWFxdWlAZ21haWwuY29tIiwicm9sZXMiOlsiUFJFUEFSQVRPUkUiXSwiaXNzIjoiL2FwaS92MS91dGVudGkvbG9naW4iLCJleHAiOjE2NDEwODU1Nzh9.KzYei4HKf6OOJstqYnWBXeRaQ3jdug47OtPMbgpVzFo"
}

export function getUser() {
    return user;
}

export function getUserFull() {
    return userFull;
}

export function getTrainer() {
    return trainer;
}

export function getCurrentFakeUser() {
    return currentUser;
}

export function getProtocollo() {
    return protocollo;
}

export function getProtocolList() {
    return listaProtocolli;
}


export function getReport() {
    return report;
}

export function getListaReport() {

    return listaReport;
}

export function getFakeSchedaAlimentare() {

    return schedaAlimentare;
}