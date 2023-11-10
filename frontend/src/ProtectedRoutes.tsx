import {AppUser} from "./App.tsx";
import {Navigate, Outlet} from "react-router-dom";

type ProtectedRoutesProps = {
    appUser: AppUser | null | undefined,
}

export default function ProtectedRoutes(props: Readonly<ProtectedRoutesProps>) {
    console.log(props.appUser)
    if (props.appUser === undefined) return (<h1>Loading...</h1>)
    const isAuth = props.appUser !== null

    return isAuth ? <Outlet/> : <Navigate to={"/login"}/>
}