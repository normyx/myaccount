/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyaccountTestModule } from '../../../test.module';
import { BudgetItemPeriodDetailComponent } from 'app/entities/budget-item-period/budget-item-period-detail.component';
import { BudgetItemPeriod } from 'app/shared/model/budget-item-period.model';

describe('Component Tests', () => {
    describe('BudgetItemPeriod Management Detail Component', () => {
        let comp: BudgetItemPeriodDetailComponent;
        let fixture: ComponentFixture<BudgetItemPeriodDetailComponent>;
        const route = ({ data: of({ budgetItemPeriod: new BudgetItemPeriod(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyaccountTestModule],
                declarations: [BudgetItemPeriodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BudgetItemPeriodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BudgetItemPeriodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.budgetItemPeriod).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
