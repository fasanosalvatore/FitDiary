import axios from 'axios';

import AuthService from "../services/auth.service";

const publicFetch = axios.create({
    baseURL: process.env.REACT_APP_SERVER_URL
})

const privateFetch = axios.create({
    baseURL: process.env.REACT_APP_SERVER_URL,
    headers:{
        'Authorization': "Bearer " + AuthService.getAccesstToken()
    }
})

export { publicFetch,privateFetch };