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

public class TestGetComments {
	
DBAdmin dbadmin;
	
	@Before
	public void init(){
		dbadmin = new DBAdmin();
		dbadmin.connect();
		dbadmin.deleteAll(Admin.class);
		dbadmin.deleteAll(Gallery.class);
		dbadmin.deleteAll(Item.class);
		dbadmin.deleteAll(Comment.class);
		dbadmin.close();
	}
	
	@Test
	public void testGetComments(){
	
		Gallery gallery1 = getMockGallery("Arte");
		Admin admin1 = getMockAdmin("Edu");
		Item item1 = getMockItem("Flauta");
		Comment comment = getMockComment("Se conserva muy bien");
		
		dbadmin.connect();
			dbadmin.getEntityManager().getTransaction().begin();
				dbadmin.getEntityManager().persist(admin1);
				dbadmin.getEntityManager().persist(gallery1);
				dbadmin.getEntityManager().persist(item1);
				dbadmin.getEntityManager().persist(comment);
				admin1.getGalleries().add(gallery1);
				gallery1.setAdmin(admin1);
				gallery1.getItems().add(item1);
				item1.setGallery(gallery1);
				comment.setItem(item1);
				item1.getComments().add(comment);
				
			dbadmin.getEntityManager().getTransaction().commit();
		dbadmin.close();
	
		ArrayList<Comment> list1 = new ArrayList<Comment>(dbadmin.getComment(item1.getId()));
		
		Assert.assertEquals(1, list1.size());
		Assert.assertEquals("Se conserva muy bien", list1.get(0).getMessage());
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
	
	public Comment getMockComment(String message){
		
		Comment comment = new Comment();
		
		comment.setMessage(message);
		comment.setRate(5);
		
		return comment;
	}

}
