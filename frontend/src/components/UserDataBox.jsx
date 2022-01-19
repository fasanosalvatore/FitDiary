import {Box, Flex, Text, VStack} from "@chakra-ui/react";
import React from "react";

export function UserDataBox(props) {
    return <Box bg={"white"} rounded={20} padding={10} minW={{base: "100%", xl: "98%"}} mt={5}>
        <Flex>
            <VStack>
                <Text>{props.utente.nome}</Text>
            </VStack>
        </Flex>
    </Box>;
}