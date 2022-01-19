import {Box} from "@chakra-ui/react";
import React from "react";

export function GradientBar(props) {
    return (
      <Box
        h={"20px"}
        bgGradient={
          "linear(to-r, " +
          (props.inverse ? "fitdiary.900" : "fitdiary.500") +
          ", " +
          (props.inverse ? "fitdiary.500" : "fitdiary.900") +
          ")"
        }
        borderTopRadius={"md"}
      />
    );
}