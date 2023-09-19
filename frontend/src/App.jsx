import React, {lazy, Suspense, useContext} from "react";
import {Box, ChakraProvider, extendTheme} from "@chakra-ui/react";
import {BrowserRouter, Outlet, Route, Routes} from "react-router-dom";
import {AuthContext, AuthProvider} from "./context/AuthContext";
import {FetchProvider} from "./context/FetchContext";
import {Navigate} from "react-router";

import AppShell from "./AppShell";
import Welcome from "./pages/Welcome";
import Login from "./pages/Home/Login";
import Logout from "./pages/Home/Logout";
import Dashboard from "./pages/Dashboard";

import UsersIndex from "./pages/User/Index";
import UsersProfile from "./pages/User/Profile";
import UsersEdit from "./pages/User/Edit";

import CustomersIndex from "./pages/User/Customers/Index";
import CustomersCreate from "./pages/User/Customers/Create";

import TrainersIndex from "./pages/User/Trainers/Index";
import StoricoProgressi from "./pages/Protocols/StoricoProgressi";
import ProtocolsIndex from "./pages/Protocols/Index"
import ProtocolsCreate from "./pages/Protocols/Create"
import ReportsCreate from "./pages/Reports/Create"
import ReportsView from "./pages/Reports/View"
import ProtocolsView from "./pages/Protocols/View";

import DietCardsIndex from "./pages/DietCards/Index";
import DietCardsView from "./pages/DietCards/View";
import DietCardsCreate from "./pages/DietCards/Create";
import DietCardsEdit from "./pages/DietCards/Edit";

import TrainingCardsIndex from "./pages/TrainingCards/Index";
import TrainingCardsView from "./pages/TrainingCards/View";
import TrainingCardsCreate from "./pages/TrainingCards/Create";
import TrainingCardsEdit from "./pages/TrainingCards/Edit";

import AlimentiIndex from "./pages/Alimento/Index.jsx";
import EserciziIndex from "./pages/Esercizio/Index.jsx";


import CustomersView from "./pages/User/Customers/View";
import CustomerInsertInfo from "./pages/User/Customers/CustomerInsertInfo";


const Signup = lazy(() => import("./pages/Home/Signup"));


const AuthenticatedRoute = () => {
    const authContext = useContext(AuthContext);
    console.log(authContext.isAuthenticated());
    return (
        !authContext.isAuthenticated() ? <Navigate to={"/login"} /> : <AppShell><Outlet /></AppShell>
    )
}

const AppRoutes = () => {
    return (
        <Suspense fallback={<div>Loading...</div>}>
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

                <Route path="dietcards" element={<DietCardsIndex />} />
                <Route path="dietcards/:id" element={<DietCardsView />} />
                <Route path="dietcards/create" element={<DietCardsCreate />} />
                <Route path="dietcards/edit/:id" element={<DietCardsEdit />} />

                <Route path="trainingcards" element={<TrainingCardsIndex />} />
                <Route path="trainingcards/:id" element={<TrainingCardsView />} />
                <Route path="trainingcards/create" element={<TrainingCardsCreate />} />
                <Route path="trainingcards/edit/:id" element={<TrainingCardsEdit />} />

                <Route path="alimenti" element={<AlimentiIndex />} />
                <Route path="esercizi" element={<EserciziIndex />} />


                <Route path="users" element={<UsersIndex/>}/>
                <Route path="customers" element={<UsersIndex/>}/>
                <Route path="insertInfo" element={<CustomerInsertInfo/>}/>
                <Route path="customers/:id" element={<CustomersView/>}/>
                <Route path="customers" element={<CustomersIndex />}>
                    <Route path="create" element={<CustomersCreate />} />
                </Route>
                <Route path="progress" element={<StoricoProgressi/>} />
                <Route path="reports/:id" element={<ReportsView/>}/>
                <Route path="reports" element={<ReportsCreate />}>
                    <Route path="create" element={<CustomersCreate />} />
                </Route>
            </Route>
            <Route path="/trainer" element={<AppShell><TrainersIndex /></AppShell>}>
                <Route path="edit" element={<AppShell><UsersEdit /></AppShell>} />
            </Route>
        </Routes>
        </Suspense>
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
