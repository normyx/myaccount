/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyaccountTestModule } from '../../../test.module';
import { EvolutionInMonthReportDeleteDialogComponent } from 'app/entities/evolution-in-month-report/evolution-in-month-report-delete-dialog.component';
import { EvolutionInMonthReportService } from 'app/entities/evolution-in-month-report/evolution-in-month-report.service';

describe('Component Tests', () => {
    describe('EvolutionInMonthReport Management Delete Component', () => {
        let comp: EvolutionInMonthReportDeleteDialogComponent;
        let fixture: ComponentFixture<EvolutionInMonthReportDeleteDialogComponent>;
        let service: EvolutionInMonthReportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [EvolutionInMonthReportDeleteDialogComponent]
            })
                .overrideTemplate(EvolutionInMonthReportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EvolutionInMonthReportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EvolutionInMonthReportService);
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
