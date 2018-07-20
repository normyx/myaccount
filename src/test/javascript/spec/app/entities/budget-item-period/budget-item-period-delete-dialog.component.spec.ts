/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyaccountTestModule } from '../../../test.module';
import { BudgetItemPeriodDeleteDialogComponent } from 'app/entities/budget-item-period/budget-item-period-delete-dialog.component';
import { BudgetItemPeriodService } from 'app/entities/budget-item-period/budget-item-period.service';

describe('Component Tests', () => {
    describe('BudgetItemPeriod Management Delete Component', () => {
        let comp: BudgetItemPeriodDeleteDialogComponent;
        let fixture: ComponentFixture<BudgetItemPeriodDeleteDialogComponent>;
        let service: BudgetItemPeriodService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [BudgetItemPeriodDeleteDialogComponent]
            })
                .overrideTemplate(BudgetItemPeriodDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetItemPeriodDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetItemPeriodService);
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
