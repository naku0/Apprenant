import { useState } from 'react';
import CodingPlayground from "../../components/ui/systems/CodingPlaygroundComponent/CodingPlayground.tsx";
import Terminal from "../../components/ui/systems/TerminalComponent/Terminal.tsx";
import ChatComponent from "../../components/ui/systems/ChatComponent/ChatComponent.tsx";
import "./playgroundPage.css";

const PlaygroundPage = () => {
    const [isTerminalOpen, setIsTerminalOpen] = useState(false);
    const [isChatOpen, setIsChatOpen] = useState(false);

    return (
        <div className="playground-page-container">
            <div
                className="main-area"
                style={{
                    width: isChatOpen ? '70%' : '100%',
                    flexDirection: isTerminalOpen ? 'column' : 'row'
                }}
            >
                <div className={`coding-wrapper ${isTerminalOpen ? 'with-terminal' : 'solo'}`}>
                    <CodingPlayground />
                </div>

                {isTerminalOpen && (
                    <div className="terminal-wrapper">
                        <Terminal onClose={() => setIsTerminalOpen(false)} />
                    </div>
                )}
            </div>

            {isChatOpen && (
                <div className="chat-area">
                    <ChatComponent onClose={() => setIsChatOpen(false)} />
                </div>
            )}

            <div className="controls">
                {!isTerminalOpen && (
                    <button
                        className="control-btn terminal-btn"
                        onClick={() => setIsTerminalOpen(true)}
                    >
                        Terminal
                    </button>
                )}
                {!isChatOpen && (
                    <button
                        className="control-btn chat-btn"
                        onClick={() => setIsChatOpen(true)}
                    >
                        Chat
                    </button>
                )}
            </div>
        </div>
    );
}

export default PlaygroundPage;