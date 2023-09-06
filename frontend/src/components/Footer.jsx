import {Box, ButtonGroup, Flex, IconButton, Link, Stack, Text,} from "@chakra-ui/react";
import {FaFacebook, FaGithub, FaInstagram, FaLinkedin, FaTwitter, FaWhatsapp} from "react-icons/fa";
import React from "react";
import Logo from "./Logo";

export default function Footer(props) {
    return (
      <Flex
        width={props.width ? props.width : "auto"}
        ml={{ base: 0, md: 60 }}
        px={[0, 5, 10]}
        minHeight="20vh"
        bgGradient="linear(to-r, fitdiary.900, fitdiary.400)"
        as="footer"
        mx="auto"
        direction={{ base: "column", md: "row" }}
        spacing={4}
        justify={{ base: "center", md: "space-between" }}
        align={{ base: "center", md: "center" }}
      >
        <Link href={"/"}>
          <Box
            transitionProperty="shadow"
            transitionDuration="1"
            transitionTimingFunction="ease-in-out"
            color="white"
            _hover={{ color: "fitdiary.300" }}
          >
            <Logo
              viewBox="-10 -50 250 250"
              color="currentColor"
              boxSize={{ base: "4em", md: "6em" }}
            />
          </Box>
        </Link>
        <Text color={"white"} mb={[0, 2]}>
          © 2023 FitDiary. All rights reserved
        </Text>
        <Stack
          direction={{ base: "column", lg: "row" }}
          spacing={{ base: "10", lg: "28" }}
        >
          <ButtonGroup colorScheme={"gray"}>
            <IconButton
              colorScheme={"twitter"}
              as="a"
              href="#"
              aria-label="Twitter"
              target="_blank"
              icon={<FaTwitter fontSize="20px" />}
            />
            <IconButton
              colorScheme={"whatsapp"}
              as="a"
              href="#"
              aria-label="Whatsapp"
              target="_blank"
              icon={<FaWhatsapp fontSize="20px" />}
            />
            <IconButton
              colorScheme={"blackAlpha"}
              as="a"
              href="https://github.com/fasanosalvatore/FitDiary"
              target="_blank"
              aria-label="Github"
              icon={<FaGithub fontSize="20px" />}
            />
            <IconButton
              bgGradient={"linear(to-r, orange.300, red.500)"}
              colorScheme={""}
              as="a"
              href="https://www.instagram.com/fitdiary_is"
              target="_blank"
              aria-label="Github"
              icon={<FaInstagram fontSize="20px" />}
            />
            <IconButton
              colorScheme={"facebook"}
              as="a"
              href="https://www.facebook.com/fitdiary21"
              target="_blank"
              aria-label="Facebook"
              icon={<FaFacebook />}
            />
            <IconButton
              colorScheme={"linkedin"}
              as="a"
              href="#"
              target="_blank"
              aria-label="Linkedin"
              icon={<FaLinkedin fontSize="20px" />}
            />
          </ButtonGroup>
        </Stack>
      </Flex>
    );
}
