import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserEmulationInterceptor implements HttpInterceptor {

    constructor() {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
            const modReq = req.clone({
                setHeaders: {
                    'Authorization': 'Bearer' + localStorage.getItem('token')
                }
            });
            return next.handle(modReq);
        return next.handle(req);
    }
}