import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { AclModel } from '../model/acl.model';

const BASE_HTTP = environment.http;

@Injectable({ providedIn: 'root' })
export class AclService {

    constructor(private http: HttpClient) { }

    public getPerformatives() {
        return this.http.get(BASE_HTTP + '/messages');
    }

    public sendAcl(msg: AclModel) {
        this.http.post(BASE_HTTP + '/messages', msg, { responseType: 'text' }).subscribe();
    }

    public startCN() {
        this.http.get(BASE_HTTP + "/messages/cn", { responseType: 'text' }).subscribe();
    }
}