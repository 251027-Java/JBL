import { Component, input, Input } from '@angular/core';

@Component({
  selector: 'app-child-component',
  imports: [],
  templateUrl: './child-component.html',
  styleUrl: './child-component.css',
})
export class ChildComponent {
  index = input(0);
  
  // decorator
  // @Input index = 0;
}
