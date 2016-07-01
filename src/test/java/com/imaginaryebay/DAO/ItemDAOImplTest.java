package com.imaginaryebay.DAO;

import com.imaginaryebay.Controller.ItemControllerImpl;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Repository.ItemRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import static java.sql.Timestamp.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chloe on 7/1/16.
 */
public class ItemDAOImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;
    @Mock
    private Query queryAll;
    @Mock
    private Query queryClothes;
    @Mock
    private Query queryElectronics;

    @Mock
    private Item item1;
    @Mock
    private Item item2;
    @Mock
    private Item item3;

    private ItemDAOImpl impl;

    private List<Item> all;
    private List<Item> clothes;
    private List<Item> electronics;

    @Before
    public void setUp() throws Exception {


        MockitoAnnotations.initMocks(this);

        when(item1.getCategory()).thenReturn(Category.Clothes);
        when(item1.getPrice()).thenReturn(20.0);
        when(item1.getDescription()).thenReturn("Scarf");
        when(item1.getEndtime()).thenReturn(valueOf("2016-10-10 00:00:00"));

        when(item2.getCategory()).thenReturn(Category.Clothes);
        when(item2.getPrice()).thenReturn(200.0);
        when(item2.getDescription()).thenReturn("Expensive Scarf");
        when(item2.getEndtime()).thenReturn(valueOf("2016-11-5 06:00:00"));

        when(item3.getCategory()).thenReturn(Category.Electronics);
        when(item3.getPrice()).thenReturn(30.0);
        when(item3.getDescription()).thenReturn("Watch");
        when(item3.getEndtime()).thenReturn(valueOf("2016-9-2 11:10:10"));

        when(item1.getId()).thenReturn(1L);
        when(item2.getId()).thenReturn(2L);
        when(item3.getId()).thenReturn(10L);


        all = new ArrayList<>();
        clothes = new ArrayList<>();
        electronics = new ArrayList<>();

        all.add(item1);
        all.add(item2);
        all.add(item3);
        clothes.add(item1);
        clothes.add(item2);
        electronics.add(item3);

        when(entityManager.find(Item.class, 1L)).thenReturn(item1);
        when(entityManager.find(Item.class, 2L)).thenReturn(item2);
        when(entityManager.find(Item.class, 10L)).thenReturn(item3);

        when(entityManager.createQuery("select i from Item i where i.category = ?1 order by i.price")).thenReturn(query);
        when(query.setParameter(1, Category.Clothes)).thenReturn(queryClothes);
        when(query.setParameter(1, Category.Electronics)).thenReturn(queryElectronics);
        when(queryClothes.getResultList()).thenReturn(clothes);
        when(queryElectronics.getResultList()).thenReturn(electronics);

        when(entityManager.createQuery("select i from Item i order by i.price")).thenReturn(queryAll);
        when(queryAll.getResultList()).thenReturn(all);

        impl = new ItemDAOImpl();
        impl.setEntityManager(entityManager);

    }

    @Test
    public void persist() throws Exception {
        Item toSave = new Item();
        impl.persist(toSave);
        verify(entityManager).persist(toSave);
    }

    @Test
    public void merge() throws Exception {
        Item toMerge = new Item();
        impl.merge(toMerge);
        verify(entityManager).merge(toMerge);
    }

    @Test
    public void refresh() throws Exception {
        Item toRefresh = new Item();
        impl.refresh(toRefresh);
        verify(entityManager).refresh(toRefresh);
    }

    @Test
    public void find() throws Exception {
        assertEquals(impl.find(item1), item1);
        assertEquals(impl.find(item2), item2);
        assertEquals(impl.find(item3), item3);

    }

    @Test
    public void findByID() throws Exception {
        assertEquals(impl.findByID(1L), item1);
        assertEquals(impl.findByID(2L), item2);
        assertEquals(impl.findByID(10L), item3);
    }

    @Test
    public void findPriceByID() throws Exception {
        assertEquals(impl.findPriceByID(1L), new Double (20.0));
        assertEquals(impl.findPriceByID(2L), new Double (200.0));
        assertEquals(impl.findPriceByID(10L), new Double (30.0));
    }

    @Test
    public void findCategoryByID() throws Exception {
        assertEquals(impl.findCategoryByID(1L), Category.Clothes);
        assertEquals(impl.findCategoryByID(2L), Category.Clothes);
        assertEquals(impl.findCategoryByID(10L), Category.Electronics);
    }

    @Test
    public void findEndtimeByID() throws Exception {
        assertEquals(impl.findEndtimeByID(1L), valueOf("2016-10-10 00:00:00"));
        assertEquals(impl.findEndtimeByID(2L), valueOf("2016-11-5 06:00:00"));
        assertEquals(impl.findEndtimeByID(10L), valueOf("2016-9-2 11:10:10"));
    }

    @Test
    public void findDescriptionByID() throws Exception {
        assertEquals(impl.findDescriptionByID(1L), "Scarf");
        assertEquals(impl.findDescriptionByID(2L), "Expensive Scarf");
        assertEquals(impl.findDescriptionByID(10L), "Watch");
    }
/*
    @Test
    public void updateItemByID() throws Exception {

    }*/

    @Test
    public void findAllItemsByCategory() throws Exception {
        assertEquals(impl.findAllItemsByCategory(Category.Clothes), clothes);
        assertEquals(impl.findAllItemsByCategory(Category.Electronics), electronics);
    }

    @Test
    public void findAllItems() throws Exception {
        assertEquals(impl.findAllItems(), all);

    }
/*
    @Test
    public void returnAllItemPicturesForItemID() throws Exception {

    }

    @Test
    public void returnAllItemPictureURLsForItemID() throws Exception {

    }
*/
}