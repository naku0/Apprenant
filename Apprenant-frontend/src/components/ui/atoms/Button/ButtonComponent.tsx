import "./buttonComponent.css"

interface ButtonProps {
    text?: string;
    onClick?: () => void;
    children?: React.ReactNode;
}

const ButtonComponent = ({text, onClick, children}: ButtonProps) => {
    return(
        <div className="button-component-container" onClick={onClick}>
            {children}
            {text && <span className="button-component-button-name">{text}</span>}
        </div>
    );
}

export default ButtonComponent;