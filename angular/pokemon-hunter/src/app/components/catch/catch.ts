import { TitleCasePipe } from "@angular/common";
import { Component, inject } from "@angular/core";
import type { Observable } from "rxjs";
import type { Pokemon } from "../../interfaces/pokemon";
import { PokemonService } from "../../services/pokemon-service";

@Component({
	selector: "app-catch",
	imports: [TitleCasePipe],
	templateUrl: "./catch.html",
	styleUrl: "./catch.css",
})
export class Catch {
	pokemon$!: Observable<Pokemon>;

	pokemonService = inject(PokemonService);
	caughtPokemon = false;

	getRandomPokemon() {
		// this.pokemon$ = this.pokemonService.getRandomPokemon();
		this.pokemonService.regeneratePokemon();
		this.caughtPokemon = false;
	}

	catchPokemon() {
		const pokemon = this.pokemonService.resource.value();

		if (pokemon) {
			this.pokemonService.caught.push(pokemon);
			this.caughtPokemon = true;
		}
	}
}
