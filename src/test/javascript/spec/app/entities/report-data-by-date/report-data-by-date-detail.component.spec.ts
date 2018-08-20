/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { ReportDataByDateDetailComponent } from 'app/entities/report-data-by-date/report-data-by-date-detail.component';
import { ReportDataByDate } from 'app/shared/model/report-data-by-date.model';

describe('Component Tests', () => {
    describe('ReportDataByDate Management Detail Component', () => {
        let comp: ReportDataByDateDetailComponent;
        let fixture: ComponentFixture<ReportDataByDateDetailComponent>;
        const route = ({ data: of({ reportDataByDate: new ReportDataByDate(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [ReportDataByDateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReportDataByDateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportDataByDateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reportDataByDate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
