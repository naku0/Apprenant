export interface CreateRoomRequest{
    roomName: string;
    hostName: string;
}

export interface CreateRoomResponse{
    roomId: string;
    roomUrl: string;
}

export interface RoomState{
    room: RoomDto;
    code: string;
    language: string;
    participants: ParticipantDto[];
}

export interface ParticipantDto{
   id: string;
   name: string;
}

export interface RoomDto{
    roomId: string;
    roomName: string;
    participants: ParticipantDto[];
}

export interface ApiError {
    message: string;
    status?: number;
    code?: string;
}

export interface ApiErrorResponse {
    message?: string;
    error?: string;
    statusCode?: number;
}
