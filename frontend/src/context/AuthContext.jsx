import React, {createContext, useState} from 'react';
import {useNavigate} from "react-router";

const AuthContext = createContext({});
const {Provider} = AuthContext;

const AuthProvider = ({children}) => {
    const navigate = useNavigate()

    const accessTokenExpireAt = localStorage.getItem('accessTokenExpireAt');
    const refreshTokenExpireAt = localStorage.getItem('refreshTokenExpireAt');
    const userInfo = localStorage.getItem('userInfo');

    const [authState, setAuthState] = useState({
      accessTokenExpireAt: accessTokenExpireAt
        ? JSON.parse(accessTokenExpireAt)
        : 0,
      refreshTokenExpireAt: refreshTokenExpireAt
        ? JSON.parse(refreshTokenExpireAt)
        : 0,
      userInfo: userInfo ? JSON.parse(userInfo) : {},
    });

    const setAuthInfo = ({accessTokenExpireAt, refreshTokenExpireAt, userInfo}) => {
        localStorage.setItem('accessTokenExpireAt', accessTokenExpireAt);
        localStorage.setItem('refreshTokenExpireAt', refreshTokenExpireAt);
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
        setAuthState({
            accessTokenExpireAt,
            refreshTokenExpireAt,
            userInfo,
        })
    };

    const logout = () => {
        localStorage.removeItem('accessTokenExpireAt');
        localStorage.removeItem('refreshTokenExpireAt');
        localStorage.removeItem('userInfo');
        setAuthState({});
        navigate("/login");
    }

    const isAuthenticated = () => {
        if (!authState.accessTokenExpireAt ) {
            return false;
        }
        return (
            new Date().getTime() < authState.accessTokenExpireAt
        );
    };

    const isAdmin = () => {
        return authState.userInfo?.roles[0].toLowerCase() === 'admin';
    }

    const isCustomer = () => {
        return authState.userInfo?.roles[0].toLowerCase()  === 'cliente';
    }

    const isTrainer = () => {
        return authState.userInfo?.roles[0].toLowerCase()  === 'preparatore';
    }

    return (
        <Provider
            value={{
                authState,
                setAuthState: authInfo => setAuthInfo(authInfo),
                logout,
                isAuthenticated,
                isAdmin,
                isTrainer,
                isCustomer
            }}
        >
            {children}
        </Provider>
    );
};

export {AuthContext, AuthProvider};
