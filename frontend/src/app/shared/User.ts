import Book from "./Book";
import Trophy from './Trophy';

export default class User{
    id: number;
    name: string;
    points: number;
    books: Book[];
    trophies: Trophy[];
}

export class UserLogin{
    name: string;
    password: string;
}

export class UserUpdate{
    id: number;
    userName: string;
    oldPassword: string;
    newPassword: string;
}

export class PageUser {
    content: User[];
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