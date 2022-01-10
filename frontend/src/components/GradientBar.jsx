import {Box} from "@chakra-ui/react";
import React from "react";

export function GradientBar(props) {
    return <Box h={"20px"}
                bgGradient={"linear(to-r, " + (props.inverse ? "blue.800" : "blue.500") + ", " + (props.inverse ? "blue.500" : "blue.800") + ")"}
                borderTopRadius={"md"}/>;
}