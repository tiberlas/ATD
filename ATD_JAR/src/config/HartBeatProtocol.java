package config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import agentManager.OnLineAgentManagerlocal;
import rest.handShakeProtocol.HandShakeSender;
import rest.hartBeatProtocol.HartBeatSender;

@Singleton
@LocalBean
public class HartBeatProtocol {
	
	@EJB
	private OnLineAgentManagerlocal onLineManager;
	
	@Resource
	TimerService timerService;

	public void startTimer() {
		System.out.println("Timer service started.");
		timerService.createTimer(1000 * 40, 1000 * 40, "HART BEAT");

	}

	public void stopTimer() {
		Collection<Timer> timers = timerService.getTimers();
		for (Timer t : timers) {
			System.out.println(t.getInfo());
			t.cancel();
		}

		System.out.println("Timer service stopped.");
	}

	@Timeout
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
