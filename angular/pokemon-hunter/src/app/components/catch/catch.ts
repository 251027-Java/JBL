import { AsyncPipe } from "@angular/common";
import { Component, inject, type OnInit } from "@angular/core";
import type { Observable } from "rxjs";
import type { Pokemon } from "../../interfaces/pokemon";
import { PokemonService } from "../../services/pokemon-service";

@Component({
	selector: "app-catch",
	imports: [AsyncPipe],
	templateUrl: "./catch.html",
	styleUrl: "./catch.css",
})
export class Catch implements OnInit {
	pokemon$!: Observable<Pokemon>;

	pokemonService = inject(PokemonService);

	ngOnInit(): void {
		this.getRandomPokemon();
	}

	getRandomPokemon() {
		this.pokemon$ = this.pokemonService.getRandomPokemon();
		this.pokemonService.regeneratePokemon();
	}
}
