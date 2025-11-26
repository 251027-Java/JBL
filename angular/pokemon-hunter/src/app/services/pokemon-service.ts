import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { map, type Observable } from "rxjs";
import type { Pokemon } from "../interfaces/pokemon";

@Injectable({
	providedIn: "root",
})
export class PokemonService {
	http = inject(HttpClient);

	getRandomPokemon(): Observable<Pokemon> {
		const id = Math.floor(Math.random() * 1025) + 1;

		return this.http.get(`https://pokeapi.co/api/v2/pokemon/${id}`).pipe(
			// biome-ignore lint/suspicious/noExplicitAny: probably should use some validation library, but do this for now
			map<any, Pokemon>((data) => ({
				id: data.id,
				name: data.name,
				sprite: data.sprites.front_default,
				types: data.types,
			})),
		);
	}
}
