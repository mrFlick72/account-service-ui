import React from "react";
import {Button, Grid} from "@mui/material";
import {useTheme} from "@emotion/react";

export default function FormButton({labelPrefix, label, type, onClickHandler, direction}) {
    const theme = useTheme()
    return <div dir={direction || ""}>
            <Grid container alignItems="flex-end" style={theme.formButton}>
                <Grid item md={true} sm={true} xs={true} justify="flex-end">
                    <Button type={type || "button"}
                            variant="outlined"
                            color="primary"
                            onClick={onClickHandler || {}}
                            style={{textTransform: "none"}}>
                        {labelPrefix} {label}
                    </Button>
                </Grid>
            </Grid>
        </div>
}