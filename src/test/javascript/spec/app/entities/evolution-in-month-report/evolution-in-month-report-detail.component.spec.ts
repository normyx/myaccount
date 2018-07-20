/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { EvolutionInMonthReportDetailComponent } from 'app/entities/evolution-in-month-report/evolution-in-month-report-detail.component';
import { EvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';

describe('Component Tests', () => {
    describe('EvolutionInMonthReport Management Detail Component', () => {
        let comp: EvolutionInMonthReportDetailComponent;
        let fixture: ComponentFixture<EvolutionInMonthReportDetailComponent>;
        const route = ({ data: of({ evolutionInMonthReport: new EvolutionInMonthReport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [EvolutionInMonthReportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EvolutionInMonthReportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EvolutionInMonthReportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.evolutionInMonthReport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
