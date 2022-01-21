import WrapperBox from "./WrapperBox";
import {Box, Flex, Heading, HStack, Text, VStack} from "@chakra-ui/react";
import {FaApple, FaApplePay, FaBook, FaDice, FaUsers} from "react-icons/fa";
import {GiMeal} from "react-icons/gi";
import {CgGym} from "react-icons/cg";

export default function DashboardTrainer() {
    return (
        <WrapperBox>
            <Flex>
                <Flex m={5} rounded={10} wrap={"wrap"} bg={"fitdiary.800"} boxShadow={"2xl"}>
                    <Heading w={"full"} ml={"5"} color={"white"} >Statistiche</Heading>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaUsers/>
                                    <Text>Clienti</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Disattivati</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaBook/>
                                    <Text>Protocolli</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <GiMeal/>
                                    <Text>Diete</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <CgGym/>
                                    <Text>Schede</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                </Flex>
            </Flex>
        </WrapperBox>
    )
}