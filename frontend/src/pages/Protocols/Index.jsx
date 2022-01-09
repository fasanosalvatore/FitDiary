import {Button} from "@chakra-ui/react";
import {Link} from "react-router-dom";
import {Outlet} from "react-router";

const Index = () => {
    return (
        <>
            <Link to="/protocols/create">
                <Button color={"blue.500"}>Crea Protocollo</Button>
            </Link>
            <Outlet/>
        </>
    )
}
export default Index;