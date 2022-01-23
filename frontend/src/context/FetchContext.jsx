import React, {createContext} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const FetchContext = createContext({});
const {Provider} = FetchContext;

const FetchProvider = ({children}) => {
    const navigate = useNavigate();
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
                if(error.response.data.message === "Session expired")
                    navigate("/login");
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