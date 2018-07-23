/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountAppTestModule } from '../../../test.module';
import { LogicAccountDetailComponent } from 'app/entities/logic-account/logic-account-detail.component';
import { LogicAccount } from 'app/shared/model/logic-account.model';

describe('Component Tests', () => {
    describe('LogicAccount Management Detail Component', () => {
        let comp: LogicAccountDetailComponent;
        let fixture: ComponentFixture<LogicAccountDetailComponent>;
        const route = ({ data: of({ logicAccount: new LogicAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccountAppTestModule],
                declarations: [LogicAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LogicAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LogicAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.logicAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
