import { Component, inject } from '@angular/core';
import { TranslatorService } from '../../services/translator-service';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  constructor(protected translatorService: TranslatorService) { }
}
