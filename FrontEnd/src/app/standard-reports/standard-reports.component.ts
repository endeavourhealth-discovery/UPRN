import {Component} from '@angular/core';
import {ReportParamsDialogComponent} from './report-params-dialog/report-params-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FolderNode} from 'eds-angular4/dist/folder/models/FolderNode';
import {FolderItem} from 'eds-angular4/dist/folder/models/FolderItem';
import {ItemType} from 'eds-angular4/dist/folder/models/ItemType';
import {SecurityService} from 'eds-angular4';
import {ItemSummaryList} from 'eds-angular4/dist/library/models/ItemSummaryList';
import {ReportLibraryItem} from '../models/ReportLibraryItem';
import {LoggerService} from 'eds-angular4';
import {StandardReportsService} from './standard-reports.service';
import {LibraryService} from 'eds-angular4/dist/library';

@Component({
  selector: 'app-standard-reports',
  templateUrl: './standard-reports.component.html',
  styleUrls: ['./standard-reports.component.css']
})
export class StandardReportsComponent {
  selectedFolder: FolderNode;
  itemSummaryList: ItemSummaryList;
  selectedItem: FolderItem;
  libraryItem: ReportLibraryItem;

  constructor(
    protected $modal: NgbModal,
    protected logger: LoggerService,
    protected securityService: SecurityService,
    protected standardReportsService: StandardReportsService,
    protected libraryService: LibraryService) {
  }

  folderChanged($event) {
    this.selectedFolder = $event.selectedFolder;
    this.refresh();
  }

  refresh() {
    const vm = this;
    vm.libraryService.getFolderContents(vm.selectedFolder.uuid)
      .subscribe(
        (data) => {
          vm.itemSummaryList = data;
          vm.selectedFolder.loading = false;
        });
  }

  getSummaryList() {
    if (!this.itemSummaryList || !this.itemSummaryList.contents)
      return null;

    return this.itemSummaryList.contents
      .filter(item => item.type === ItemType.CountReport);
  }

  selectRow(item: FolderItem) {
    if (this.selectedItem === item)
      return;

    this.selectedItem = item;
    const vm = this;
    vm.libraryService.getLibraryItem<ReportLibraryItem>(item.uuid)
      .subscribe(
        (libraryItem) => vm.libraryItem = libraryItem,
        (error) => vm.logger.error('Error loading', error, 'Error')
      );
  }

  runReport() {
    const vm = this;
    if (vm.securityService.currentUser.organisation) {
      // Get param list from query
      ReportParamsDialogComponent.open(vm.$modal, this.libraryItem.countReport)
        .result.then((params) => {
          if (params)
            vm.executeReport(params);
        }
      );
    } else {
      vm.logger.warning('Select a service', null, 'No service selected');
    }
  }

  executeReport(params: Map<string, string>) {
    const vm = this;
    vm.standardReportsService.runReport(this.libraryItem.uuid, params)
      .subscribe(
        (result) => {
          vm.logger.success('Report successfully run', result, 'Run report');
          vm.libraryItem.countReport = result.countReport;
        },
        (error) => vm.logger.error('Error running report', error, 'Error')
      );
  }

  exportNHSNumber() {
    const vm = this;
    const reportId = this.libraryItem.uuid;
    vm.standardReportsService.exportNHSNumbers(reportId)
      .subscribe(
        (result) => {
          vm.logger.success('NHS numbers successfully exported', reportId, 'Export NHS numbers');
          const filename = 'Report_' + reportId + '_NHS.csv';
          vm.downloadFile(filename, result)
        },
        (error) => vm.logger.error('Error exporting NHS numbers', error, 'Error')
      );
  }

  exportData() {
    const vm = this;
    const reportId = this.libraryItem.uuid;
    vm.standardReportsService.exportData(reportId)
      .subscribe(
        (result) => {
          vm.logger.success('Data successfully exported', reportId, 'Export data');
          const filename = 'Report_' + reportId + '_Dat.csv';
          vm.downloadFile(filename, result)
        },
        (error) => vm.logger.error('Error exporting data', error, 'Error')
      );
  }

  downloadFile(filename: string, data: string) {
    const blob = new Blob([data], { type: 'text/plain' });
    window['saveAs'](blob, filename);
  }
}
