import React, {useEffect, useState} from 'react'
import Menu from "./component/menu/Menu";
import {getAccountData, save} from "./domain/repository/AccountRepository";
import {getMessages, getMessagesFor} from "./domain/repository/MessagesRepository";
import {Container, Paper, ThemeProvider} from "@mui/material";
import FormButton from "./component/form/FormButton";
import FormInputTextField from "./component/form/FormInputTextField";
import Separator from "./component/form/Separator";
import {createTheme} from '@mui/material/styles';
import CheckIcon from '@mui/icons-material/Check';
import FormDatePicker, {DateFormatPattern} from "./component/form/FormDatePicker";
import moment from "moment";

const links = {
    logOut: "/account/oidc_logout.html",
    home: "/family-budget/index"
};

const AccountDetailsPage = () => {

    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [phone, setPhone] = useState("")
    const [birthDate, setBirthDate] = useState("")
    const [mail, setMail] = useState("")

    const [messageRegistry, setMessageRegistry] = useState([])


    useEffect(() => {
        getAccountData().then(data => {
            setFirstName(data.firstName);
            setLastName(data.lastName);
            setPhone(data.phone);
            setBirthDate(data.birthDate);
            setMail(data.mail);
        })
    }, [])

    useEffect(() => {
        getMessages()
            .then(data => setMessageRegistry((data)))
    }, [])

    const padding = "10px"
    let theme = createTheme({
        formInputText: {
            padding: padding
        },
        formButton: {
            padding: padding
        },
        formDatePicker: {
            padding: padding
        },
        palette: {
            primary: {
                main: '#252624',
                contrastText: '#fff',
            },
            neutral: {
                main: '#64748B',
                contrastText: '#fff',
            },
        },
    });

    return <ThemeProvider theme={theme}>
        <Paper variant="outlined">
            <Menu messages={{
                title: getMessagesFor(messageRegistry, "common.title"),
                logOutLabel: getMessagesFor(messageRegistry, "logout.label")
            }} links={links}></Menu>

            <Container>
                <FormInputTextField id="firstName"
                                    label={getMessagesFor(messageRegistry, "form.firstName.label")}
                                    required={true}
                                    handler={(value) => {
                                        setFirstName(value.target.value)
                                    }}
                                    value={firstName || ""}/>

                <FormInputTextField id="lastName"
                                    label={getMessagesFor(messageRegistry, "form.lastName.label")}
                                    required={true}
                                    handler={(value) => {
                                        setLastName(value.target.value)
                                    }}
                                    value={lastName || ""}/>

                <FormDatePicker
                    value={moment(birthDate, DateFormatPattern)}
                    onClickHandler={(value) => {
                        setBirthDate(value.format(DateFormatPattern))
                    }}
                    label={getMessagesFor(messageRegistry, "form.birthDate.label")}/>

                <FormInputTextField id="phone"
                                    label={getMessagesFor(messageRegistry, "form.phone.label")}
                                    required={true}
                                    handler={(value) => {
                                        setPhone(value.target.value)
                                    }}
                                    value={phone || ""}/>

                <FormInputTextField id="mail"
                                    label={getMessagesFor(messageRegistry, "form.mail.label")}
                                    required={true}
                                    disabled={true}
                                    handler={(value) => {
                                        setMail(value.target.value)
                                    }}
                                    value={mail || ""}/>

                <Separator/>

                <FormButton type="button"
                            onClickHandler={() => {
                                save({
                                    "email": mail,
                                    "firstName": firstName,
                                    "lastName": lastName,
                                    "phone": phone,
                                    "birthDate": birthDate
                                })
                            }}
                            labelPrefix={<CheckIcon fontSize="large"/>}
                            label={getMessagesFor(messageRegistry, "form.save.value")}/>

            </Container>
        </Paper>
    </ThemeProvider>
}

export default AccountDetailsPage