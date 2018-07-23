import { IUser } from 'app/core/user/user.model';
import { IAffiliazione } from 'app/shared/model//affiliazione.model';

export interface ILogicAccount {
    id?: number;
    codiceFiscale?: string;
    email?: string;
    telefono?: string;
    cellulare?: string;
    fotoContentType?: string;
    foto?: any;
    userJh?: IUser;
    logicRelations?: IAffiliazione[];
}

export class LogicAccount implements ILogicAccount {
    constructor(
        public id?: number,
        public codiceFiscale?: string,
        public email?: string,
        public telefono?: string,
        public cellulare?: string,
        public fotoContentType?: string,
        public foto?: any,
        public userJh?: IUser,
        public logicRelations?: IAffiliazione[]
    ) {}
}
