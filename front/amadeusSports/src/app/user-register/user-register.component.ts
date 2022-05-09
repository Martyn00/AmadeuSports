import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y/input-modality/input-modality-detector';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserDto } from '../dto/UserDto';
import { UserService } from '../service/user.service';

@Component({
    selector: 'app-user-register',
    templateUrl: './user-register.component.html',
    styleUrls: ['./user-register.component.css'],
})
export class UserRegisterComponent implements OnInit {

    firstNameAutofilled: boolean = false;
    lastNameAutofilled: boolean = false;
    regForm = new FormGroup({
        username: new FormControl(''),
        password: new FormControl(''),
        email: new FormControl('')
    })


    constructor(private userService: UserService) {

    }

    ngOnInit(): void {

    }

    submitData() {
        const result: UserDto = Object.assign({}, this.regForm.value);
        this.userService.register(result);
        console.log(result);
        this.regForm.reset();
    }
}
