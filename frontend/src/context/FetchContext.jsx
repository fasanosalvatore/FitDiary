import React, {createContext} from 'react';
import axios from 'axios';

const FetchContext = createContext({});
const {Provider} = FetchContext;

const FetchProvider = ({children}) => {
    const authAxios = (!process.env.NODE_ENV || process.env.NODE_ENV === 'development')
        ? axios.create({
            baseURL: process.env.REACT_APP_SERVER_URL
        })
        : axios.create({
            baseURL: process.env.REACT_APP_SERVER_URL,
            withCredentials: true
        });

    authAxios.interceptors.response.use(
        response => {
            return response;
        },
        error => {
            const code =
                error && error.response ? error.response.status : 0;
            if (code === 401 || code === 403) {
                console.log('error code', code);
            }
            return Promise.reject(error);
        }
    );

    return (
        <Provider
            value={{
                authAxios
            }}
        >
            {children}
        </Provider>
    );
};

export {FetchContext, FetchProvider};