import {translate} from "../components/Translate";
import msg from "../i18n/msg";

export const KNOWN_SALTS = [
{ id: 1, name: translate(msg.tableSalt), ca: 0, mg: 0, na: 39, so4: 0, cl: 61, hco3: 0, },
{ id: 2, name: translate(msg.gypsum), ca: 23, mg: 0, na: 0, so4: 56, cl: 0, hco3: 0, },
{ id: 3, name: translate(msg.epsomSalt), ca: 0, mg: 10, na: 0, so4: 39, cl: 0, hco3: 0, },
{ id: 4, name: translate(msg.calciumChloride), ca: 36, mg: 0, na: 0, so4: 0, cl: 64, hco3: 0, },
{ id: 5, name: translate(msg.bakingSoda), ca: 0, mg: 0, na: 27, so4: 0, cl: 0, hco3: 72, },
{ id: 6, name: translate(msg.chalk), ca: 40, mg: 0, na: 0, so4: 0, cl: 0, hco3: 122, },
{ id: 7, name: translate(msg.picklingLime), ca: 54, mg: 0, na: 0, so4: 0, cl: 0, hco3: 164, },
{ id: 8, name: translate(msg.magnesiumChloride), ca: 0, mg: 12, na: 0, so4: 0, cl: 35, hco3: 0, },
];

