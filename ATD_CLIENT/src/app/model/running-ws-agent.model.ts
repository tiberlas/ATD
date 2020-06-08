import { RunningAgentModel } from './running-agent.model';

export interface RunningWsAgentModel {

    content: RunningAgentModel;
    status: boolean;
}