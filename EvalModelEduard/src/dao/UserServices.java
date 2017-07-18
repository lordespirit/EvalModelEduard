package dao;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import model.Comment;
import model.User;

public interface UserServices {

	/**
	 * Insereix un nou User a la base de dades
	 * @param user
	 */
	public void createUser(User user);
	
	/**
	 * Remou un User de la base de dades. No ha de remoure res més.
	 * @param user
	 */
	public void removeUser(User user);
	
	/**
	 * Remou un Comment, ha de remoure també les referencies al comentario
	 * @param comment
	 */
	public void removerComment(Comment comment);
	
	/**
	 * Insereix un nou Comment a la base de dades.
	 * @param itemId > Indica l'identificador de item. Si Item no existeix ha de llençar l'excepció 'EntityNotFoundException'
	 * @param userId > es l'id del User, si l'usuario no existeix ha de llençar l'excepció 'EntityNotFoundException'
	 * @param comment > El Comment ha inserir.
	 * @throws EntityNotFoundException
	 */
	public void createComment(int itemId,int userId,Comment comment) throws EntityNotFoundException;
	
	/**
	 * Obté la llista de Comment de l'usuari amb la id passada.
	 * @param userId
	 * @return
	 */
	public Set<Comment> getAllComments(int userId);
	
}
