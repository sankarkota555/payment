import { Injectable, EventEmitter } from '@angular/core';

@Injectable()
export class CustomEventManagerService {

    private itemChangeEventEmitter: EventEmitter<number>;

    constructor() {
        this.itemChangeEventEmitter = new EventEmitter<number>();
    }

    /**
     * Emits custom event for bill item change.
     * @param index index of bill item.
     */
    emitItemChangeEvent(index: number): void {
        console.log("emitting item change event form service");
        this.itemChangeEventEmitter.emit(index);
    }

    /**
     * Gives Item change event emitter.
     */
    getItemChangeEvent(): EventEmitter<number> {
        return this.itemChangeEventEmitter;
    }
}