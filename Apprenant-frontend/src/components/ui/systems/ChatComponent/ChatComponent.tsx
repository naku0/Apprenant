import "./chatComponent.css"

interface ChatComponentProps {
    onClose?: () => void;
}

const ChatComponent = ({ onClose }: ChatComponentProps) => {
    return (
        <div className="chat-wrapper component-wrapper">
            {onClose && (
                <button className="close-button" onClick={onClose}>
                    ×
                </button>
            )}
            <h1>Chat</h1>
            {/* Здесь будет ваш контент чата */}
        </div>
    );
}

export default ChatComponent;