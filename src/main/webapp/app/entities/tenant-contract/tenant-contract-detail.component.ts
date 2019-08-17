import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITenantContract } from 'app/shared/model/tenant-contract.model';

@Component({
  selector: 'jhi-tenant-contract-detail',
  templateUrl: './tenant-contract-detail.component.html'
})
export class TenantContractDetailComponent implements OnInit {
  tenantContract: ITenantContract;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tenantContract }) => {
      this.tenantContract = tenantContract;
    });
  }

  previousState() {
    window.history.back();
  }
}
