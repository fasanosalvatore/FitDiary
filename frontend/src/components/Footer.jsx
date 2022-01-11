import {
    ButtonGroup,
    Flex,
    IconButton,
    Link,
    Stack,
    Text,
} from "@chakra-ui/react";
import { FaFacebook, FaInstagram } from "react-icons/fa";
import React from "react";
import Logo from "./Logo";

export default function Footer() {
    return (
        <Flex
            ml={{ base: 0, md: 60 }}
            px={[0, 5, 10]}
            minHeight="20vh"
            bg={"blue.500"}
            as="footer"
            mx="auto"
            direction={{ base: "column", md: "row" }}
            spacing={4}
            justify={{ base: "center", md: "space-between" }}
            align={{ base: "center", md: "center" }}
        >
            <Link href={"/"}>
                <Logo
                    viewBox="-10 -50 250 250"
                    color="white"
                    boxSize={{ base: "4em", md: "6em" }}
                />
            </Link>
            <Text color={"white"} mb={[0, 2]}>
                © 2022 FitDiary. All rights reserved
            </Text>
            <Stack
                direction={{ base: "column", lg: "row" }}
                spacing={{ base: "10", lg: "28" }}
            >
                <ButtonGroup colorScheme={"gray"}>
                    <IconButton
                        colorScheme={"facebook"}
                        as="a"
                        href="https://www.facebook.com/fitdiary21"
                        aria-label="Facebook"
                        icon={<FaFacebook />}
                    />
                    <IconButton
                        colorScheme={"red"}
                        as="a"
                        href="https://www.instagram.com/fitdiary_is"
                        aria-label="Facebook"
                        icon={<FaInstagram fontSize="20px" />}
                    />
                </ButtonGroup>
            </Stack>
        </Flex>
    );
}
