import {createRoom, isApiError} from "../../services/api/SessionApi.ts";
import "./createSessionPage.css"
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";

interface CreateRoomForm {
    roomName: string;
    hostName: string;
}

const CreateSessionPage = () => {
    const {
        register,
        handleSubmit,
        formState: {errors, isSubmitting}
    } = useForm<CreateRoomForm>({
        defaultValues:{
            roomName:"",
            hostName:"",
        }
    });

    const nav = useNavigate();

    const onSubmit = async (data: CreateRoomForm) => {
        try {
            const response = await createRoom({
                roomName: data.roomName,
                hostName: data.hostName,
            });
            if ("roomId" in response) {
                nav(`/room/${response.roomId}`);
            }
        } catch (error: unknown) {
            if (isApiError(error)) {
                console.error('Ошибка API:', error.message);
            } else {
                console.error('Неизвестная ошибка:', error);
            }
        }
    };


    return (
        <>
            <div className="sessionPage-form-container">
            <form className="sessionPage-form" onSubmit={handleSubmit(onSubmit)}>
                <span>Create room</span>
                <div className="input-container">
                    <input id="room-name-input" type="text"
                           className={errors.roomName ? "input-error" : ""}
                           placeholder=" "
                           {...register("roomName", {
                               required: "Room's name is required",
                               minLength: {
                               value: 3,
                               message: "Room must be at least 3 characters"
                           },
                               maxLength: {
                               value: 20,
                               message: "Room should not be longer than 20 characters"
                           },
                           })}
                    />
                    <label htmlFor="room-name-input" className="input-label">
                        <span>Room name:</span>
                    </label>
                    <div className="input-example">Java interview for Alex</div>
                    {errors.roomName && (
                        <div className="alert-message">
                            {errors.roomName.message}
                        </div>
                    )}
                </div>
                <div className="input-container">
                    <input id="host-name-input" type="text"
                           className={errors.hostName ? "input-error" : ""}
                           placeholder=" "
                           {...register("hostName", {
                               required: "Enter your name",
                               minLength: {
                                   value: 1,
                                   message: "Your name should be at least 1 character"
                               },
                               maxLength: {
                                   value: 20,
                                   message: "Your name should not be longer than 20 characters"
                               }
                           })}
                    />
                    <label htmlFor="host-name-input" className="input-label">
                        <span>Host name:</span>
                    </label>
                    <div className="input-example">Antoine Roquentin</div>
                    {errors.hostName && (
                        <div className="alert-message">{errors.hostName.message}</div>
                    )}
                </div>
                <div className="sessionPage-button-container">
                    <button type="submit" disabled={isSubmitting}>
                        {isSubmitting? "Creating..." : "Create!"}
                    </button>
                </div>
            </form>
            </div>
        </>

    )
}

export default CreateSessionPage;