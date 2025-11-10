import "./headerComponent.css"
import Dark from "@/assets/svgs/dark-theme.svg?react"
import Light from "@/assets/svgs/light-theme.svg?react"
import {useNavigate} from "react-router-dom"

interface HeaderComponentProps {
    func:()=>void;
    theme:string;
}

const HeaderComponent = ({func, theme}: HeaderComponentProps) => {
    const nav = useNavigate();
    return (
        <div className="headerComponent">
            <span className="header-app-name" onClick={()=> nav('/')}>
                Apprenant
            </span>
            <button className="header-app-button" onClick={func}>
                {theme === 'light' ?
                <span className="material-symbols-outlined">
                    <Light/>
                </span>
                :
                <span className="material-symbols-outlined">
                    <Dark/>
                </span>
            }</button>
        </div>
    )
}

export default HeaderComponent;