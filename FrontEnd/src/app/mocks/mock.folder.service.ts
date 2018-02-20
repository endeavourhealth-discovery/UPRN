import { Injectable } from '@angular/core';
import {AbstractMockObservable} from './mock.observable';
import {FolderType} from 'eds-angular4/dist/folder/models/FolderType';
import {FolderNode} from 'eds-angular4/dist/folder/models/FolderNode';

@Injectable()
export class MockFolderService extends AbstractMockObservable {
  getFolders(folderType: FolderType, folderUuid: string) {

    if (!folderUuid) // ROOT requested
      this._fakeContent = {
        folders: [
          {
            uuid: '',
            folderName: 'Root',
          } as FolderNode
        ]
      };
    else
      this._fakeContent = [{uuid: ''}];

    return this;
  }
}
