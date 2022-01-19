import React, {useContext, useEffect, useState} from 'react';
import {
    CategoryScale,
    Chart as ChartJS,
    Legend,
    LinearScale,
    LineElement,
    PointElement,
    Title,
    Tooltip,
} from 'chart.js';
import {Line} from 'react-chartjs-2';
import {Heading, Text, useToast, VStack,Box} from "@chakra-ui/react";
import {InfoIcon} from "@chakra-ui/icons";
import {FetchContext} from "../../context/FetchContext";
import TableResponsive from "../../components/TableResponsive";

const urlReport = "/progress"
const urlReports = "/reports"

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
    const [toastMessage, setToastMessage] = useState(undefined);
    const toast = useToast({
        duration: 3000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },
    })
    useEffect(() => {
        if (toastMessage) {
            const { title, body, stat } = toastMessage;

            toast({
                title,
                description: body,
                status: stat,
                duration: 9000,
                isClosable: true
            });
        }
    }, [toastMessage, toast]);

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
        console.log("pages/protocols/storicoprogressi");
        const getReports = async () => {
            try {
                let params = (new URL(document.location)).searchParams;
                const idCliente = params.get("idCliente") || "";
                const { data } = await fetchContext.authAxios("reports" + (idCliente !== "" ? "?clienteId=" + idCliente : ""));
                setReport(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                console.log(error);
                setToastMessage({title:'ERROR',body:'NOT AUTHORIZED',stat:'error'})
            }
        }
        getReports();

    }, [fetchContext]);

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
                <VStack m={[0, 5, 10, 20]} >
                    <Heading textAlign={"center"}>Storico Progressi</Heading>
                    <Box bg={"white"} borderRadius='xl' pb={5} w={"full"} >
                    {listaReport.report.length > 0 ? (
                        <>
                            <VStack width={"full"} p={5}>
                                <Line options={options} data={data} />
                            </VStack>
                            <Text fontSize="xl" my={5} textAlign={"center"}>
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
                                            link: urlReports+"/",
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
                    </Box>
                </VStack>
            )}
        </>


    );
}

export default StoricoProgressi;