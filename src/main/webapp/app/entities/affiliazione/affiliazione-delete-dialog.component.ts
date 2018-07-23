import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAffiliazione } from 'app/shared/model/affiliazione.model';
import { AffiliazioneService } from './affiliazione.service';

@Component({
    selector: 'jhi-affiliazione-delete-dialog',
    templateUrl: './affiliazione-delete-dialog.component.html'
})
export class AffiliazioneDeleteDialogComponent {
    affiliazione: IAffiliazione;

    constructor(
        private affiliazioneService: AffiliazioneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.affiliazioneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'affiliazioneListModification',
                content: 'Deleted an affiliazione'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-affiliazione-delete-popup',
    template: ''
})
export class AffiliazioneDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ affiliazione }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AffiliazioneDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.affiliazione = affiliazione;
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
