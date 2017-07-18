package dao;

import java.util.Set;

import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public class DBAdmin extends DBManager implements AdminServices {

	@Override
	public void createAdmin(Admin admin) {
		
		if(admin==null){
			throw new RuntimeException("No se pueden enviar objectos nulos");
		}else{
		
			connect();
			getEntityManager().getTransaction().begin();
			
			if(admin.getId()==0)
				getEntityManager().persist(admin);
			else
				 System.err.println("El administrador que intenta añadir ya existe");
				
			getEntityManager().getTransaction().commit();
			close();
		}
		
	}

	@Override
	public void removeAdmin(Admin admin) {
		
		if(admin==null){
			throw new RuntimeException("No se pueden enviar objectos nulos");
		}else{
		
			if(admin.getId()==0){
				throw new RuntimeException("La id del admin no es válida");
			}else{
			
				connect();
				getEntityManager().getTransaction().begin();
					getEntityManager().remove(getEntityManager().find(Admin.class, admin.getId()));
				getEntityManager().getTransaction().commit();
				close();
			}
		}
				
	}

	@Override
	public void updateAdmin(Admin admin) {

		if(admin==null){
			throw new RuntimeException("No se pueden enviar objectos con valor null");
		}else if(admin.getId()==0){
			throw new RuntimeException("El administrador no existe");
		}else{
				connect();
				Admin recovered = find(Admin.class, admin.getId());
	
				getEntityManager().getTransaction().begin();
					recovered.setName(admin.getName());
				getEntityManager().getTransaction().commit();
			
				close();
		}
		
	}

	@Override
	public Set<Gallery> getGalleries(int adminId) {
		connect();
			Admin adminRecovered = find(Admin.class, adminId);
		close();
		return adminRecovered.getGalleries();
	}

	@Override
	public void createGallery(Admin admin, Gallery gallery) {
		
		if(gallery==null||admin==null){
			throw new RuntimeException("No se pueden enviar objectos nulos");
		}else{
			connect();
			Admin adminRecovered = find(Admin.class, admin.getId());
			
			if(adminRecovered==null)
				throw new RuntimeException("El administrador enviado no se encuentra en la base de datos");
			else{		
						
				getEntityManager().getTransaction().begin();
						getEntityManager().persist(gallery);
						gallery.setAdmin(adminRecovered);
						adminRecovered.getGalleries().add(gallery);
					
				getEntityManager().getTransaction().commit();
			}	
			
			close();
			
		}
		
	}

	@Override
	public void removeGallery(Gallery gallery) {

		if(gallery==null){
			throw new RuntimeException("No se pueden enviar objectos nulos");
		}else{
		
			if(gallery.getId()==0){
				throw new RuntimeException("El item enviado no es válido");
			}else{
			
				connect();
					getEntityManager().getTransaction().begin();
						Admin adminToRemoveGallery = getEntityManager().find(Admin.class, gallery.getAdmin().getId());
						Gallery galleryToRemove = getEntityManager().find(Gallery.class, gallery.getId());
						adminToRemoveGallery.getGalleries().remove(galleryToRemove);
						//getEntityManager().remove(getEntityManager().find(Gallery.class, gallery.getId()));
					getEntityManager().getTransaction().commit();
				close();
			}
		}
				
		
	}

	@Override
	public void update(Gallery gallery) {

		if(gallery==null){
			throw new RuntimeException("No se pueden enviar objectos con valor null");
		}else if(gallery.getId()==0){
			throw new RuntimeException("La galería no existe");
		}else{
				connect();
				Gallery recovered = find(Gallery.class, gallery.getId());
	
				getEntityManager().getTransaction().begin();
					recovered.setName(gallery.getName());
					recovered.setDescription(gallery.getDescription());
				getEntityManager().getTransaction().commit();
			
				close();
		}
		
	}

	@Override
	public Set<Item> getItems(int galleryId) {
		connect();
			Gallery galleryRecovered = find(Gallery.class, galleryId);
		close();
		return galleryRecovered.getItems();
	}

	@Override
	public void createItem(Gallery gallery, Item item) {

		if(gallery==null||item==null){
			throw new RuntimeException("No se pueden enviar objectos nulos");
		}else{
			connect();
			Gallery galleryRecovered = find(Gallery.class, gallery.getId());
			
			if(galleryRecovered==null)
				throw new RuntimeException("La galería que pasada no existe");
			else{		
						
				getEntityManager().getTransaction().begin();
						getEntityManager().persist(item);
						item.setGallery(galleryRecovered);
						galleryRecovered.getItems().add(item);
				getEntityManager().getTransaction().commit();
			}	
			
			close();
			
		}
		
	}

	@Override
	public void removeItem(Item item) {

		if(item==null){
			throw new RuntimeException("No se pueden enviar objectos nulos");
		}else{
		
			if(item.getId()==0){
				throw new RuntimeException("El item enviado no es válido");
			}else{
			
				connect();
					getEntityManager().getTransaction().begin();
						Gallery galleryToRemoveItem = getEntityManager().find(Gallery.class, item.getGallery().getId());
						Item itemToRemove = getEntityManager().find(Item.class, item.getId());
						galleryToRemoveItem.getItems().remove(itemToRemove);
						getEntityManager().remove(getEntityManager().find(Item.class, item.getId()));
					getEntityManager().getTransaction().commit();
				close();
			}
		}
		
	}

	@Override
	public void updateItem(Item item) {
		
		if(item==null){
			throw new RuntimeException("No se pueden enviar objectos con valor null");
		}else if(item.getId()==0){
			throw new RuntimeException("El item no existe");
		}else{
				connect();
				Item recovered = find(Item.class, item.getId());
	
				getEntityManager().getTransaction().begin();
					recovered.setName(item.getName());
					recovered.setDescription(item.getDescription());
					recovered.setPrice(item.getPrice());
				getEntityManager().getTransaction().commit();
			
				close();
		}
		
	}

	@Override
	public Set<Comment> getComment(int itemId) {
		connect();
		Item itemRecovered = find(Item.class, itemId);
	close();
	return itemRecovered.getComments();
	}


}
