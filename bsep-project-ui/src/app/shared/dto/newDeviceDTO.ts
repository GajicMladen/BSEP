import { DeviceType } from "../enums/DeviceType";

export interface NewDeviceDTO{
    deviceType:DeviceType,
    deviceDescription: string,
    realestateID : number
}