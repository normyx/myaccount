import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'jhi-category-icon',
    templateUrl: './category-icon.component.html',
    styleUrls: ['category-icon.scss']
})
export class CategoryIconComponent implements OnInit {
    @Input() categoryId: string;
    @Input() iconSize: string;

    constructor() {}

    ngOnInit() {}

    getCategoryIcon(): string {
        let iconString: string;
        switch (this.categoryId) {
            case '1': {
                iconString = 'home';
                break;
            }
            case '2': {
                iconString = 'shopping-cart';
                break;
            }
            case '3': {
                iconString = 'money-bill-alt';
                break;
            }
            case '4': {
                iconString = 'child';
                break;
            }
            case '5': {
                iconString = 'gamepad';
                break;
            }
            case '6': {
                iconString = 'university';
                break;
            }
            case '7': {
                iconString = 'utensils';
                break;
            }
            case '8': {
                iconString = 'car';
                break;
            }
            case '9': {
                iconString = 'handshake';
                break;
            }
            case '10': {
                iconString = 'stethoscope';
                break;
            }
            case '11': {
                iconString = 'euro-sign';
                break;
            }
            case '12': {
                iconString = 'suitecase';
                break;
            }
            case '13': {
                iconString = 'question';
                break;
            }
            default: {
                iconString = 'times';
                break;
            }
        }
        return iconString;
    }
}
