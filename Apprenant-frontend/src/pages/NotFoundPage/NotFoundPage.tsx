import {ROUTES} from "../Routes.ts";
import "./notFoundPage.css"

const NotFoundPage = () => {
    return (
        <>
            <h1>404</h1>
            <p>there's no such page, go&nbsp;
                <a href={ROUTES.ROOT}>home</a>
            </p>
        </>
    )
}

export default NotFoundPage;