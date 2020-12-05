export interface IEvent {
    title: string;
    description: string;
    date: Date;
    maxAmountOfAttendees: number;
    euroAmount: number;
}

export interface IEventResponse extends IEvent {
    id: number;
}