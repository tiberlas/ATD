package messageManager;

import javax.ejb.Local;

import model.ACL;

@Local
public interface ACLSenderLocal {

	void sendACL(ACL acl);
}
