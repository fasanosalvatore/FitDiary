import SidebarWithHeader from "./components/SidebarWithHeader";
import React from "react";
import Footer from "./components/Footer";
import { Box, calc, Container } from "@chakra-ui/react";

const AppShell = ({children}) => {
    return (
      <>
        <SidebarWithHeader>
            {children}
        </SidebarWithHeader>
        <Footer />
      </>
    );
}

export default AppShell;