import axios from 'axios';

const publicFetch = axios.create({
    baseURL: process.env.REACT_APP_SERVER_URL
})

export {publicFetch};