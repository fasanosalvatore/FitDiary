import React from 'react';
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
import {getListaReport} from "../../fakeBackend";
import {useEffect, useState} from "react";
import {Heading} from "@chakra-ui/react";

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
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Chart.js Line Chart',
        },
    },
};


function StoricoProgressi() {
    const listaReport = getListaReport().data;

    const [data, setData] = useState({
        labels: [],
        datasets: [
            {
                label: "peso", //nome cliente,
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                "data": []
            },
            {
                label: "pesoStimato", //nome cliente,
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                "data": []
            },
        ]
    });


    useEffect(() => {
        function extractData() {
            const newData={
                labels: [],
                datasets: [
                    {
                        label: "peso", //nome cliente,
                        borderColor: 'rgb(255, 99, 132)',
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        "data": []
                    },
                    {
                        label: "pesoStimato", //nome cliente,
                        borderColor: 'rgb(255, 99, 132)',
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        "data": []
                    },
                ]};
            listaReport.report.map((report, m) => {
                newData.labels.push(m);
                newData.datasets[0].data.push(report.peso);
                newData.datasets[1].data.push(report.pesoStimato);

            });
            setData(newData);
        }

        extractData();
    }, [data]);


    return (
        <>
            <Heading textAlign={"center"}>Storico Progressi</Heading>
            <Line options={options} data={data}/>
        </>
    );
}

export default StoricoProgressi;