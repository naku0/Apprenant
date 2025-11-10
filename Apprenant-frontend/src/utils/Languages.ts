const LanguageSet = new Set(['java', 'javascript', 'python']);

interface LanguageInfo {
    name: string;
    monacoId: string;
    template: string;
}

const LanguageMetadata: Record<string, LanguageInfo> = {
    java: { name: 'Java', monacoId: 'java', template: 'public class Main {\n    public static void main(String[] args) {\n        System.out.println("Hello World");\n    }\n}' },
    javascript: { name: 'JavaScript', monacoId: 'javascript', template: 'console.log("Hello World");' },
    python: { name: 'Python', monacoId: 'python', template: 'print("Hello World")' },
};


const isLanguageSupported = (lang:string)=>  {
    return LanguageSet.has(lang);
}

const getLanguageTemplate = (lang: string) => {
    return LanguageMetadata[lang]?.template || '';
}

export { LanguageSet, LanguageMetadata, getLanguageTemplate, isLanguageSupported };