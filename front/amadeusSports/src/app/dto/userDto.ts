
import { required, prop, propObject, propArray } from "@rxweb/reactive-form-validators";

export class UserDto {
    username: string = '';
    password: string = '';
    email: string = '';
    role: string = '';

}