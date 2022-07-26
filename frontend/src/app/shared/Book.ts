import User from './User';

export default class Book{
    id: number;
    name: string;
    pages: number;
    genre: string;
    urlImg:string;
    users?:User[];
}

export class PageBook {
    content: Book[];
    empty: boolean;
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    pageable: any;
    size: number;
    sort: any;
    totalElements: number;
    totalPage: number;
}