package testDBAdmin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;
import model.Item;

public class TestRemoveItem {

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
	public void testRemoveItem(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Admin admin1 = getMockAdmin("Edu");
		Item item1 = getMockItem("Flauta");
		Item item2 = getMockItem("Hacha");
		
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(gallery1);
				dbadmin.getEntityManager().persist(item1);
				dbadmin.getEntityManager().persist(item2);
				admin1.getGalleries().add(gallery1);
				gallery1.setAdmin(admin1);
				gallery1.getItems().add(item1);
				gallery1.getItems().add(item2);
				item1.setGallery(gallery1);
				item2.setGallery(gallery1);
				
			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
	
		// Primero comprobamos como gallery1 tiene 2 items
		dbadmin.connect();
		Gallery galleryRecovered = dbadmin.find(Gallery.class, gallery1.getId());
		dbadmin.close();
		Assert.assertEquals(2,galleryRecovered.getItems().size());
		
		// Borramos el item (recuperandolo de la base de datos) > Persistir
		dbadmin.connect();
		Item itemRecovered = dbadmin.find(Item.class, item1.getId());
		dbadmin.close();
		
		dbadmin.removeItem(itemRecovered);
		
		// Ahora comprobamos que solo le queda 1 item a gallery1
		dbadmin.connect();
		Gallery galleryRecovered2 = dbadmin.find(Gallery.class, gallery1.getId());
		dbadmin.close();
		
		Assert.assertEquals(1,galleryRecovered2.getItems().size());

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
