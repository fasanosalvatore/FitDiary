import React, { useContext } from 'react';
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
import { Line } from 'react-chartjs-2';
import { useEffect, useState } from "react";
import {Heading, Text, useToast, VStack } from "@chakra-ui/react";
import { InfoIcon } from "@chakra-ui/icons";
import { FetchContext } from "../../context/FetchContext";
import TableResponsive from "../../components/TableResponsive";

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
    elements: {
        point: {
            radius: 5,
            backgroundColor: 'rgb(4, 153, 242)',
            borderColor: 'rgb(0,0,0)'
        },
        line: {
            borderColor: 'rgb(4, 153, 242)',
        }
    },
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
                "data": []
            },
            {
                label: "peso stimato", //nome cliente,
                borderDash: [5, 5],
                "data": []
            },
        ]
    });

    useEffect(() => {
        const getReports = async () => {
            try {
                let params = (new URL(document.location)).searchParams;
                const idCliente = params.get("idCliente") || "";
                const { data } = await fetchContext.authAxios("reports" + (idCliente !== "" ? "?clienteId=" + idCliente : ""));
                setReport(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                console.log(error);
                toast({
                    title: "ERROR", description: "NOT AUTHORIZED", status: "error"
                })
            }
        }

        getReports();

    }, [fetchContext,toast]);

    useEffect(() => {
        function extractData() {
            const newData = {
                labels: [0],  // ci sta almeno una riga in tabella
                datasets: [
                    {
                        label: "peso", //nome cliente,
                        "data": []
                    },
                    {
                        label: "peso stimato", //nome cliente,
                        borderDash: [5, 5],
                        "data": [null] // per far partire il peso stimato dal peso stimato
                    },
                ]
            };
            if (listaReport) {
                listaReport.report.map((report, m) => {
                    newData.labels.push(m + 1);
                    newData.datasets[0].data.push(report.peso);
                    newData.datasets[1].data.push(report.pesoStimato);
                    return newData;
                });
            }
            setData(newData);
        }
        extractData();
    }, [listaReport])

    return (
        <>
            {!isLoading && listaReport && (
                <VStack m={[0, 5, 10, 20]} bgColor={"whiteAlpha.700"}>
                    <Heading textAlign={"center"}>Storico Progressi</Heading>
                    {listaReport.report.length > 0 ? (
                        <>
                            <VStack width={"60vw"}  >
                                <Line options={options} data={data} />
                            </VStack>
                            <Text fontSize="xl" my={5}>
                                Report di {listaReport.report[0].cliente.nome}{" "}
                                {listaReport.report[0].cliente.cognome}
                            </Text>
                            <TableResponsive
                                name='Lista Report'
                                head={
                                    [
                                        "Id",
                                        "Peso",
                                        "Peso Stimato",
                                        "Circoferenza Addome",
                                        "Circoferenza Bicipite",
                                        "Circoferenza Quadricipite",
                                        "Data Creazione",
                                        "Azione"
                                    ]
                                }
                                body={listaReport.report}
                                obj={
                                    [
                                        "id",
                                        "peso",
                                        "pesoStimato",
                                        "crfAddome",
                                        "crfBicipite",
                                        "crfQuadricipite",
                                        {
                                            isData:true,
                                            name:"dataCreazione"
                                        },
                                        {
                                            link: urlReport,
                                            element:<InfoIcon />,
                                            name: "id"
                                        }
                                    ]
                                }
                            />
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