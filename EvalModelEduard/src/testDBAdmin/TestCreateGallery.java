package testDBAdmin;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;

public class TestCreateGallery {

DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		//dbadmin.deleteAll(Gallery.class);
		dbadmin.close();
	}
	
	@Test
	public void testCreateGalleryAllNotNull(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Gallery gallery2 = getMockGallery("Historia");
		
		Admin admin1 = getMockAdmin("Edu");
		Admin admin2 = getMockAdmin("Fredy");
		
		
		//dbadmin.createAdmin(admin1);
		//dbadmin.createAdmin(admin2);
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(admin2);
			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
		
		dbadmin.createGallery(admin1,gallery1);
		dbadmin.createGallery(admin2,gallery2);
		
		dbadmin.connect();
		ArrayList<Gallery> list = dbadmin.selectAll(Gallery.class);
		dbadmin.close();
		
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("Arte", list.get(0).getName());
		Assert.assertEquals("Edu", list.get(0).getAdmin().getName());
		Assert.assertEquals("Historia", list.get(1).getName());
		Assert.assertEquals("Fredy", list.get(1).getAdmin().getName());
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateGalleryAllNull(){
	
		Gallery gallery = null;
		Admin admin = null;

		dbadmin.createGallery(admin,gallery);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateGalleryAdminNull(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Admin admin = null;

		dbadmin.createGallery(admin,gallery1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateGalleryGalleryNull(){
	
		Gallery gallery1 = null;
		Admin admin1 = getMockAdmin("Edu");

		dbadmin.createGallery(admin1,gallery1);
		
	}
	
	public Gallery getMockGallery(String name){
		
		Gallery gallery = new Gallery();
		gallery.setDescription("Prueba 1234");
		gallery.setName(name);
		
		return gallery;

	}
	
	public Admin getMockAdmin(String name){
		
		Admin admin = new Admin();
		admin.setName(name);
		
		return admin;

	}
	
}
