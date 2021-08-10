import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from '../../authentication.service';
import {UserDetails} from '../../model/user-details';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {


    constructor(private authenticationService: AuthenticationService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let token: string = null;
        this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
          if(userDetails)
            token = userDetails.token;
        });
        if (token) {
            request = request.clone({
                setHeaders: {
                  Authorization: token
                }
            });
        }

        return next.handle(request);
    }
}
