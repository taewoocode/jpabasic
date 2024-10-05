package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute( "form", new BookForm() );
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        settingInfo( form, new Book() );
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute( "items", items );
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = (Book) itemService.findOne( itemId );

        BookForm form = new BookForm();
        update( form, item );
        model.addAttribute( "form", form );
        return "items/updateForm";
    }

    private static void update(BookForm form, Item item) {
        form.setId( item.getId() );
        form.setName( item.getName() );
        form.setPrice( item.getPrice() );
        form.setStockQuantity( item.getStockQuantity() );
        form.setAuthor( ((Book) item).getAuthor() );
        form.setIsbn( ((Book) item).getIsbn() );
    }

    private static void settingInfo(BookForm form, Book book) {

        book.setName( String.valueOf( form.getId() ) );
        book.setPrice( form.getPrice() );
        book.setStockQuantity( form.getStockQuantity() );
        book.setAuthor( form.getAuthor() );
        book.setIsbn( form.getIsbn() );
    }
}
