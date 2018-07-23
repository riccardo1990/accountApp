import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ILogicAccount } from 'app/shared/model/logic-account.model';
import { LogicAccountService } from './logic-account.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-logic-account-update',
    templateUrl: './logic-account-update.component.html'
})
export class LogicAccountUpdateComponent implements OnInit {
    private _logicAccount: ILogicAccount;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private logicAccountService: LogicAccountService,
        private userService: UserService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ logicAccount }) => {
            this.logicAccount = logicAccount;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.logicAccount, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.logicAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.logicAccountService.update(this.logicAccount));
        } else {
            this.subscribeToSaveResponse(this.logicAccountService.create(this.logicAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILogicAccount>>) {
        result.subscribe((res: HttpResponse<ILogicAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get logicAccount() {
        return this._logicAccount;
    }

    set logicAccount(logicAccount: ILogicAccount) {
        this._logicAccount = logicAccount;
    }
}
