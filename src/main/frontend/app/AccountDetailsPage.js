import React, {useEffect, useState} from 'react'
import Menu from "./component/menu/Menu";
import {getAccountData, save} from "./domain/repository/AccountRepository";
import {getMessages, getMessagesFor} from "./domain/repository/MessagesRepository";
import {Container, Paper} from "@mui/material";
import FormButton from "./component/form/FormButton";
import FormInputTextField from "./component/form/FormInputTextField";
import Separator from "./component/form/Separator";
// import {useTheme} from '@mui/material/styles';
import CheckIcon from '@mui/icons-material/Check';
import FormDatePicker from "./component/form/FormDatePicker";

const links = {
    logOut: "/account/oidc_logout.html",
    home: "/family-budget/index"
};
/*

class AccountDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        state = {
            messageRegistry: [],
            firstName: "",
            lastName: "",
            phone: "",
            birthDate: "",
            mail: ""
        };

        save = save.bind(this);
        accountRepository = new AccountRepository();
        messageRepository = new MessageRepository();

        firstNameInputRef = React.createRef();
        lastNameInputRef = React.createRef();
        phoneInputRef = React.createRef();
        birthDateInputRef = React.createRef();
        mailInputRef = React.createRef();
    }

    save() {
        let account = {
            firstName: firstNameInputRef.current.value,
            lastName: lastNameInputRef.current.value,
            phone: phoneInputRef.current.value,
            birthDate: birthDateInputRef.current.value
        };

        accountRepository.save(account);
    }

    componentDidMount() {
        accountRepository.getAccountData()
            .then((data) => {
                firstNameInputRef.current.value = data.firstName;
                lastNameInputRef.current.value = data.lastName;
                phoneInputRef.current.value = data.phone;
                birthDateInputRef.current.value = data.birthDate;
                mailInputRef.current.value = data.mail;
            })

        getMessages()
            .then((data) => {
                setState({messageRegistry: data})
            })
    }

    render() {
        // const theme = useTheme();

        return (
            <Paper variant="outlined">
                <Menu messages={{
                          title: getMessagesFor(messageRegistry, "common.title"),
                          logOutLabel: getMessagesFor(messageRegistry, "logout.label")
                      }} links={links}></Menu>

                <div className="container">
                    <div className="content">
                        <div className="form-group">
                            <label
                                htmlFor="firstName">{getMessagesFor(messageRegistry, "form.firstName.label")} </label>
                            <input type="text" className="form-control" ref={firstNameInputRef}
                                   id="firstName"
                                   placeholder={getMessagesFor(messageRegistry, "form.firstName.placeholder")}/>
                        </div>
                        <div className="form-group">
                            <label
                                htmlFor="lastName">{getMessagesFor(messageRegistry, "form.lastName.label")}</label>
                            <input type="text" className="form-control" ref={lastNameInputRef}
                                   id="lastName"
                                   placeholder={getMessagesFor(messageRegistry, "form.lastName.placeholder")}/>
                        </div>

                        <div className="form-group">
                            <label
                                htmlFor="birthDate">{getMessagesFor(messageRegistry, "form.birthDate.label")}</label>
                            {/!*      <DatePicker inputProps={{id: "birthDate", ref: birthDateInputRef}}
                                        input={true}
                                        closeOnSelect={true}
                                        dateFormat="DD/MM/YYYY"
                                        isValidDate={() => true}
                                        timeFormat={false}/>*!/}
                        </div>

                        <div className="form-group">
                            <label
                                htmlFor="phone">{getMessagesFor(messageRegistry, "form.phone.label")}</label>
                            <input type="text" className="form-control" ref={phoneInputRef}
                                   id="phone"
                                   placeholder={getMessagesFor(messageRegistry, "form.phone.placeholder")}/>
                        </div>

                        <div className="form-group">
                            <label
                                htmlFor="mail">{getMessagesFor(messageRegistry, "form.mail.label")}</label>
                            <input type="text" className="form-control" ref={mailInputRef}
                                   id="mail"
                                   placeholder={getMessagesFor(messageRegistry, "form.mail.placeholder")}
                                   readOnly="readonly"/>
                        </div>

                        <div className="form-group">

                            <button type="submit" className="btn btn-success" onClick={save}>
                                <i className="fas fa-check fa-lg"></i> {getMessagesFor(messageRegistry, "form.save.value")}
                            </button>
                        </div>
                    </div>
                </div>
            </Paper>
        )
    }
}
*/

const AccountDetailsPage = () => {

    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [phone, setPhone] = useState("")
    const [birthDate, setBirthDate] = useState("")
    const [mail, setMail] = useState("")

    const [messageRegistry, setMessageRegistry] = useState([])


    useEffect(() => {
        getAccountData().then(data => {
            console.log("resetData")
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

    return <Paper variant="outlined">
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

            {/* <FormDatePicker
                value={birthDate}
                onClickHandler={(value) => {
                    setBirthDate(value.target.value)
                }}
                label={getMessagesFor(messageRegistry, "form.birthDate.label")}/>
            */}
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
}

export default AccountDetailsPage