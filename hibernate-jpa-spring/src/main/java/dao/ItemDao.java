package dao;

import model.Item;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional //N.B. this would normally be on the service class
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Item findById(Long id) {
        return entityManager.find(Item.class, id);
    }

    public Item persist(Item item) {
        Session hibernateSession = entityManager.unwrap(Session.class);
        hibernateSession.save(item);
        return item;
    }
}
