import React from "react";
import {Button, Grid, TextField} from "@mui/material";
import DesktopDatePicker from "@mui/x-date-pickers"

export default function FormDatePicker({label, value, onClickHandler}) {
    return <Grid container alignItems="flex-end" style={{marginTop: '10px'}}>
        <Grid item md={true} sm={true} xs={true} justify="flex-end">
            <DesktopDatePicker
                label={label}
                inputFormat="dd/MM/yyyy"
                value={value}
                onClick={onClickHandler || {}}
                renderInput={(params) => <TextField {...params} />}
            />
        </Grid>
    </Grid>
}