import {Box, Flex, Heading} from "@chakra-ui/react";
import {GradientBar} from "./GradientBar";
import React from "react";

export default function WrapperBox({children}) {
    return (
    <Flex wrap={"wrap"} p={5}>
        <Flex alignItems={"center"} mb={5}>
            <Heading w={"full"}>Dashboard</Heading>
        </Flex>
        <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
            <GradientBar />
            {children}
        </Box>
    </Flex>)
}