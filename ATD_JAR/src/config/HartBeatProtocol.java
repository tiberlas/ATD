package config;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import agentManager.OnLineAgentManagerlocal;
import rest.handShakeProtocol.HandShakeSender;
import rest.hartBeatProtocol.HartBeatSender;

@Singleton
public class HartBeatProtocol {
	
	@EJB
	private OnLineAgentManagerlocal onLineManager;

	@Schedule(hour = "*", minute = "*", second = "*/40", info = "hart beat", persistent=false)
	public void trigerHartBeat() {
		
		if(onLineManager.getAllHosts() == null || onLineManager.getAllHosts().isEmpty()) {
			System.out.println("NO NODES FOR HART BEAT");
			return;
		}

		System.out.println("HartBeat protokol started");			
		List<String> fallenNodes = new ArrayList<String>();
		onLineManager.getAllHosts().forEach(host -> {
			if(!HartBeatSender.checkNode(host)) {
				if(!HartBeatSender.checkNode(host)) {
					fallenNodes.add(host.getAlias());
					System.out.println("FALLEN NODE: " + host);
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
