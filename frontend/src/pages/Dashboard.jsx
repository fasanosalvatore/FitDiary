import React, {useContext} from "react";
import {AuthContext} from "../context/AuthContext";
import DashboardTrainer from "../components/DashboardTrainer";
import DashboardAdmin from "../components/DashboardAdmin";
import DashboardCustomer from "../components/DashboardCustomer";
import WrapperBox from "../components/WrapperBox";

const Dashboard = () => {
    const authContext = useContext(AuthContext);

    if(authContext.isAdmin()) {
        return <DashboardAdmin/>
    } else if (authContext.isCustomer()) {
        return <DashboardCustomer/>
    } else if (authContext.isTrainer()) {
        return <DashboardTrainer/>
    }

    return (
        <>
            <WrapperBox></WrapperBox>
        </>
    )
}

export default Dashboard;