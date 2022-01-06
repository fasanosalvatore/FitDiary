import {
    Box,
    chakra,
    Container, createIcon,
    Link,
    Stack,
    Text,
    useColorModeValue,
    VisuallyHidden,
} from '@chakra-ui/react';
import { FaInstagram, FaTwitter, FaYoutube } from 'react-icons/fa';
import React from "react";
import {createBreakpoints} from "@chakra-ui/theme-tools";

createBreakpoints({
    sm: '320px',
    md: '768px',
    lg: '960px',
    xl: '1200px',
    '2xl': '1536px',
})

const Logo = createIcon({
    displayName: 'Logo',
    viewBox: '0 0 250 200',
    // path can also be an array of elements, if you have multiple paths, lines, shapes, etc.
    path: (
        <g id="Layer_2" data-name="Layer 2" fill="currentColor">
            <g id="Layer_1-2" data-name="Layer 1">
                <polygon className="cls-1"
                         points="180.27 87.64 147.29 87.64 163.53 111.14 164.31 112.27 183.32 139.79 56.71 139.79 5.77 166.93 235.01 166.93 180.27 87.64"/>
                <polygon className="cls-1"
                         points="180.27 87.64 147.29 87.64 133.46 67.62 133.43 67.64 119.87 47.86 119.87 0.03 152.13 46.95 152.16 46.92 180.27 87.64"/>
                <polygon className="cls-1" points="164.66 112.27 81.21 112.27 97.37 87.64 147.29 87.64 164.66 112.27"/>
                <path className="cls-2" d="M82.36,37.16,108,0V47.8l-6.64,9.63C95.37,51.08,88.27,43.5,82.36,37.16Z"/>
                <path fill="currentColor" className="cls-2"
                      d="M74.91,47.75,0,156.54l52.11-27.76,12.14-17.64,16.16-23.5L93.9,68C87.92,61.67,80.81,54.1,74.91,47.75Z"/>
                <polygon className="cls-2" points="59.77 87.64 80.41 87.64 64.25 111.14 59.77 111.14 59.77 87.64"/>
                <polygon className="cls-1" points="180.27 87.64 180.27 111.14 163.53 111.14 147.29 87.64 180.27 87.64"/>
            </g>
        </g>
    ),
})

const SocialButton = ({
                          children,
                          label,
                          href,
}) => {
    return (
        <chakra.button
            bg={useColorModeValue('blackAlpha.100', 'whiteAlpha.100')}
            color="blue.500"
            rounded={'full'}
            w={8}
            h={8}
            cursor={'pointer'}
            as={'a'}
            href={href}
            display={'inline-flex'}
            alignItems={'center'}
            justifyContent={'center'}
            transition={'background 0.3s ease'}
            _hover={{
                bg: 'blue.500',
                color: "white"
            }}>
            <VisuallyHidden>{label}</VisuallyHidden>
            {children}
        </chakra.button>
    );
};

export default function Footer() {
    return (
        <Box
            bg={useColorModeValue('gray.50', 'gray.900')}
            color={useColorModeValue('gray.700', 'gray.200')}>
            <Container
                as={Stack}
                maxW={'6xl'}
                py={4}
                direction={{ base: 'column', md: 'row' }}
                spacing={4}
                justify={{ base: 'center', md: 'space-between' }}
                align={{ base: 'center', md: 'center' }}>
                <Link href="#">
                    <Text fontSize={{base:"9xl",md: '7xl'}} fontFamily="monospace" fontWeight="bold" color={"blue.500"} >
                        <Logo/>
                    </Text>
                </Link>
                <Text>© 2022 FitDiary. All rights reserved</Text>
                <Stack direction={'row'} spacing={6}>
                    <SocialButton label={'Twitter'} href={'#'}>
                        <FaTwitter />
                    </SocialButton>
                    <SocialButton label={'YouTube'} href={'#'}>
                        <FaYoutube />
                    </SocialButton>
                    <SocialButton label={'Instagram'} href={'#'}>
                        <FaInstagram />
                    </SocialButton>
                </Stack>
            </Container>
        </Box>
    );
}