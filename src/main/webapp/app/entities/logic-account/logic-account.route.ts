import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LogicAccount } from 'app/shared/model/logic-account.model';
import { LogicAccountService } from './logic-account.service';
import { LogicAccountComponent } from './logic-account.component';
import { LogicAccountDetailComponent } from './logic-account-detail.component';
import { LogicAccountUpdateComponent } from './logic-account-update.component';
import { LogicAccountDeletePopupComponent } from './logic-account-delete-dialog.component';
import { ILogicAccount } from 'app/shared/model/logic-account.model';

@Injectable({ providedIn: 'root' })
export class LogicAccountResolve implements Resolve<ILogicAccount> {
    constructor(private service: LogicAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((logicAccount: HttpResponse<LogicAccount>) => logicAccount.body));
        }
        return of(new LogicAccount());
    }
}

export const logicAccountRoute: Routes = [
    {
        path: 'logic-account',
        component: LogicAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogicAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'logic-account/:id/view',
        component: LogicAccountDetailComponent,
        resolve: {
            logicAccount: LogicAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogicAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'logic-account/new',
        component: LogicAccountUpdateComponent,
        resolve: {
            logicAccount: LogicAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogicAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'logic-account/:id/edit',
        component: LogicAccountUpdateComponent,
        resolve: {
            logicAccount: LogicAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogicAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logicAccountPopupRoute: Routes = [
    {
        path: 'logic-account/:id/delete',
        component: LogicAccountDeletePopupComponent,
        resolve: {
            logicAccount: LogicAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LogicAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
