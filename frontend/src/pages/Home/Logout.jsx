import {Text, useToast} from "@chakra-ui/react";
import {useEffect} from "react";
import AuthService from "../../services/auth.service";
import {useNavigate} from "react-router-dom";

export default function Logout() {
    const toast = useToast();
    const navigate = useNavigate();
    useEffect(() => {
        AuthService.logout();
        toast({
            title: 'Logout eseguito!',
            description: "Verrai riderizionato a breve!",
            status: 'success',
            duration: 2000
        })
        setTimeout(() => {
          navigate("/login");
        },2000)
    })

    return (
        <>
            <Text>Successfull Logout</Text>
        </>
    );
}