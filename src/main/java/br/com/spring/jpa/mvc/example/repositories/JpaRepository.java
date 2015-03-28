package br.com.spring.jpa.mvc.example.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public abstract class JpaRepository<T, K> {
    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> forClazz;

    public JpaRepository() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        forClazz = (Class<T>) type.getActualTypeArguments()[0]; //pegar o .class do T
    }

    public T save(T entity) {
        return entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entity);
    }

    public T find(K key) {
        return entityManager.find(forClazz, key);
    }

    public T findReference(K key) {
        return entityManager.getReference(forClazz, key);
    }

    public void removeWithKey(K key) {
        T reference = findReference(key);
        remove(reference);
    }

    public List<T> findAll() {
        return entityManager.createQuery("FROM " + forClazz.getName()).getResultList();
    }
}
