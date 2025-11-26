import type { Routes } from "@angular/router";
import { Catch } from "./components/catch/catch";
import { Dashboard } from "./components/dashboard/dashboard";
import { Login } from "./components/login/login";
import { authGuard } from "./guards/auth-guard";

export const routes: Routes = [
	{ path: "", component: Login },
	{ path: "dashboard", component: Dashboard, canActivate: [authGuard] },
	{ path: "catch", component: Catch, canActivate: [authGuard] },
];
