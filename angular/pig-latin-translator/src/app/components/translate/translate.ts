import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslatorService } from '../../services/translator-service';

@Component({
  selector: 'app-translate',
  imports: [FormsModule],
  templateUrl: './translate.html',
  styleUrl: './translate.css',
})
export class Translate {
  englistText = '';
  pigLatinText = '';

  translatorService = inject(TranslatorService);


  translate() {
    if (!this.englistText) {
      alert('no text found');
      return;
    }

    if (this.englistText === 'JavaScript') {
      alert('watch your language');
      return;
    }

    const words = this.englistText.split(' ');

    this.pigLatinText = words.map(e => {
      if (/^[^a-z]/i.test(e)) {
        return e;
      }

      const parts = e.match(/([a-z]+)(.*)/i);

      if (!parts) {
        return e;
      }

      if (/^[aeiou]/i.test(parts[1])) {
        return parts[1] + 'way' + parts[2];
      }

      const match = parts[1].match(/^([^aeiou]+)(.*)/i);

      if (!match) {
        return e;
      }

      return match[2] + match[1] + 'ay' + parts[2];
    }).join(' ');

    this.translatorService.translationCounter++;
  }
}
