import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogicAccount } from 'app/shared/model/logic-account.model';
import { LogicAccountService } from './logic-account.service';

@Component({
    selector: 'jhi-logic-account-delete-dialog',
    templateUrl: './logic-account-delete-dialog.component.html'
})
export class LogicAccountDeleteDialogComponent {
    logicAccount: ILogicAccount;

    constructor(
        private logicAccountService: LogicAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logicAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'logicAccountListModification',
                content: 'Deleted an logicAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-logic-account-delete-popup',
    template: ''
})
export class LogicAccountDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ logicAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LogicAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.logicAccount = logicAccount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
