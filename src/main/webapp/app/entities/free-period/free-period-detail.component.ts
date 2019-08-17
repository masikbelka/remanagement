import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFreePeriod } from 'app/shared/model/free-period.model';

@Component({
  selector: 'jhi-free-period-detail',
  templateUrl: './free-period-detail.component.html'
})
export class FreePeriodDetailComponent implements OnInit {
  freePeriod: IFreePeriod;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ freePeriod }) => {
      this.freePeriod = freePeriod;
    });
  }

  previousState() {
    window.history.back();
  }
}
