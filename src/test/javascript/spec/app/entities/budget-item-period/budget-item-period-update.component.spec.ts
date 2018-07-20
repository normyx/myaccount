/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { BudgetItemPeriodUpdateComponent } from 'app/entities/budget-item-period/budget-item-period-update.component';
import { BudgetItemPeriodService } from 'app/entities/budget-item-period/budget-item-period.service';
import { BudgetItemPeriod } from 'app/shared/model/budget-item-period.model';

describe('Component Tests', () => {
    describe('BudgetItemPeriod Management Update Component', () => {
        let comp: BudgetItemPeriodUpdateComponent;
        let fixture: ComponentFixture<BudgetItemPeriodUpdateComponent>;
        let service: BudgetItemPeriodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [BudgetItemPeriodUpdateComponent]
            })
                .overrideTemplate(BudgetItemPeriodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BudgetItemPeriodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BudgetItemPeriodService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BudgetItemPeriod(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.budgetItemPeriod = entity;
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
                    const entity = new BudgetItemPeriod();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.budgetItemPeriod = entity;
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
