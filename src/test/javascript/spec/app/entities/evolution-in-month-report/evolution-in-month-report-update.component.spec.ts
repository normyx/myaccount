/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { EvolutionInMonthReportUpdateComponent } from 'app/entities/evolution-in-month-report/evolution-in-month-report-update.component';
import { EvolutionInMonthReportService } from 'app/entities/evolution-in-month-report/evolution-in-month-report.service';
import { EvolutionInMonthReport } from 'app/shared/model/evolution-in-month-report.model';

describe('Component Tests', () => {
    describe('EvolutionInMonthReport Management Update Component', () => {
        let comp: EvolutionInMonthReportUpdateComponent;
        let fixture: ComponentFixture<EvolutionInMonthReportUpdateComponent>;
        let service: EvolutionInMonthReportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [EvolutionInMonthReportUpdateComponent]
            })
                .overrideTemplate(EvolutionInMonthReportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EvolutionInMonthReportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvolutionInMonthReportService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EvolutionInMonthReport(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.evolutionInMonthReport = entity;
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
                    const entity = new EvolutionInMonthReport();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.evolutionInMonthReport = entity;
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
