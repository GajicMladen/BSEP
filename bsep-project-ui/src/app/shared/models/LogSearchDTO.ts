export interface LogSearchDTO{
    realestateID :number,
    deviceID :number ,
    startDate :string | null,
    endDate :string | null,
    onlyAlarms : boolean,
}