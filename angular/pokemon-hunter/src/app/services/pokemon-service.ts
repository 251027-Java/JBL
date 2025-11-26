import { HttpClient, httpResource } from "@angular/common/http";
import { Injectable, inject, signal } from "@angular/core";
import { map, type Observable } from "rxjs";
import * as v from "valibot";
import type { Pokemon } from "../interfaces/pokemon";

const PokeApiPokemonSchema = v.object({
	id: v.number(),
	name: v.string(),
	sprites: v.object({
		front_default: v.string(),
	}),
	types: v.array(
		v.object({
			slot: v.number(),
			type: v.object({
				name: v.string(),
				url: v.string(),
			}),
		}),
	),
});

type PokeApiPokemon = v.InferOutput<typeof PokeApiPokemonSchema>;

@Injectable({
	providedIn: "root",
})
export class PokemonService {
	private http = inject(HttpClient);

	private resId = signal(this.randomPokemonId());
	readonly resource = httpResource(
		() => `https://pokeapi.co/api/v2/pokemon/${this.resId()}`,
		{
			parse: (e): Pokemon => {
				const out = v.parse(PokeApiPokemonSchema, e);
				return {
					id: out.id,
					name: out.name,
					sprite: out.sprites.front_default,
					types: out.types,
				};
			},
		},
	);

	private caught: Pokemon[] = [];

	private randomPokemonId() {
		return Math.floor(Math.random() * 1025) + 1;
	}

	getRandomPokemon(): Observable<Pokemon> {
		const id = this.randomPokemonId();

		return this.http.get(`https://pokeapi.co/api/v2/pokemon/${id}`).pipe(
			map((e) => v.parse(PokeApiPokemonSchema, e)),
			map<PokeApiPokemon, Pokemon>((data) => ({
				id: data.id,
				name: data.name,
				sprite: data.sprites.front_default,
				types: data.types,
			})),
		);
	}

	regeneratePokemon() {
		this.resId.set(this.randomPokemonId());
	}

	validateCurrent() {
		const last = this.caught.at(-1);

		if (last && last.id === this.resId()) {
			this.regeneratePokemon();
		}
	}

	catchCurrent() {
		if (this.resource.hasValue()) {
			this.caught.push(this.resource.value());
		}
	}

	getCaught() {
		return this.caught;
	}
}
