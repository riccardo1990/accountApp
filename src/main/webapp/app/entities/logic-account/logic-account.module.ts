import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccountAppSharedModule } from 'app/shared';
import { AccountAppAdminModule } from 'app/admin/admin.module';
import {
    LogicAccountComponent,
    LogicAccountDetailComponent,
    LogicAccountUpdateComponent,
    LogicAccountDeletePopupComponent,
    LogicAccountDeleteDialogComponent,
    logicAccountRoute,
    logicAccountPopupRoute
} from './';

const ENTITY_STATES = [...logicAccountRoute, ...logicAccountPopupRoute];

@NgModule({
    imports: [AccountAppSharedModule, AccountAppAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LogicAccountComponent,
        LogicAccountDetailComponent,
        LogicAccountUpdateComponent,
        LogicAccountDeleteDialogComponent,
        LogicAccountDeletePopupComponent
    ],
    entryComponents: [
        LogicAccountComponent,
        LogicAccountUpdateComponent,
        LogicAccountDeleteDialogComponent,
        LogicAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountAppLogicAccountModule {}
