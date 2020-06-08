import { AgentTypeModel } from './agent-type.model';

export interface AgentWsTypeModel {

    content: AgentTypeModel;
    status: boolean;
}