import React, {useContext} from "react";
import {Box, ChakraProvider} from "@chakra-ui/react";
import {BrowserRouter, Outlet, Route, Routes} from "react-router-dom";
import {AuthContext, AuthProvider} from "./context/AuthContext";
import {FetchProvider} from "./context/FetchContext";
import {Navigate} from "react-router";

import AppShell from "./AppShell";
import Welcome from "./pages/Welcome";
import Login from "./pages/Home/Login";
import Logout from "./pages/Home/Logout";
import Signup from "./pages/Home/Signup";
import Dashboard from "./pages/Dashboard";

import UsersProfile from "./pages/User/Profile";
import UsersEdit from "./pages/User/Edit";

import CustomersIndex from "./pages/User/Customer/Index";
import CustomersCreate from "./pages/User/Customer/Create";

import TrainersIndex from "./pages/User/Trainer/Index";

import ProtocolsIndex from "./pages/Protocols/Index"
import ProtocolsCreate from "./pages/Protocols/Create"
import ReportsCreate from "./pages/Reports/Create"
import ProtocolsView from "./pages/Protocols/View";
import ProtocolsDietCardsView from "./pages/Protocols/DietCards/View";
import ProtocolsTrainingCardsView from "./pages/Protocols/TrainingCards/View";

const AuthenticatedRoute = () => {
    const authContext = useContext(AuthContext);
    console.log(authContext.isAuthenticated());
    return (
        !authContext.isAuthenticated() ? <Navigate to={"/login"}/> : <AppShell><Outlet/></AppShell>
    )
}

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Welcome/>}/>
            <Route path="signup" element={<Signup/>}/>
            <Route path="login" element={<Login/>}/>
            <Route path="/" element={<AuthenticatedRoute/>}>
                <Route path="dashboard" index element={<Dashboard/>}/>
                <Route path="account" element={<UsersEdit/>}/>
                <Route path="profile" element={<UsersProfile/>}/>
                <Route path="logout" element={<Logout/>}/>
                <Route path="protocols" element={<ProtocolsIndex/>}/>
                <Route path="protocols/:id" element={<ProtocolsView/>}/>
                <Route path="protocols/create" element={<ProtocolsCreate/>}/>
                <Route path="dietcard/:id" element={<ProtocolsDietCardsView/>}/>
                <Route path="trainingcard/:id" element={<ProtocolsTrainingCardsView/>}/>
                <Route path="/customers" element={<CustomersIndex/>}>
                    <Route path="create" element={<CustomersCreate/>}/>
                </Route>
                <Route path="reports/" element={<ReportsCreate/>}>
                    <Route path="create" element={<CustomersCreate/>}/>
                </Route>
            </Route>
            <Route path="/trainer" element={<AppShell><TrainersIndex/></AppShell>}>
                <Route path="edit" element={<AppShell><UsersEdit/></AppShell>}/>
            </Route>
        </Routes>
    )
}

const App = () => {
    return (
        <ChakraProvider>
            <BrowserRouter>
                <AuthProvider>
                    <FetchProvider>
                        <Box bg={"gray.100"}>
                            <AppRoutes/>
                        </Box>
                    </FetchProvider>
                </AuthProvider>
            </BrowserRouter>
        </ChakraProvider>
    )
}

export default App;