package testDBAdmin;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;
import model.Item;

public class TestCreateItem {

DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		//dbadmin.deleteAll(Gallery.class);
		//dbadmin.deleteAll(Item.class);
		dbadmin.close();
	}
	
	@Test
	public void testCreateItemAllNotNull(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Gallery gallery2 = getMockGallery("Historia");
		Admin admin1 = getMockAdmin("Edu");
		Admin admin2 = getMockAdmin("Fredy");
		Item item1 = getMockItem("Flauta");
		Item item2 = getMockItem("Hacha");
		
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(admin2);
				dbadmin.getEntityManager().persist(gallery1);
				dbadmin.getEntityManager().persist(gallery2);
				admin1.getGalleries().add(gallery1);
				gallery1.setAdmin(admin1);
				admin2.getGalleries().add(gallery2);
				gallery2.setAdmin(admin2);

			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
		
		dbadmin.createItem(gallery1,item1);
		dbadmin.createItem(gallery2,item2);
		
		dbadmin.connect();
		ArrayList<Item> list = dbadmin.selectAll(Item.class);
		dbadmin.close();
		
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("Flauta", list.get(0).getName());
		Assert.assertEquals("Arte", list.get(0).getGallery().getName());
		Assert.assertEquals("Hacha", list.get(1).getName());
		Assert.assertEquals("Historia", list.get(1).getGallery().getName());
		
		
		dbadmin.connect();
		 Admin recoveredAdmin = dbadmin.find(Admin.class,admin1.getId());
		dbadmin.close();
		
		
		Assert.assertEquals(1,recoveredAdmin.getGalleries().size());
		
		
		Assert.assertEquals(1,recoveredAdmin.getGalleries().iterator().next().getItems().size());
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateItemAllNull(){
	
		Gallery gallery1 = null;
		Item item1 = null;

		dbadmin.createItem(gallery1,item1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateItemItemNull(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Item item1 = null;

		dbadmin.createItem(gallery1,item1);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateItemGalleryNull(){
	
		Gallery gallery1 = null;
		Item item1 = getMockItem("Hacha");
		dbadmin.createItem(gallery1,item1);
		
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
	
	public Item getMockItem(String name){
		
		Item item = new Item();
		
		item.setName(name);
		item.setDescription("Descripcion de item");
		item.setPrice(1.25f);
		
		return item;
		
	}
	
}
