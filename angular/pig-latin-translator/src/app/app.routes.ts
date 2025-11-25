import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard';
import { Translate } from './components/translate/translate';

export const routes: Routes = [
    { path: '', component: Dashboard },
    { path: 'translate', component: Translate },
];
