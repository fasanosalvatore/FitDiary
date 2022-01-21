import {Box, Button, Flex, Heading, HStack, Image, Link, Text, VStack} from "@chakra-ui/react";
import {Link as ReactLink} from "react-router-dom";
import Logo from "../components/Logo";
import TierPrice from "../components/TierPrice";
import imbBg from "../images/fitness2.jpg"
import Footer from "../components/Footer"

export default function Welcome() {
    return (
        <>
            <VStack>
                <Flex width={"full"} justify={"space-between"} align={"center"}>
                    <HStack pl={[0, 5, 10, 20]}>
                        <Logo penColor="black" viewBox={"0 0 250 200"} boxSize={"5em"} />
                        <Heading>FitDiary</Heading>
                    </HStack>
                    <Box pr={[0, 5, 10, 20]}>
                        <Link as={ReactLink} to={"/signup"}>
                            <Button colorScheme='fitdiary' mr='4'>
                                Registrati
                            </Button>
                        </Link>
                        <Link as={ReactLink} to={"/login"}>
                            <Button colorScheme='fitdiary'>
                                Login
                            </Button>
                        </Link>
                    </Box>
                </Flex>
                <Flex width={"full"} justify={"center"} align={"center"}>
                    <Image w={"full"} src={imbBg} filter={"grayscale(100%)"} />
                    <VStack pos={"absolute"} textAlign={"center"}>
                        <Text fontSize={["0.1em", "1.5em", "3em", "4em"]} color={"whiteAlpha.900"}>
                            Un nuovo modo di interagire con i tuoi clienti
                        </Text>
                        <Flex>
                            <Heading fontSize={["1em", "2em", "3em", "5em"]} color={"whiteAlpha.900"}>FitD</Heading>
                            <Heading fontSize={["1em", "2em", "3em", "5em"]} color={"fitdiary.600"}>ia</Heading>
                            <Heading fontSize={["1em", "2em", "3em", "5em"]} color={"whiteAlpha.900"}>ry</Heading>
                        </Flex>
                    </VStack>
                </Flex>
                <Flex backgroundColor={"whiteAlpha.700"} width={"full"} justify={"center"}>
                    <TierPrice link={"/signup"}/>
                </Flex>
                <Footer width={"full"} />
            </VStack>
        </>
    )
}