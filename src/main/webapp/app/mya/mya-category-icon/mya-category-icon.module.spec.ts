import { MyaCategoryIconModule } from './mya-category-icon.module';

describe('MyaCategoryIconModule', () => {
    let myaCategoryIconModule: MyaCategoryIconModule;

    beforeEach(() => {
        myaCategoryIconModule = new MyaCategoryIconModule();
    });

    it('should create an instance', () => {
        expect(myaCategoryIconModule).toBeTruthy();
    });
});
