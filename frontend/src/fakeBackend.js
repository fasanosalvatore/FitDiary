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

const report={
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

export function getSchedaAlimentare() {
    return protocollo.listaAlimenti;
}

export function getReport(){
    return report;
}