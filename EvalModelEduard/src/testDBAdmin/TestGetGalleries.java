package testDBAdmin;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;

public class TestGetGalleries {

DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		dbadmin.deleteAll(Gallery.class);
		dbadmin.close();
	}
	
	@Test
	public void testGetGalleries(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Gallery gallery2 = getMockGallery("Historia");
		Gallery gallery3 = getMockGallery("Pintura");
		Gallery gallery4 = getMockGallery("Guerra");
		
		Admin admin1 = getMockAdmin("Edu");
		Admin admin2 = getMockAdmin("Fredy");
		
		
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(admin2);
				dbadmin.getEntityManager().persist(gallery1);
				dbadmin.getEntityManager().persist(gallery2);
				dbadmin.getEntityManager().persist(gallery3);
				dbadmin.getEntityManager().persist(gallery4);
				gallery1.setAdmin(admin1);
				gallery2.setAdmin(admin2);
				gallery3.setAdmin(admin2);
				gallery4.setAdmin(admin2);
				admin1.getGalleries().add(gallery1);
				
				admin2.getGalleries().add(gallery2);
				admin2.getGalleries().add(gallery3);
				admin2.getGalleries().add(gallery4);
				
			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
		
		Set<Gallery> list = dbadmin.getGalleries(admin2.getId());
		
		Assert.assertEquals(3, list.size());

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

