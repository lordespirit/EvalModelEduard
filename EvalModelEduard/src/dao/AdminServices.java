package dao;

import java.util.Set;

import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public interface AdminServices {

	/**
	 * Insereix un nou administrador 'Admin' a la base de dades
	 * @param admin
	 */
	public void createAdmin(Admin admin);
	
	/**
	 * Remou un administrador de la base de dades: Remou en cascade Item, Gallery i Comment
	 * @param admin
	 */
	public void removeAdmin(Admin admin);
	
	/**
	 * Actualitza les dades d'un Admin
	 * @param admin
	 */
	public void updateAdmin(Admin admin);
	
	/**
	 * Obté la llista de 'Galleries' de un administrador (passat la seva Id)
	 * @param adminId
	 * @return HashSet de Galleries
	 */
	public Set<Gallery> getGalleries(int adminId);
	
	/**
	 * Insereix un nou Gallery a la base de dades
	 * @param admin
	 * @param gallery
	 */
	public void createGallery(Admin admin,Gallery gallery);
	
	/**
	 * Remou un Gallery de la base de dades, remou en cascada els seus comments
	 * @param gallery
	 */
	public void removeGallery(Gallery gallery);
	
	/**
	 * Actualiza les dades d'un Gallery
	 * @param gallery
	 */
	public void update(Gallery gallery);
	
	/**
	 * Obté la llista de Items d'un Gallery passat com Id
	 * @param Gallery
	 * @return
	 */
	public Set<Item> getItems(int Gallery);
	
	/**
	 * Insereix un nou item a la base de dades
	 * @param gallery
	 * @param item
	 */
	public void createItem(Gallery gallery, Item item);
	
	/**
	 * Remou un item de la base de dades. Remou en cascade els seus comments
	 * @param item
	 */
	public void removeItem(Item item);
	
	/**
	 * Actualiza un Item de la base de dades
	 * @param item
	 */
	public void updateItem(Item item);
	
	/**
	 * Retorna una llista de Comment d'un Item passat com Id
	 * @param itemId
	 * @return
	 */
	public Set<Comment> getComment(int itemId);
	
	
}
