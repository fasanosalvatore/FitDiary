import {Button, ButtonGroup} from "@chakra-ui/react";
import {Link} from "react-router-dom";
import React from "react";

export function ButtonsUserActions(props) {
    return <ButtonGroup>
        <Link to={`/protocols?idCliente=${props.c.id}`}>
            <Button colorScheme="fitdiary">Protocolli</Button>
        </Link>
        {props.authContext.isAdmin() ? (
            <Button colorScheme="red">Elimina</Button>
        ) : (
            <Button colorScheme={props.c.attivo ? "red" : "green"}>
                {props.c.attivo ? "Disattiva" : "Attiva"}
            </Button>
        )}
    </ButtonGroup>;
}