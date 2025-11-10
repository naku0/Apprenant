import axios from "axios";

const createApiClient = () => {
    return axios.create({
        baseURL: 'http://localhost:8080/api',
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json',
        }
    })
}

export const apiClient = createApiClient();