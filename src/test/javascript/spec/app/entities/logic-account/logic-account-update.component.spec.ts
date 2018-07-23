/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccountAppTestModule } from '../../../test.module';
import { LogicAccountUpdateComponent } from 'app/entities/logic-account/logic-account-update.component';
import { LogicAccountService } from 'app/entities/logic-account/logic-account.service';
import { LogicAccount } from 'app/shared/model/logic-account.model';

describe('Component Tests', () => {
    describe('LogicAccount Management Update Component', () => {
        let comp: LogicAccountUpdateComponent;
        let fixture: ComponentFixture<LogicAccountUpdateComponent>;
        let service: LogicAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccountAppTestModule],
                declarations: [LogicAccountUpdateComponent]
            })
                .overrideTemplate(LogicAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LogicAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogicAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LogicAccount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.logicAccount = entity;
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
                    const entity = new LogicAccount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.logicAccount = entity;
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
