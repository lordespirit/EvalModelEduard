package testDBAdmin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public class TestUpdateItem {

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
	public void testUpdateItem(){
	
		Gallery gallery1 = getMockGallery("Arte");		
		Admin admin1 = getMockAdmin("Edu");
		Item item1 = getMockItem("Hacha");
		
		
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(gallery1);
				dbadmin.getEntityManager().persist(item1);
				gallery1.setAdmin(admin1);
				admin1.getGalleries().add(gallery1);
				
			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
		
		item1.setDescription("Descripcion modificada");
		item1.setName("Updated");
		
		dbadmin.updateItem(item1);
	
		dbadmin.connect();
		Item itemRecovered = dbadmin.find(Item.class, item1.getId());
		dbadmin.close();
		
		Assert.assertEquals("Updated", itemRecovered.getName());
		Assert.assertEquals("Descripcion modificada", itemRecovered.getDescription());

	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateItemNull(){
	
		Item item1 = null;
		dbadmin.updateItem(item1);
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
