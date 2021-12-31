import {
    Container,
    Flex,
} from "@chakra-ui/react";
import Login from "./Login.js";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";


import IscrizioneCliente from "./IscrizioneCliente";
import RegistrazionePage from "./RegistrazionePage";


const App = () => {
    return (
    <Container maxW={"container.xl"} p={[0,10,20]} >
        <Flex py={[0,10,20]} >
            <BrowserRouter>
                <Routes>
                    <Route path="/registrati" element={<RegistrazionePage/>}/>
                    <Route path="/login" element={<Login />} />
                    <Route path="/iscrizioneCliente" element={<IscrizioneCliente/>}/>
                </Routes>
            </BrowserRouter>
        </Flex>
    </Container>
)}

export default App;