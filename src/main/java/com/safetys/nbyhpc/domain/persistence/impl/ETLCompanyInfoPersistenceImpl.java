package com.safetys.nbyhpc.domain.persistence.impl;

import java.util.Date;
import java.util.List;

import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.framework.orm.hibernate3.PersistenceDao;
import com.safetys.nbyhpc.domain.model.ETLCompany;
import com.safetys.nbyhpc.domain.model.TCompany;
import com.safetys.nbyhpc.domain.persistence.iface.ETLCompanyInfoPersistenceIface;

/**
 * @author llj
 * @create_time: 2014-4-23
 * @Description: 更新及获取本地同步号信息
 */
public class ETLCompanyInfoPersistenceImpl implements ETLCompanyInfoPersistenceIface {

	private PersistenceDao<TCompany> persistenceDao;

	public void executeSQLUpdate(String sql) throws ApplicationAccessException {
		System.out.println("sql: " + sql);
		persistenceDao.executeQuery(sql);
	}
	
	public void executeHQLUpdate(String hql) throws ApplicationAccessException {
		System.out.println("hql: " + hql);
		persistenceDao.executeHQLUpdate(hql);
	}
	
	
	public List<TCompany> getSwapList() {
		String hql = "from TCompany com where com.isSynchro=1 ";
		return persistenceDao.executeHQLQuery(hql);
	}

	public void updateSynStatus(List<Object> idList) {
		StringBuilder hqlStringBuilder = new StringBuilder();
		hqlStringBuilder.append("update TCompany com set com.isSynchro=0 where com.id in (");
		for (Object id : idList) {
			hqlStringBuilder.append(id + ",");
		}
		hqlStringBuilder.delete(hqlStringBuilder.length() - 1, hqlStringBuilder.length());
		hqlStringBuilder.append(")");
		persistenceDao.executeHQLUpdate(hqlStringBuilder.toString());
	}

	public void save(ETLCompany entity) throws Exception {
		if (entity.getUUID() != null) {
			
			String hql = "from TCompany com where com.uuid ='" + entity.getUUID() + "'";
			System.out.println("uuid相同 更新: " + hql);
			List<TCompany> tcompany = persistenceDao.executeHQLQuery(hql);
			if (tcompany.size() > 0) { // uuid相同,则更新 (中心库高优先级字段,其他字段不更新)
				TCompany tc =tcompany.get(0);
				tc.setId(tcompany.get(0).getId());
				tc.setUuid(entity.getUUID());
				tc.setCompanyName(entity.getCOMPANY_NAME());
				tc.setBusinessRegNum(entity.getBUSINESS_REG_NUM());
				tc.setOrgCode(entity.getORG_CODE()); 
				tc.setRegAddress(entity.getREG_ADDRESS()); 
				tc.setEstablishmentDay(entity.getESTABLISHMENT_DAY());
				tc.setLegalPerson(entity.getLEGAL_PERSON());
				tc.setPrincipalPerson(entity.getPRINCIPAL_PERSON());// 主要负责人
				tc.setBusinessScope(entity.getBUSINESS_SCOPE()); // 经营范围
				tc.setNationalEconomicType1(entity.getNATIONAL_ECONOMIC_TYPE1());
				tc.setNationalEconomicType2(entity.getNATIONAL_ECONOMIC_TYPE2());
				tc.setEconomicType1(entity.getECONOMIC_TYPE1());
				tc.setEconomicType2(entity.getECONOMIC_TYPE2());
				tc.setManagementLevel1(entity.getMANAGEMENT_LEVEL1());
				tc.setManagementLevel2(entity.getMANAGEMENT_LEVEL2());
				tc.setIsSynchro(0);
				tc.setIsSYN(1l);
				tc.setZjId(entity.getZJ_ID());//设置质检id
				tc.setGsId(entity.getGS_ID());//设置工商id
				tc.setDeptCode(entity.getDEPT_CODE());
				persistenceDao.update(tc);
			} else { // 通过uuid查询不到，再通过名称相同工商注册号或组织机构代码  更新uuid (中心库高优先级字段,其他字段不更新)
				//hql = "from TCompany com where com.uuid is null ";
				//StringBuffer sb=new StringBuffer(" and (com.companyName='" + entity.getCOMPANY_NAME() + "' ");
				//if(entity.getBUSINESS_REG_NUM()!=null&&!"".equals(entity.getBUSINESS_REG_NUM())&&!"null".equals(entity.getBUSINESS_REG_NUM())){
				//	sb.append(" or  com.businessRegNum='" +entity.getBUSINESS_REG_NUM() + "' ");
				//}
				//if(entity.getORG_CODE()!=null&&!"".equals(entity.getORG_CODE())&&!"null".equals(entity.getORG_CODE())){
				//	sb.append(" or  com.orgCode='"+entity.getORG_CODE()+"'");
				//}
				//sb.append(")");
				//hql=hql+sb.toString();
				TCompany tc =null;
				//先根据名称
			    if(entity.getCOMPANY_NAME()!=null&&!"".equals(entity.getCOMPANY_NAME())&&!"null".equals(entity.getCOMPANY_NAME())){
			    	    String hql1 = "from TCompany com where com.uuid is null and com.companyName='" +entity.getCOMPANY_NAME() + "' ";
			    	    System.out.println("uuid为空 名称相同   更新: " + hql1);
						List<TCompany> tcompany1 = persistenceDao.executeHQLQuery(hql1);
						if(tcompany1!=null&&tcompany1.size()>0){
							tc=tcompany1.get(0);
						}
				}
			    if(tc==null){
			    	 //先根据工商注册号
				    if(entity.getBUSINESS_REG_NUM()!=null&&!"".equals(entity.getBUSINESS_REG_NUM())&&!"null".equals(entity.getBUSINESS_REG_NUM())){
				    	    String hql2  = "from TCompany com where com.uuid is null and com.businessRegNum='" +entity.getBUSINESS_REG_NUM() + "'  ";
				    	    System.out.println("uuid为空 工商注册号  更新: " + hql2);
							List<TCompany> tcompany2 = persistenceDao.executeHQLQuery(hql2);
							if(tcompany2!=null&&tcompany2.size()>0){
								tc=tcompany2.get(0);
							}
					}
			    }
			    if(tc==null){
			    	 //先根据组织机构代码
				    if(entity.getORG_CODE()!=null&&!"".equals(entity.getORG_CODE())&&!"null".equals(entity.getORG_CODE())){
				    	    String hql3 = "from TCompany com where com.uuid is null and  com.orgCode='"+entity.getORG_CODE()+"' ";
				    	    System.out.println("uuid为空 组织机构代码 更新: " + hql3);
							List<TCompany> tcompany3 = persistenceDao.executeHQLQuery(hql3);
							if(tcompany3!=null&&tcompany3.size()>0){
								tc=tcompany3.get(0);
							}
					}
			    }
				if (tc!=null) {
					System.out.println("uuid: "+entity.getUUID());
					tc.setUuid(entity.getUUID());
					tc.setCompanyName(entity.getCOMPANY_NAME());
					tc.setBusinessRegNum(entity.getBUSINESS_REG_NUM());
					tc.setOrgCode(entity.getORG_CODE());
					tc.setRegAddress(entity.getREG_ADDRESS());
					tc.setEstablishmentDay(entity.getESTABLISHMENT_DAY());
					tc.setLegalPerson(entity.getLEGAL_PERSON());
					tc.setPrincipalPerson(entity.getPRINCIPAL_PERSON());// 主要负责人
					tc.setBusinessScope(entity.getBUSINESS_SCOPE()); // 经营范围
					tc.setNationalEconomicType1(entity.getNATIONAL_ECONOMIC_TYPE1());
					tc.setNationalEconomicType2(entity.getNATIONAL_ECONOMIC_TYPE2());
					tc.setEconomicType1(entity.getECONOMIC_TYPE1());
					tc.setEconomicType2(entity.getECONOMIC_TYPE2());
					tc.setManagementLevel1(entity.getMANAGEMENT_LEVEL1());
					tc.setManagementLevel2(entity.getMANAGEMENT_LEVEL2());
					tc.setIsSynchro(0);
					tc.setIsSYN(1l);
					tc.setZjId(entity.getZJ_ID());//设置质检id
					tc.setGsId(entity.getGS_ID());//设置工商id
					tc.setDeptCode(entity.getDEPT_CODE());
					persistenceDao.update(tc);

				} else { // 新增操作            (所有隐患中有用到的字段)
					tc = new TCompany();
					tc.setUuid(entity.getUUID());
					tc.setCompanyName(entity.getCOMPANY_NAME());
					tc.setBusinessRegNum(entity.getBUSINESS_REG_NUM());
					tc.setOrgCode(entity.getORG_CODE());
					tc.setRegAddress(entity.getREG_ADDRESS());
					tc.setEstablishmentDay(entity.getESTABLISHMENT_DAY());
					tc.setLegalPerson(entity.getLEGAL_PERSON());
					tc.setBusinessScope(entity.getBUSINESS_SCOPE()); // 经营范围
					tc.setNationalEconomicType1(entity.getNATIONAL_ECONOMIC_TYPE1());
					tc.setNationalEconomicType2(entity.getNATIONAL_ECONOMIC_TYPE2());
					tc.setEconomicType1(entity.getECONOMIC_TYPE1());
					tc.setEconomicType2(entity.getECONOMIC_TYPE2());
					tc.setManagementLevel1(entity.getMANAGEMENT_LEVEL1());
					tc.setManagementLevel2(entity.getMANAGEMENT_LEVEL2());
					tc.setFirstArea(entity.getFIRST_AREA());
					tc.setSecondArea(entity.getSECOND_AREA());
					tc.setThirdArea(entity.getTHIRD_AREA());
					tc.setFouthArea(entity.getFOUTH_AREA());
					tc.setFifthArea(entity.getFIFTH_AREA());
					tc.setBusinessAddress(entity.getBUSINESS_ADDRESS());
					tc.setZipCode(entity.getZIP_CODE());
					tc.setProductionScale(entity.getPRODUCTION_SCALE());
					tc.setPhone(entity.getPHONE());
					tc.setEnterprise(entity.getIS_ENTERPRISE());
					tc.setIsSynchro(0);
					tc.setCreateTime(new Date());
					tc.setModifyTime(new Date());
					tc.setIsSYN(1l);
					//添加如下几个字段，当法定代表人为空的时候，在判断主要负责人
					tc.setLegalTelephone(entity.getLEGAL_TELEPHONE());//添加法定代表人手机
					tc.setPrincipalPerson(entity.getPRINCIPAL_PERSON());// 主要负责人
					tc.setPrincipalMobile(entity.getPRINCIPAL_MOBILE());// 主要负责人手机号码
					tc.setPrincipalTelephone(entity.getPRINCIPAL_TELEPHONE());//主要负责人联系电话
					tc.setZjId(entity.getZJ_ID());//设置质检id
					tc.setGsId(entity.getGS_ID());//设置工商id
					tc.setDeptCode(entity.getDEPT_CODE());
					persistenceDao.save(tc);

				}

			}

		}

	}

	public PersistenceDao<TCompany> getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDao<TCompany> persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

}
