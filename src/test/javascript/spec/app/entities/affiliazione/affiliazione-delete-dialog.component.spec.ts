/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccountAppTestModule } from '../../../test.module';
import { AffiliazioneDeleteDialogComponent } from 'app/entities/affiliazione/affiliazione-delete-dialog.component';
import { AffiliazioneService } from 'app/entities/affiliazione/affiliazione.service';

describe('Component Tests', () => {
    describe('Affiliazione Management Delete Component', () => {
        let comp: AffiliazioneDeleteDialogComponent;
        let fixture: ComponentFixture<AffiliazioneDeleteDialogComponent>;
        let service: AffiliazioneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccountAppTestModule],
                declarations: [AffiliazioneDeleteDialogComponent]
            })
                .overrideTemplate(AffiliazioneDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AffiliazioneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffiliazioneService);
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
