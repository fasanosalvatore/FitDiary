import React, {useState, createContext} from 'react';
import {useNavigate} from "react-router";

const AuthContext = createContext({});
const {Provider} = AuthContext;

const AuthProvider = ({children}) => {
    const navigate = useNavigate()

    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');
    const userInfo = localStorage.getItem('userInfo');

    const [authState, setAuthState] = useState({
        accessToken: accessToken ? JSON.parse(accessToken) : {},
        refreshToken: refreshToken ? JSON.parse(refreshToken) : {},
        userInfo: userInfo ? JSON.parse(userInfo) : {}
    });

    const setAuthInfo = ({accessToken, refreshToken, userInfo}) => {
        localStorage.setItem('accessToken', JSON.stringify(accessToken));
        localStorage.setItem('refreshToken', JSON.stringify(refreshToken));
        localStorage.setItem('userInfo', JSON.stringify(userInfo));
        setAuthState({
            accessToken,
            refreshToken,
            userInfo,
        })
    };

    const logout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('userInfo');
        setAuthState({});
        navigate("/login");
    }

    const isAuthenticated = () => {
        if (!authState.accessToken || !authState.accessToken.expiresAt) {
            return false;
        }
        return (
            new Date().getTime() / 1000 < authState.accessToken.expiresAt
        );
    };

    const isAdmin = () => {
        return authState.userInfo.roles[0] === 'admin';
    }

    return (
        <Provider
            value={{
                authState,
                setAuthState: authInfo => setAuthInfo(authInfo),
                logout,
                isAuthenticated,
                isAdmin
            }}
        >
            {children}
        </Provider>
    );
};

export {AuthContext, AuthProvider};
