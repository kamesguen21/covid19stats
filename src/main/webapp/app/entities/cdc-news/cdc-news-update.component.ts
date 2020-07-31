import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICdcNews, CdcNews } from 'app/shared/model/cdc-news.model';
import { CdcNewsService } from './cdc-news.service';

@Component({
  selector: 'jhi-cdc-news-update',
  templateUrl: './cdc-news-update.component.html',
})
export class CdcNewsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    pubDate: [],
    lastBuildDate: [],
    link: [],
  });

  constructor(protected cdcNewsService: CdcNewsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cdcNews }) => {
      this.updateForm(cdcNews);
    });
  }

  updateForm(cdcNews: ICdcNews): void {
    this.editForm.patchValue({
      id: cdcNews.id,
      title: cdcNews.title,
      description: cdcNews.description,
      pubDate: cdcNews.pubDate,
      lastBuildDate: cdcNews.lastBuildDate,
      link: cdcNews.link,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cdcNews = this.createFromForm();
    if (cdcNews.id !== undefined) {
      this.subscribeToSaveResponse(this.cdcNewsService.update(cdcNews));
    } else {
      this.subscribeToSaveResponse(this.cdcNewsService.create(cdcNews));
    }
  }

  private createFromForm(): ICdcNews {
    return {
      ...new CdcNews(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      pubDate: this.editForm.get(['pubDate'])!.value,
      lastBuildDate: this.editForm.get(['lastBuildDate'])!.value,
      link: this.editForm.get(['link'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICdcNews>>): void {
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
}
