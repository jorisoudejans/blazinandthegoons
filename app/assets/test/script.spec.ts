import { Script } from './app/api/script';
describe('Script', () => {
    it('has name', () => {
        let hero: Script = {id: 1, name: 'Super Cat'};
        expect(hero.name).toEqual('Super Cat');
    });
    it('has id', () => {
        let hero: Script = {id: 1, name: 'Super Cat'};
        expect(hero.id).toEqual(1);
    });
});