export interface IGlobalConfigurations {
  id?: string;
  name?: string;
  discription?: string;
  host?: string;
  code?: string;
}

export class GlobalConfigurations implements IGlobalConfigurations {
  constructor(public id?: string, public name?: string, public discription?: string, public host?: string, public code?: string) {}
}
