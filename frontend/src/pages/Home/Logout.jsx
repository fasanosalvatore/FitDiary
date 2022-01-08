import {Text, useToast} from "@chakra-ui/react";
import {useContext, useEffect} from "react";
import AuthService from "../../services/auth.service";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../../context/AuthContext";

export default function Logout() {
    const authContext = useContext(AuthContext);
    const toast = useToast();
    const navigate = useNavigate();
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