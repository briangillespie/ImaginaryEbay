package com.imaginaryebay.Repository;

import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.ItemPicture;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/28/16.
 */
public interface ItemRepository {

    public void save(Item item);

    public void update(Item item);

    public Item findByID(Long id);

    public Double findPriceByID(Long id);

    public Category findCategoryByID(Long id);

    public Timestamp findEndtimeByID(Long id);

    //public Userr findOwnerByID(Long id);

    public String findDescriptionByID(Long id);

    public Item updateItemByID(Long id, Item item);

    public List<Item> findAllItemsByCategory(String category);

    public List<Item> findAllItems();

    public List<ItemPicture> findAllItemPicturesForItem(Long id, String urlOnly);

    public ItemPicture createItemPictureForItem(Long id, MultipartFile file);

    public String createItemPicturesForItem(Long id, MultipartFile[] files);
}
