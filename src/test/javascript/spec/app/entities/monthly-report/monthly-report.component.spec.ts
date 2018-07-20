/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyaccountTestModule } from '../../../test.module';
import { MonthlyReportComponent } from 'app/entities/monthly-report/monthly-report.component';
import { MonthlyReportService } from 'app/entities/monthly-report/monthly-report.service';
import { MonthlyReport } from 'app/shared/model/monthly-report.model';

describe('Component Tests', () => {
    describe('MonthlyReport Management Component', () => {
        let comp: MonthlyReportComponent;
        let fixture: ComponentFixture<MonthlyReportComponent>;
        let service: MonthlyReportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [MonthlyReportComponent],
                providers: []
            })
                .overrideTemplate(MonthlyReportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonthlyReportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlyReportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MonthlyReport(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.monthlyReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
