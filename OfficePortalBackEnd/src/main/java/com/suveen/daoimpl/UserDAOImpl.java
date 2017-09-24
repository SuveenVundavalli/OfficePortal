package com.suveen.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.suveen.dao.UserDAO;
import com.suveen.model.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	private static Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean save(User user) {
		log.debug("---> Starting of save() in UserDAOImpl");

		try {
			user.setUserId(getMaxId());
			getCurrentSession().save(user);
			log.debug("---> save success in save() in UserDAOImpl");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("---> Exception arised in save() in UserDAOImpl");
			return false;
		}
		log.debug("---> Ending of save() in UserDAOImpl");
		return true;
	}

	public int getMaxId() {
		log.debug("---> Starting of getMaxId() in UserDAOImpl");
		int id = 1;
		try {
			id = ((Integer) getCurrentSession().createQuery("SELECT MAX(userId) from User").uniqueResult()) + 1;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("---> Exception arised in getMaxId() in UserDAOImpl");
		}
		log.debug("---> Ending of getMaxId() in UserDAOImpl");
		log.debug("---> Max id : "+id);

		return id;
		

	}

	public boolean update(User user) {
		log.debug("---> Starting of update() in UserDAOImpl");

		try {
			getCurrentSession().update(user);
			log.debug("---> update success in update() in UserDAOImpl");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("---> Exception arised in update() in UserDAOImpl");
			return false;
		}
		log.debug("---> Ending of update() in UserDAOImpl");
		return true;
	}

	public boolean delete(int userId) {
		log.debug("---> Starting of delete() in UserDAOImpl");

		try {
			getCurrentSession().delete(get(userId));
			log.debug("---> delete success in delete() in UserDAOImpl");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("---> Exception arised in delete() in UserDAOImpl");
			return false;
		}
		log.debug("---> Ending of delete() in UserDAOImpl");
		return true;
	}

	public User get(int userId) {
		log.debug("---> Starting of get() in UserDAOImpl");
		return (User) getCurrentSession().get(User.class, userId);
		
	}

	@SuppressWarnings("unchecked")
	public List<User> list() {
		log.debug("---> Starting of list() in UserDAOImpl");
		return (List<User>) getCurrentSession().createCriteria(User.class).list();
	}

	public User validate(String userName, String password) {
		log.debug("---> Starting of validate() in UserDAOImpl");
		return (User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("userName", userName)).add(Restrictions.eq("password", password)).uniqueResult();
	}

}
