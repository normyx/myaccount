/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { MonthlyReportDetailComponent } from 'app/entities/monthly-report/monthly-report-detail.component';
import { MonthlyReport } from 'app/shared/model/monthly-report.model';

describe('Component Tests', () => {
    describe('MonthlyReport Management Detail Component', () => {
        let comp: MonthlyReportDetailComponent;
        let fixture: ComponentFixture<MonthlyReportDetailComponent>;
        const route = ({ data: of({ monthlyReport: new MonthlyReport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [MonthlyReportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MonthlyReportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonthlyReportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.monthlyReport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
