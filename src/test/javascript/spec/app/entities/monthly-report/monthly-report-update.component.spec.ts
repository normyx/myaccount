/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { MonthlyReportUpdateComponent } from 'app/entities/monthly-report/monthly-report-update.component';
import { MonthlyReportService } from 'app/entities/monthly-report/monthly-report.service';
import { MonthlyReport } from 'app/shared/model/monthly-report.model';

describe('Component Tests', () => {
    describe('MonthlyReport Management Update Component', () => {
        let comp: MonthlyReportUpdateComponent;
        let fixture: ComponentFixture<MonthlyReportUpdateComponent>;
        let service: MonthlyReportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [MonthlyReportUpdateComponent]
            })
                .overrideTemplate(MonthlyReportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonthlyReportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlyReportService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MonthlyReport(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.monthlyReport = entity;
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
                    const entity = new MonthlyReport();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.monthlyReport = entity;
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
