package testDBAdmin;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Item;

public class TestCreateAdmin {

	DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		dbadmin.close();
	}
	
	@Test
	public void testCreateAdmin(){
	
		Admin admin1 = getMockAdmin("Edu");
		Admin admin2 = getMockAdmin("Fredy");
		Admin admin3 = getMockAdmin("Jose");
		
		dbadmin.createAdmin(admin1);
		dbadmin.createAdmin(admin2);
		dbadmin.createAdmin(admin3);
		
		dbadmin.connect();
		ArrayList<Admin> list = dbadmin.selectAll(Admin.class);
		dbadmin.close();
		
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("Edu", list.get(0).getName());
		Assert.assertEquals("Fredy", list.get(1).getName());
		Assert.assertEquals("Jose", list.get(2).getName());
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateAdminNull(){
	
		Admin admin1 = null;
		dbadmin.createAdmin(admin1);
	}
	
	public Admin getMockAdmin(String name){
		
		Admin admin = new Admin();
		admin.setName(name);
		
		return admin;

	}
	
}
