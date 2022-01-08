import React, {useContext} from 'react';
import {
    IconButton,
    Avatar,
    Box,
    CloseButton,
    Flex,
    HStack,
    VStack,
    Icon,
    useColorModeValue,
    Link,
    Drawer,
    DrawerContent,
    Text,
    useDisclosure,
    Menu,
    MenuButton,
    MenuDivider,
    MenuItem,
    MenuList,
} from '@chakra-ui/react';
import {
    FiHome,
    FiTrendingUp,
    FiSettings,
    FiMenu,
    FiBell,
    FiChevronDown, FiUser, FiBook, FiUsers, FiPlus, FiUserPlus
} from 'react-icons/fi';
import Logo from "./Logo";
import {Link as ReactLink} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";

const navItems = [
    {
        name: 'Home',
        icon: FiHome,
        to: "/",
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
        to: "/trainer/addCustomer",
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
        <Link as={ReactLink} to={navItem.to} style={{ textDecoration: 'none' }} textAlign={"center"}>
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
                    <Icon color="blue.500" fontWeight="bolder" mr="4" fontSize="20" _groupHover={{ color: 'white',}} as={navItem.icon} />
                )}
                {navItem.name}
            </Flex>
        </Link>
    );
};

export default function SidebarWithHeader({children}) {
    const { isOpen, onOpen, onClose } = useDisclosure();

    return (
        <Box minH="100vh" bg={useColorModeValue('white', 'gray.900')}>
            <SidebarContent
                onClose={() => onClose}
                display={{ base: 'none', md: 'block' }}
            />
            <Drawer
                autoFocus={false}
                isOpen={isOpen}
                placement="left"
                onClose={onClose}
                returnFocusOnClose={false}
                onOverlayClick={onClose}
                size="full">
                <DrawerContent>
                    <SidebarContent onClose={onClose} />
                </DrawerContent>
            </Drawer>
            {/* mobilenav */}
            <MobileNav onOpen={onOpen} />
            <Box ml={{ base: 0, md: 60 }}>
                {children}
            </Box>
        </Box>
    );
}

const NavItemContainer = ({children}) => (
    <div>{children}</div>
);

const SidebarContent = ({ onClose, ...rest }) => {
    const authContext = useContext(AuthContext);
    const { authState } = authContext;
    return (
        <Box
            transition="3s ease"
            bg={useColorModeValue('white', 'gray.900')}
            borderRight="1px"
            borderRightColor={useColorModeValue('gray.200', 'gray.700')}
            w={{ base: 'full', md: 60 }}
            pos="fixed"
            h="full"
            {...rest}>
            <Flex h="20" alignItems="center"  mx="8" justifyContent={{base:"space-evenly",md:"center"}}>
                <Link href={"/"}>
                    <Text fontSize="8xl" fontFamily="monospace" fontWeight="bold" color={"blue.500"} >
                        <Logo/>
                    </Text>
                </Link>
                <CloseButton display={{ base: 'flex', md: 'none' }} onClick={onClose} />
            </Flex>
            {navItems.map((item) => (
                <NavItemContainer key={item.name}>
                    {item.allowedRoles.includes(authState.userInfo.roles[0].toLowerCase()) && (
                        <NavItem key={item} navItem={item}/>
                    )}
                </NavItemContainer>
            ))}
        </Box>
    );
};

const MobileNav = ({ onOpen, ...rest }) => {
    const authContext = useContext(AuthContext);
    const { authState } = authContext;
    return (
        <Flex
            ml={{ base: 0, md: 60 }}
            px={{ base: 4, md: 4 }}
            height="20"
            alignItems="center"
            borderBottomWidth="1px"
            justifyContent={{ base: 'space-between', md: 'flex-end' }}
            {...rest}>
            <IconButton
                display={{ base: 'flex', md: 'none' }}
                onClick={onOpen}
                variant="outline"
                aria-label="open menu"
                icon={<FiMenu />}
            />
            <Text
                display={{ base: 'flex', md: 'none' }}
                fontSize="2xl"
                fontFamily="monospace"
                fontWeight="bold">
                Logo
            </Text>
            <HStack spacing={{ base: '0', md: '6' }}>
                <IconButton
                    color={"blue.500"}
                    size="lg"
                    variant="ghost"
                    aria-label="open menu"
                    icon={<FiBell />}
                />
                <Flex alignItems={'center'}>
                    <Menu>
                        <MenuButton
                            py={2}
                            transition="all 0.3s"
                            _focus={{ boxShadow: 'none' }}>
                            <HStack>
                                <Avatar size={'sm'} />
                                 <VStack
                                    display={{base: 'none', md: 'flex'}}
                                    alignItems="flex-start"
                                    spacing="1px"
                                    ml="2">
                                    <Text fontSize="sm">{authState.userInfo.name} {authState.userInfo.surname}</Text>
                                    <Text fontSize="xs" color="gray.600">
                                        {authState.userInfo.roles[0] || "" }
                                    </Text>
                                </VStack>
                                <Box display={{ base: 'none', md: 'flex' }}>
                                    <FiChevronDown />
                                </Box>
                            </HStack>
                        </MenuButton>
                        <MenuList>
                            <MenuItem>Profile</MenuItem>
                            <MenuItem>Settings</MenuItem>
                            <MenuItem>Billing</MenuItem>
                            <MenuDivider />
                            <MenuItem><Link href={"/logout"}>Logout</Link></MenuItem>
                        </MenuList>
                    </Menu>
                </Flex>
            </HStack>
        </Flex>
    );
};