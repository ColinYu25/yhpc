package com.safetys.nbyhpc.facade.impl.android;

import java.util.List;

import com.safetys.nbyhpc.domain.model.android.ClientAuthKey;
import com.safetys.nbyhpc.domain.persistence.iface.android.ClientAuthKeyPersistence;
import com.safetys.nbyhpc.facade.iface.android.ClientAuthKeyFacadeIface;
import com.safetys.nbyhpc.facade.impl.BaseFacadeImpl;

public class ClientAuthKeyFacadeImpl extends BaseFacadeImpl<ClientAuthKey> implements ClientAuthKeyFacadeIface {

	private ClientAuthKeyPersistence clientAuthKeyPersistence;
	
	public void saveAfterClear(ClientAuthKey clientAuthKey) {
		String hql = "update ClientAuthKey set status=? where userId=?";
		clientAuthKeyPersistence.executeHql(hql, new Object[]{ClientAuthKey.DEAD, clientAuthKey.getUserId()});
		clientAuthKeyPersistence.create(clientAuthKey);
	}

	@Override
	public ClientAuthKey findByKey(String key) {
		String hql = "from ClientAuthKey where authKey = ? and deleted=false";
		List<ClientAuthKey> list = clientAuthKeyPersistence.findByHql(hql, new Object[]{key});
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ClientAuthKey findByUser(Long userId) {
		String hql = " from ClientAuthKey where deleted=false and status=? order by createTime desc";
		List<ClientAuthKey> list = clientAuthKeyPersistence.findByHql(hql, new Object[]{ClientAuthKey.ACTIVE});
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public void setClientAuthKeyPersistence(ClientAuthKeyPersistence clientAuthKeyPersistence) {
		this.clientAuthKeyPersistence = clientAuthKeyPersistence;
	}

	@Override
	public ClientAuthKeyPersistence getService() {
		return clientAuthKeyPersistence;
	}
	
}
