/// <reference path="../../../typings/jasmine.d.ts"/>

import {describe, it, beforeEach, expect} from 'angular2/testing';
import {Script} from "../app/api/script";

describe('Script', () => {
    it('has name', () => {
        let hero: Script = {id: 1, name: 'Super Cat', creationdate: "0", actions: [], location: null, presets: []};
        expect(hero.name).toEqual('Super Cat');
    });
    it('has id', () => {
        let hero: Script = {id: 1, name: 'Super Cat', creationdate: "0", actions: [], location: null, presets: []};
        expect(hero.id).toEqual(1);
    });
});