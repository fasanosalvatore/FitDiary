import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogContent,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Button,
} from "@chakra-ui/react";
import React from "react";

export function Alert(props) {
    const [isAlertOpen, setIsAlertOpen] = React.useState(false)
    const onAlertClose = () => setIsAlertOpen(false)
    const wrapperFunction = () => {
        props.onClick();
        onAlertClose();
    }
    console.log(props);
    return (
        <>
            <Button onClick={() => setIsAlertOpen(true)} colorScheme={props.buttonCancel ? props.buttonColor : "red"}>{props.buttonOk ? props.buttonOk : "Delete"}</Button>
            <AlertDialog
                isOpen={isAlertOpen}
                leastDestructiveRef={props.leastDestructiveRef}
                onClose={onAlertClose}
            >
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize="lg" fontWeight="bold">
                            {props.title ? props.title : "Delete"}
                        </AlertDialogHeader>

                        <AlertDialogBody>
                            {props.body ? props.body : "Are you sure? You can't undo this action afterwards."}
                        </AlertDialogBody>

                        <AlertDialogFooter>
                            <Button ref={props.leastDestructiveRef} onClick={onAlertClose}>
                                {props.buttonCancel ? props.buttonCancel : "Cancel"}
                            </Button>
                            <Button colorScheme={props.buttonCancel ? props.buttonColor : "red"} onClick={wrapperFunction}
                                    ml={3}>
                                {props.buttonOk ? props.buttonOk : "Delete"}
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </AlertDialog>
        </>
    );
}