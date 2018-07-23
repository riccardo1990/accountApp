import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccountAppSharedModule } from 'app/shared';
import {
    AffiliazioneComponent,
    AffiliazioneDetailComponent,
    AffiliazioneUpdateComponent,
    AffiliazioneDeletePopupComponent,
    AffiliazioneDeleteDialogComponent,
    affiliazioneRoute,
    affiliazionePopupRoute
} from './';

const ENTITY_STATES = [...affiliazioneRoute, ...affiliazionePopupRoute];

@NgModule({
    imports: [AccountAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AffiliazioneComponent,
        AffiliazioneDetailComponent,
        AffiliazioneUpdateComponent,
        AffiliazioneDeleteDialogComponent,
        AffiliazioneDeletePopupComponent
    ],
    entryComponents: [
        AffiliazioneComponent,
        AffiliazioneUpdateComponent,
        AffiliazioneDeleteDialogComponent,
        AffiliazioneDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountAppAffiliazioneModule {}
