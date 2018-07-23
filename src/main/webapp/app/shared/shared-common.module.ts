import { NgModule } from '@angular/core';

import { AccountAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AccountAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AccountAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AccountAppSharedCommonModule {}
