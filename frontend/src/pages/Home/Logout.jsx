import {Text, useToast} from "@chakra-ui/react";
import {useContext, useEffect} from "react";
import {AuthContext} from "../../context/AuthContext";

export default function Logout() {
    const authContext = useContext(AuthContext);
    const toast = useToast();
    useEffect(() => {
        toast({
            title: 'Logout eseguito!',
            description: "Verrai riderizionato a breve!",
            status: 'success',
            duration: 2000
        })
        setTimeout(() => {
            authContext.logout();
        },2000)
    })

    return (
        <>
            <Text>Successfull Logout</Text>
        </>
    );
}