/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyaccountTestModule } from '../../../test.module';
import { ReportDataByDateDeleteDialogComponent } from 'app/entities/report-data-by-date/report-data-by-date-delete-dialog.component';
import { ReportDataByDateService } from 'app/entities/report-data-by-date/report-data-by-date.service';

describe('Component Tests', () => {
    describe('ReportDataByDate Management Delete Component', () => {
        let comp: ReportDataByDateDeleteDialogComponent;
        let fixture: ComponentFixture<ReportDataByDateDeleteDialogComponent>;
        let service: ReportDataByDateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [ReportDataByDateDeleteDialogComponent]
            })
                .overrideTemplate(ReportDataByDateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportDataByDateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportDataByDateService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
