import React, { ReactNode } from 'react';
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
    BoxProps,
    FlexProps,
    Menu,
    MenuButton,
    MenuDivider,
    MenuItem,
    MenuList, createIcon,
} from '@chakra-ui/react';
import {
    FiHome,
    FiTrendingUp,
    FiCompass,
    FiStar,
    FiSettings,
    FiMenu,
    FiBell,
    FiChevronDown,
} from 'react-icons/fi';
import { IconType } from 'react-icons';
import { ReactText } from 'react';
import {BiExit} from "react-icons/bi";
import authService from "../services/auth.service";

export const Logo = createIcon({
    displayName: 'Logo',
    viewBox: '0 0 250 200',
    // path can also be an array of elements, if you have multiple paths, lines, shapes, etc.
    path: (
        <g id="Layer_2" data-name="Layer 2" fill="currentColor">
            <g id="Layer_1-2" data-name="Layer 1">
                <polygon className="cls-1"
                         points="180.27 87.64 147.29 87.64 163.53 111.14 164.31 112.27 183.32 139.79 56.71 139.79 5.77 166.93 235.01 166.93 180.27 87.64"/>
                <polygon className="cls-1"
                         points="180.27 87.64 147.29 87.64 133.46 67.62 133.43 67.64 119.87 47.86 119.87 0.03 152.13 46.95 152.16 46.92 180.27 87.64"/>
                <polygon className="cls-1" points="164.66 112.27 81.21 112.27 97.37 87.64 147.29 87.64 164.66 112.27"/>
                <path className="cls-2" d="M82.36,37.16,108,0V47.8l-6.64,9.63C95.37,51.08,88.27,43.5,82.36,37.16Z"/>
                <path fill="currentColor" className="cls-2"
                      d="M74.91,47.75,0,156.54l52.11-27.76,12.14-17.64,16.16-23.5L93.9,68C87.92,61.67,80.81,54.1,74.91,47.75Z"/>
                <polygon className="cls-2" points="59.77 87.64 80.41 87.64 64.25 111.14 59.77 111.14 59.77 87.64"/>
                <polygon className="cls-1" points="180.27 87.64 180.27 111.14 163.53 111.14 147.29 87.64 180.27 87.64"/>
            </g>
        </g>
    ),
})
const user=authService.getCurrentUser();
console.log(user);

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
            <Flex h="20" alignItems="center" mx="8" justifyContent="space-between">
                <Text fontSize="7xl" fontFamily="monospace" fontWeight="bold" color={"blue.500"}>
                    <Logo/>
                </Text>
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
        <Link href={to} style={{ textDecoration: 'none' }}>
            <Flex
                align="center"
                p="4"
                mx="4"
                borderRadius="lg"
                role="group"
                cursor="pointer"
                _hover={{
                    bg: 'cyan.400',
                    color: 'white',
                }}
                {...rest}>
                {icon && (
                    <Icon
                        mr="4"
                        fontSize="16"
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