import React, {useContext} from 'react';
import {
    Avatar,
    Box,
    CloseButton,
    Drawer,
    DrawerContent,
    Flex,
    HStack,
    Icon,
    IconButton,
    Link,
    Menu,
    MenuButton,
    MenuDivider,
    MenuItem,
    MenuList,
    Text,
    useColorModeValue,
    useDisclosure,
    VStack,
} from '@chakra-ui/react';
import {
    FiBell,
    FiBook,
    FiChevronDown,
    FiHome,
    FiMenu,
    FiSettings,
    FiTrendingUp,
    FiUser,
    FiUserPlus,
    FiUsers
} from 'react-icons/fi';
import Logo from "./Logo";
import {Link as ReactLink} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";

const navItems = [
    {
        name: 'Home',
        icon: FiHome,
        to: "/dashboard",
        allowedRoles: ['admin', 'cliente', 'preparatore']
    },
    {
        name: 'Profilo',
        icon: FiUser,
        to: "/profile",
        allowedRoles: ['admin', 'cliente', 'preparatore']
    },
    {
        name: 'Aggiungi Cliente',
        icon: FiUserPlus,
        to: "/customers/create",
        allowedRoles: ['admin', 'preparatore']
    },
    {
        name: 'Protocolli',
        icon: FiBook,
        to: "/protocols",
        allowedRoles: ['admin', 'cliente', 'preparatore']
    },
    {
        name: 'Progressi',
        icon: FiTrendingUp,
        to: "/reports",
        allowedRoles: ['admin', 'cliente']
    },
    {
        name: 'Clienti',
        icon: FiUsers,
        to: "/customers",
        allowedRoles: ['admin', 'preparatore']
    },
    {
        name: 'Impostazioni',
        icon: FiSettings,
        to: "/account",
        allowedRoles: ['admin', 'cliente', 'preparatore']
    },
    {
        name: 'Utenti',
        icon: FiUsers,
        to: "/users",
        allowedRoles: ['admin']
    }
];


const NavItem = ({navItem, ...rest}) => {
    return (
        <Link as={ReactLink} to={navItem.to} style={{textDecoration: 'none'}} textAlign={"center"}>
            <Flex
                textAlign={"left"}
                color="gray.700"
                fontWeight="bolder"
                align="center"
                p="4"
                mx="4"
                borderRadius="lg"
                role="group"
                cursor="pointer"
                _hover={{
                    bg: 'blue.500',
                    color: 'white',
                }}
                {...rest}>
                {navItem.icon && (
                    <Icon color="blue.500" fontWeight="bolder" mr="4" fontSize="20" _groupHover={{color: 'white',}}
                          as={navItem.icon}/>
                )}
                {navItem.name}
            </Flex>
        </Link>
    );
};

export default function SidebarWithHeader({children}) {
    const {isOpen, onOpen, onClose} = useDisclosure();
    return (
      <Box bg={useColorModeValue("blue.50", "gray.900")}>
        <SidebarContent
          h={"full"}
          onClose={() => onClose}
          display={{ base: "none", md: "block" }}
        />
        <Drawer
          autoFocus={false}
          isOpen={isOpen}
          placement="left"
          onClose={onClose}
          returnFocusOnClose={false}
          onOverlayClick={onClose}
          size="full"
        >
          <DrawerContent>
            <SidebarContent onClose={onClose} />
          </DrawerContent>
        </Drawer>
        {/* mobilenav */}
        <MobileNav minHeight="10vh" onOpen={onOpen} />
        <Box minHeight="70vh" ml={{ base: 0, md: 60 }} bg={"blue.50"}>
          {children}
        </Box>
      </Box>
    );
}

const NavItemContainer = ({children}) => (
    <div>{children}</div>
);

const SidebarContent = ({onClose, ...rest}) => {
    const authContext = useContext(AuthContext);
    const {authState} = authContext;
    return (
        <Box
            transition="3s ease"
            bg={useColorModeValue('white', 'gray.900')}
            borderRight="1px"
            borderRightColor={useColorModeValue('gray.200', 'gray.700')}
            w={{base: 'full', md: 60}}
            pos="fixed"
            h="full"
            {...rest}>
            <Flex h="20" alignItems="center" mx="8" justifyContent={{base: "space-evenly", md: "center"}}>
                <Link href={"/"}>
                    <Text fontSize="8xl" fontFamily="monospace" fontWeight="bold" color={"blue.500"}>
                        <Logo penColor="black" viewBox={"0 -35 250 250"}/>
                    </Text>
                </Link>
                <CloseButton display={{base: 'flex', md: 'none'}} onClick={onClose}/>
            </Flex>
            <Box pt={5}>
                {navItems.map((item) => (
                    <NavItemContainer key={item.name}>
                        {item.allowedRoles.includes(authState.userInfo.roles[0].toLowerCase()) && (
                            <NavItem key={item} navItem={item}/>
                        )}
                    </NavItemContainer>
                ))}
            </Box>
        </Box>
    );
};

const MobileNav = ({onOpen, ...rest}) => {
    const authContext = useContext(AuthContext);
    const {authState} = authContext;
    return (
      <Flex
        bg={"white"}
        ml={{ base: 0, md: 60 }}
        px={{ base: 4, md: 4 }}
        height="20"
        alignItems="center"
        borderBottomWidth="1px"
        justifyContent={{ base: "space-between", md: "flex-end" }}
        {...rest}
      >
        <IconButton
          display={{ base: "flex", md: "none" }}
          onClick={onOpen}
          variant="outline"
          aria-label="open menu"
          icon={<FiMenu />}
        />
        <Text
          display={{ base: "flex", md: "none" }}
          fontSize="2xl"
          fontFamily="monospace"
          fontWeight="bold"
        >
          <Logo penColor="black" viewBox={"0 -35 250 250"} boxSize="3em" />
        </Text>
        <HStack spacing={{ base: "0", md: "6" }}>
          <IconButton
            color={"blue.500"}
            size="lg"
            variant="ghost"
            aria-label="open menu"
            icon={<FiBell />}
          />
          <Flex alignItems={"center"}>
            <Menu>
              <MenuButton
                py={2}
                transition="all 0.3s"
                _focus={{ boxShadow: "none" }}
              >
                <HStack>
                  <Avatar size={"sm"} />
                  <VStack
                    display={{ base: "none", md: "flex" }}
                    alignItems="flex-start"
                    spacing="1px"
                    ml="2"
                  >
                    <Text fontSize="sm">
                      {authState.userInfo.name} {authState.userInfo.surname}
                    </Text>
                    <Text fontSize="xs" color="gray.600">
                      {authState.userInfo.roles[0] || ""}
                    </Text>
                  </VStack>
                  <Box display={{ base: "none", md: "flex" }}>
                    <FiChevronDown />
                  </Box>
                </HStack>
              </MenuButton>
              <MenuList>
                <ReactLink to={"/profile"}>
                  <MenuItem>Profile</MenuItem>
                </ReactLink>
                <ReactLink to={"/account"}>
                  <MenuItem>Settings</MenuItem>
                </ReactLink>
                <MenuDivider />
                <ReactLink to={"/logout"}>
                  <MenuItem>Logout</MenuItem>
                </ReactLink>
              </MenuList>
            </Menu>
          </Flex>
        </HStack>
      </Flex>
    );
};