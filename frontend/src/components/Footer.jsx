import {ButtonGroup, Flex, Icon, IconButton, Link, Stack, Text,} from '@chakra-ui/react';
import {FaFacebook, FaInstagram} from 'react-icons/fa';
import React from "react";

export default function Footer() {
    return (
        <Flex
            px={[0, 5, 10]} h={{base: "25vh", md: "20vh"}} bg={"blue.500"} as="footer" mx="auto"

            direction={{base: 'column', md: 'row'}}
            spacing={4}
            justify={{base: 'center', md: 'space-between'}}
            align={{base: 'center', md: 'center'}}>
            <Link href={"/"}>
                <Icon viewBox="-10 -50 250 250" color={"white"} boxSize={{base: "8em", md: "6em"}}
                      verticalAlign={"middle"}>
                    <g id="Layer_2" data-name="Layer 2" fill="currentColor">
                        <g id="Layer_1-2" data-name="Layer 1">
                            <polygon className="cls-1"
                                     points="180.27 87.64 147.29 87.64 163.53 111.14 164.31 112.27 183.32 139.79 56.71 139.79 5.77 166.93 235.01 166.93 180.27 87.64"/>
                            <polygon className="cls-1"
                                     points="180.27 87.64 147.29 87.64 133.46 67.62 133.43 67.64 119.87 47.86 119.87 0.03 152.13 46.95 152.16 46.92 180.27 87.64"/>
                            <polygon className="cls-1"
                                     points="164.66 112.27 81.21 112.27 97.37 87.64 147.29 87.64 164.66 112.27"/>
                            <path className="cls-2"
                                  d="M82.36,37.16,108,0V47.8l-6.64,9.63C95.37,51.08,88.27,43.5,82.36,37.16Z"/>
                            <path fill="currentColor" className="cls-2"
                                  d="M74.91,47.75,0,156.54l52.11-27.76,12.14-17.64,16.16-23.5L93.9,68C87.92,61.67,80.81,54.1,74.91,47.75Z"/>
                            <polygon className="cls-2"
                                     points="59.77 87.64 80.41 87.64 64.25 111.14 59.77 111.14 59.77 87.64"/>
                            <polygon className="cls-1"
                                     points="180.27 87.64 180.27 111.14 163.53 111.14 147.29 87.64 180.27 87.64"/>
                        </g>
                    </g>
                </Icon>
            </Link>
            <Text color={"white"} mb={[0, 5]}>© 2022 FitDiary. All rights reserved</Text>
            <Stack direction={{base: 'column', lg: 'row'}} spacing={{base: '10', lg: '28'}}>
                <ButtonGroup colorScheme={"gray"}>
                    <IconButton colorScheme={"facebook"} as="a" href="https://www.facebook.com/fitdiary21"
                                aria-label="Facebook"
                                icon={<FaFacebook fontSize="20px"/>}/>
                    <IconButton colorScheme={"red"} as="a" href="https://www.instagram.com/fitdiary_is"
                                aria-label="Facebook"
                                icon={<FaInstagram fontSize="20px"/>}/>
                </ButtonGroup>
            </Stack>
        </Flex>
    );
}