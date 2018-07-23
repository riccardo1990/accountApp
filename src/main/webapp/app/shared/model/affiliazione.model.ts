import { Moment } from 'moment';
import { ILogicAccount } from 'app/shared/model//logic-account.model';

export interface IAffiliazione {
    id?: number;
    codPuntoVenditaExt?: string;
    dataAffiliazione?: Moment;
    logicAccount?: ILogicAccount;
}

export class Affiliazione implements IAffiliazione {
    constructor(
        public id?: number,
        public codPuntoVenditaExt?: string,
        public dataAffiliazione?: Moment,
        public logicAccount?: ILogicAccount
    ) {}
}
