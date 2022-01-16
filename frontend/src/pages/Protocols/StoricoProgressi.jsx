import React, {useContext} from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import {Line} from 'react-chartjs-2';

import {useEffect, useState} from "react";
import {Flex, Heading, Table, TableCaption, Tbody, Td, Text, Th, Thead, Tr, useToast, VStack} from "@chakra-ui/react";
import moment from "moment";
import {Link as ReactLink} from "react-router-dom";
import {InfoIcon} from "@chakra-ui/icons";
import {AuthContext} from "../../context/AuthContext";
import {FetchContext} from "../../context/FetchContext";

const urlReport = "/progress"
ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)

export const options = {
    responsive: true,
    maintainAspectRatio:false,
    plugins: {
        legend: {
            position: 'top',
            display: false,
        },
        title: {
            display: true,
            text: 'FitDiary Storico Progressi',
        },

    },
};


function StoricoProgressi() {
    const [isLoading, setLoading] = useState(true);
    const [search, setSearch] = useState("");
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    const fetchContext = useContext(FetchContext);
    const [listaReport, setReport] = useState();
    const toast = useToast({
        duration: 9000, isClosable: true, variant: "solid", containerStyle: {
            width: '100%', maxWidth: '100%',
        },

    })
    const [data, setData] = useState({
        labels: [],
        datasets: [
            {
                label: "peso", //nome cliente,
                borderColor: 'rgb(0,21,255)',
                backgroundColor: 'rgb(0,4,255)',
                clip: {left: 5, top: false, right: -2, bottom: 0},
                "data": []
            },
            {
                label: "pesoStimato", //nome cliente,
                borderColor: 'rgb(0,21,255)',
                backgroundColor: 'rgb(0,21,255)',
                borderDash: [5, 5],
                // clip: {left: 5, top: false, right: -2, bottom: 0},
                "data": []
            },
        ]
    });

    useEffect(async () => {
        const getReports = async () => {
            try {
                let params = (new URL(document.location)).searchParams;
                const idCliente = params.get("idCliente") || "";
                const {data} = await fetchContext.authAxios("reports"+ (idCliente !== "" ? "?clienteId=" + idCliente : ""));
                setReport(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                console.log(error);
                toast({
                    title: "ERROR", description: "NOT AUTHORIZED", status: "error"
                })
            }
        }

        function extractData() {
            const newData = {
                labels: [0],  // ci sta almeno una riga in tabella
                datasets: [
                    {
                        label: "peso", //nome cliente,
                        borderColor: 'rgb(0,21,255)',
                        backgroundColor: 'rgb(0,110,229)',
                        "data": []
                    },
                    {
                        label: "pesoStimato", //nome cliente,
                        borderColor: 'rgb(0,21,255)',
                        backgroundColor: 'rgb(0,110,229)',
                        "data": [null] // per far partire il peso stimato dal peso stimato
                    },
                ]
            };
            if (listaReport) {
                listaReport.report.map((report, m) => {
                    newData.labels.push(m + 1);
                    newData.datasets[0].data.push(report.peso);
                    newData.datasets[1].data.push(report.pesoStimato);

                });
            }
            setData(newData);
        }

        await getReports();
        extractData();
    }, [data]);

    return (
        <>
            {!isLoading && listaReport && (
                <VStack width={"full"}>
                    <Heading textAlign={"center"}>Storico Progressi</Heading>
                    {listaReport.report.length > 0 ? (
                        <>
                            <VStack height={"60vh"} width={"80vw"}  >
                                <Line  options={options} data={data}/>
                            </VStack>
                            <Text fontSize="xl" my={5}>
                                Report di {listaReport.report[0].cliente.nome}{" "}
                                {listaReport.report[0].cliente.cognome}
                            </Text>
                            <Table className={"responsiveTable"} variant="striped" colorScheme="whiteAlpha.500" size="md">
                                <TableCaption>Lista Report</TableCaption>
                                <Thead bg="fitdiary.100">
                                    <Tr>
                                        <Th>ID</Th>
                                        <Th>Peso</Th>
                                        <Th>Peso Stimato</Th>
                                        <Th>Circoferenza Addome</Th>
                                        <Th>Circoferenza Bicipite</Th>
                                        <Th>Circoferenza Quadricipite</Th>
                                        <Th>Data Creazione</Th>
                                        <Th>Azione</Th>
                                    </Tr>
                                </Thead>
                                <Tbody>
                                    {listaReport.report.map(
                                        (rep) =>
                                            (rep.id === parseInt(search) ||
                                                search === "") && (
                                                <Tr key={rep.id}>
                                                    <Td>{rep.id}</Td>
                                                    <Td>
                                                        {rep.peso}
                                                    </Td>
                                                    <Td>
                                                        {rep.pesoStimato}
                                                    </Td>
                                                    <Td>
                                                        {rep.crfAddome}
                                                    </Td>
                                                    <Td>
                                                        {rep.crfBicipite}
                                                    </Td>
                                                    <Td>
                                                        {rep.crfQuadricipite}
                                                    </Td>
                                                    <Td>
                                                        {moment(rep.dataCreazione).format(
                                                            "DD/MM/yyyy"
                                                        )}
                                                    </Td>
                                                    <Td>
                                                        <ReactLink to={urlReport + "/" + rep.id}>
                                                            <InfoIcon/>
                                                        </ReactLink>
                                                    </Td>
                                                </Tr>
                                            )
                                    )}
                                </Tbody>
                            </Table>
                        </>
                    ) : (
                        <Heading py={5} textAlign={"center"}>
                            Non c'è niente qui...
                        </Heading>
                    )}

                </VStack>
            )}
        </>


    );
}

export default StoricoProgressi;