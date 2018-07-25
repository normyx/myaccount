import { Component, OnInit, Input } from '@angular/core';
import { AccountCategoryMonthReportService } from './account-category-month-report.service';
import { IAccountCategoryMonthReport } from './account-category-month-report.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'jhi-account-category-month-report',
  templateUrl: './account-category-month-report.component.html'
})
export class AccountCategoryMonthReportComponent implements OnInit {

  @Input() categoryId: number;
  accountCategoryMonthReport: IAccountCategoryMonthReport;
  data: any;

  constructor(
    private accountCategoryMonthReportService: AccountCategoryMonthReportService
  ) { }

  loadAll() {
    if (this.categoryId) {
      this.accountCategoryMonthReportService
        .getData(this.categoryId)
        .subscribe(
          (res: HttpResponse<IAccountCategoryMonthReport>) => (this.accountCategoryMonthReport = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      this.data = {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
          {
            label: 'First Dataset',
            data: [65, 59, 80, 81, 56, 55, 40]
          },
          {
            label: 'Second Dataset',
            data: [28, 48, 40, 19, 86, 27, 90]
          }
        ]
      };
    }
  }

  ngOnInit() {
    this.loadAll();
  }
  private onError(errorMessage: string) {

  }
}
