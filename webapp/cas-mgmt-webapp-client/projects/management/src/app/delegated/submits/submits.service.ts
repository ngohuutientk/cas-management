/**
 * Created by tschmidt on 2/15/17.
 */
import {Injectable} from '@angular/core';
import {Branch, Service} from 'mgmt-lib';
import {Observable} from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class SubmitService extends Service {

  controller = 'api/submit';

  getSubmits(): Observable<Branch[]> {
    return this.get<Branch[]>(this.controller);
  }

  revert(name: string): Observable<string> {
    return this.getText(this.controller + '/revert/' + name);
  }

}
