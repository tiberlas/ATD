# Projekat iz agentskih tehnologija
Agents for Tech Deals

## Uradje tacke
- komunikacija izmedju klijent agentski centar
- komunikacija izmedju agenta i agentskog centra
- komunikacija izmedju agentskih centara

## Tipovi agenata
- Test agent; sluzi za testiranje ACL poruka
- Agenti za Contract-net protokol (Iniator i Participant)

 ### Ukratko o projektu
 Agentski centri se pokrecu na mrezu sa adresom 192.168.56.X. Prilikom pokretanja novog centar pretrazuje se mreza i pronalaze se agentski centri. Ako se ne pronadje ni jedan centar novokreirani centar proglasava se za master-om. Svaki agentski centar ima spisak: svojih agenata, svojih ppodrzanih tipova, ostalih agentskih centara kao i njihovih agenata i tipova.
 Implementiran je contract-net protocol koji moze da se izvrsava nad agentima iz razlicitih agentskih centara.
 Implementirani su hand-shake i hart-beat prokoli.
 Front end je radjenu u angularu i exportovano je u plain JS i nalazi se u WAR-u.
