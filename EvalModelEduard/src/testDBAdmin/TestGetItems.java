package testDBAdmin;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public class TestGetItems {

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
	public void testGetItems(){
	
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
	
		ArrayList<Item> list1 = new ArrayList<Item>(dbadmin.getItems(gallery1.getId()));
	
		sort(list1);
		
		Assert.assertEquals(2, list1.size());
		Assert.assertEquals("Flauta", list1.get(0).getName());
		Assert.assertEquals("Hacha", list1.get(1).getName());

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
	
	
	private static void sort(ArrayList<Item> list1){
		
		list1.sort(new Comparator<Item>() {

			@Override
			public int compare(Item item1, Item item2) {
				return item1.getName().compareToIgnoreCase(item2.getName());
			}
		});
		
	}
	
}
