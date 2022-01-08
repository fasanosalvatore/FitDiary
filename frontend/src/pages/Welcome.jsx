import {Link, Text} from "@chakra-ui/react";
import {Link as ReactLink} from "react-router-dom";

export default function Welcome() {
    return (
        <>
            <Text>Benvenuto</Text>
            <Link as={ReactLink} to={"/signup"}>Registrati</Link>
            <Link as={ReactLink} to={"/login"}>Login</Link>
        </>
    )
}