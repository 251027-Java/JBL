import { Component, inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";

@Component({
	selector: "app-login",
	imports: [FormsModule],
	templateUrl: "./login.html",
	styleUrl: "./login.css",
})
export class Login {
	username = "";
	password = "";

	router = inject(Router);

	login() {
		if (this.username === "user" && this.password === "password") {
			this.router.navigateByUrl("/dashboard");
		} else {
			alert("invalid username or password");
		}
	}
}
