import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserEmulationInterceptor implements HttpInterceptor {

    constructor() {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const skipIntercept = request.headers.has('skip');
        if (skipIntercept) {
            request = request.clone({
                headers: request.headers.delete('skip')
            });
            return next.handle(request);
        }
        let token = localStorage.getItem("token");
        if (token) {
            // If we have a token, we set it to the header
            request = request.clone({
                setHeaders: { "Authorization": "Bearer " + token }
            });
        }

        return next.handle(request);
    }
}