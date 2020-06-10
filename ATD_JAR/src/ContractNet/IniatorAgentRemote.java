package ContractNet;

import java.util.Set;

import javax.ejb.Remote;

import agents.Agent;
import model.AID;

@Remote
public interface IniatorAgentRemote extends Agent {

	void startedCFP(Set<AID> aids);
	void AcceptBest();
}
