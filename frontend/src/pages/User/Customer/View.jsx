import {Flex, Heading} from "@chakra-ui/react";
import {UserMainBox} from "../../../components/UserMainBox";
import {UserInfoBox} from "../../../components/UserInfoBox";
import {UserDataBox} from "../../../components/UserDataBox";
import React, {useContext, useEffect, useState} from "react";
import {FetchContext} from "../../../context/FetchContext";
import {useParams} from "react-router";
import {AuthContext} from "../../../context/AuthContext";

const urlGetInfo = `utenti`;

export default function View() {
    const [utente, setUtente] = useState({ nome: "pippo" });
    const [isLoading, setLoading] = useState(true);
    const fetchContext = useContext(FetchContext);
    const authContext = useContext(AuthContext);
    const { id } = useParams();

    useEffect(() => {
        console.log(id)
        const getInfoUtente = async () => {
            try {
                const { data } = await fetchContext.authAxios.get(urlGetInfo+"/"+id)
                console.log(data)
                setUtente(data.data.cliente);
                setLoading(false);
            } catch (error) {
                console.log("error", error);
            }
        };
        getInfoUtente();
    }, [fetchContext]);

    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"} p={5}>
                    <Heading w={"full"} mb={5}>Profilo Utente</Heading>
                    <UserMainBox utente={utente} showMoreBtns={true}/>
                    <UserInfoBox utente={utente}/>
                    <UserDataBox utente={utente}/>
                </Flex>
            )}</>
    )
}