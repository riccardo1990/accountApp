import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAffiliazione } from 'app/shared/model/affiliazione.model';

@Component({
    selector: 'jhi-affiliazione-detail',
    templateUrl: './affiliazione-detail.component.html'
})
export class AffiliazioneDetailComponent implements OnInit {
    affiliazione: IAffiliazione;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ affiliazione }) => {
            this.affiliazione = affiliazione;
        });
    }

    previousState() {
        window.history.back();
    }
}
