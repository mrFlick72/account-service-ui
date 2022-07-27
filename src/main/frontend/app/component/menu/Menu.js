import React from "react"
import {AppBar, Box, Button, IconButton, Toolbar, Typography, useTheme} from "@mui/material";
import LogoutIcon from '@mui/icons-material/Logout';
import MenuIcon from '@mui/icons-material/Menu';


const Menu = ({links, messages}) => {
    let theme = useTheme()

    return <Box sx={{flexGrow: 1}}>
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
                    <a href={links.home} style={{
                        "text-decoration": "none",
                        "color": theme.palette.primary.contrastText
                    }}>{messages.title}</a>
                </Typography>
                <form action={links.logOut} method="GET">
                    <Button color="inherit" type="submit">
                        {messages.logOutLabel} <LogoutIcon/>
                    </Button>
                </form>
            </Toolbar>
        </AppBar>
    </Box>


}

export default Menu