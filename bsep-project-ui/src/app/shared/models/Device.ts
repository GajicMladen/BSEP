import { DeviceType } from "../enums/DeviceType";
import { Realestate } from "./Realestate";

export interface Device{
    deviceID:number,
    deviceType:DeviceType,
    readData :boolean,
    description: string,
    realestate: Realestate
}