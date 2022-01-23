import axios from 'axios';

const publicFetch = (!process.env.NODE_ENV || process.env.NODE_ENV === 'development')
    ? axios.create({
        baseURL: process.env.REACT_APP_SERVER_URL
    })
    : axios.create({
        baseURL: process.env.REACT_APP_SERVER_URL,
        withCredentials: true
    });

export {publicFetch};