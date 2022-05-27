import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  username:string = "user1";
  password:string = "password1";

  constructor() { }

}
