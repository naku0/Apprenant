import "./terminal.css"

interface TerminalProps {
    onClose?: () => void;
}

const Terminal = ({ onClose }: TerminalProps) => {
    return (
        <div className="terminal-wrapper component-wrapper">
            {onClose && (
                <button className="close-button" onClick={onClose}>
                    ×
                </button>
            )}
            <h1>Terminal</h1>
        </div>
    )
}

export default Terminal;