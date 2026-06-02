package hello.item_service.domain.web.basic;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "/basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item",findItem);
        return "/basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam Integer quantity, Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        Item savedItem = itemRepository.save(item);
        model.addAttribute("item",savedItem);
        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, Model model) {
        Item savedItem = itemRepository.save(item);
        model.addAttribute("item",savedItem); // 사실 @ModelAttribute에서 자동으로 Item 클래스명을 "item"으로 자동으로 model에 넣어준다
        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item) {
        Item savedItem = itemRepository.save(item);
        //model.addAttribute("item",savedItem); @ModelAttribute("item") : 여기서 자동으로 Model에 "item"이라는 이름으로 넣어준다
        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item) { // @ModelAttribute 생략 시 Item 클래스명을 "item"으로 자동으로 model에 넣어준다
        Item savedItem = itemRepository.save(item);
        //model.addAttribute("item",savedItem);
        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV5(Item item) {
        Item savedItem = itemRepository.save(item);
        return "redirect:/basic/items" + savedItem.getId(); //PRG
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; // /basic/itesm/1?status=true
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId ,Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item",findItem);
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    // 테스트용 데이터 생성
    @PostConstruct
    public void init() {
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 20);
        itemRepository.save(itemA);
        itemRepository.save(itemB);
    }
}
