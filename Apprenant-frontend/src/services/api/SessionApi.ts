import type {
    ApiError,
    ApiErrorResponse,
    CreateRoomRequest,
    CreateRoomResponse,
    RoomState
} from "../../types/api/session/sessionTypes.ts";
import {apiClient} from "../apiClient.ts";
import {SERVER} from "../ServerRoutes.ts";
import type {AxiosError} from "axios";

const isAxiosError = (err: unknown): err is AxiosError<ApiErrorResponse> => {
    const axiosErr = err as AxiosError;
    return axiosErr?.isAxiosError === true;
}

const handleError = (err: unknown): ApiError => {
    if (isAxiosError(err)) {
        if (err.response) {
            const responseData: ApiErrorResponse = err.response.data;
            return {
                message: responseData?.message || responseData?.error || err.response.statusText,
                status: err.response.status,
                code: err.code,
            };
        }
        else if (err.request) {
            return {
                message: "Request failed",
                code: err.code,
            };
        }
    }
    if (err instanceof Error) {
        return {
            message: err.message,
        };
    }

    return {
        message: "Something went wrong",
    };
};

export const createRoom =
    async (req: CreateRoomRequest): Promise<ApiError | CreateRoomResponse> => {
        try {
            const res = await apiClient.post<CreateRoomResponse>(SERVER.ROOM, req);
            return res.data;
        } catch (err) {
            return handleError(err);
        }
    }

export const getRoomState =
    async (roomId: string): Promise<ApiError | RoomState> => {
        try {
            const res =
                await apiClient.get<RoomState>(`${SERVER.ROOM}/${roomId}`);
            return res.data;
        } catch (err) {
            return handleError(err);
        }
}

export const roomExists = async (roomId: string): Promise<boolean> => {
    try {
        await getRoomState(roomId);
        return true;
    } catch (err) {
        if (isAxiosError(err) && err.response?.status === 404) {
            return false;
        }
        return false;
    }
};

export const isApiError = (value: unknown): value is ApiError => {
    return typeof value === 'object' &&
        value !== null &&
        'message' in value
};