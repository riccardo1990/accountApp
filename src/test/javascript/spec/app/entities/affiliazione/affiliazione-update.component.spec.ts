/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccountAppTestModule } from '../../../test.module';
import { AffiliazioneUpdateComponent } from 'app/entities/affiliazione/affiliazione-update.component';
import { AffiliazioneService } from 'app/entities/affiliazione/affiliazione.service';
import { Affiliazione } from 'app/shared/model/affiliazione.model';

describe('Component Tests', () => {
    describe('Affiliazione Management Update Component', () => {
        let comp: AffiliazioneUpdateComponent;
        let fixture: ComponentFixture<AffiliazioneUpdateComponent>;
        let service: AffiliazioneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccountAppTestModule],
                declarations: [AffiliazioneUpdateComponent]
            })
                .overrideTemplate(AffiliazioneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AffiliazioneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffiliazioneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Affiliazione(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.affiliazione = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Affiliazione();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.affiliazione = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
