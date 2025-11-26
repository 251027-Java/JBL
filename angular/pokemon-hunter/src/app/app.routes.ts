import type { Routes } from "@angular/router";
import { Catch } from "./components/catch/catch";
import { Dashboard } from "./components/dashboard/dashboard";
import { Login } from "./components/login/login";

export const routes: Routes = [
	{ path: "", component: Login },
	{ path: "dashboard", component: Dashboard },
	{ path: "catch", component: Catch },
];
