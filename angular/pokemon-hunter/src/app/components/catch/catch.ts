import { TitleCasePipe } from "@angular/common";
import { Component, inject, type OnInit } from "@angular/core";
import type { Observable } from "rxjs";
import type { Pokemon } from "../../interfaces/pokemon";
import { PokemonService } from "../../services/pokemon-service";

@Component({
	selector: "app-catch",
	imports: [TitleCasePipe],
	templateUrl: "./catch.html",
	styleUrl: "./catch.css",
})
export class Catch implements OnInit {
	pokemon$!: Observable<Pokemon>;

	pokemonService = inject(PokemonService);
	caughtPokemon = false;

	ngOnInit(): void {
		this.getRandomPokemon();
	}

	getRandomPokemon() {
		// this.pokemon$ = this.pokemonService.getRandomPokemon();
		this.pokemonService.regeneratePokemon();
		this.caughtPokemon = false;
	}

	catchPokemon() {
		const pokemon = this.pokemonService.resource.value();

		if (pokemon) {
			this.pokemonService.caught.push(pokemon);
			alert(`caught ${pokemon.name}`);

			this.caughtPokemon = true;
		}
	}
}
