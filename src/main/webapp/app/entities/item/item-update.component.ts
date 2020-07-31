import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IItem, Item } from 'app/shared/model/item.model';
import { ItemService } from './item.service';
import { ICdcNews } from 'app/shared/model/cdc-news.model';
import { CdcNewsService } from 'app/entities/cdc-news/cdc-news.service';

@Component({
  selector: 'jhi-item-update',
  templateUrl: './item-update.component.html',
})
export class ItemUpdateComponent implements OnInit {
  isSaving = false;
  cdcnews: ICdcNews[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    link: [],
    pubDate: [],
    media: [],
    cdcNews: [],
  });

  constructor(
    protected itemService: ItemService,
    protected cdcNewsService: CdcNewsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ item }) => {
      this.updateForm(item);

      this.cdcNewsService.query().subscribe((res: HttpResponse<ICdcNews[]>) => (this.cdcnews = res.body || []));
    });
  }

  updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      title: item.title,
      description: item.description,
      link: item.link,
      pubDate: item.pubDate,
      media: item.media,
      cdcNews: item.cdcNews,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  private createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      link: this.editForm.get(['link'])!.value,
      pubDate: this.editForm.get(['pubDate'])!.value,
      media: this.editForm.get(['media'])!.value,
      cdcNews: this.editForm.get(['cdcNews'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICdcNews): any {
    return item.id;
  }
}
