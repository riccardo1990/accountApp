import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILogicAccount } from 'app/shared/model/logic-account.model';

type EntityResponseType = HttpResponse<ILogicAccount>;
type EntityArrayResponseType = HttpResponse<ILogicAccount[]>;

@Injectable({ providedIn: 'root' })
export class LogicAccountService {
    private resourceUrl = SERVER_API_URL + 'api/logic-accounts';

    constructor(private http: HttpClient) {}

    create(logicAccount: ILogicAccount): Observable<EntityResponseType> {
        return this.http.post<ILogicAccount>(this.resourceUrl, logicAccount, { observe: 'response' });
    }

    update(logicAccount: ILogicAccount): Observable<EntityResponseType> {
        return this.http.put<ILogicAccount>(this.resourceUrl, logicAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILogicAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILogicAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
