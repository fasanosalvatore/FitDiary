import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import {ChakraProvider} from "@chakra-ui/react";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import CustomerProfile from "./pages/User/Customer/CustomerProfile";
import Welcome from "./pages/Welcome";
import CustomerCreate from "./pages/User/Customer/CustomerCreate";
import Signup from "./pages/Home/Signup";
import Login from "./pages/Home/Login";
import TrainerEdit from "./pages/User/Trainer/TrainerEdit";
import TrainerIndex from "./pages/User/Trainer/TrainerIndex";
import CustomerIndex from "./pages/User/Customer/CustomerIndex";
import Index from "./pages/Home/Index";
import CustomerInsertInfo from "./pages/User/Customer/CustomerInsertInfo";
import CustomerEditInfo from "./pages/User/Customer/CustomerEditInfo";
import Logout from "./pages/Home/Logout";
import CustomerviewProtocol from "./pages/User/Customer/CustomerviewProtocol";
import ProtocolsList from "./pages/User/Customer/ProtocolsList";

let rootElement = document.getElementById("root");

ReactDOM.render(
    <ChakraProvider>
        <BrowserRouter>
            <Routes>
                <Route path={"/welcome"} element={<Welcome/>}/>
                <Route path="/" element={<App/>}>
                    <Route index element={<Index/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/logout" element={<Logout/>}/>
                    <Route path="/signup" element={<Signup/>}/>
                    <Route path="/customer" element={<CustomerIndex/>}>
                        <Route path="me" element={<CustomerProfile/>}/>
                        <Route path="create" element={<CustomerCreate/>}/>
                        <Route path="protocol/:id" element={<CustomerviewProtocol/>}/>
                        <Route path="protocols" element={<ProtocolsList/>}/>
                    </Route>
                    <Route path="/trainer" element={<TrainerIndex/>}>
                        <Route path="edit" element={<TrainerEdit/>}/>
                        <Route path="addCustomer" element={<CustomerCreate/>}/>
                    </Route>
                    <Route path="/test" element={<CustomerEditInfo/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    </ChakraProvider>,
    rootElement
);
