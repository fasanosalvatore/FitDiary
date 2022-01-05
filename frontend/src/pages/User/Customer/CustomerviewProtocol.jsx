import React from 'react';
import {
    Box,
    Heading,
    Text,
    Avatar,
    Flex, VStack, HStack, Button, FormLabel, Grid, GridItem, SimpleGrid, Table,
} from '@chakra-ui/react';
import {
    RiArrowGoBackLine,
} from 'react-icons/ri';
import authService from "../../../services/auth.service";
export default function CustomerviewProtocol() {
    /**const utente = authService.getCurrentUser().utente;**/

        return(



            <Flex wrap={"wrap"}>
                <Button leftIcon={<RiArrowGoBackLine/>}>Torna alla lista</Button>
                <Heading w={"full"} mb={5} textAlign={"center"}>Protocollo n.{}</Heading>
                <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={"full"}  height={500} >
                    <Flex>
                        <VStack w="full" h="full" align="start" >
                            <SimpleGrid column={3} row={1} w="full" >

                                <GridItem colStart={1} textAlign="start">
                                    <Heading size={"xs"} >Data inizio: </Heading><Text>09/10/2021</Text>
                                </GridItem>

                                <GridItem  colStart={2} textAlign="center">
                                    <Heading size={"xs"}>Nome preparatore: </Heading><Text>Mario Rossi</Text>
                                </GridItem>

                                <GridItem colStart={3} textAlign="end">
                                    <Heading size={"xs"}>Data fine: </Heading><Text>09/11/2021</Text>
                                </GridItem>

                            </SimpleGrid>

                            <Table>



                            </Table>

                        </VStack>
                    </Flex>
                </Box>

            </Flex>


);
}
