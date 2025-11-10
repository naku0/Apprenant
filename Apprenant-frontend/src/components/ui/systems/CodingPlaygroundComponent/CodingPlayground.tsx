import "./codingPlayground.css"
import { LanguageMetadata, LanguageSet } from "../../../../utils/Languages.ts"

const CodingPlayground = () => {
    return (
        <div className="playground-wrapper">
            <div className="playground-container">

                <select defaultValue="java" className="playground-selector">
                    {[...LanguageSet].map(language => (
                        <option key={language} value={language}>
                            {LanguageMetadata[language]?.name || language}
                        </option>
                    ))}
                </select>
            </div>
            <div className="playground-code-block">
                There will be code
            </div>
        </div>
    )
}

export default CodingPlayground