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
import RegistrazioneForm from "./RegistrazioneForm";
import IscrizioneCliente from "./IscrizioneCliente";

const App = () => {
    return (
    <Container maxW={"container.md"} p={[0,10,20]} >
        <Flex h="100vh" py={[0,10,20]} >
            <BrowserRouter>
                <Routes>
                    <Route path="/registrati" element={<RegistrazioneForm/>}/>
                    <Route path="/login" element={<Login />} />
                    <Route path="/iscrizioneCliente" element={<IscrizioneCliente/>}/>
                </Routes>
            </BrowserRouter>
        </Flex>
    </Container>
)}

export default App;