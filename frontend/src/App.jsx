import React, { useContext } from "react";
import { Box, ChakraProvider, extendTheme } from "@chakra-ui/react";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import { AuthContext, AuthProvider } from "./context/AuthContext";
import { FetchProvider } from "./context/FetchContext";
import { Navigate } from "react-router";

import AppShell from "./AppShell";
import Welcome from "./pages/Welcome";
import Login from "./pages/Home/Login";
import Logout from "./pages/Home/Logout";
import Signup from "./pages/Home/Signup";
import Dashboard from "./pages/Dashboard";

import UsersIndex from "./pages/User/Index";
import UsersProfile from "./pages/User/Profile";
import UsersEdit from "./pages/User/Edit";

import CustomersIndex from "./pages/User/Customer/Index";
import CustomersCreate from "./pages/User/Customer/Create";

import TrainersIndex from "./pages/User/Trainer/Index";
import StoricoProgressi from "./pages/Protocols/StoricoProgressi";
import ProtocolsIndex from "./pages/Protocols/Index"
import ProtocolsCreate from "./pages/Protocols/Create"
import ReportsCreate from "./pages/Reports/Create"
import ProtocolsView from "./pages/Protocols/View";
import ProtocolsDietCardsView from "./pages/Protocols/DietCards/View";
import ProtocolsTrainingCardsView from "./pages/Protocols/TrainingCards/View";
import CustomersView from "./pages/User/Customer/View";


const AuthenticatedRoute = () => {
    const authContext = useContext(AuthContext);
    console.log(authContext.isAuthenticated());
    return (
        !authContext.isAuthenticated() ? <Navigate to={"/login"} /> : <AppShell><Outlet /></AppShell>
    )
}

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Welcome />} />
            <Route path="signup" element={<Signup />} />
            <Route path="login" element={<Login />} />
            <Route path="/" element={<AuthenticatedRoute />}>
                <Route path="dashboard" index element={<Dashboard />} />
                <Route path="account" element={<UsersEdit />} />
                <Route path="profile" element={<UsersProfile />} />
                <Route path="logout" element={<Logout />} />
                <Route path="protocols" element={<ProtocolsIndex />} />
                <Route path="protocols/:id" element={<ProtocolsView />} />
                <Route path="protocols/create" element={<ProtocolsCreate />} />
                <Route path="dietcard/:id" element={<ProtocolsDietCardsView />} />
                <Route path="trainingcard/:id" element={<ProtocolsTrainingCardsView />} />
                <Route path="users" element={<UsersIndex/>}/>
                <Route path="customers" element={<UsersIndex/>}/>
                <Route path="customers/:id" element={<CustomersView/>}/>
                <Route path="customers" element={<CustomersIndex />}>
                    <Route path="create" element={<CustomersCreate />} />
                </Route>
                <Route path="progress" element={<StoricoProgressi/>} />
                <Route path="reports/" element={<ReportsCreate />}>
                    <Route path="create" element={<CustomersCreate />} />
                </Route>
            </Route>
            <Route path="/trainer" element={<AppShell><TrainersIndex /></AppShell>}>
                <Route path="edit" element={<AppShell><UsersEdit /></AppShell>} />
            </Route>
        </Routes>
    )
}

const customTheme = extendTheme({
    colors: {
        fitdiary: {
            900: "#1056a7",
            800: "#0c76c9",
            700: "#0d87dd",
            600: "#0499F2",
            500: "#00a9ff",
            300: "#4bc3ff",
            400: "#20b6ff",
            100: "#b3e5ff",
            200: "#80d4ff",
            50: "#e1f5ff"
        },
    },
});

const App = () => {
    return (
        <ChakraProvider theme={customTheme}>
            <BrowserRouter>
                <AuthProvider>
                    <FetchProvider>
                        <Box bg={"fitdiary.50"}>
                            <AppRoutes />
                        </Box>
                    </FetchProvider>
                </AuthProvider>
            </BrowserRouter>
        </ChakraProvider>
    );
}

export default App;