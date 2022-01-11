import {
    Box,
    Button,
    FormControl, FormErrorMessage,
    FormLabel,
    GridItem,
    Heading,
    HStack, Image,
    Input,
    SimpleGrid,
    Text,
    useToast,
    VStack
} from "@chakra-ui/react";
import {useForm} from "react-hook-form"
import React, {useCallback, useContext, useEffect, useState} from "react";
import {useDropzone} from 'react-dropzone';
import {FetchContext} from "../../context/FetchContext";
import {CloseIcon} from "@chakra-ui/icons";
import {GradientBar} from "../../components/GradientBar";

const urlReports = "reports";

const Create = () => {
    const fetchContext = useContext(FetchContext);
    const [selectedFileImages, setselectedFileImages] = useState([]);
    const {register, handleSubmit,setValue, formState: {errors, isSubmitting}} = useForm();
    const toast = useToast({
        duration: 3000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })

    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }

    const onDropImages = useCallback(acceptedFileImages => {
        console.log(acceptedFileImages)
        setValue("images", acceptedFileImages);
    }, [setValue])
    const {
        acceptedFiles: acceptedFileImages,
        getRootProps: getRootPropsImages,
        getInputProps: getInputPropsImages,
        isDragActive: isDragActiveImages
    } = useDropzone({onDrop: onDropImages, maxFiles: 10})

    const onSubmit = async (values) => {
        const formData = new FormData();
        console.log(values);
        formData.append("peso", values.peso)
        formData.append("idProtocollo", values.idProtocollo)
        formData.append("crfBicipite", values.crfBicipite)
        formData.append("crfAddome", values.crfAddome)
        formData.append("crfQuadricipite", values.crfQuadricipite)

        if (values.images)
            for(let i=0;i<values.images.length;i++)
                formData.append("immagini", values.images[i])
        try {
            const {data} = await fetchContext.authAxios.post(urlReports, formData)
            console.log(data);
            toast(toastParam("Creato!", "Report inserito correttamente", data.status))
        } catch (error) {
            console.log(error.response);
            toast(toastParam("Errore", error.response.data.data, "error"))
        }

    }

    useEffect(() => {
        var arrayImages= []
        for(let i=0;i<acceptedFileImages.length;i++){
            arrayImages.push(acceptedFileImages[i]);
        }
        setselectedFileImages(arrayImages);
    }, [acceptedFileImages]);

    return (
        <>
            <VStack w="full" h="full" p={[5, 10, 20]}>
                <Box bg={"white"} borderRadius='xl' pb={5} w={"full"}>
                    <GradientBar/>
                    <VStack spacing={3} alignItems="center" pb={5} mt={5}>
                        <Heading size="2xl">Inserisci report</Heading>
                        <form onSubmit={handleSubmit(onSubmit)} style={{width: "100%"}}>
                            <SimpleGrid columns={2} columnGap={5} rowGap={5} pl={[0, 5, 10]} pr={[0, 5, 10]} w="full">
                                <GridItem colSpan={2}>
                                    <FormControl id={"peso"} isInvalid={errors.peso} pt={5}>
                                        <FormLabel htmlFor="peso">Peso</FormLabel>
                                        <Input type="text" placeholder="70,50" {...register("peso", {
                                            required: "Il peso è obbligatorio",
                                            maxLength: {value: 8, message: "valore troppo lungo"},
                                            pattern: {value: /^[0-9]+(,[0-9]+)*$/i, message: "Formato peso non valido"}
                                        })} />
                                        <FormErrorMessage>{errors.peso && errors.peso.message}</FormErrorMessage>
                                    </FormControl>
                                </GridItem>
                                <GridItem colSpan={2}>
                                    <FormControl id={"crfBicipite"} isInvalid={errors.crfBicipite} pt={5}>
                                        <FormLabel htmlFor="crfBicipite">Circonferenza Bicipite</FormLabel>
                                        <Input type="text" placeholder="123" {...register("crfBicipite", {
                                            required: "La Circonferenza Bicipite è obbligatoria",
                                            maxLength: {value: 8, message: "valore troppo lungo"},
                                            pattern: {value: /^[0-9]+(,[0-9]+)*$/i, message: "Formato Circonferenza Bicipite non valida"}
                                        })} />
                                        <FormErrorMessage>{errors.crfBicipite && errors.crfBicipite.message}</FormErrorMessage>
                                    </FormControl>
                                </GridItem>
                                <GridItem colSpan={2}>
                                    <FormControl id={"crfAddome"} isInvalid={errors.crfAddome} pt={5}>
                                        <FormLabel htmlFor="crfAddome">Circonferenza Addome</FormLabel>
                                        <Input type="text" placeholder="123" {...register("crfAddome", {
                                            required: "La Circonferenza Addome è obbligatoria",
                                            maxLength: {value: 8, message: "valore troppo lungo"},
                                            pattern: {value: /^[0-9]+(,[0-9]+)*$/i, message: "Formato Circonferenza Addome non valida"}
                                        })} />
                                        <FormErrorMessage>{errors.crfAddome && errors.crfAddome.message}</FormErrorMessage>
                                    </FormControl>
                                </GridItem>
                                <GridItem colSpan={2}>
                                    <FormControl id={"crfQuadricipite"} isInvalid={errors.crfQuadricipite} pt={5}>
                                        <FormLabel htmlFor="crfQuadricipite">Circonferenza Quadricipite</FormLabel>
                                        <Input type="text" placeholder="123" {...register("crfQuadricipite", {
                                            required: "La Circonferenza Quadricipite è obbligatoria",
                                            maxLength: {value: 8, message: "valore troppo lungo"},
                                            pattern: {value: /^[0-9]+(,[0-9]+)*$/i, message: "Formato Circonferenza Quadricipite non valida"}
                                        })} />
                                        <FormErrorMessage>{errors.crfQuadricipite && errors.crfQuadricipite.message}</FormErrorMessage>
                                    </FormControl>
                                </GridItem>
                                <GridItem colSpan={2}>
                                    <FormControl id={"images"}>
                                        <FormLabel>Immagini</FormLabel>

                                        {selectedFileImages.length > 0 && (
                                            <VStack>
                                            <HStack pb={3}>
                                                <Button color={"white"} backgroundColor={"red"} value={"Rimuovi immagini"} onClick={() => {
                                                    console.log(acceptedFileImages)
                                                    setselectedFileImages([]);
                                                    setValue("images", []);
                                                }}>
                                                Rimuovi immagini</Button>
                                            </HStack>
                                            <SimpleGrid columns={[1,1,1,2,2,3]} columnGap={2} rowGap={4} pl={[0,0,0,2,2,5]} pr={[0,0,0,2,2,5]} w="full">

                                                {
                                                    selectedFileImages.map((f, i) => {
                                                        return (
                                                    <HStack key={i} justifyContent={"center"}>
                                                        <GridItem  >
                                                            <Image src={URL.createObjectURL(f)} h={200} w={300} ></Image>
                                                        </GridItem>
                                                    </HStack>
                                                )})}
                                            </SimpleGrid>
                                            </VStack>
                                            )}
                                        {selectedFileImages.length == 0 && (
                                            <div {...getRootPropsImages()}>
                                                <Box w={"full"} bg={"gray.50"} p={5} border={"dotted"}
                                                     borderColor={"gray.200"}>
                                                    <Input {...getInputPropsImages()}/>
                                                    {
                                                        isDragActiveImages ?
                                                            <p style={{color: "gray", textAlign: "center"}}>
                                                                Lascia il file qui ...
                                                            </p> :
                                                            <p style={{color: "gray", textAlign: "center"}}>
                                                                Clicca e trascina un file qui, oppure clicca per
                                                                selezionare un file
                                                            </p>
                                                    }
                                                </Box>
                                            </div>
                                        )}
                                    </FormControl>
                                </GridItem>
                                <GridItem colSpan={2} >
                                    <Button type={"submit"} w={"full"} bg={"blue.500"} color={"white"}>Inserisci </Button>
                                </GridItem>
                            </SimpleGrid>
                        </form>
                    </VStack>
                </Box>
            </VStack>
        </>
    )
}

export default Create;
