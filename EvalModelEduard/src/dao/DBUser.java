package dao;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import model.Comment;
import model.User;

public class DBUser extends DBManager implements UserServices {

	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createComment(int itemId, int userId, Comment comment) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Comment> getAllComments(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
