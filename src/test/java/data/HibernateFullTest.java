package data;

import data.entities.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HibernateFullTest {

    @Test
    public void storeLoadMessage() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("snotes");

        try {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            List<Note> notes = em.createQuery("select n from Note n", Note.class).getResultList();
            em.getTransaction().commit();

            assertAll(
                    () -> assertEquals(1, notes.size()),
                    () -> assertEquals("Test 1", notes.get(0).getHeader())
            );

            em.close();

        } finally {
            emf.close();
        }
    }

}