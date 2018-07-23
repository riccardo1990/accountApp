/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountAppTestModule } from '../../../test.module';
import { AffiliazioneDetailComponent } from 'app/entities/affiliazione/affiliazione-detail.component';
import { Affiliazione } from 'app/shared/model/affiliazione.model';

describe('Component Tests', () => {
    describe('Affiliazione Management Detail Component', () => {
        let comp: AffiliazioneDetailComponent;
        let fixture: ComponentFixture<AffiliazioneDetailComponent>;
        const route = ({ data: of({ affiliazione: new Affiliazione(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccountAppTestModule],
                declarations: [AffiliazioneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AffiliazioneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AffiliazioneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.affiliazione).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
