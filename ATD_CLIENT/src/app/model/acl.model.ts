export interface AclModel {

    performative: string;
    senderAID: string;
    receiverAIDs: string[];
    replayToAID: string;
    content: string;
    contentObj: any;
    userArgs: any[];
    language: string;
    ontology: string;
    encoding: string;
    protocol: string;
    conversationId: string;
    replayWith: string;
    inReplyTo: string;
    replayBy: string;
}