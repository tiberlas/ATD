package config;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import agentManager.OnLineAgentManagerlocal;
import rest.handShakeProtocol.HandShakeSender;
import rest.hartBeatProtocol.HartBeatSender;

@Stateless
@LocalBean
public class HartBeatProtocol {
	
	@EJB
	private OnLineAgentManagerlocal onLineManager;
	
	@Schedule(hour = "*", minute = "*", second = "*/45", info = "check other nodes")
	public void trigerHartBeat() {
		
		System.out.println("HartBeat protokol started");

		List<String> fallenNodes = new ArrayList<>();
		onLineManager.getAllHosts().forEach(host -> {
			if(!HartBeatSender.checkNode(host)) {
				if(!HartBeatSender.checkNode(host)) {
					fallenNodes.add(host.getAlias());
				}
			}
		});
		
		//obrisemo sve pale cvorove iz zapisa od ovog host-a
		fallenNodes.forEach(node -> {
			onLineManager.removeHost(node);
		});
		
		//preostalim cvorovima javimo da su ovi cvorovi pali
		onLineManager.getAllHosts().forEach(host -> {
			fallenNodes.forEach(node -> {
				HandShakeSender.remove(host, node);
			});
		});
	}
}
