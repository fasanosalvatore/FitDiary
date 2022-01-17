import {Box, Flex, VStack} from "@chakra-ui/react";
import React from "react";

export function UserDataBox(props) {
    return <Box bg={"white"} rounded={20} padding={10} minW={{base: "100%", xl: "98%"}} mt={5}>
        <Flex>
            <VStack>
                <text>{props.utente.nome}</text>
            </VStack>
        </Flex>
    </Box>;
}