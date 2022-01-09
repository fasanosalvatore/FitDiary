import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Welcome from "./pages/Welcome";
import Login from "./pages/Home/Login";
import Logout from "./pages/Home/Logout";
import Signup from "./pages/Home/Signup";
import CustomerIndex from "./pages/User/Customer/CustomerIndex";
import Profile from "./pages/User/Profile";
import Create from "./pages/User/Customer/Create";
import CustomerviewProtocol from "./pages/User/Customer/CustomerviewProtocol";
import ProtocolsList from "./pages/User/Customer/ProtocolsList";
import TrainerIndex from "./pages/User/Trainer/TrainerIndex";
import Edit from "./pages/User/Edit";
import {Box, ChakraProvider} from "@chakra-ui/react";
import {AuthProvider} from "./context/AuthContext";
import AppShell from "./AppShell";
import Dashboard from "./pages/Dashboard";
import {FetchProvider} from "./context/FetchContext";


const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Welcome/>}/>
            <Route path="signup" element={<Signup/>}/>
            <Route path="login" element={<Login/>}/>
            <Route path="logout" element={<Logout/>}/>
            <Route path="dashboard" element={<AppShell><Dashboard/></AppShell>}/>
            <Route path="test" element={<AppShell><Edit/></AppShell>}/>
            <Route path="profile" element={<AppShell><Profile/></AppShell>}/>
            <Route path="account" element={<AppShell><Edit/></AppShell>}/>
            <Route path="/customer" element={<AppShell><CustomerIndex/></AppShell>}>
                <Route path="create" element={<Create/>}/>
            </Route>
            <Route path="protocols" element={<AppShell><ProtocolsList/></AppShell>}/>
            <Route path="protocols/:id" element={<AppShell><CustomerviewProtocol/></AppShell>}/>
            <Route path="/trainer" element={<AppShell><TrainerIndex/></AppShell>}>
                <Route path="edit" element={<AppShell><Edit/></AppShell>}/>
                <Route path="addCustomer" element={<Create/>}/>
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