package testDBAdmin;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public class TestRemoveAdmin {

DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		//dbadmin.deleteAll(Gallery.class);
		//dbadmin.deleteAll(Item.class);
		//dbadmin.deleteAll(Comment.class);
		dbadmin.close();
	}
	
	@Test
	public void testRemoveAdmin(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Gallery gallery2 = getMockGallery("Historia");
		
		Admin admin1 = getMockAdmin("Edu");
		Admin admin2 = getMockAdmin("Fredy");
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(admin2);
				dbadmin.getEntityManager().persist(gallery1);
				dbadmin.getEntityManager().persist(gallery2);
				
				gallery1.setAdmin(admin1);
				gallery2.setAdmin(admin2);
				
				admin1.getGalleries().add(gallery1); 
				admin2.getGalleries().add(gallery2);
				
			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
		
		dbadmin.removeAdmin(admin1);
		
		dbadmin.connect();
		ArrayList<Gallery> list = dbadmin.selectAll(Gallery.class);
		dbadmin.close();
		
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("Historia", list.get(0).getName());
		Assert.assertEquals("Fredy", list.get(0).getAdmin().getName());
	}
	
	@Test(expected = RuntimeException.class)
	public void testRemoveAdminNull(){
	
		Admin admin1 = null;

		dbadmin.removeAdmin(admin1);
		
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

