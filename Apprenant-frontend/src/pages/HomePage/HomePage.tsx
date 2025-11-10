import ButtonComponent from "../../components/ui/atoms/Button/ButtonComponent.tsx";
import Plus from "@/assets/svgs/add.svg?react"
import {useNavigate} from "react-router-dom";
import {ROUTES} from "../Routes.ts";
import "./homePage.css"

const HomePage = () => {
    const nav = useNavigate();
    return (
        <div className="page-container">
            <div className="how-to-tip">
                <h1>How to use Apprenant:</h1>
                <ol>
                    <li>Create room</li>
                    <li>Enter room name and your nickname</li>
                    <li>Share room's link</li>
                </ol>
            </div>
            <ButtonComponent text="Create room"
                             onClick={() => nav(ROUTES.SESSION_CREATE)}>
                <Plus/>
            </ButtonComponent>
        </div>
    )
}

export default HomePage;
