import React from "react"
import {AppBar, Box, Button, IconButton, ThemeProvider, Toolbar, Typography} from "@mui/material";
import LogoutIcon from '@mui/icons-material/Logout';
import MenuIcon from '@mui/icons-material/Menu';

// <ThemeProvider theme={theme}>    </ThemeProvider>
export default ({links, messages}) => (
    <Box sx={{flexGrow: 1}}>
        <AppBar position="static">
            <Toolbar>
                <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    sx={{mr: 2}}>
                    <MenuIcon/>
                </IconButton>
                <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                    {messages.title}
                </Typography>
                <form action={links.logOut} method="GET">
                    <Button color="inherit" type="submit">
                        {messages.logOutLabel} <LogoutIcon/>
                    </Button>
                </form>
            </Toolbar>
        </AppBar>
    </Box>



    // <div className="container-fluid" style={{"margin-bottom": "15px"}}>
    //     <nav className="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
    //         <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navBar"
    //                 aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
    //             <span className="navbar-toggler-icon"></span>
    //         </button>
    //         <div className="collapse navbar-collapse" id="navBar">
    //             <a className="navbar-brand" href={links.home}>{messages.title}</a>
    //
    //             <ul className="navbar-nav ml-auto mt-2 mt-lg-0">
    //             </ul>
    //             <form action={links.logOut} method="GET">
    //                 <button type="submit" className="btn btn-secondary"> {messages.logOutLabel}
    //                     <i className="fas fa-sign-out-alt fs-lg"></i></button>
    //             </form>
    //         </div>
    //     </nav>
    // </div>
);