package hello.item_service.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when(when에서는 주로 테스트하고 싶은 기능을 적는다)
        Item savedItem = itemRepository.save(item);

        //then(then에서는 검증하고 싶은 것을 적는다)
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        Item savedItem1 = itemRepository.save(item1);
        Item savedItem2 = itemRepository.save(item2);

        //when(when에서는 주로 테스트하고 싶은 기능을 적는다)
        List<Item> allItems = itemRepository.findAll();


        //then(then에서는 검증하고 싶은 것을 적는다)
        assertThat(allItems.size()).isEqualTo(2);
        assertThat(allItems).contains(item1,item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("item1", 10000, 10);
        Item updateParam = itemRepository.save(item);
        Long itemId = updateParam.getId();

        //when
        Item updateItem = new Item("item2", 20000, 20);
        itemRepository.update(itemId,updateItem);

        //then
        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo("item2");
        assertThat(findItem.getPrice()).isEqualTo(20000);
        assertThat(findItem.getQuantity()).isEqualTo(20);
    }
}