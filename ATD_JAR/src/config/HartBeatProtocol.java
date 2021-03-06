package config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import agentManager.OnLineAgentManagerlocal;
import model.Host;
import rest.handShakeProtocol.HandShakeSender;
import rest.hartBeatProtocol.HartBeatSender;

@Singleton
public class HartBeatProtocol {
	
	@EJB
	private OnLineAgentManagerlocal onLineManager;

	@Schedule(hour = "*", minute = "*", second = "*/40", info = "hart beat", persistent=false)
	public void trigerHartBeat() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Set<Host> nodes = new HashSet<Host>();
				
				if(onLineManager.getAllHosts() != null) {
					nodes.addAll(onLineManager.getAllHosts());
				}
				if(onLineManager.getMaster() != null) {
					nodes.add(onLineManager.getMaster());
				}
				
				System.out.println("NODES FOR HART BEAT "+ nodes);
				if(nodes.isEmpty()) {
					System.out.println("NO NODES FOR HART BEAT");	
					return;
				}
				
				System.out.println("HartBeat protokol started");			
				List<String> fallenNodes = new ArrayList<String>();
				nodes.forEach(host -> {
					if(!HartBeatSender.checkNode(host)) {
						if(!HartBeatSender.checkNode(host)) {
							fallenNodes.add(host.getAlias());
							System.out.println("FALLEN NODE: " + host);
						}
					}
				});
				
				//obrisemo sve pale cvorove iz zapisa od ovog host-a
				fallenNodes.forEach(alias -> {
					if(alias.equals("master")) {
						onLineManager.setMaster(null);
						System.out.println("MASTER NODE HAS FALLEN");
					} else {
						onLineManager.removeHost(alias);
					}
				});
				
				//preostalim cvorovima javimo da su ovi cvorovi pali
				onLineManager.getAllHosts().forEach(host -> {
					fallenNodes.forEach(node -> {
						HandShakeSender.remove(host, node);
					});
				});
				if(onLineManager.getMaster()!=null) {
					fallenNodes.forEach(node -> {
						HandShakeSender.remove(onLineManager.getMaster(), node);
					});
				}
			}
		}).start();
		
	}
}
