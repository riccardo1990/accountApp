import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AccountAppLogicAccountModule } from './logic-account/logic-account.module';
import { AccountAppAffiliazioneModule } from './affiliazione/affiliazione.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AccountAppLogicAccountModule,
        AccountAppAffiliazioneModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountAppEntityModule {}
