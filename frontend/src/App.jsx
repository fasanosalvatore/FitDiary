import {Outlet} from "react-router";
import SidebarWithHeader from "./components/SidebarWithHeader";
import Footer from "./components/Footer";


const App = () => {
    return (
        <>

            <SidebarWithHeader>
                    <Outlet/>
            </SidebarWithHeader>
            <Footer/>
        </>
    )
}

export default App;