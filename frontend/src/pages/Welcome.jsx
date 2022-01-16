import { Box, Button, Flex, Heading, Link, VStack, HStack, Text, Image } from "@chakra-ui/react";
import { Link as ReactLink } from "react-router-dom";
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
                            <Button colorScheme='blue' mr='4'>
                                Registrati
                            </Button>
                        </Link>
                        <Link as={ReactLink} to={"/login"}>
                            <Button colorScheme='blue'>
                                Login
                            </Button>
                        </Link>
                    </Box>
                </Flex>
                <Flex width={"full"} justify={"center"} align={"center"}>
                    <Image w={"full"} src={imbBg} filter={"grayscale(100%)"} />
                    <VStack pos={"absolute"} textAlign={"center"}>
                        <Text fontSize={["1em", "2em", "3em", "5em"]} color={"whiteAlpha.900"}>
                            un nuovo modo di interagire con i tuoi clienti
                        </Text>
                        <Heading fontSize={["1em", "2em", "3em", "5em"]} color={"whiteAlpha.900"}>FitDiary</Heading>
                    </VStack>
                </Flex>
                <Flex backgroundColor={"whiteAlpha.700"} width={"full"} justify={"center"}>
                    <TierPrice />
                </Flex>
                <Footer width={"full"} />
            </VStack>
        </>
    )
}