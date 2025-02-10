package data;

import data.entities.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HibernateFullTest {

    @Test
    public void saveData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("snotes");

        try {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            Note note = new Note();
            note.setDateCreation(LocalDate.now());
            note.setDateModification(LocalDate.now());
            note.setHeader("Test 1");
            note.setText("---");
            em.persist(note);

            em.getTransaction().commit();
            em.close();

        } finally {
            emf.close();
        }
    }


    @Test
    public void loadData() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("snotes");

        try {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            List<Note> notes = em.createQuery("select n from Note n", Note.class).getResultList();

            Note note = notes.get(0);
            assertEquals("Test 1", note.getHeader());
            note.setText(note.getText() + "+");
            note.setDateModification(LocalDate.now());

            em.getTransaction().commit();

            System.out.println("Size: " + notes.size());
            notes.forEach(System.out::println);

            em.close();

        } finally {
            emf.close();
        }
    }

}