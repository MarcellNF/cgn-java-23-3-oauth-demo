import './App.css'
import {useEffect, useState} from "react";
import axios from "axios";
import {Link, Navigate, Route, Routes} from "react-router-dom";
import Login from "./Login.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";
import SecuredComponent from "./SecuredComponent.tsx";

export type AppUser = {
    id: number,
    login: string,
    avatarUrl: string,
}

function App() {
    const [appUser, setAppUser] = useState<AppUser | null | undefined>(undefined);

    useEffect(() => {
        axios.get("/api/auth/me")
            .then((r) => setAppUser(r.data))
            .catch((e) => {
                console.log(e)
                setAppUser(null)
                return (
                    <Navigate to={"/login"}/>
                )
            })
    }, []);

    return (
        <Routes>
            <Route path={"/"} element={(
                <div>
                    <Link to={"/secured"}>Secured</Link>
                </div>
            )}/>
            <Route path={"/login"} element={<Login/>}/>
            <Route element={<ProtectedRoutes appUser={appUser}/>}>
                <Route path={"/secured"} element={<SecuredComponent/>}/>
            </Route>
        </Routes>
    )
}

export default App
