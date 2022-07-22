import React from "react"
import {AppBar, Box, Button, IconButton, Link, Toolbar, Typography} from "@mui/material";
import LogoutIcon from '@mui/icons-material/Logout';
import MenuIcon from '@mui/icons-material/Menu';

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
                    <Link href={links.home}>{messages.title}</Link>
                </Typography>
                <form action={links.logOut} method="GET">
                    <Button color="inherit" type="submit">
                        {messages.logOutLabel} <LogoutIcon/>
                    </Button>
                </form>
            </Toolbar>
        </AppBar>
    </Box>
);