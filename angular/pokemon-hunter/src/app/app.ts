import { Component, inject, signal } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { Navbar } from "./components/navbar/navbar";
import { AuthService } from "./services/auth-service";

@Component({
	selector: "app-root",
	imports: [RouterOutlet, Navbar],
	templateUrl: "./app.html",
	styleUrl: "./app.css",
})
export class App {
	protected readonly title = signal("pokemon-hunter");

	protected authService = inject(AuthService);
}
