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
		
		System.out.println("HartBeat protokol started");			
		if(onLineManager.getAllHosts() == null || onLineManager.getAllHosts().isEmpty()) {
			return;
		}

		List<String> fallenNodes = new ArrayList<String>();
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
