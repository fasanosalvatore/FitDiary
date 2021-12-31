import {Outlet} from "react-router";
import {Link as RouteLink} from "react-router-dom";
import {Link} from "@chakra-ui/react";
import SidebarWithHeader from "./components/SidebarWithHeader";


const App = () => {
    return (
        <>
            <SidebarWithHeader>
                <Outlet/>
            </SidebarWithHeader>
        </>
    )
}

export default App;