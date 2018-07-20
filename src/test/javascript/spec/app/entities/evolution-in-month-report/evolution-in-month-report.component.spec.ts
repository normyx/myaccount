/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyaccountTestModule } from '../../../test.module';
import { EvolutionInMonthReportComponent } from 'app/entities/evolution-in-month-report/evolution-in-month-report.component';
import { EvolutionInMonthReportService } from 'app/entities/evolution-in-month-report/evolution-in-month-report.service';
import { EvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';

describe('Component Tests', () => {
    describe('EvolutionInMonthReport Management Component', () => {
        let comp: EvolutionInMonthReportComponent;
        let fixture: ComponentFixture<EvolutionInMonthReportComponent>;
        let service: EvolutionInMonthReportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [EvolutionInMonthReportComponent],
                providers: []
            })
                .overrideTemplate(EvolutionInMonthReportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EvolutionInMonthReportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvolutionInMonthReportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EvolutionInMonthReport(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.evolutionInMonthReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
