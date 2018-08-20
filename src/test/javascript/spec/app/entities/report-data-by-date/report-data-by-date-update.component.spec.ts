/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { ReportDataByDateUpdateComponent } from 'app/entities/report-data-by-date/report-data-by-date-update.component';
import { ReportDataByDateService } from 'app/entities/report-data-by-date/report-data-by-date.service';
import { ReportDataByDate } from 'app/shared/model/report-data-by-date.model';

describe('Component Tests', () => {
    describe('ReportDataByDate Management Update Component', () => {
        let comp: ReportDataByDateUpdateComponent;
        let fixture: ComponentFixture<ReportDataByDateUpdateComponent>;
        let service: ReportDataByDateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [ReportDataByDateUpdateComponent]
            })
                .overrideTemplate(ReportDataByDateUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportDataByDateUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportDataByDateService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReportDataByDate(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reportDataByDate = entity;
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
                    const entity = new ReportDataByDate();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reportDataByDate = entity;
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
