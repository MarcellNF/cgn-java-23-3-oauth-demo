import axios from "axios";
import {useEffect, useState} from "react";

export default function SecuredComponent() {
    const [secured, setSecured] = useState<string>("");

    useEffect(() => {
        axios.get("/api/secured")
            .then((r) => setSecured(r.data))
            .catch((e) => console.log(e))
    }, [])

    function logout() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + "/logout", "_self")
    }

    return (
        <>
            <h1>{secured}</h1>
            <button onClick={logout}>Logout</button>
        </>
    )

}
