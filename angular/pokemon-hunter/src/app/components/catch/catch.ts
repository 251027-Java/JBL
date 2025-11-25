import { Component, inject, OnInit } from '@angular/core';
import { PokemonService } from '../../services/pokemon-service';
import { AsyncPipe } from '@angular/common';
import { Observable } from 'rxjs';
import { Pokemon } from '../../interfaces/pokemon';

@Component({
  selector: 'app-catch',
  imports: [AsyncPipe],
  templateUrl: './catch.html',
  styleUrl: './catch.css',
})
export class Catch implements OnInit {
  pokemon$!: Observable<Pokemon>;

  pokemonService = inject(PokemonService);

  ngOnInit(): void {
    this.getPokemon();
  }

  getPokemon() {
    this.pokemon$ = this.pokemonService.getPokemon();
  }
}

