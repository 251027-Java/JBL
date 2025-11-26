import type { Routes } from "@angular/router";
import { Dashboard } from "./components/dashboard/dashboard";
import { Login } from "./components/login/login";
import { authGuard } from "./guards/auth-guard";

export const routes: Routes = [
	{ path: "", component: Login },
	{
		path: "dashboard",
		component: Dashboard,
		canActivate: [authGuard],
	},
	{
		path: "catch",
		loadComponent: () =>
			import("./components/catch/catch").then((e) => e.Catch),
		canActivate: [authGuard],
	},
	{
		path: "**",
		redirectTo: "dashboard",
	},
];
