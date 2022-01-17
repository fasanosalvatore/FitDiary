import React from 'react'
import PropTypes from 'prop-types'
import { Box, Table, TableCaption, Tbody, Td, Th, Thead, Tr } from '@chakra-ui/react'
import moment from "moment";
import { Link as ReactLink } from "react-router-dom";
function TableResponsive(props) {
    return (
        <Box width={"full"} overflowX={"auto"}>
            <Table variant={"striped"} colorScheme={"whiteAlpha.500"} size={"md"}>
                <TableCaption>{props.name}</TableCaption>
                <Thead bg={"fitdiary.100"}>
                    <Tr>
                        {
                            props.head.map((head, key) => {
                                return <Th textAlign={"center"} key={key}>{head}</Th>
                            })
                        }
                    </Tr>
                </Thead>
                <Tbody>
                    {
                        props.body.map((body, key) => {
                            return <Tr key={key}>
                                {
                                    props.obj.map((obj, key2) => {
                                        return <Td textAlign={"center"} key={`tr${key}_td${key2}`}>
                                            {obj.isData
                                                ? moment(body[obj.name]).format("DD/MM/yyyy")
                                                : ""}
                                            {
                                                obj.link
                                                    ? <ReactLink to={obj.link + "" + body[obj.name]}>
                                                        {
                                                            obj.element
                                                                ? obj.element
                                                                : ""
                                                        }
                                                    </ReactLink>
                                                    : ""
                                            }
                                            {body[obj]}
                                        </Td>
                                    })
                                }
                            </Tr>
                        })
                    }
                </Tbody>
            </Table>
        </Box>
    )
}

TableResponsive.propTypes = {
    name: PropTypes.string.isRequired,
    head: PropTypes.array.isRequired,
    body: PropTypes.array.isRequired,
    obj: PropTypes.array.isRequired
}

export default TableResponsive