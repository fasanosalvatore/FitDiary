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

export function getCurrentFakeUser(){
    return currentUser;
}