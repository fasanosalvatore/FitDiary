import SidebarWithHeader from "./components/SidebarWithHeader";
import React from "react";
import Footer from "./components/Footer";

const AppShell = ({children}) => {
    return (
        <>
            <SidebarWithHeader>
                {children}
                <Footer/>
            </SidebarWithHeader>
        </>
    )
}

export default AppShell;