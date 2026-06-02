package hello.item_service.domain.item;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data // 지금은 학습용이니, 도메일 모델에 그냥 @Data를 사용하도록 하겠다.
//@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price;      // int를 사용하지 않은 이유 : price에 데이터가 없는 경우가 있어서 그걸 null로 표현하고 싶어서!
                                // -> 물론 int를 사용해도 기본값을 0으로 표현해서 처리하는 방법도 있지만, price가 0이면 상품 가격이 무료라서 0인지 아니면 데이터가 아직 할당이 안돼서 0인지 판별이 안된다.
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
