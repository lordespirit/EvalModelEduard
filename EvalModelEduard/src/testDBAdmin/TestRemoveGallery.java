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

public class TestRemoveGallery {

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
	public void testRemoveGallery(){
	
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
			
			Gallery galleryRecovered = dbadmin.find(Gallery.class, gallery2.getId());
			
		dbadmin.close();
		

		dbadmin.removeGallery(galleryRecovered);

		
		dbadmin.connect();
			ArrayList<Gallery> listGallery = dbadmin.selectAll(Gallery.class);
			Admin adminRecovered = dbadmin.find(Admin.class, admin2.getId());
		dbadmin.close();

		
		Assert.assertEquals(1, listGallery.size());
		Assert.assertEquals(0, adminRecovered.getGalleries().size());

	}
	
	@Test(expected = RuntimeException.class)
	public void testRemoveGalleryNull(){
	
		Gallery gallery1 = null;

		dbadmin.removeGallery(gallery1);
		
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
