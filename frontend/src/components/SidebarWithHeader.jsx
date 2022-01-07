import React from 'react';
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
    FiCompass,
    FiSettings,
    FiMenu,
    FiBell,
    FiChevronDown,
} from 'react-icons/fi';
import {BiExit} from "react-icons/bi";
import authService from "../services/auth.service";
import Logo from "./Logo";

const user=authService.getCurrentUser();

const LinkItems = [
    { name: 'Home', icon: FiHome, to: "/" },
    { name: 'Registrati', icon: FiCompass, to: "/signup" },
    { name: 'Profilo', icon: FiSettings, to: "/customer/me" },
    { name: 'test', icon: FiSettings, to: "/test" },
    { name: user ? 'Logout' : 'Login',icon:user ? BiExit : FiTrendingUp,to: user? "/logout" : "/login"}
];

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
            <Box ml={{ base: 0, md: 60 }} p="4">
                {children}
            </Box>
        </Box>
    );
}


const SidebarContent = ({ onClose, ...rest }) => {
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
            {LinkItems.map((link) => (

                <NavItem key={link.name} icon={link.icon} to={link.to}>
                    {link.name}

                </NavItem>
            ))}
        </Box>
    );
};

const NavItem = ({ icon, to, children, ...rest }) => {
    return (
        <Link href={to} style={{ textDecoration: 'none' }} textAlign={"center"}>
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
                {icon && (
                    <Icon
                        color="blue.500"
                        fontWeight="bolder"
                        mr="4"
                        fontSize="20"
                        _groupHover={{
                            color: 'white',
                        }}
                        as={icon}
                    />
                )}
                {children}
            </Flex>
        </Link>
    );
};

const MobileNav = ({ onOpen, ...rest }) => {
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
                    {!user ? <Text>Login</Text> :
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
                                    <Text fontSize="sm">{user.userInfo.name} {user.userInfo.surname}</Text>
                                    <Text fontSize="xs" color="gray.600">
                                        {user.userInfo.roles[0]}
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
                            <MenuItem>Sign out </MenuItem>
                        </MenuList>
                    </Menu> }
                </Flex>
            </HStack>
        </Flex>
    );
};