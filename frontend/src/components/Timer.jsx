import React, {useEffect, useState} from "react";
import {Box, Button, Text} from "@chakra-ui/react";
import {Alert} from "./AlertDialog";

function FormatTime(secs) {
    let hours = Math.floor(secs / 3600);
    let minutes = Math.floor(secs / 60) % 60;
    let seconds = secs % 60;
    return [hours, minutes, seconds]
        .map(v => ('' + v).padStart(2, '0'))
        .filter((v, i) => v !== '00' || i > 0)
        .join(':');
}


function Suona() {
    console.log("AAAAAAAAAAAAAAAAAAAAAAAAA")
}

export function Timer({children, maxSeconds}) {
    const [time, setTime] = useState(0);
    const [isActive, setActive] = useState(false);
    const [hasPlayed, setHasPlayed] = useState(false);

    function Reset() {
        setTime(0);
        setActive(false);
        setHasPlayed(false);
    }


    useEffect(() => {
        let intervalId;
        if (isActive) {
            intervalId = setInterval(() => {
                setTime(time => time + 1); // ✅ Pass a state updater
            }, 1000);
        }

        return () => clearInterval(intervalId);
    }, [isActive]); // ✅ Now count is not a dependency

    useEffect(() => {
        if (time >= maxSeconds && !hasPlayed) {
            Suona();

            alert("Time is up")
            setHasPlayed(true)

        }
    }, [time]); // ✅ Now count is not a dependency
    const button = isActive ?
        <Button onClick={ Reset} alignSelf={"center"} colorScheme={"fitdiary"}>Ferma</Button> :
        <Button onClick={() => setActive(true)} alignSelf={"center"} colorScheme={"fitdiary"}>Avvia</Button>;

    return (
        <Box align={"center"} width={"full"} bg={"white"}>
            <Text fontSize={30} fontWeight='bold' align={"center"}>{FormatTime(time)}</Text>
            {button}
        </Box>

    );
}