/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyaccountTestModule } from '../../../test.module';
import { MonthlyReportDeleteDialogComponent } from 'app/entities/monthly-report/monthly-report-delete-dialog.component';
import { MonthlyReportService } from 'app/entities/monthly-report/monthly-report.service';

describe('Component Tests', () => {
    describe('MonthlyReport Management Delete Component', () => {
        let comp: MonthlyReportDeleteDialogComponent;
        let fixture: ComponentFixture<MonthlyReportDeleteDialogComponent>;
        let service: MonthlyReportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [MonthlyReportDeleteDialogComponent]
            })
                .overrideTemplate(MonthlyReportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonthlyReportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonthlyReportService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
