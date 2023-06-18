import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { LoginLogDTO } from 'src/app/shared/models/LoginLog';
import { LoginLogsService } from 'src/app/shared/services/login-logs.service';
import {MatPaginator} from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-login-logs',
  templateUrl: './login-logs.component.html',
  styleUrls: ['./login-logs.component.css']
})
export class LoginLogsComponent implements AfterViewInit {

  displayedColumns: string[] = ['id', 'executionTime', 'email', 'message'];
  dataSource!: MatTableDataSource<LoginLogDTO>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private logsService: LoginLogsService) { }

  ngAfterViewInit(): void {
    this.logsService.getAll()
      .subscribe((res) => {
        console.log(res);
        this.dataSource = new MatTableDataSource<LoginLogDTO>(res);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
