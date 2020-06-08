import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AclService } from 'src/app/service/acl.service';
import { RunningAgentService } from 'src/app/service/running-agent.service';

@Component({
	selector: 'app-create-acl',
	templateUrl: './create-acl.component.html',
	styleUrls: ['./create-acl.component.css']
})
export class CreateAclComponent implements OnInit {

	performativeArray: string[];
	selectedPerformative: string;
	selectedReceiver: string;
	receiverArray: string[];
	selectedSender: string;
	senderArray: string[];
	selectedReplayTo: string;
	replayToArray: string[];

	constructor(private acl: AclService, private agents: RunningAgentService) { }

	ngOnInit(): void {
		this.acl.getPerformatives().subscribe(
			(data: string[]) => {
				this.performativeArray = data;
			}
		);

		this.receiverArray = [];
		this.replayToArray = [];
		this.senderArray = [];
		for (let ag of this.agents.runningAgentArray) {
			let aid = ag.agentName + "@" + ag.hostAlias + " type:" + ag.agentTypeName + ":" + ag.agentTypeModule;
			this.receiverArray.push(aid);
			this.replayToArray.push(aid);
			this.senderArray.push(aid);
		}
	}

	onSendACL(form: NgForm) {
		let performative = form.value.InputPerformative;
		let receiver = form.value.InputReceiver;
		let content = form.value.InputContent;
		let sender = form.value.InputSender;
		let replayTo = form.value.InputReplayTo ? form.value.InputReplayTo : null;
		let language = form.value.InputLanguage ? form.value.InputLanguage : null;
		let encoding = form.value.InputEncoding ? form.value.InputEncoding : null;
		let ontology = form.value.InputOntology ? form.value.InputOntology : null;
		let protocol = form.value.InputProtocol ? form.value.InputProtocol : null;
		let replayWith = form.value.InputReplayWith ? form.value.InputReplayWith : null;
		let inReplayTo = form.value.InputInReplayTo ? form.value.InputInReplayTo : null;
		let repalyBy = form.value.InputReplayBy ? form.value.InputReplayBy : null;
		let conversation = form.value.InputConversation ? form.value.InputConversation : null;

		this.acl.sendAcl({
			performative: performative,
			receiverAIDs: [receiver],
			content: content,
			senderAID: sender,
			replayBy: repalyBy,
			replayToAID: replayTo,
			language: language,
			encoding: encoding,
			ontology: ontology,
			protocol: protocol,
			replayWith: replayWith,
			inReplyTo: inReplayTo,
			conversationId: conversation,
			contentObj: null,
			userArgs: null
		});
	}

}
