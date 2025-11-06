import useLocalStorage from "../hooks/useLocalStorage.ts";
import {useEffect} from "react";
import "./App.css";
import HeaderComponent from "../components/ui/headerComponent/HeaderComponent.tsx";
import HomePage from "../pages/HomePage/HomePage.tsx";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Playground from "../pages/Playground.tsx";
import {ROUTES} from "../pages/Routes.ts";
import CreateSession from "../pages/Session/CreateSession.tsx";
import NotFoundPage from "../pages/NotFoundPage/NotFoundPage.tsx";

const App = () => {
    const defaultTheme = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
    const [theme, setTheme] = useLocalStorage<string>("theme", defaultTheme);

    useEffect(() => {
        document.documentElement.setAttribute("data-theme", theme);
    }, [theme]);

    const changeTheme = () => {
        setTheme(theme === "light" ? "dark" : "light");
    }

    return(
        <BrowserRouter>
        <div className="app-component">
            <HeaderComponent func={changeTheme} theme={theme}/>
            <Routes>
                <Route path={ROUTES.ROOT} element={<HomePage/>} />
                <Route path={ROUTES.SESSION_CREATE} element={<CreateSession/>}/>
                <Route path={ROUTES.PLAYGROUND} element={<Playground/>}/>
                <Route path="*" element={<NotFoundPage/>}/>
            </Routes>
        </div>
        </BrowserRouter>

    )
}

export default App;