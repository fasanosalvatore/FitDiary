import {
    Box, ButtonGroup, Container, Link, Stack, Text, IconButton,
} from '@chakra-ui/react';
import {FaFacebook, FaInstagram} from 'react-icons/fa';
import React from "react";
import Logo from "./Logo";

export default function Footer() {
    return (<Box bg={"gray.100"} as="footer" mx="auto" maxW="7xl" px={{base: '4', md: '8'}} >
        <Container
            as={Stack}
            maxW={'6xl'}
            py={4}
            direction={{base: 'column', md: 'row'}}
            spacing={4}
            justify={{base: 'center', md: 'space-between'}}
            align={{base: 'center', md: 'center'}}>
            <Link href={"/"}>
                <Text fontSize={{base: "9xl", md: '7xl'}} fontFamily="monospace" fontWeight="bold"
                      color={"blue.500"}>
                    <Logo/>
                </Text>
            </Link>
            <Text>© 2022 FitDiary. All rights reserved</Text>
            <Stack direction={{base: 'column', lg: 'row'}} spacing={{base: '10', lg: '28'}}>
                <ButtonGroup>
                    <IconButton as="a" href="https://www.facebook.com/fitdiary21" aria-label="Facebook"
                                icon={<FaFacebook fontSize="20px"/>}/>
                    <IconButton as="a" href="https://www.instagram.com/fitdiary_is" aria-label="Facebook"
                                icon={<FaInstagram fontSize="20px"/>}/>
                </ButtonGroup>
            </Stack>
        </Container>
    </Box>);
}