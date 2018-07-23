import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAffiliazione } from 'app/shared/model/affiliazione.model';
import { AffiliazioneService } from './affiliazione.service';
import { ILogicAccount } from 'app/shared/model/logic-account.model';
import { LogicAccountService } from 'app/entities/logic-account';

@Component({
    selector: 'jhi-affiliazione-update',
    templateUrl: './affiliazione-update.component.html'
})
export class AffiliazioneUpdateComponent implements OnInit {
    private _affiliazione: IAffiliazione;
    isSaving: boolean;

    logicaccounts: ILogicAccount[];
    dataAffiliazioneDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private affiliazioneService: AffiliazioneService,
        private logicAccountService: LogicAccountService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ affiliazione }) => {
            this.affiliazione = affiliazione;
        });
        this.logicAccountService.query().subscribe(
            (res: HttpResponse<ILogicAccount[]>) => {
                this.logicaccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.affiliazione.id !== undefined) {
            this.subscribeToSaveResponse(this.affiliazioneService.update(this.affiliazione));
        } else {
            this.subscribeToSaveResponse(this.affiliazioneService.create(this.affiliazione));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliazione>>) {
        result.subscribe((res: HttpResponse<IAffiliazione>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLogicAccountById(index: number, item: ILogicAccount) {
        return item.id;
    }
    get affiliazione() {
        return this._affiliazione;
    }

    set affiliazione(affiliazione: IAffiliazione) {
        this._affiliazione = affiliazione;
    }
}
