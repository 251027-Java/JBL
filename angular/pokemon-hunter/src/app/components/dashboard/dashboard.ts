import { TitleCasePipe } from "@angular/common";
import { Component, inject } from "@angular/core";
import { PokemonService } from "../../services/pokemon-service";

@Component({
	selector: "app-dashboard",
	imports: [TitleCasePipe],
	templateUrl: "./dashboard.html",
	styleUrl: "./dashboard.css",
})
export class Dashboard {
	private pokemonService = inject(PokemonService);

	getCaught() {
		return this.pokemonService.getCaught();
	}
}
