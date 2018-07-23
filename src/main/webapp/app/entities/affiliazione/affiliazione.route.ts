import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Affiliazione } from 'app/shared/model/affiliazione.model';
import { AffiliazioneService } from './affiliazione.service';
import { AffiliazioneComponent } from './affiliazione.component';
import { AffiliazioneDetailComponent } from './affiliazione-detail.component';
import { AffiliazioneUpdateComponent } from './affiliazione-update.component';
import { AffiliazioneDeletePopupComponent } from './affiliazione-delete-dialog.component';
import { IAffiliazione } from 'app/shared/model/affiliazione.model';

@Injectable({ providedIn: 'root' })
export class AffiliazioneResolve implements Resolve<IAffiliazione> {
    constructor(private service: AffiliazioneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((affiliazione: HttpResponse<Affiliazione>) => affiliazione.body));
        }
        return of(new Affiliazione());
    }
}

export const affiliazioneRoute: Routes = [
    {
        path: 'affiliazione',
        component: AffiliazioneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Affiliaziones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'affiliazione/:id/view',
        component: AffiliazioneDetailComponent,
        resolve: {
            affiliazione: AffiliazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Affiliaziones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'affiliazione/new',
        component: AffiliazioneUpdateComponent,
        resolve: {
            affiliazione: AffiliazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Affiliaziones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'affiliazione/:id/edit',
        component: AffiliazioneUpdateComponent,
        resolve: {
            affiliazione: AffiliazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Affiliaziones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const affiliazionePopupRoute: Routes = [
    {
        path: 'affiliazione/:id/delete',
        component: AffiliazioneDeletePopupComponent,
        resolve: {
            affiliazione: AffiliazioneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Affiliaziones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
