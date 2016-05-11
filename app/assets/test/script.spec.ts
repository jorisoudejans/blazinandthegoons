/// <reference path="../../../typings/jasmine.d.ts"/>

import { Script } from './app/api/script';
import {describe, it, beforeEach, expect} from 'angular2/testing';

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