import {
    Container,
    Flex,
} from "@chakra-ui/react";
import RegistrazioneForm from "./RegistrazioneForm";

const App = () => {
    return (
    <Container maxW={"container.md"} p={[0,10,20]} >
        <Flex h="100vh" py={[0,10,20]} >
            <RegistrazioneForm/>
        </Flex>
    </Container>
)}

export default App;