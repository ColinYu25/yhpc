package com.safetys.nbyhpc.domain.persistence.iface;

import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.criterion.DetachedCriteriaProxy;
import com.safetys.nbyhpc.domain.model.DaImageFile;

public interface ImageFilePersistenceIface {
	
	/**
	 * 保存文件信息
	 * @param stFile
	 * @throws ApplicationAccessException
	 */
	public void createFile(DaImageFile daImageFile) throws ApplicationAccessException;
	
	/**
	 * 更新文件信息
	 * @param daImageFile
	 * @throws ApplicationAccessException
	 */
	public void updateFile(DaImageFile daImageFile) throws ApplicationAccessException;
	
	/**
	 * 删除文件信息
	 * 如果已为假删除，则删除；不然则设为假删除
	 * @param daImageFile
	 * @param deleted
	 * @throws ApplicationAccessException
	 */
	public void deleteFile(DaImageFile daImageFile, boolean deleted) throws ApplicationAccessException;
	
	/**
	 * 假删除
	 * @param daImageFile
	 * @throws ApplicationAccessException
	 */
	public void deleteFile(DaImageFile daImageFile) throws ApplicationAccessException;

	/**
	 * 查询并列出文件
	 * @param detachedCriteriaProxy
	 * @param pagination
	 * @return
	 * @throws ApplicationAccessException
	 */
	public List<DaImageFile> loadFiles(DetachedCriteriaProxy detachedCriteriaProxy, 
			Pagination pagination) throws ApplicationAccessException;
	
	/**
	 * 查询并显示单个文件信息
	 * @param daImageFile
	 * @return
	 * @throws ApplicationAccessException
	 */
	public DaImageFile loadDaImageFile(DaImageFile daImageFile) throws ApplicationAccessException;
	
	/**
	 * @param daImageFile
	 * @throws ApplicationAccessException
	 */
	public void mergeDaImageFile(DaImageFile daImageFile) throws ApplicationAccessException;
	
	public DaImageFile findById(long id) throws Exception;
}
