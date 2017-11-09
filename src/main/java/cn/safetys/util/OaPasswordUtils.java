package cn.safetys.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.safetys.nbyhpc.domain.persistence.iface.CompanyPersistenceIface;
import com.safetys.nbyhpc.util.ConnectionFactory;

public class OaPasswordUtils {
	
	// 编码
	public static String encode(String s){
		
		try {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < s.length(); i++) {
				int c=s.charAt(i);
				String hexs = Integer.toHexString(c);
				sb.append(hexs);
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// 解码
	public static String decode(String s){
		try {
			StringBuffer sb = new StringBuffer();
			int len = s.length();
			for (int i = 0; i < len; i++) {
				if ((i % 2)==0){
					int a =Integer.parseInt(s.substring(i, i+2),16);
					char c = (char)a;
					sb.append(c);
				}
			} 
			
			String sql = "select  f.user_name  from fk_user f, fk_user_info u, fk_user_role_rel r where (f.user_name = '"+sb.toString()+"'  or  f.cas_user_name = '"+sb.toString()+"'   ) and f.id = u.id   and f.id = r.user_id   and f.is_deleted = 0   and  u.is_deleted=0";
			ConnectionFactory cFactory = new ConnectionFactory();
			PreparedStatement pState = cFactory.createConnection().prepareStatement(sql);
			ResultSet rs = pState.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}else{
				return "null";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "null";
	}

		
	
}
