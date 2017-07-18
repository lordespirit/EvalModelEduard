package testDBAdmin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;

public class TestUpdateAdmin {

DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		dbadmin.close();
	}
	
	@Test
	public void testUpdateAdmin(){
	
		Admin admin1 = getMockAdmin("Edu");
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
			dbadmin.getEntityManager().getTransaction().commit();
		
		Admin adminRecovered  = dbadmin.find(Admin.class, admin1.getId());
		dbadmin.close();
		
		Assert.assertEquals("Edu", adminRecovered.getName());

		admin1.setName("Jose");
		dbadmin.updateAdmin(admin1);
		Assert.assertEquals("Jose", adminRecovered.getName());

	}
	
	public Admin getMockAdmin(String name){
		
		Admin admin = new Admin();
		admin.setName(name);
		
		return admin;

	}
	
}
