package test;
//import org.junit.Test;
//import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
//
//import com.safetys.nbyhpc.facade.iface.CompanyFacadeIface;
//public class TestBean  extends AbstractDependencyInjectionSpringContextTests {
//
//	private CompanyFacadeIface companyFacadeIface;// 企业基本信息的业务接口
//	  
//	    public TestBean(){  
//	        // 启用直接对保护类型属性变量进行注入的机制  
//	        this.setPopulateProtectedVariables(true);  
//	    }  
//	  
//	    /** 
//	     * 设置bean xml路径，可以添加多个 
//	     */  
//	    protected String[] getConfigLocations() {  
//	        return new String[] {"spring/spring.xml"};  
//	    }  
//	  
//	    @Test  
//	    public void testBeanInjection() { 
//	    	//ApplicationContext testContext = new ClassPathXmlApplicationContext("spring/spring.xml");  
//	        assertTrue(companyFacadeIface != null);  
//	    }
//
//		/**
//		 * @return the companyFacadeIface
//		 */
//		public CompanyFacadeIface getCompanyFacadeIface() {
//			return companyFacadeIface;
//		}
//
//		/**
//		 * @param companyFacadeIface the companyFacadeIface to set
//		 */
//		public void setCompanyFacadeIface(CompanyFacadeIface companyFacadeIface) {
//			this.companyFacadeIface = companyFacadeIface;
//		}
//
//
//
//	
//}
