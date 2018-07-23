import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAffiliazione } from 'app/shared/model/affiliazione.model';

type EntityResponseType = HttpResponse<IAffiliazione>;
type EntityArrayResponseType = HttpResponse<IAffiliazione[]>;

@Injectable({ providedIn: 'root' })
export class AffiliazioneService {
    private resourceUrl = SERVER_API_URL + 'api/affiliaziones';

    constructor(private http: HttpClient) {}

    create(affiliazione: IAffiliazione): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(affiliazione);
        return this.http
            .post<IAffiliazione>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(affiliazione: IAffiliazione): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(affiliazione);
        return this.http
            .put<IAffiliazione>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAffiliazione>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAffiliazione[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(affiliazione: IAffiliazione): IAffiliazione {
        const copy: IAffiliazione = Object.assign({}, affiliazione, {
            dataAffiliazione:
                affiliazione.dataAffiliazione != null && affiliazione.dataAffiliazione.isValid()
                    ? affiliazione.dataAffiliazione.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataAffiliazione = res.body.dataAffiliazione != null ? moment(res.body.dataAffiliazione) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((affiliazione: IAffiliazione) => {
            affiliazione.dataAffiliazione = affiliazione.dataAffiliazione != null ? moment(affiliazione.dataAffiliazione) : null;
        });
        return res;
    }
}
