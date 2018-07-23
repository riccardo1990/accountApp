import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILogicAccount } from 'app/shared/model/logic-account.model';

@Component({
    selector: 'jhi-logic-account-detail',
    templateUrl: './logic-account-detail.component.html'
})
export class LogicAccountDetailComponent implements OnInit {
    logicAccount: ILogicAccount;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ logicAccount }) => {
            this.logicAccount = logicAccount;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
