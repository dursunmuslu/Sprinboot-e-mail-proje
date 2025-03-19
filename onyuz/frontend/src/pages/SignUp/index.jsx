import {useEffect, useMemo, useState} from "react";
import axios from "axios";
import {signUp} from "./api.jsx"
import {Input} from "./component/input.jsx";

export function SignUp() {

    const [username, setUsername] = useState()
    const [email, setEmail] = useState()
    const [password, setPassword] = useState()
    const [passwordRepeat, setPasswordRepeat] = useState()
    const [apiProgress, setApiProgress] = useState(false);
    const [successMessage, setSuccessMessage] = useState();
    const [errors, setErrors] = useState({})
    const [generalError, setGeneralError] = useState();

    useEffect(() => {
        setErrors(function (lastErrors){
            return{
                ...lastErrors,
                username: undefined
            }
        });
    }, [username])


    useEffect(() => {
        setErrors(function (lastErrors){
            return{
                ...lastErrors,
                email: undefined
            }
        });
    }, [email])

    useEffect(() => {
        setErrors(function (lastErrors){
            return{
                ...lastErrors,
                password: undefined
            }
        });
    }, [password])



    const onSubmit = async (event) => {
        event.preventDefault();
        setSuccessMessage();
        setGeneralError();
        setApiProgress(true);

        try {
            const response = await signUp({
                username: username,
                email: email,
                password: password,
            })
            setSuccessMessage(response.data.message);
        } catch (axiosError){
            if (axiosError.response?.data && axiosError.response.data.status === 400) {
                setErrors(axiosError.response.data.validationErrors);
            }else{
                setGeneralError("Unexpected error occured. Please try again");
            }

        }

        finally {
            setApiProgress(false);
        }


        //}).then((response) => {
        //    setSuccessMessage(response.data.message);
        //}).finally(() => setApiProgress(false));
    }

    const passwordRepeatError = useMemo(() => {
        if (password && password !== passwordRepeat) {
            console.log("Allways running")
            return "Password mismatch"
        }
        return "";
    }, [password, passwordRepeat]);

    return(

        <div className="container">
            <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">

                <form onSubmit={onSubmit} className="card">
                    <div className="text-center card-header" >
                        <h1> Sign Up</h1>
                    </div>
                    <div className="card-body">
                        <Input id="username" label ="Username" error={errors.username} onChange={(event) => setUsername(event.target.value)}/>
                        <Input id ="email" label ="E-mail" error={errors.email} onChange={(event) => setEmail(event.target.value)} />
                        <Input id ="password" label ="Password" error={errors.password} onChange={(event) => setPassword(event.target.value)} type="password" />
                        <Input id ="passwordRepeat" label ="Password Repeat" error={passwordRepeatError} onChange={(event) => setPasswordRepeat(event.target.value)} type="password" />

                        {/*
                        <div className="mb-3">
                        <label htmlFor="password">Password</label>
                        <input id="password" className="form-control" type="password" onChange={(event) => setPassword(event.target.value)}/>
                        </div>*/}


                        {successMessage && (<div className="alert alert-success">
                            {successMessage}</div>)}
                        {generalError && (<div className="alert alert-danger"> {generalError}</div>)}

                    <div className="text-center">
                        <button
                            className="btn btn-primary"
                            disabled={apiProgress || (!password || password !==
                            passwordRepeat)}       >
                            {apiProgress && <span className="spinner-border
                             spinner-border-sm" aria-hidden="true"></span>}
                            Sign Up
                        </button>
                    </div>
                    </div>
                </form>
            </div>
        </div>
)

}