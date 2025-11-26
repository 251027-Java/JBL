import { Injectable } from "@angular/core";

@Injectable({
	providedIn: "root",
})
export class AuthService {
	private loggedIn = false;

	login() {
		this.loggedIn = true;
	}

	isLoggedIn() {
		return this.loggedIn;
	}
}
