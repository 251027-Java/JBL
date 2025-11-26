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

	private pokemonService = inject(PokemonService);

	caughtPokemon = false;

	ngOnInit(): void {
		this.pokemonService.validateCurrent();
	}

	getRandomPokemon() {
		// this.pokemon$ = this.pokemonService.getRandomPokemon();
		this.pokemonService.regeneratePokemon();
		this.caughtPokemon = false;
	}

	get pokemonResource() {
		return this.pokemonService.resource;
	}

	catchPokemon() {
		if (this.caughtPokemon) return;

		if (this.pokemonResource.hasValue()) {
			this.pokemonService.catchCurrent();
			this.caughtPokemon = true;
		}
	}
}
