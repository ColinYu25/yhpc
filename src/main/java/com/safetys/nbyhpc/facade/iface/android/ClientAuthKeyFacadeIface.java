package com.safetys.nbyhpc.facade.iface.android;

import com.safetys.nbyhpc.domain.model.android.ClientAuthKey;
import com.safetys.nbyhpc.facade.iface.BaseFacadeIface;

public interface ClientAuthKeyFacadeIface extends BaseFacadeIface<ClientAuthKey>{

	
	/**
	 * 保存密钥前更新对应的老密钥为DEAD
	 * 
	 * @param clientAuthKey
	 * @throws Exception
	 */
	public abstract void saveAfterClear(ClientAuthKey clientAuthKey);

	/**
	 * 根据密钥查找
	 * 
	 * @param key
	 * @throws Exception
	 */
	public abstract ClientAuthKey findByKey(String key);

	/**
	 * 查找用户最新密钥
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public abstract ClientAuthKey findByUser(Long userId);

}
