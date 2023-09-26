package org.biryukov.sharebill.web.controller;

import org.biryukov.sharebill.service.jdbcrepo.PersonJdbcRepository;
import org.biryukov.sharebill.service.jdbcrepo.ProductJdbcRepository;
import org.biryukov.sharebill.service.jdbcrepo.RoomJdbcRepository;
import org.biryukov.sharebill.service.jparepo.GlobalSessionJpaRepository;
import org.biryukov.sharebill.service.jparepo.PersonJpaRepository;
import org.biryukov.sharebill.service.jparepo.ProductJpaRepository;
import org.biryukov.sharebill.service.jparepo.RoomJpaRepository;
import org.biryukov.sharebill.service.jparepo.entity.Product;
import org.biryukov.sharebill.service.service.ProductService;
import org.biryukov.sharebill.service.service.SettlmentService;
import org.biryukov.sharebill.web.service.SimpBroadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class ProductApiController {

    @Autowired
    private SimpBroadcast simpBroadcast;

    @Autowired
    private ProductService productService;



    @Autowired
    private RoomJdbcRepository roomJdbcRepository;

    @Autowired
    private PersonJdbcRepository personJdbcRepository;

    @Autowired
    private ProductJdbcRepository productJdbcRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private GlobalSessionJpaRepository globalSessionJpaRepository;

    @Autowired
    private SettlmentService settlmentService;


    @GetMapping("/api/room/{roomId}/products")
    @ResponseBody
    //@Async
    public List<org.biryukov.sharebill.service.jparepo.entity.Product> products(@PathVariable UUID roomId) {
        return productJpaRepository.findByRoom(roomId);
    }


    @GetMapping("/api/product/{productId}")
    @ResponseBody
    public org.biryukov.sharebill.service.jparepo.entity.Product getProduct(@PathVariable UUID productId) {
        return productJpaRepository.findById(productId).get();

    }

    @PutMapping("/api/product/{productId}")
    @ResponseBody
    public void updatePlainProduct(@RequestBody Product product, @PathVariable UUID productId) {

        product.setId(productId);
        productService.updatePlainProduct(product);
        UUID roomId = productJdbcRepository.findRoomId(productId);

        simpBroadcast.castProdcuts(roomId);
    }

    @GetMapping("/api/room/{roomId}/settlement/calculate")
    @ResponseBody
    public org.biryukov.sharebill.service.service.pojo.Settlement calculateSettlement(@PathVariable UUID roomId) {
        return settlmentService.calculate(roomId);
    }

}
