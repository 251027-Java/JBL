import type { Routes } from "@angular/router";
import { Login } from "./components/login/login";
import { authGuard } from "./guards/auth-guard";

export const routes: Routes = [
	{ path: "", component: Login },
	{
		path: "dashboard",
		loadComponent: () =>
			import("./components/dashboard/dashboard").then((e) => e.Dashboard),
		canActivate: [authGuard],
	},
	{
		path: "catch",
		loadComponent: () =>
			import("./components/catch/catch").then((e) => e.Catch),
		canActivate: [authGuard],
	},
];
