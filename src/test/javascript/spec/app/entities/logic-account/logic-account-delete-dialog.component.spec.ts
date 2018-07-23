/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccountAppTestModule } from '../../../test.module';
import { LogicAccountDeleteDialogComponent } from 'app/entities/logic-account/logic-account-delete-dialog.component';
import { LogicAccountService } from 'app/entities/logic-account/logic-account.service';

describe('Component Tests', () => {
    describe('LogicAccount Management Delete Component', () => {
        let comp: LogicAccountDeleteDialogComponent;
        let fixture: ComponentFixture<LogicAccountDeleteDialogComponent>;
        let service: LogicAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccountAppTestModule],
                declarations: [LogicAccountDeleteDialogComponent]
            })
                .overrideTemplate(LogicAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LogicAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogicAccountService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
