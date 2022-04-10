package personal.assessment.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid Order order) {
        orderService.create(order);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }

    @GetMapping("/{date}")
    ResponseEntity<List<Order>> searchOrderByDate(@PathVariable String date) {
        List orders = orderService.findOrdersByDate(date);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

}
