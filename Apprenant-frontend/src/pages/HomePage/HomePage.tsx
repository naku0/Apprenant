import ButtonComponent from "../../components/ui/atoms/Button/ButtonComponent.tsx";
import Plus from "@/assets/svgs/add.svg?react"
import Edit from "@/assets/svgs/edit.svg?react"
import {useNavigate} from "react-router-dom";
import {ROUTES} from "../Routes.ts";
import "./homePage.css"

const HomePage = () => {
    const nav = useNavigate();
    return (
        <div className="page-container">
            <ButtonComponent text="Create room"
                             onClick={() => nav(ROUTES.SESSION_CREATE)}>
                <Plus/>
            </ButtonComponent>
            <ButtonComponent text="Open playground"
                             onClick={() => nav(ROUTES.PLAYGROUND)}>
                <Edit/>
            </ButtonComponent>
        </div>
    )
}

export default HomePage;
