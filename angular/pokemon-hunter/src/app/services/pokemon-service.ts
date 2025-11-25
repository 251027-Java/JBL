import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Pokemon } from '../interfaces/pokemon';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PokemonService {
  http = inject(HttpClient);

  getPokemon() {
    const id = Math.floor(Math.random() * 1025) + 1;

    return this.http.get(`https://pokeapi.co/api/v2/pokemon/${id}`).pipe(map<any, Pokemon>(data => ({
      id: data.id,
      name: data.name,
      sprite: data.sprites.front_default,
      types: data.types,
    })));
  }
}
